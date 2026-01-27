package com.pjw.tickgettinig.ticket.repository;

import com.pjw.tickgettinig.ticket.entity.Ticket;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  @Override
  Optional<Ticket> findById(Long id);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Ticket> findByProductIdAndSeatIdAndScheduleDateAndScheduleTime(Long productId, Long seatId, LocalDate scheduledDate,
      LocalTime scheduledTime);
}
