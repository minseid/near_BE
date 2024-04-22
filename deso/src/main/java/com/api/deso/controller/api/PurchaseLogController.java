package com.api.deso.controller.api;

import com.api.deso.handler.service.PurchaseLogService;
import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.PurchaseLogApiRequest;
import com.api.deso.model.network.response.PurchaseLogApiReaponse;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/purchaseLog")
public class PurchaseLogController implements CrudInterface<PurchaseLogApiRequest, PurchaseLogApiReaponse> {
    private PurchaseLogService purchaseLogService;
    @Override
    @PostMapping("")
    public Header<PurchaseLogApiReaponse> create(Header<PurchaseLogApiRequest> request) {
        return purchaseLogService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<PurchaseLogApiReaponse> read(@PathVariable  Long id) {
        return purchaseLogService.read(id);
    }

    @Override
    public Header<PurchaseLogApiReaponse> update(Header<PurchaseLogApiRequest> request) {
        return purchaseLogService.update(request);
    }

    @Override
    @DeleteMapping("delete/{id}")
    public Header<PurchaseLogApiReaponse> delete(Long id) {
        return purchaseLogService.delete(id);
    }
}
