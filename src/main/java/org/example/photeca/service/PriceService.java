package org.example.photeca.service;

import org.example.photeca.entity.PriceEntity;
import org.example.photeca.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public Map<String, Double> getPriceMap() {
        List<PriceEntity> priceEntities = priceRepository.findAll();
        return priceEntities.stream()
                .collect(Collectors.toMap(PriceEntity::getSymbol, PriceEntity::getPrice));
    }
}
