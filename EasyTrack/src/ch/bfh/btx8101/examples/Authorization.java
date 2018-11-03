package ch.bfh.btx8101.examples;

public interface Authorization {
	
	public boolean authorize(Employee emp);
	public boolean authorize(Employee emp, String role);

}
