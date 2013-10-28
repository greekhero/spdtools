package ua.org.tumakha.spdtool.web;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.tumakha.spdtool.entity.Report;
import ua.org.tumakha.spdtool.services.ReportService;
import ua.org.tumakha.spdtool.services.TemplateService;
import ua.org.tumakha.spdtool.template.XlsProcessor;
import ua.org.tumakha.spdtool.template.xls.row.ResultSetDynaExtractor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuriy Tumakha
 */
@RequestMapping(ReportsController.BASE_PATH)
@Controller
public class ReportsController {

	private static final Log log = LogFactory.getLog(ReportsController.class);

	protected static final String BASE_PATH = "/reports";

    @Autowired
    private ReportService reportService;

	@Autowired
	private TemplateService templateService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private XlsProcessor xlsProcessor = new XlsProcessor();

	@RequestMapping(method = RequestMethod.GET)
	public String initData() {
		return view("list");
	}

	@ModelAttribute("reports")
	public Collection<Report> populateReports() {
		return reportService.findAllReports();
	}

    @RequestMapping(value = "/generateReport", method = RequestMethod.GET)
    public void generateReport(HttpServletResponse response, @RequestParam(value = "reportId") Integer reportId) throws InvalidFormatException, IOException, SQLException {
        Report report = reportService.findReport(reportId);

        Map<String, Object> beans = new HashMap<String, Object>();
        RowSetDynaClass rowSet = jdbcTemplate.query(report.getSql(), new ResultSetDynaExtractor());
        beans.put("rs", rowSet.getRows());

        response.setHeader("Content-Disposition", "attachment; filename=" + report.getTemplate());
        xlsProcessor.generateReport(report, beans, response.getOutputStream());
    }

	private String view(String viewName) {
		return BASE_PATH.substring(1) + "/" + (viewName == null ? "" : viewName);
	}

	private String redirect(String viewName) {
		return String.format("redirect:%s/%s", BASE_PATH, viewName);
	}

}
