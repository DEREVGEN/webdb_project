package com.project.webdb.lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LottoStoreRepositoryTest {

    @Autowired
    LottoStoreRepository storeRepository;


    @Test
    @DisplayName("LottoData-LottoStoreEntity 1대1 단방향 매핑 테스트")
    void repo_test() {
        LottoDataEntity lottoData = LottoDataEntity.builder()
                .id(1004)
                .num1(1)
                .num2(2)
                .num3(4)
                .num4(5)
                .num5(6)
                .num6(7)
                .bonus(111)
                .build();

        storeRepository.save(LottoStoreEntity.builder()
                .nickname("wow")
                .location("good")
                .auto(false)
                .lottoDataEntity(lottoData)
                .build());
    }

    @Test
    @DisplayName("findById 테스트")
    void repo_test2() {
        Optional<LottoStoreEntity> lottoStore = storeRepository.findById(2L);

        if (lottoStore.isPresent()) {
            System.out.println(lottoStore.get().toString());
        }
    }

    @Test
    @DisplayName("findByRound 테스트")
    void repo_test3() {
        List<LottoStoreEntity> lottoStoreEntities = storeRepository.findByRound(1041);

        if (lottoStoreEntities.size() == 0) {
            System.err.println("에러");
        } else {
            for (LottoStoreEntity lottoStoreEntity : lottoStoreEntities)
                System.out.println(lottoStoreEntity.toString());
        }
    }
}