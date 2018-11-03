package ch.bfh.btx8101.EasyTrack.Auth;

import ch.bfh.btx8101.examples.Authorization;
import ch.bfh.btx8101.examples.Employee;

public class CarrierAuthorization  implements Authorization {

	@Override
	public boolean authorize(Employee emp) {
		if (
				emp.getUsername().equalsIgnoreCase("carrier01") ||
				emp.getUsername().equalsIgnoreCase("carrier02") ||
				emp.getUsername().equalsIgnoreCase("carrier03") ||
				emp.getUsername().equalsIgnoreCase("carrier04") ||
				emp.getUsername().equalsIgnoreCase("carrier05")
				)
			return true;
		return false;
	}

	@Override
	public boolean authorize(Employee emp, String role) {
		// TODO Auto-generated method stub
		return authorize(emp);
	}

}
