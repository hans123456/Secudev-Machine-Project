package models;

import models.exceptions.SecurityBreachException;

public class ManagementQueryFactory {

	public static BoardQuery createQuery(int a, int b, int c) throws SecurityBreachException {
		BoardQuery bq = new BoardQuery(a, b, c);
		switch (a) {
			case 1:
				bq.setConcat("and");
				break;
			case 2:
				bq.setConcat("or");
				break;
			default:
				throw new SecurityBreachException();
		}
		switch (b) {
			case 1:
				bq.setColumn("`username`");
				switch (c) {
					case 1:
						bq.setConstraints("= ?");
						bq.setExpectedNoOfParams(1);
						break;
					default:
						throw new SecurityBreachException();
				}
				break;
			case 2:
				bq.setColumn("DATE(`datetime_updated`)");
				switch (c) {
					case 1:
						bq.setConstraints(">= ?");
						bq.setExpectedNoOfParams(1);
						break;
					case 2:
						bq.setConstraints("<= ?");
						bq.setExpectedNoOfParams(1);
						break;
					case 3:
						bq.setConstraints("= ?");
						bq.setExpectedNoOfParams(1);
						break;
					case 4:
						bq.setConstraints("between ? and ?");
						bq.setExpectedNoOfParams(2);
						break;
					default:
						throw new SecurityBreachException();
				}
				break;
			default:
				throw new SecurityBreachException();
		}
		return bq;
	}
}
