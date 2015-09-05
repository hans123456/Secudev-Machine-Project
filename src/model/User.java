package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import shiro.ShiroPasswordManager;

public class User {

	private HashMap<String, String> info;

	private List<String> salutationsMale = Arrays.asList(new String[] { "Mr", "Sir", "Senior", "Count" });
	private List<String> salutationsFemale = Arrays
			.asList(new String[] { "Miss", "Ms", "Mrs", "Madame", "Majesty", "Seniora" });

	public User() {
		this.info = new HashMap<String, String>();
	}

	// public void setInfo(String key, String value) {
	// this.info.put(key, value);
	// }

	public String getInfo(String key) {
		return this.info.get(key);
	}

	public void setUsername(String username) throws Exception {
		if (username.length() <= 0 || username.length() > 50 || !username.matches("^[a-zA-Z0-9_]*$"))
			throw new Exception();
		this.info.put("username", username);
	}

	public void setPassword(String password) throws Exception {
		if (password.length() <= 0 || password.length() > 50 || !password.matches("^\\S*$")) throw new Exception();
		this.info.put("password", ShiroPasswordManager.encryptPassword(password));
	}

	public void setFirstname(String firstname) throws Exception {
		if (firstname.length() <= 0 || firstname.length() > 50 || !firstname.matches("^[a-zA-Z0-9 ]*$"))
			throw new Exception();
		this.info.put("firstname", firstname);
	}

	public void setLastname(String lastname) throws Exception {
		if (lastname.length() <= 0 || lastname.length() > 50 || !lastname.matches("^[a-zA-Z0-9 ]*$"))
			throw new Exception();
		this.info.put("lastname", lastname);
	}

	public void setBirthdateString(String birthdate) throws Exception {
		long yBday = LocalDate.parse(birthdate, DateTimeFormat.forPattern("yyyy-MM-dd")).getYear();
		long yNow = LocalDate.now().getYear();
		long age = yNow - yBday;
		if (age < 19) throw new Exception();
		this.info.put("birthdate", birthdate);
	}

	public void setAboutMe(String aboutMe) throws Exception {
		if (aboutMe.length() > 1000) throw new Exception();
		this.info.put("about_me", aboutMe);
	}

	public void setRole(String role) throws Exception {
		if (!role.equals("admin") && !role.equals("user")) throw new Exception();
		this.info.put("role", role);
	}

	public void setGender(String gender) throws Exception {
		if (!(gender.equals("Male") || gender.equals("Female"))) throw new Exception();
		this.info.put("gender", gender);
	}

	public void setSalutation(String salutation) throws Exception {
		String gender = this.info.get("gender");
		if (!((gender.equals("Male") && salutationsMale.contains(salutation))
				|| (gender.equals("Female") && salutationsFemale.contains(salutation))))
			throw new Exception();
		this.info.put("salutation", salutation);
	}

}
