package ua.org.tumakha.spdtool.web.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import ua.org.tumakha.spdtool.entity.Declaration;
import ua.org.tumakha.spdtool.entity.User;

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

	public Map<Integer, Declaration> processFile(BindingResult bindingResult)
			throws IOException {
		System.out.println(incomeFile.getOriginalFilename());
		System.out.println(incomeFile.getSize());

		if (incomeFile.isEmpty()) {
			return null;
		} else if (!incomeFile.getOriginalFilename().endsWith(".xls")
				&& !incomeFile.getOriginalFilename().endsWith(".xlsx")) {
			bindingResult.reject("error_declaration_upload_fileextension");
			return null;
		}
		Map<Integer, Declaration> declarations = new HashMap<Integer, Declaration>();
		InputStream inputStream = incomeFile.getInputStream();
		return declarations;
	}

	public Declaration createDeclaration(User user, Integer income, Integer tax) {
		Declaration declaration = new Declaration();
		declaration.setUser(user);
		declaration.setYear(getYear());
		declaration.setQuarter(getQuarter());
		declaration.setIncome(income);
		declaration.setTax(tax);
		return declaration;
	}

	public List<Declaration> getDeclarations() {
		return declarations;
	}

	public void setDeclarations(List<Declaration> declarations) {
		this.declarations = declarations;
	}

}
