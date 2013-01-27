package ua.org.tumakha.spdtool.smartgwt.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	/**
	 * GWT-RPC service asynchronous (client-side) interface
	 * 
	 * @see ua.org.tumakha.spdtool.UserService.GreetingService
	 */
	void findAll(AsyncCallback<List<Map<String, String>>> callback);

	/**
	 * Utility class to get the RPC Async interface from client-side code
	 */
	public static final class Util {
		private static UserServiceAsync instance;

		public static final UserServiceAsync getInstance() {
			if (instance == null) {
				instance = (UserServiceAsync) GWT.create(UserService.class);
			}
			return instance;
		}

		private Util() {
			// Utility class should not be instanciated
		}
	}
}
