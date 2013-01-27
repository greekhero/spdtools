package ua.org.tumakha.spdtool.smartgwt.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SpdSmart implements EntryPoint {

	private final UserServiceAsync userService = GWT.create(UserService.class);

	@Override
	public void onModuleLoad() {

		Canvas canvas = new Canvas();

		final ListGrid usersGrid = new ListGrid();
		usersGrid.setWidth(1000);
		usersGrid.setHeight(700);
		usersGrid.setShowAllRecords(true);

		ListGridField userIdField = new ListGridField("userId", "ID");
		ListGridField pinField = new ListGridField("pin", "ІПН");
		ListGridField lastnameField = new ListGridField("lastname", "Прізвище");
		ListGridField firstnameField = new ListGridField("firstname", "Ім'я");
		ListGridField lastnameEnField = new ListGridField("lastnameEn",
				"Lastname");
		ListGridField firstnameEnField = new ListGridField("firstnameEn",
				"Firstname");
		ListGridField activeField = new ListGridField("active", "Активний");
		usersGrid.setFields(userIdField, pinField, lastnameField,
				firstnameField, firstnameEnField, lastnameEnField, activeField);
		usersGrid.setCanResizeFields(true);
		canvas.addChild(usersGrid);

		userService.findAll(new AsyncCallback<List<Map<String, String>>>() {
			@Override
			public void onFailure(final Throwable caught) {
				SC.warn(caught.getClass().getName() + ": "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(List<Map<String, String>> result) {
				List<ListGridRecord> records = new ArrayList<ListGridRecord>(
						result.size());
				for (Map<String, String> properties : result) {
					ListGridRecord record = new ListGridRecord();
					for (String property : properties.keySet()) {
						record.setAttribute(property, properties.get(property));
					}
					records.add(record);
				}
				ListGridRecord[] recordsArray = new ListGridRecord[result
						.size()];
				records.toArray(recordsArray);
				usersGrid.setData(recordsArray);
			}
		});

		RootPanel.get("usersListContainer").add(canvas);
	}

}