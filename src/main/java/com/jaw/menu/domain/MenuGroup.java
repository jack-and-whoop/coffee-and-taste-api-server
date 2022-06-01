package com.jaw.menu.domain;

import com.jaw.category.domain.Category;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "menuGroup")
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public MenuGroup(String name, String englishName, Category category) {
        this.name = name;
        this.englishName = englishName;
        this.category = category;
    }
}
