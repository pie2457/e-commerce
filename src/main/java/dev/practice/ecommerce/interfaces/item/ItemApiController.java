package dev.practice.ecommerce.interfaces.item;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.practice.ecommerce.application.item.ItemFacade;
import dev.practice.ecommerce.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemApiController {
	private final ItemFacade itemFacade;
	private final ItemDtoMapper itemDtoMapper;

	@PostMapping
	public CommonResponse registerItem(@RequestBody @Valid ItemDto.RegisterItemRequest request) {
		var partnerToken = request.getPartnerToken();
		var itemCommand = itemDtoMapper.of(request);
		var itemToken = itemFacade.registerItem(itemCommand, partnerToken);
		var response = itemDtoMapper.of(itemToken);
		return CommonResponse.success(response);
	}

	@PostMapping("/change-on-sales")
	public CommonResponse changeOnSaleItem(@RequestBody @Valid ItemDto.ChangeStatusItemRequest request) {
		var itemToken = request.getItemToken();
		itemFacade.changeOnSaleItem(itemToken);
		return CommonResponse.success("OK");
	}

	@PostMapping("/change-end-of-sales")
	public CommonResponse changeEndOfSaleItem(@RequestBody @Valid ItemDto.ChangeStatusItemRequest request) {
		var itemToken = request.getItemToken();
		itemFacade.changeEndOfSaleItem(itemToken);
		return CommonResponse.success("OK");
	}

	@GetMapping("/{itemToken}")
	public CommonResponse retrieve(@PathVariable String itemToken) {
		var itemInfo = itemFacade.retrieveItemInfo(itemToken);
		var response = itemDtoMapper.of(itemInfo);
		return CommonResponse.success(response);
	}
}
