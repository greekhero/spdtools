package ua.org.tumakha.spdtool.smartgwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SpdSmart implements EntryPoint {

	// @Autowired
	// private UserService userService =
	// WebApplicationContextUtils.getWebApplicationContext(getServletContext());;

	@Override
	public void onModuleLoad() {

		Canvas canvas = new Canvas();

		final ListGrid countryGrid = new ListGrid();
		countryGrid.setWidth(500);
		countryGrid.setHeight(224);
		countryGrid.setShowAllRecords(true);

		ListGridField userIdField = new ListGridField("userId", "User ID");
		ListGridField pinField = new ListGridField("pin", "PIN");
		ListGridField lastnameField = new ListGridField("lastname", "Lastname");
		ListGridField firstnameField = new ListGridField("firstname",
				"Firstname");
		countryGrid.setFields(userIdField, pinField, lastnameField,
				firstnameField);
		countryGrid.setCanResizeFields(true);
		countryGrid.setData(getUserRecords());
		canvas.addChild(countryGrid);

		RootPanel.get("usersListContainer").add(canvas);
	}

	private ListGridRecord[] getUserRecords() {
		// List<User> users = userService.findAllUsers();
		// List<ListGridRecord> records = new ArrayList<ListGridRecord>(
		// users.size());
		// for (User user : users) {
		// ListGridRecord record = new ListGridRecord();
		// record.setAttribute("userId", user.getUserId());
		// record.setAttribute("pin", user.getPin());
		// record.setAttribute("lastname", user.getLastname());
		// record.setAttribute("firstname", user.getFirstname());
		// records.add(record);
		// }
		// ListGridRecord[] recordsArray = new ListGridRecord[users.size()];
		// records.toArray(recordsArray);
		// return recordsArray;
		return new ListGridRecord[] {};
	}

}