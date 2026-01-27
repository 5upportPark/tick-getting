package com.pjw.tickgettinig.ticket.service;

import com.pjw.tickgettinig.common.ErrorCode;
import com.pjw.tickgettinig.common.exceptions.BusinessException;
import com.pjw.tickgettinig.service.RedisService;
import com.pjw.tickgettinig.ticket.repository.TicketRepository;
import com.pjw.tickgettinig.ticket.dto.ReserveRequest;
import com.pjw.tickgettinig.ticket.dto.ReserveResponse;
import com.pjw.tickgettinig.ticket.entity.Ticket;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;
  private final RedisService redisService;

  private final RedisTemplate<String, String> redisTemplate;
  private static final String STOCK_KEY = "ticket:stock:";

  /**
   * 티켓 예매
   *
   * @param req
   * @return
   */
  @Transactional
  public ReserveResponse reserveTicket(ReserveRequest.Reservation req) {
    Optional<Ticket> ticketOptional = ticketRepository.findByProductIdAndSeatIdAndScheduleDateAndScheduleTime(req.getProductId(), req.getSeatId(),
        req.getScheduleDate(), req.getScheduleTime());

    if (ticketOptional.isEmpty()) {
      throw new BusinessException("티켓 정보가 존재하지 않습니다.", ErrorCode.RESOURCE_NOT_FOUND);
    }

    Ticket ticket = ticketOptional.get();
    if (!ticket.getStatus().equals("IDLE")) {
      throw new BusinessException("예매중인 티켓입니다.", ErrorCode.RESOURCE_NOT_FOUND);
    }

    String stockKey = STOCK_KEY + req.getProductId();
    if (!isReservationAvaliable(stockKey)) {
      throw new BusinessException(ErrorCode.RESERVED_TICKET);
    }

    Long userId = 0L; // FIXME
    ticket.reservation(userId);
    ticketRepository.save(ticket);
    ReserveResponse result = new ReserveResponse();
    result.setMessage("Success");
    redisService.decreaseLong(stockKey); // 티켓 잔여수량 차감
    return result;
  }

  public boolean isReservationAvaliable(String stockKey) {
    Long remaining = redisService.getLong(stockKey);
    return remaining != null && remaining >= 0;
  }
}
