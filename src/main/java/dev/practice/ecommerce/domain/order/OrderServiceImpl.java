package dev.practice.ecommerce.domain.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.practice.ecommerce.domain.order.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
	private final OrderStore orderStore;
	private final OrderReader orderReader;
	private final OrderItemSeriesFactory orderItemSeriesFactory;
	private final PaymentProcessor paymentProcessor;
	private final OrderInfoMapper orderInfoMapper;

	@Override
	@Transactional
	public String registerOrder(OrderCommand.RegisterOrder registerOrder) {
		Order order = orderStore.store(registerOrder.toEntity());
		orderItemSeriesFactory.store(order, registerOrder);
		return order.getOrderToken();
	}

	@Override
	@Transactional
	public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
		var orderToken = paymentRequest.getOrderToken();
		var order = orderReader.getOrder(orderToken);
		paymentProcessor.pay(order, paymentRequest);
		order.orderComplete();
	}

	@Override
	public OrderInfo.Main retrieveOrder(String orderToken) {
		var order = orderReader.getOrder(orderToken);
		var orderItemList = order.getOrderItemList();
		return orderInfoMapper.of(order, orderItemList);
	}

	@Override
	@Transactional
	public void updateReceiver(String orderToken, OrderCommand.UpdateReceiverRequest receiverRequest) {
		var order = orderReader.getOrder(orderToken);
		var updateOrder = order.toEntity(order, receiverRequest);
		orderStore.store(updateOrder);
	}
}
