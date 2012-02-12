package ua.org.tumakha.spdtool.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ua.org.tumakha.spdtool.entity.Group;
import ua.org.tumakha.spdtool.services.GroupService;

/**
 * @author Yuriy Tumakha
 */
@Component("groupFormatter")
public class GroupFormatter implements Formatter<Group> {

	@Autowired
	private GroupService groupService;

	@Override
	public String print(Group group, Locale locale) {
		return group.getName();
	}

	@Override
	public Group parse(String groupId, Locale locale) throws ParseException {
		return groupService.findGroup(Integer.valueOf(groupId));
	}

}
