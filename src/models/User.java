package models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import models.exceptions.SecurityBreachException;
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

	public void setUsername(String username) throws SecurityBreachException {
		if (username.length() <= 0 || username.length() > 50 || !username.matches("^[a-zA-Z0-9_]*$"))
			throw new SecurityBreachException();
		this.info.put("username", username);
	}

	public void setPassword(String password) throws SecurityBreachException {
		if (password.length() <= 0 || password.length() > 50 || !password.matches("^\\S*$"))
			throw new SecurityBreachException();
		this.info.put("password", ShiroPasswordManager.encryptPassword(password));
	}

	public void setFirstname(String firstname) throws SecurityBreachException {
		if (firstname.length() <= 0 || firstname.length() > 50 || !firstname.matches("^[a-zA-Z0-9 ]*$"))
			throw new SecurityBreachException();
		this.info.put("firstname", firstname);
	}

	public void setLastname(String lastname) throws SecurityBreachException {
		if (lastname.length() <= 0 || lastname.length() > 50 || !lastname.matches("^[a-zA-Z0-9 ]*$"))
			throw new SecurityBreachException();
		this.info.put("lastname", lastname);
	}

	public void setBirthdateString(String birthdate) throws SecurityBreachException {
		long yBday = 0;
		try {
			yBday = LocalDate.parse(birthdate, DateTimeFormat.forPattern("yyyy-MM-dd")).getYear();
		} catch (Exception e) {
			throw new SecurityBreachException();
		}
		long yNow = LocalDate.now().getYear();
		long age = yNow - yBday;
		if (age < 19) throw new SecurityBreachException();
		this.info.put("birthdate", birthdate);
	}

	public void setAboutMe(String aboutMe) throws SecurityBreachException {
		if (aboutMe.length() > 1000) throw new SecurityBreachException();
		this.info.put("about_me", aboutMe);
	}

	public void setRole(String role) throws SecurityBreachException {
		if (!role.equals("admin") && !role.equals("user")) throw new SecurityBreachException();
		this.info.put("role", role);
	}

	public void setSalutation(String gender, String salutation) throws SecurityBreachException {
		if (!((gender.equals("Male") && salutationsMale.contains(salutation))
				|| (gender.equals("Female") && salutationsFemale.contains(salutation))))
			throw new SecurityBreachException();
		this.info.put("salutation", salutation);
	}

}
