package com.api.deso.redis;

import com.api.deso.handler.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class RedisCrudTest {
    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);
    @Autowired
    private RedisService redisService;

    @BeforeEach
    void shutDown() {
        redisService.setValues(KEY, VALUE, DURATION);
    }

    @AfterEach
    void tearDown() {
        redisService.deleteValues(KEY);
    }

    @Test
    @DisplayName("Redis에 데이터를 저장하면 정상적으로 조회된다.")
    void saveAndFindTest() throws Exception {
        // when
        String findValue = redisService.getValues(KEY);

        // then
        assertThat(VALUE).isEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 수정할 수 있다.")
    void updateTest() throws Exception {
        // given
        String updateValue = "updateValue";
        redisService.setValues(KEY, updateValue, DURATION);

        // when
        String findValue = redisService.getValues(KEY);

        // then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }


}