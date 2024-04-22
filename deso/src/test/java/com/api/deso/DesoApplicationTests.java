package com.api.deso;

import com.api.deso.controller.api.SelectController;
import com.api.deso.handler.service.SelectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
class DesoApplicationTests {

    @Autowired
    private SelectController selectController;

    @Test
    public void contextLoads() {
        Pageable pageable = PageRequest.of(0,3);

    }



}


