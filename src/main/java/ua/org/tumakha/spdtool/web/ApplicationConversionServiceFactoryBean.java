package ua.org.tumakha.spdtool.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ua.org.tumakha.spd.entity.User;

/**
 * A central place to register application converters and formatters.
 */
public class ApplicationConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public void installLabelConverters(FormatterRegistry registry) {
		registry.addConverter(new UserConverter());
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		installLabelConverters(getObject());
	}

	static class UserConverter implements Converter<User, String> {
		@Override
		public String convert(User user) {
			return new StringBuilder().append(user.getLastname()).append(" ")
					.append(user.getFirstname()).append("/")
					.append(user.getFirstnameEn()).append(" ")
					.append(user.getLastnameEn()).toString();
		}

	}

}
