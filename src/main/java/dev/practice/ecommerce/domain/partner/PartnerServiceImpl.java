package dev.practice.ecommerce.domain.partner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
	private final PartnerStore partnerStore;
	private final PartnerReader partnerReader;

	@Override
	@Transactional
	public PartnerInfo registerPartner(PartnerCommand command) {
		var initPartner = command.toEntity();
		Partner partner = partnerStore.store(initPartner);
		return new PartnerInfo(partner);
	}

	@Override
	@Transactional(readOnly = true)
	public PartnerInfo getPartnerInfo(String partnerToken) {
		Partner partner = partnerReader.getPartner(partnerToken);
		return new PartnerInfo(partner);
	}

	@Override
	@Transactional
	public PartnerInfo enablePartner(String partnerToken) {
		Partner partner = partnerReader.getPartner(partnerToken);
		partner.enable();
		return new PartnerInfo(partner);
	}

	@Override
	@Transactional
	public PartnerInfo disablePartner(String partnerToken) {
		Partner partner = partnerReader.getPartner(partnerToken);
		partner.disable();
		return new PartnerInfo(partner);
	}
}
