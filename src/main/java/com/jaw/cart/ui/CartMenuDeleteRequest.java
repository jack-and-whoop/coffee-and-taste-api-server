package com.jaw.cart.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartMenuDeleteRequest {

    private List<Long> cartMenuIds;
}
