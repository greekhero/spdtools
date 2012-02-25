package ua.org.tumakha.spdtool.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.entity.Kved2010;
import ua.org.tumakha.spdtool.services.Kved2010Service;

/**
 * @author Yuriy Tumakha
 */
@Component("kved2010Formatter")
public class Kved2010Formatter implements Formatter<Kved2010> {

	@Autowired
	private Kved2010Service kved2010Service;

	@Override
	public String print(Kved2010 kved, Locale locale) {
		return new StringBuilder().append(kved.getCode()).append(" ")
				.append(kved.getName()).toString();
	}

	@Override
	public Kved2010 parse(String kvedId, Locale locale) throws ParseException {
		return kved2010Service.findKved(Integer.valueOf(kvedId));
	}

}
