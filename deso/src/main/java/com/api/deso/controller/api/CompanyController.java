package com.api.deso.controller.api;


import com.api.deso.handler.service.CompanyService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.CompanyApiRequest;
import com.api.deso.model.network.response.CompanyApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/company")
public class CompanyController implements CrudInterface<CompanyApiRequest, CompanyApiResponse> {
    @Autowired
    private CompanyService companyService;
    @Override
    @PostMapping("")
    public Header<CompanyApiResponse> create(Header<CompanyApiRequest> request) {

        return companyService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<CompanyApiResponse> read(@PathVariable  Long id) {
        return companyService.read(id);
    }

    @Override
    public Header<CompanyApiResponse> update(Header<CompanyApiRequest> request) {
        return companyService.update(request);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public Header<CompanyApiResponse> delete(Long id) {
        return delete(id);
    }
}
