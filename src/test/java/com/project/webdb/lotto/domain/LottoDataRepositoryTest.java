package com.project.webdb.lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LottoDataRepositoryTest {

    @Autowired
    LottoDataRepository dataRepository;

    @Test
    void repo_test() {
        dataRepository.save(LottoDataEntity.builder()
                .num1(2)
                .num2(3)
                .num3(4)
                .num4(0)
                .num5(5)
                .num6(2)
                .id(1004)
                .build());
    }
}