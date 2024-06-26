package dev.practice.ecommerce.interfaces.partner;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import dev.practice.ecommerce.domain.partner.Partner;
import dev.practice.ecommerce.domain.partner.PartnerInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PartnerDto {

	@Getter
	@Setter
	@ToString
	public static class RegisterRequest {
		@NotEmpty(message = "partnerName 는 필수값입니다")
		private String partnerName;

		@NotEmpty(message = "businessNo 는 필수값입니다")
		private String businessNo;

		@Email(message = "email 형식에 맞아야 합니다")
		@NotEmpty(message = "email 는 필수값입니다")
		private String email;
	}

	@Getter
	@ToString
	public static class RegisterResponse {
		private final String partnerToken;
		private final String partnerName;
		private final String businessNo;
		private final String email;
		private final Partner.Status status;

		public RegisterResponse(PartnerInfo partnerInfo) {
			this.partnerToken = partnerInfo.getPartnerToken();
			this.partnerName = partnerInfo.getPartnerName();
			this.businessNo = partnerInfo.getBusinessNo();
			this.email = partnerInfo.getEmail();
			this.status = partnerInfo.getStatus();
		}
	}
}
