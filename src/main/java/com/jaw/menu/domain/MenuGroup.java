package com.jaw.menu.domain;

import com.jaw.category.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MenuGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_group_id")
    @Setter
    private Long id;

    @Column(nullable = false)
    private String name;

    private String englishName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public MenuGroup(String name) {
        this.name = name;
    }

    public MenuGroup(String name, String englishName) {
        this.name = name;
        this.englishName = englishName;
    }
}
