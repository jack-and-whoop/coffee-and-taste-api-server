package com.jaw.menu.ui;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuResponseDTO {

    private Long id;
    private String name;
    private String englishName;
    private BigDecimal price;
    private boolean onSale;

    @Builder
    public MenuResponseDTO(Long id, String name, String englishName, BigDecimal price, boolean onSale) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.price = price;
        this.onSale = onSale;
    }
}
