package models;

import models.exceptions.SecurityBreachException;

public class DonationPack {

	private int amount;
	private int[] possiblePacks = { 5, 10, 20 };

	public DonationPack(String amount) throws SecurityBreachException {
		boolean found = false;
		int a = 0;
		try {
			a = Integer.parseInt(amount);
		} catch (Exception e) {
			throw new SecurityBreachException();
		}
		for (int i : possiblePacks) {
			if (i == a) found = true;
		}
		if (found == false) throw new SecurityBreachException();
		this.amount = a;
	}

	public int getAmount() {
		return amount;
	}

}
