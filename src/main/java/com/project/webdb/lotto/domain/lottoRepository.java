package com.project.webdb.lotto.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface lottoRepository extends JpaRepository<lottoEntity, Long> {
    List<lottoEntity> findByNickname(String nickname);
}