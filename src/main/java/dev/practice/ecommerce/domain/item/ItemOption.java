package dev.practice.ecommerce.domain.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import dev.practice.ecommerce.common.exception.InvalidParamException;
import dev.practice.ecommerce.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "item_options")
public class ItemOption extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item_option_group_id")
	private ItemOptionGroup itemOptionGroup;
	private Integer ordering;
	private String itemOptionName;
	private Long itemOptionPrice;

	@Builder
	public ItemOption(
		ItemOptionGroup itemOptionGroup,
		Integer ordering,
		String itemOptionName,
		Long itemOptionPrice
	) {
		if (itemOptionGroup == null)
			throw new InvalidParamException("ItemOption.itemOptionGroup");
		if (ordering == null)
			throw new InvalidParamException("ItemOption.ordering");
		if (StringUtils.isBlank(itemOptionName))
			throw new InvalidParamException("ItemOption.itemOptionName");
		if (itemOptionPrice == null)
			throw new InvalidParamException("ItemOption.itemOptionPrice");

		this.itemOptionGroup = itemOptionGroup;
		this.ordering = ordering;
		this.itemOptionName = itemOptionName;
		this.itemOptionPrice = itemOptionPrice;
	}
}
