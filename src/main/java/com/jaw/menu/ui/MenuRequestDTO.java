package com.jaw.menu.ui;

import com.jaw.menu.domain.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDTO {

    private String name;
    private BigDecimal price;
    private boolean onSale;

    public MenuRequestDTO(String name, BigDecimal price, boolean onSale) {
        this.name = name;
        this.price = price;
        this.onSale = onSale;
    }

    public Menu toEntity() {
        return new Menu(name, price, onSale);
    }
}
