package dev.practice.ecommerce.domain.order;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import dev.practice.ecommerce.domain.order.item.OrderItem;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderInfoMapper {

	@Mappings({
		@Mapping(source = "order.id", target = "orderId"),
		@Mapping(source = "order.deliveryFragment", target = "deliveryInfo"),
		@Mapping(expression = "java(order.calculateTotalAmount())", target = "totalAmount"),
		@Mapping(expression = "java(order.getStatus().name())", target = "status"),
		@Mapping(expression = "java(order.getStatus().getDescription())", target = "statusDescription")
	})
	OrderInfo.Main of(Order order, List<OrderItem> orderItemList);

	@Mappings({
		@Mapping(expression = "java(orderItem.getDeliveryStatus().name())", target = "deliveryStatus"),
		@Mapping(expression = "java(orderItem.getDeliveryStatus().getDescription())", target = "deliveryStatusDescription"),
		@Mapping(expression = "java(orderItem.calculateTotalAmount())", target = "totalAmount")
	})
	OrderInfo.OrderItem of(OrderItem orderItem);
}
