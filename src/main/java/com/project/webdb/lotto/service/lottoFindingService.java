package com.project.webdb.lotto.service;

import com.project.webdb.lotto.domain.lottoEntity;
import com.project.webdb.lotto.domain.lottoRepository;
import com.project.webdb.lotto.dto.lottoQueueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class lottoFindingService {
    @Autowired
    lottoRepository repo;

    public List<lottoEntity> find(double lat, double lon) {
        List<lottoEntity> entityList = repo.findAll();

        PriorityQueue<lottoQueueDto> priorityQueue = new PriorityQueue<>(); // 우선순위 큐

        for (lottoEntity entity : entityList) {
            double key = distance(lat, lon, entity.getLat(), entity.getLon());
            priorityQueue.add(new lottoQueueDto(key, entity.getNickname()));
        }

        List<lottoEntity> queueResult = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            if (!priorityQueue.isEmpty())
                queueResult.add(repo.findByNickname(priorityQueue.poll().getNickname()).get(0));

        return queueResult;
    }

    double distance(double a_x, double a_y, double b_x, double b_y) {
        return Math.sqrt(Math.pow(a_x-b_x,2)+Math.pow(a_y-b_y,2));
    }
}
