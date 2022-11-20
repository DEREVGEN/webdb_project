package com.project.webdb.lotto.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LottoStoreRepository extends JpaRepository<LottoStoreEntity, Long> {
    Optional<LottoStoreEntity> findById(Long id);

    @Query(value = "select * from lotto_store_entity i where i.round like %:round%", nativeQuery = true)
    List<LottoStoreEntity> findByRound(@Param("round") int id);
}
