package com.jaw.menu.ui;

import com.jaw.menu.domain.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuRequestDTO {

    private String name;
    private String englishName;
    private Long price;
    private boolean onSale;

    public MenuRequestDTO(String name, String englishName, Long price, boolean onSale) {
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
