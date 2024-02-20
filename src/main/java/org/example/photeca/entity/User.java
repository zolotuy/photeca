package org.example.photeca.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PriceEntity> prices;

    public void deleteAllPrices() {
        if (prices != null) {
            prices.clear();
        }
    }
}
