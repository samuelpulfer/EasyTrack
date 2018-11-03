package ch.bfh.btx8101.examples;

public class LocalEmployee implements Employee {
	
	private String empNr;
	private String title;
	private String firstname;
	private String lastname;
	private Sex sex;
	private String username;
	private String email;
	
	public LocalEmployee(String username) {
		this.username = username;
	}
	public LocalEmployee(String username, String firstname, String lastname, Sex sex) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.sex = sex;
	}
	
	public void setEmpNr(String empNr) {
		this.empNr = empNr;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setSex(String sex) {
		if(sex != null && sex.equalsIgnoreCase("M"))
			this.sex = Sex.MALE;
		else if(sex != null && sex.equalsIgnoreCase("F"))
			this.sex = Sex.FEMALE;
		else
			this.sex = Sex.APACHEHELICOPTER;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getEmployeeNumber() {
		// TODO Auto-generated method stub
		return empNr;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public String getFirstname() {
		// TODO Auto-generated method stub
		return firstname;
	}

	@Override
	public String getLastname() {
		// TODO Auto-generated method stub
		return lastname;
	}

	@Override
	public Sex getSex() {
		// TODO Auto-generated method stub
		return sex;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{EmployeeNumber: " + this.getEmployeeNumber() + ",");
		sb.append("Title: " + this.getTitle() + ",");
		sb.append("Firstname: " + this.getFirstname() + ",");
		sb.append("Lastname: " + this.getLastname() + ",");
		sb.append("Username: " + this.getUsername() + ",");
		sb.append("Email: " + this.getEmail() + "}");
		return sb.toString();
		
	}
	
	

}
