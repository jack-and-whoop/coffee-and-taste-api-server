package com.jaw.menu.ui;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.jaw.menu.domain.Menu;

@Getter
@Setter
public class MenuResponseDTO {

    private Long id;
    private String name;
    private String englishName;
    private BigDecimal price;
    private boolean onSale;

    public MenuResponseDTO(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.englishName = menu.getEnglishName();
        this.price = menu.getPrice();
        this.onSale = menu.isOnSale();
    }
}
