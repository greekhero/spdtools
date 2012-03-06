package ua.org.tumakha.spdtool.template.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ua.org.tumakha.spdtool.entity.Kved2010;
import ua.org.tumakha.spdtool.entity.User;
import ua.org.tumakha.spdtool.template.DocxTemplate;

/**
 * @author Yuriy Tumakha
 */
public class Form11KvedModel extends TemplateModel {

	private static final int NAME_MAXLENGHT = 35;

	private static final int KVED_NAME_COLUMN_WIDTH = 20;

	private static final int MAX_ROWS = 28;

	/**
	 * Firstname.
	 */
	private String f;

	/**
	 * Lastname.
	 */
	private String l;

	/**
	 * Middlename.
	 */
	private String m;

	private String pin;

	/**
	 * Phone.
	 */
	private String p;

	/**
	 * Names column.
	 */
	private String n;

	/**
	 * Code list.
	 */
	List<String> c;

	/**
	 * On list.
	 */
	List<String> on;

	/**
	 * Off list.
	 */
	List<String> off;

	/**
	 * Item number.
	 */
	List<String> i;

	public Form11KvedModel() {
	}

	public Form11KvedModel(User user) {
		super(user);
		copyProperties(user);
	}

	private void copyProperties(User user) {
		p = "+380976884343";
		pin = getPIN();

		f = getFirstname().toUpperCase();
		f += StringUtils.repeat(" ", NAME_MAXLENGHT - f.length());
		m = getMiddlename().toUpperCase();
		m += StringUtils.repeat(" ", NAME_MAXLENGHT - m.length());
		l = getLastname().toUpperCase();
		l += StringUtils.repeat(" ", NAME_MAXLENGHT - l.length());

		n = " ";
		String emptyCode = "       ";
		List<String> codeList = new ArrayList<String>();
		on = new ArrayList<String>();
		off = new ArrayList<String>();
		i = new ArrayList<String>();
		codeList.add(emptyCode);
		on.add("");
		i.add("");
		List<Kved2010> kveds = user.getKveds2010();
		int row = 1;
		for (Kved2010 kved : kveds) {
			String name = kved.getName().toUpperCase();
			int len = name.length();
			int rows = (int) Math.ceil((float) len / KVED_NAME_COLUMN_WIDTH);
			n += name;
			int space = KVED_NAME_COLUMN_WIDTH - len % KVED_NAME_COLUMN_WIDTH;
			if (space != KVED_NAME_COLUMN_WIDTH) {
				n += StringUtils.repeat(" ", KVED_NAME_COLUMN_WIDTH - len
						% KVED_NAME_COLUMN_WIDTH);
			}
			codeList.add(kved.getCode() + emptyCode);
			on.add("x");
			i.add("" + row++);
			for (int k = 2; k <= rows; k++) {
				codeList.add(emptyCode);
				on.add("");
				i.add("");
			}

		}
		for (int k = codeList.size(); k <= MAX_ROWS; k++) {
			codeList.add(emptyCode);
			on.add("");
			i.add("");
		}
		for (int k = 0; k <= MAX_ROWS; k++) {
			codeList.add(emptyCode);
			off.add("");
		}
		c = codeList;
		n += StringUtils.repeat(" ", 561 - n.length());

	}

	@Override
	public String getOutputFilename(DocxTemplate template) {
		return String.format("/Form11/%s_%s_%s", getLastnameEn(),
				getFirstnameEn(), template.getFilename());
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<String> getC() {
		return c;
	}

	public void setC(List<String> c) {
		this.c = c;
	}

	public List<String> getOn() {
		return on;
	}

	public void setOn(List<String> on) {
		this.on = on;
	}

	public List<String> getOff() {
		return off;
	}

	public void setOff(List<String> off) {
		this.off = off;
	}

	public List<String> getI() {
		return i;
	}

	public void setI(List<String> i) {
		this.i = i;
	}

}
