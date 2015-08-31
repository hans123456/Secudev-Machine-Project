package model;

import java.util.HashMap;

public class User {

	private HashMap<String, String> info;

	public User(String username) {
		this.info = new HashMap<String, String>();
		this.info.put("username", username);
	}

	public String getUsername() {
		return this.info.get("username");
	}

	public void setUsername(String username) {
		this.info.put("username", username);
	}

	public String getPassword() {
		return this.info.get("username");
	}

	public void setPassword(String password) {
		this.info.put("password", password);
	}

	public void setInfo(String key, String value) {
		this.info.put(key, value);
	}

	public String getInfo(String key) {
		return this.info.get(key);
	}

}
