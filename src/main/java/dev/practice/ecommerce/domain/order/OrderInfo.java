package dev.practice.ecommerce.domain.order;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class OrderInfo {

	@Getter
	@Builder
	@ToString
	public static class Main {
		private final Long orderId;
		private final String orderToken;
		private final Long userId;
		private final String payMethod;
		private final Long totalAmount;
		private final DeliveryInfo deliveryInfo;
		private final ZonedDateTime orderedAt;
		private final String status;
		private final String statusDescription;
		private final List<OrderItem> orderItemList;
	}

	@Getter
	@Builder
	@ToString
	public static class DeliveryInfo {
		private final String receiverName;
		private final String receiverPhone;
		private final String receiverZipcode;
		private final String receiverAddress;
		private final String receiverDetailAddress;
		private final String etcMessage;
	}

	@Getter
	@Builder
	@ToString
	public static class OrderItem {
		private final Integer orderCount;
		private final String partnerToken;
		private final Long itemId;
		private final String itemName;
		private final Long totalAmount;
		private final Long itemPrice;
		private final String deliveryStatus;
		private final String deliveryStatusDescription;
		private final List<OrderItemOptionGroup> orderItemOptionGroupList;
	}

	@Getter
	@Builder
	@ToString
	public static class OrderItemOptionGroup {
		private final Integer ordering;
		private final String itemOptionGroupName;
		private final List<OrderItemOption> orderItemOptionList;
	}

	@Getter
	@Builder
	@ToString
	public static class OrderItemOption {
		private final Integer ordering;
		private final String itemOptionName;
		private final Long itemOptionPrice;
	}
}
