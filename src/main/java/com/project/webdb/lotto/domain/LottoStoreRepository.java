package com.project.webdb.lotto.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LottoStoreRepository extends JpaRepository<LottoStoreEntity, Long> {
    Optional<LottoStoreEntity> findById(Long id);
}
