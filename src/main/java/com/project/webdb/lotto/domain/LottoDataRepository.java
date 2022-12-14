package com.project.webdb.lotto.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LottoDataRepository extends JpaRepository<LottoDataEntity, Integer> {

}
