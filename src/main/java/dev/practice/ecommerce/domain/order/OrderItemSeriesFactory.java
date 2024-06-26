package dev.practice.ecommerce.domain.order;

import java.util.List;

import dev.practice.ecommerce.domain.order.item.OrderItem;

public interface OrderItemSeriesFactory {
	List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder);
}
