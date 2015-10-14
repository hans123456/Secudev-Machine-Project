package models;

import java.util.ArrayList;
import java.util.List;

public class BoardQuery {

	protected int a, b, c;
	protected String concat;
	protected String column;
	protected String constraints;
	protected int expectedNoOfParams;
	protected List<String> queryParams;

	public BoardQuery(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
		queryParams = new ArrayList<String>();
	}

	public void setConcat(String concat) {
		this.concat = concat;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public void setExpectedNoOfParams(int expectedNoOfParams) {
		this.expectedNoOfParams = expectedNoOfParams;
	}

	public int getExpectedNoOfParams() {
		return expectedNoOfParams;
	}

	public void addParams(String p) {
		queryParams.add(p);
	}

	public List<String> getQueryParams() {
		return queryParams;
	}

	@Override
	public String toString() {
		return " " + concat + " " + column + " " + constraints;
	}

	public String getURLParams() {
		StringBuilder sb = new StringBuilder();
		sb.append("&a=" + a);
		sb.append("&b=" + b);
		sb.append("&c=" + c);
		for (String i : queryParams)
			sb.append("&i=" + i);
		return sb.toString();
	}

}
