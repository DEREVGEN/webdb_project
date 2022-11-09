package com.project.webdb.lotto.dto;

import com.project.webdb.lotto.domain.lottoEntity;
import lombok.*;

@AllArgsConstructor
@Getter
public class lottoQueueDto implements Comparable<lottoQueueDto>{

    double key;
    String nickname;

    @Override
    public int compareTo(lottoQueueDto target) {
        return this.key <= target.getKey()? -1 : 1;
    }
}
