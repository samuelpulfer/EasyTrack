package ch.bfh.btx8101.examples;

import ch.bfh.btx8101.examples.Employee.Sex;

public class InternalAuth implements Authentication {

	@Override
	public Employee authenticate(String username, String password) {
		Employee admin = new LocalEmployee("admin", "Super", "Administrator", Sex.FEMALE);
		Employee user = new LocalEmployee("user", "Normal", "User", Sex.MALE);
		if(username.equals("user") && password.equals("user"))
			return user;
			
		if(username.equals("admin") && password.equals("admin"))
			return admin;
			
		return null;
	}

}
