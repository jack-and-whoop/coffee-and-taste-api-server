package com.jaw.menu.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Lob
    private String description;

    private String imagePath;

    private boolean onSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_group_id")
    private MenuGroup menuGroup;

    @Builder
    public Menu(Long id, String name, String englishName, long price, String description, String imagePath, boolean onSale, MenuGroup menuGroup) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.price = BigDecimal.valueOf(price);
        this.description = description;
        this.imagePath = imagePath;
        this.onSale = onSale;
        this.menuGroup = menuGroup;
    }
}
