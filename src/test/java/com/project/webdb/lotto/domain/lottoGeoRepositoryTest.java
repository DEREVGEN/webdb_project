package com.project.webdb.lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class lottoGeoRepositoryTest {

    @Autowired
    LottoDataRepository dataRepository;
    @Autowired
    LottoStoreRepository storeRepository;
    @Autowired
    LottoGeoRepository geoRepo;

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    @DisplayName("geo repo 저장 테스트")
    public void repo_test() {
        LottoDataEntity lottoData = LottoDataEntity.builder()
                .id(1005)
                .num1(1)
                .num2(2)
                .num3(4)
                .num4(5)
                .num5(6)
                .num6(7)
                .bonus(111)
                .build();
        dataRepository.save(lottoData);

        LottoStoreEntity storeEntity = LottoStoreEntity.builder()
                .nickname("wow")
                .location("good")
                .auto(false)
                .lottoDataEntity(lottoData)
                .build();
        storeRepository.save(storeEntity);

        em.merge(storeEntity);

        geoRepo.save(LottoGeoEntity.builder()
                .lat(14.2)
                .lon(15.2)
                .storeEntity(storeEntity)
                .build());
    }
}