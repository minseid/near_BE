package com.api.deso.repository;

import com.api.deso.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByEvent_id(Long Event_id);

}
