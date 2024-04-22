package com.api.deso.controller.api;

import com.api.deso.dto.EventDtoClass;
import com.api.deso.handler.service.TicketService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.TicketApiRequest;
import com.api.deso.model.network.response.TicketApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController implements CrudInterface<TicketApiRequest, TicketApiResponse> {
    @Autowired
    private TicketService ticketService;
    @Override
    @PostMapping("")
    public Header<TicketApiResponse> create(Header<TicketApiRequest> request) {
        log.info("create ticket: {}",request);
        return ticketService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<TicketApiResponse> read(@PathVariable(name="id")Long id) {
        log.info("read ticket: {}",id);
        return ticketService.read(id);
    }

    @Override
    public Header<TicketApiResponse> update(Header<TicketApiRequest> request) {
        return ticketService.update(request);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public Header<TicketApiResponse> delete(Long id) {
        return ticketService.delete(id);
    }


}
