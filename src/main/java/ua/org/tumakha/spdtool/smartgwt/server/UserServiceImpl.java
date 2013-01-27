package ua.org.tumakha.spdtool.smartgwt.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.smartgwt.client.UserService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	@Override
	public List<Map<String, String>> findAll() {

		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());

		ua.org.tumakha.spdtool.services.UserService userService = (ua.org.tumakha.spdtool.services.UserService) webApplicationContext
				.getBean("userService");

		List<User> users = userService.findAllUsers();
		List<Map<String, String>> records = new ArrayList<Map<String, String>>(
				users.size());
		for (User user : users) {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("userId", user.getUserId().toString());
			properties.put("pin", user.getPin().toString());
			properties.put("lastname", user.getLastname());
			properties.put("firstname", user.getFirstname());
			properties.put("firstnameEn", user.getFirstnameEn());
			properties.put("lastnameEn", user.getLastnameEn());
			properties.put("active", user.isActive() ? "Так" : "Ні");
			records.add(properties);
		}
		return records;
	}

}
