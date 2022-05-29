package com.jaw.menu.ui;

import com.jaw.menu.domain.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDTO {

    private String name;
    private String englishName;
    private BigDecimal price;
    private boolean onSale;

    @Builder
    public MenuRequestDTO(String name, String englishName, BigDecimal price, boolean onSale) {
        this.name = name;
        this.englishName = englishName;
        this.price = price;
        this.onSale = onSale;
    }

    public Menu toEntity() {
        return Menu.builder()
            .name(name)
            .englishName(englishName)
            .price(price)
            .onSale(onSale)
            .build();
    }
}
