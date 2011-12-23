package ua.org.tumakha.spd.template;

import java.util.List;

import org.junit.Test;

import ua.org.tumakha.spd.template.DocxTemplate.Template;
import ua.org.tumakha.spd.template.model.ActModel;

public class DocxTemplateTest {

	DocxTemplate docxTemplate = new DocxTemplate();

	@Test
	public void test() throws Exception {
		ActModel actModel = (ActModel) docxTemplate
				.getTemplateModel(Template.ACT);
		List<ActModel> listModel = null;
		docxTemplate.saveReports(listModel);
	}

}
