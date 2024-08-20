package hello.core.domain.order.service;

import hello.core.domain.order.entity.Order;

public interface OrderService {

    Order createOrder(Long memberId, String itemName, int itemPrice);
}
