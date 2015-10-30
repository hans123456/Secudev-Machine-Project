package models;

import utilities.UUIDGenerator;

public class DonationDAO extends DAO {

	public String createDonation(DonationPack pack) {
		String uuid = UUIDGenerator.generateUUID();
		return uuid;
	}

}
