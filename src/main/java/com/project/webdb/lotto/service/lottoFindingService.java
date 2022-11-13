package com.project.webdb.lotto.service;

import com.project.webdb.lotto.domain.LottoGeoEntity;
import com.project.webdb.lotto.domain.LottoGeoRepository;
import com.project.webdb.lotto.domain.LottoStoreEntity;
import com.project.webdb.lotto.domain.LottoStoreRepository;
import com.project.webdb.lotto.dto.lottoQueueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class lottoFindingService {
    @Autowired
    LottoStoreRepository lottoStoreRepo;
    @Autowired
    LottoGeoRepository lottoGeoRepo;

    public List<LottoStoreEntity> find(double lat, double lon) {
        List<LottoGeoEntity> entityList = lottoGeoRepo.findAll();

        PriorityQueue<lottoQueueDto> priorityQueue = new PriorityQueue<>(); // 우선순위 큐

        for (LottoGeoEntity entity : entityList) {
            double key = distance(lat, lon, entity.getLat(), entity.getLon());
            priorityQueue.add(new lottoQueueDto(key, entity.getId()));
        }

        List<LottoStoreEntity> storeEntities = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            if (!priorityQueue.isEmpty())
                storeEntities.add(lottoStoreRepo.findById(priorityQueue.poll().getStoreId()).get());// store엔티티는 geo엔티티 대한 강한 엔티티이기에, 반드시 존재 한다.

        return storeEntities;
    }

    double distance(double a_x, double a_y, double b_x, double b_y) {
        return Math.sqrt(Math.pow(a_x-b_x,2)+Math.pow(a_y-b_y,2));
    }
}
