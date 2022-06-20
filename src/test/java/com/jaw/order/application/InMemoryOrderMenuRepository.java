package com.jaw.order.application;

import com.jaw.order.domain.OrderMenu;
import com.jaw.order.domain.OrderMenuRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderMenuRepository implements OrderMenuRepository {

    private final Map<Long, OrderMenu> orderMenus = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public OrderMenu save(OrderMenu orderMenu) {
        orderMenu.setId(++sequence);
        orderMenus.put(orderMenu.getId(), orderMenu);
        return orderMenu;
    }

    public void clear() {
        sequence = 0L;
        orderMenus.clear();
    }
}
