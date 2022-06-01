package com.jaw.menu.ui;

import com.jaw.menu.domain.Menu;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuResponseDTO {

    private final Long id;
    private final String name;
    private final String englishName;
    private final BigDecimal price;
    private final boolean onSale;

    public MenuResponseDTO(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.englishName = menu.getEnglishName();
        this.price = menu.getPrice();
        this.onSale = menu.isOnSale();
    }
}
