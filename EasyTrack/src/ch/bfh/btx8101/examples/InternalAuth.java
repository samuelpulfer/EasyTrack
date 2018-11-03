package ch.bfh.btx8101.examples;

import ch.bfh.btx8101.examples.Employee.Sex;

public class InternalAuth implements Authentication {

	@Override
	public Employee authenticate(String username, String password) {

		if(username.equals("user") && password.equals("user"))
			return new LocalEmployee("user", "Normal", "User", Sex.MALE);
		if(username.equals("admin") && password.equals("admin"))
			return new LocalEmployee("admin", "Super", "Administrator", Sex.FEMALE);
		// Carrier
		if(username.equalsIgnoreCase("carrier01") && password.equals("carrier01"))
			return new LocalEmployee("carrier01", "Max", "Muster01", Sex.MALE);
		if(username.equalsIgnoreCase("carrier02") && password.equals("carrier02"))
			return new LocalEmployee("carrier02", "Max", "Muster02", Sex.MALE);
		if(username.equalsIgnoreCase("carrier03") && password.equals("carrier03"))
			return new LocalEmployee("carrier03", "Max", "Muster03", Sex.MALE);
		if(username.equalsIgnoreCase("carrier04") && password.equals("carrier04"))
			return new LocalEmployee("carrier04", "Max", "Muster04", Sex.MALE);
		if(username.equalsIgnoreCase("carrier05") && password.equals("carrier05"))
			return new LocalEmployee("carrier05", "Max", "Muster05", Sex.MALE);
		return null;
	}

}
