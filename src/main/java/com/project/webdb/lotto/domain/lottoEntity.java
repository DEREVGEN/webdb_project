package com.project.webdb.lotto.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
public class lottoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //회차수
    @Column
    @NonNull
    int round;

    //상호명
    @Column
    @NonNull
    String nickname;

    //로또 수동,자동
    @Column
    @NonNull
    boolean auto;

    //로또 당첨 소재지
    @Column
    @NonNull
    String location;

    //위도
    @Column
    @NonNull
    Double lat;

    //경도
    @Column
    @NonNull
    Double lon;

    @Builder
    public lottoEntity(int round, boolean auto, String nickname, String location, Double lat, Double lon) {
        this.round = round;
        this.auto = auto;
        this.nickname = nickname;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
    }
}
