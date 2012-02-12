package ua.org.tumakha.spdtool.web.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

/**
 * @author Yuriy Tumakha
 */
public class EntityFormatterRegistrar implements FormatterRegistrar {

	@Autowired
	private KvedFormatter kvedFormatter;

	@Override
	public void registerFormatters(FormatterRegistry registry) {
		registry.addFormatter(kvedFormatter);
	}

}
