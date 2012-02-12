package ua.org.tumakha.spdtool.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.entity.Kved;
import ua.org.tumakha.spdtool.services.KvedService;

/**
 * @author Yuriy Tumakha
 */
@Component("kvedFormatter")
public class KvedFormatter implements Formatter<Kved> {

	@Autowired
	private KvedService kvedService;

	@Override
	public String print(Kved kved, Locale locale) {
		return new StringBuilder().append(kved.getCode()).append(" ")
				.append(kved.getName()).toString();
	}

	@Override
	public Kved parse(String kvedId, Locale locale) throws ParseException {
		return kvedService.findKved(Integer.valueOf(kvedId));
	}

}
