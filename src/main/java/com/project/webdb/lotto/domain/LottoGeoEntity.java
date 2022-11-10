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
public class LottoGeoEntity {
    @Id
    @Column(name="store_id")
    Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "store_id")
    LottoStoreEntity storeEntity;

    @Column
    double lat;

    @Column
    double lon;

    @Builder
    public LottoGeoEntity(LottoStoreEntity storeEntity, double lat, double lon) {
        this.storeEntity = storeEntity;
        this.lat = lat;
        this.lon = lon;
    }
}
