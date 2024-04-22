package com.api.deso.controller.api;

import com.api.deso.handler.service.AlimApiLogicService;
import com.api.deso.handler.service.BookMarkApiLogicService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.BookMarkApiRequest;
import com.api.deso.model.network.request.BookMarkApiRequest;
import com.api.deso.model.network.response.BookMarkApiResponse;
import com.api.deso.model.network.response.BookMarkApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookMarkController implements CrudInterface<BookMarkApiRequest, BookMarkApiResponse> {

    private final BookMarkApiLogicService bookMarkApiLogicService;

    @Override
    @PostMapping("")
    public Header<BookMarkApiResponse> create(@RequestBody Header<BookMarkApiRequest> request) {
        return bookMarkApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<BookMarkApiResponse> read(@PathVariable Long id) {
        return bookMarkApiLogicService.read(id);
    }

    @Override
    public Header<BookMarkApiResponse> update(Header<BookMarkApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("delete/{id}")
    public Header<BookMarkApiResponse> delete(@PathVariable Long id) {
        return bookMarkApiLogicService.delete(id);
    }
}
