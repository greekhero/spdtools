package ua.org.tumakha.spdtool.web.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

/**
 * @author Yuriy Tumakha
 */
public class EntityFormatterRegistrar implements FormatterRegistrar {

	@Autowired
	private GroupFormatter groupFormatter;

	@Autowired
	private KvedFormatter kvedFormatter;

	@Autowired
	private Kved2010Formatter kved2010Formatter;

	@Autowired
	private PensionOrganizationFormatter pensionOrganizationFormatter;

	@Autowired
	private TaxOrganizationFormatter taxOrganizationFormatter;

	@Override
	public void registerFormatters(FormatterRegistry registry) {
		registry.addFormatter(groupFormatter);
		registry.addFormatter(kvedFormatter);
		registry.addFormatter(kved2010Formatter);
		registry.addFormatter(pensionOrganizationFormatter);
		registry.addFormatter(taxOrganizationFormatter);
	}

}
