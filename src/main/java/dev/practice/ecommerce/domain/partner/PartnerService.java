package dev.practice.ecommerce.domain.partner;

public interface PartnerService {
	PartnerInfo registerPartner(PartnerCommand command);

	PartnerInfo getPartnerInfo(String partnerToken);

	PartnerInfo enablePartner(String partnerToken);

	PartnerInfo disablePartner(String partnerToken);
}
