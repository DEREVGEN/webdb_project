package com.project.webdb.lotto.dto;

import com.project.webdb.lotto.domain.LottoGeoEntity;
import com.project.webdb.lotto.domain.LottoStoreEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class StoreGeoDto {
    double lon;
    double lat;
    String nickname;
    boolean auto;
    String location;

    public StoreGeoDto(LottoGeoEntity geoEntity, LottoStoreEntity storeEntity) {
        this.lon = geoEntity.getLon();
        this.lat = geoEntity.getLat();
        this.nickname = storeEntity.getNickname();
        this.location = storeEntity.getLocation();
        this.auto = storeEntity.isAuto();
    }
}
