package com.project.webdb.lotto.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Entity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class lottoRepositoryTest {

    @Autowired
    lottoRepository repo;

    @Test
    void repo_test() {
        List<lottoEntity> entityList = repo.findByNickname("소리창고");

        System.out.println(entityList.get(0).toString());
    }
}