package models;

import models.exceptions.SecurityBreachException;

public class ManagementQueryFactory {

	public static ManagementQuery createQuery(int a, int b, int c) throws SecurityBreachException {
		ManagementQuery mq = new ManagementQuery(a, b, c);
		switch (a) {
			case 0:
				mq.setConcat("");
				break;
			case 1:
				mq.setConcat("and");
				break;
			case 2:
				mq.setConcat("or");
				break;
			default:
				throw new SecurityBreachException();
		}
		switch (b) {
			case 1:
				mq.setColumn("`username`");
				switch (c) {
					case 1:
						mq.setConstraints("= ?");
						mq.setExpectedNoOfParams(1);
						break;
					default:
						throw new SecurityBreachException();
				}
				break;
			case 2:
				mq.setColumn("DATE(`datetime_updated`)");
				switch (c) {
					case 1:
						mq.setConstraints(">= ?");
						mq.setExpectedNoOfParams(1);
						break;
					case 2:
						mq.setConstraints("<= ?");
						mq.setExpectedNoOfParams(1);
						break;
					case 3:
						mq.setConstraints("= ?");
						mq.setExpectedNoOfParams(1);
						break;
					case 4:
						mq.setConstraints("between ? and ?");
						mq.setExpectedNoOfParams(2);
						break;
					default:
						throw new SecurityBreachException();
				}
				break;
			default:
				throw new SecurityBreachException();
		}
		return mq;
	}
}
