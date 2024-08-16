package com.shopping.store.service.order;

import com.shopping.store.dto.OrderDto;
import com.shopping.store.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);


}
