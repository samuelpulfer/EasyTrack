package ch.bfh.btx8101.examples;

public class EnvironmentAuthorization implements Authorization {

	@Override
	public boolean authorize(Employee emp) {
		// If
		if (emp.getUsername().equalsIgnoreCase("admin"))
			return true;
		return false;
	}

}
