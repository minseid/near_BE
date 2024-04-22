package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Company;
import com.api.deso.model.entity.Event;
import com.api.deso.model.entity.Ticket;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.TicketApiRequest;
import com.api.deso.model.network.response.TicketApiResponse;
import com.api.deso.repository.CompanyRepository;
import com.api.deso.repository.EventRepository;
import com.api.deso.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements CrudInterface<TicketApiRequest, TicketApiResponse> {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EventRepository eventRepository;


    @Override
    public Header<TicketApiResponse> create(Header<TicketApiRequest> request) {
        TicketApiRequest ticketApiRequest = request.getData();
        Company company = companyRepository.findById(ticketApiRequest.getCompanyId()).orElse(null);
        if(company==null)
            return Header.ERROR("Wrong company");

        Event event = eventRepository.findById(ticketApiRequest.getEventId()).orElse(null);

        if(event==null)
            return Header.ERROR("Wrong Event");

        Ticket ticket = Ticket.builder()
                .code(ticketApiRequest.getCode())
                .date(ticketApiRequest.getDate())
                .startAt(ticketApiRequest.getStartAt())
                .company(company)
                .event(event)
                .build();

        Ticket newTicket = ticketRepository.save(ticket);
        return Header.OK(repose(newTicket));
    }

    @Override
    public Header<TicketApiResponse> read(Long id) {
        Ticket ticket =  ticketRepository.findById(id).orElse(null);
        if(ticket==null)
            return Header.ERROR("ticket dosen't exist");
        else
            return Header.OK(repose(ticket));
    }

    @Override
    public Header<TicketApiResponse> update(Header<TicketApiRequest> request) {
        TicketApiRequest ticketApiRequest = request.getData();
        Ticket ticket = ticketRepository.findById(ticketApiRequest.getId()).orElse(null);

        if(ticket==null)
            return Header.ERROR("ticket dosen't exist");

        Ticket ticket1 = Ticket.builder()
                .id(ticketApiRequest.getId())
                .code(ticketApiRequest.getCode())
                .date(ticketApiRequest.getDate())
                .startAt(ticketApiRequest.getStartAt())
                .company(ticket.getCompany())
                .event(ticket.getEvent())
                .build();

        ticketRepository.save(ticket1);
        return Header.OK(repose(ticket1));
    }

    @Override
    public Header<TicketApiResponse> delete(Long id) {
        ticketRepository.deleteById(id);
        return Header.OK();
    }


    private TicketApiResponse repose(Ticket ticket){

        TicketApiResponse ticketApiResponse =  TicketApiResponse .builder()
                .code(ticket.getCode())
                .date(ticket.getDate())
                .startAt(ticket.getStartAt())
                .companyId(ticket.getCompany().getId())
                .eventId(ticket.getEvent().getId())
                .build();

        return ticketApiResponse;
    }
}
