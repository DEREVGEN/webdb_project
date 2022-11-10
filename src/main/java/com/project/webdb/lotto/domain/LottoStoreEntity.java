package com.project.webdb.lotto.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class LottoStoreEntity {
    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String nickname;

    @Column
    boolean auto;

    @Column
    String location;

    @ManyToOne
    @JoinColumn(name="round")
    LottoDataEntity lottoDataEntity;

    @Builder
    public LottoStoreEntity(LottoDataEntity lottoDataEntity, String nickname, boolean auto, String location) {
        this.lottoDataEntity = lottoDataEntity;
        this.nickname = nickname;
        this.auto = auto;
        this.location = location;
    }
}
