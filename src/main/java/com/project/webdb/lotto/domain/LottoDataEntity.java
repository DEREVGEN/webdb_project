package com.project.webdb.lotto.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ToString
@Getter
@NoArgsConstructor
public class LottoDataEntity {
    @Id
    @Column(name="round")
    Integer id;

    @Column
    int num1, num2, num3, num4, num5, num6;

    @Column
    int bonus;

    @Builder
    public LottoDataEntity(Integer id, int num1, int num2, int num3, int num4, int num5, int num6, int bonus) {
        this.id = id;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.num5 = num5;
        this.num6 = num6;
        this.bonus = bonus;
    }
}
