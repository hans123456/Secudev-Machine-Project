package models;

import java.util.HashMap;
import java.util.Map;

public class CartTransaction {

	Map<String, String> info;

	public CartTransaction() {
		info = new HashMap<String, String>();
	}

	public void setInfo(String key, String value) {
		this.info.put(key, value);
	}

	public String getInfo(String key) {
		return this.info.get(key);
	}

}
