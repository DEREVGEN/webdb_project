package com.project.webdb.lotto.service;

import com.project.webdb.lotto.domain.lottoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class lottoFindingServiceTest {

    @Autowired
    lottoFindingService service;

    @Test
    void service_test() {
        List<lottoEntity> entityList = service.find(37.4532, 127.1365);

        for(lottoEntity entity : entityList) {
            System.out.println(entity.toString());
        }
    }
}