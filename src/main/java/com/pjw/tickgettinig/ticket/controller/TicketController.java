package com.pjw.tickgettinig.ticket.controller;

import com.pjw.tickgettinig.ticket.service.TicketService;
import com.pjw.tickgettinig.ticket.dto.ReserveRequest;
import com.pjw.tickgettinig.ticket.dto.ReserveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "티켓 예매 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ticket")
public class TicketController {

  private final TicketService ticketService;

  @Operation(summary = "티켓 예매")
  @PostMapping
  public ResponseEntity<ReserveResponse> reserveTicket(@RequestBody ReserveRequest.Reservation req) {
    return new ResponseEntity<>(ticketService.reserveTicket(req), HttpStatus.OK);
  }
}
