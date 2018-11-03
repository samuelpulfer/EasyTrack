package ch.bfh.btx8101.examples;

public class EnvironmentAuthorization implements Authorization {

	@Override
	public boolean authorize(Employee emp) {
		// If
		if (emp != null && emp.getUsername().equalsIgnoreCase("admin"))
			return true;
		return false;
	}

	@Override
	public boolean authorize(Employee emp, String role) {
		return authorize(emp);
	}

}
