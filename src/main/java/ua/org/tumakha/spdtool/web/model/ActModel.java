package ua.org.tumakha.spdtool.web.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ua.org.tumakha.spdtool.entity.Act;
import ua.org.tumakha.spdtool.reader.XlsMapping;
import ua.org.tumakha.spdtool.reader.XlsReader;
import ua.org.tumakha.spdtool.reader.model.ActReaderModel;

public class ActModel implements Serializable {

	private static final long serialVersionUID = 1308953488548550645L;

	@NotNull
	private Integer year;

	@NotNull
	private Integer month;

	private boolean generateContracts;

	private boolean generateActs;

	private transient MultipartFile actFile;

	private List<Act> acts;

	private Set<Integer> enabledUserIds;

	public ActModel() {
		enabledUserIds = new HashSet<Integer>();
		generateActs = true;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public boolean isGenerateContracts() {
		return generateContracts;
	}

	public void setGenerateContracts(boolean generateContracts) {
		this.generateContracts = generateContracts;
	}

	public boolean isGenerateActs() {
		return generateActs;
	}

	public void setGenerateActs(boolean generateActs) {
		this.generateActs = generateActs;
	}

	public void setActFile(MultipartFile actFile) {
		this.actFile = actFile;
	}

	public List<ActReaderModel> processFile(BindingResult bindingResult)
			throws IOException, InvalidFormatException, SAXException {
		if (actFile.isEmpty()) {
			return null;
		} else if (!actFile.getOriginalFilename().endsWith(".xls")
				&& !actFile.getOriginalFilename().endsWith(".xlsx")) {
			bindingResult.reject("error_act_upload_fileextension");
			return null;
		}
		XlsReader xlsReader = new XlsReader();
		List<ActReaderModel> acts = new ArrayList<ActReaderModel>();
		xlsReader.addBean("acts", acts);
		xlsReader.read(actFile.getInputStream(), XlsMapping.ACT);
		if (acts.size() == 0) {
			bindingResult.reject("error_act_file_no_records");
		}
		return acts;
	}

	public List<Act> getActs() {
		return acts;
	}

	public void setActs(List<Act> acts) {
		this.acts = acts;
	}

	public Set<Integer> getEnabledUserIds() {
		return enabledUserIds;
	}

	public void setEnabledUserIds(Set<Integer> enabledUserIds) {
		this.enabledUserIds = enabledUserIds;
	}

}
