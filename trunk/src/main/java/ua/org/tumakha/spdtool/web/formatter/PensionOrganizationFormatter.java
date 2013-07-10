package ua.org.tumakha.spdtool.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.entity.PensionOrganization;
import ua.org.tumakha.spdtool.services.PensionOrganizationService;

/**
 * @author Yuriy Tumakha
 */
@Component("pensionOrganizationFormatter")
public class PensionOrganizationFormatter implements Formatter<PensionOrganization> {

	@Autowired
	private PensionOrganizationService pensionOrganizationService;

	@Override
	public String print(PensionOrganization pensionOrganization, Locale locale) {
		StringBuilder builder = new StringBuilder().append(pensionOrganization.getName());
		if (pensionOrganization.getComment() != null) {
			builder.append(" (");
			builder.append(pensionOrganization.getComment());
			builder.append(")");
		}
		return builder.toString();
	}

	@Override
	public PensionOrganization parse(String organizationId, Locale locale) throws ParseException {
		return pensionOrganizationService.findPensionOrganization(Integer.valueOf(organizationId));
	}

}
