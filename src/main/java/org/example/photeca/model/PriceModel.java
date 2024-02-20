package org.example.photeca.model;

import lombok.Data;
import org.example.photeca.entity.PriceEntity;

@Data
public class PriceModel {
    String symbol;
    double price;

    public static PriceEntity convertToEntity(PriceModel priceModel) {
        if (priceModel == null) {
            return null;
        }
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setSymbol(priceModel.getSymbol());
        priceEntity.setPrice(priceModel.getPrice());
        return priceEntity;
    }
}
