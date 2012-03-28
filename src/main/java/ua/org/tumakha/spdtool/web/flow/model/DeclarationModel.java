package ua.org.tumakha.spdtool.web.flow.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class DeclarationModel implements Serializable {

	private static final long serialVersionUID = 1308953328548550645L;

	@Size(min = 1)
	private Set<Integer> groupIds;

	@NotNull
	private Integer year;

	@NotNull
	private Integer quarter;

	private transient MultipartFile incomeFile;

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

	public void processFile() {
		System.out.println(groupIds);
		// Do something with the MultipartFile here
		// incomeFile.getInputStream();
		System.out.println(incomeFile.getOriginalFilename());
		System.out.println(incomeFile.getSize());
	}

}
