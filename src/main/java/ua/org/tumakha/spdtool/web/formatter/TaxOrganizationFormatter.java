package ua.org.tumakha.spdtool.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.entity.TaxOrganization;
import ua.org.tumakha.spdtool.services.TaxOrganizationService;

/**
 * @author Yuriy Tumakha
 */
@Component("taxOrganizationFormatter")
public class TaxOrganizationFormatter implements Formatter<TaxOrganization> {

	@Autowired
	private TaxOrganizationService taxOrganizationService;

	@Override
	public String print(TaxOrganization taxOrganization, Locale locale) {
		StringBuilder builder = new StringBuilder().append(taxOrganization.getName());
		if (taxOrganization.getCode() != null) {
			builder.append(" (").append(taxOrganization.getCode()).append(")");
		}
		return builder.toString();
	}

	@Override
	public TaxOrganization parse(String organizationId, Locale locale) throws ParseException {
		return taxOrganizationService.findTaxOrganization(Integer.valueOf(organizationId));
	}

}
