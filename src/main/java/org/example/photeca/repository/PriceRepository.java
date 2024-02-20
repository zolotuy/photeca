package org.example.photeca.repository;

import org.example.photeca.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    public PriceEntity findBySymbol(String symbol);
}
