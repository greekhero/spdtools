package ua.org.tumakha.spdtool.web.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.reader.XlsMapping;
import ua.org.tumakha.spdtool.reader.XlsReader;
import ua.org.tumakha.spdtool.reader.model.DeclarationReaderModel;

public class DeclarationModel implements Serializable {

	private static final long serialVersionUID = 1308953328548550645L;

	@Size(min = 1)
	@NotNull
	private Set<Integer> groupIds;

	@NotNull
	private Integer year;

	@NotNull
	private Integer quarter;

	private transient MultipartFile incomeFile;

	private List<Declaration> declarations;

	public Set<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(Set<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	public void setIncomeFile(MultipartFile incomeFile) {
		this.incomeFile = incomeFile;
	}

	public List<DeclarationReaderModel> processFile(BindingResult bindingResult)
			throws IOException, InvalidFormatException, SAXException {
		if (incomeFile.isEmpty()) {
			return null;
		} else if (!incomeFile.getOriginalFilename().endsWith(".xls")
				&& !incomeFile.getOriginalFilename().endsWith(".xlsx")) {
			bindingResult.reject("error_declaration_upload_fileextension");
			return null;
		}
		XlsReader xlsReader = new XlsReader();
		List<DeclarationReaderModel> declarations = new ArrayList<DeclarationReaderModel>();
		xlsReader.addBean("declarations", declarations);
		xlsReader.read(incomeFile.getInputStream(), XlsMapping.DECLARATION);
		if (declarations.size() == 0) {
			bindingResult.reject("error_declaration_file_no_records");
		}
		return declarations;
	}

	public List<Declaration> getDeclarations() {
		return declarations;
	}

	public void setDeclarations(List<Declaration> declarations) {
		this.declarations = declarations;
	}

}
