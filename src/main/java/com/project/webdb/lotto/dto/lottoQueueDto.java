package com.project.webdb.lotto.dto;

import lombok.*;

@AllArgsConstructor
@Getter
public class lottoQueueDto implements Comparable<lottoQueueDto>{

    double key;
    Long storeId;

    @Override
    public int compareTo(lottoQueueDto target) {
        return this.key <= target.getKey()? -1 : 1;
    }
}
