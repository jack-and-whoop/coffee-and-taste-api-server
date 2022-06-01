package com.jaw.menu.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    @Setter
    private Long id;

    @Column(nullable = false)
    private String name;

    private String englishName;

    @Column(nullable = false)
    private BigDecimal price;

    private boolean onSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_group_id")
    private MenuGroup menuGroup;

    @Builder
    public Menu(String name, String englishName, long price, boolean onSale, MenuGroup menuGroup) {
        this.name = name;
        this.englishName = englishName;
        this.price = BigDecimal.valueOf(price);
        this.onSale = onSale;
        this.menuGroup = menuGroup;
    }
}
