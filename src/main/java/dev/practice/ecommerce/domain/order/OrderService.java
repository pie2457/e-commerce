package dev.practice.ecommerce.domain.order;

public interface OrderService {
	String registerOrder(OrderCommand.RegisterOrder registerOrder);
}