package models;

import java.util.HashMap;
import java.util.Map;

import models.exceptions.SecurityBreachException;

public class CartItem {

	private Map<String, String> info;

	public CartItem() {
		info = new HashMap<String, String>();
	}

	public void setItemIdString(String itemId) throws SecurityBreachException {
		try {
			Integer.parseInt(itemId);
			this.info.put("itemId", itemId);
		} catch (Exception e) {
			throw new SecurityBreachException();
		}
	}

	public void setQuantityString(String quantity) throws SecurityBreachException {
		try {
			Integer.parseInt(quantity);
			this.info.put("quantity", quantity);
		} catch (Exception e) {
			throw new SecurityBreachException();
		}
	}

	public void setPriceString(String price) throws SecurityBreachException {
		try {
			Integer.parseInt(price);
			this.info.put("price", price);
		} catch (Exception e) {
			throw new SecurityBreachException();
		}
	}

	public void setInfo(String key, String value) {
		this.info.put(key, value);
	}

	public String getInfo(String key) {
		return this.info.get(key);
	}

}
