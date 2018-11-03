package ch.bfh.btx8101.examples;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

public class DatabaseAuth implements Authentication, Authorization {
	
	private DataSource ds;
	
	public DatabaseAuth() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MariaDB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Override
	public Employee authenticate(String username, String password) {
		Employee emp = null;
		try {
			Connection conn = ds.getConnection();
			PreparedStatement prep = conn.prepareStatement("SELECT employeenumber,title,firstname,lastname,sex,email FROM users WHERE username=? and password=?");
			prep.setString(1, username);
			prep.setString(2, toMD5(password));
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				LocalEmployee lemp = new LocalEmployee(username);
				lemp.setEmpNr(rs.getString(1));
				lemp.setTitle(rs.getString(2));
				lemp.setFirstname(rs.getString(3));
				lemp.setLastname(rs.getString(4));
				lemp.setSex(rs.getString(5));
				lemp.setEmail(rs.getString(6));
				emp = lemp;
			}
			rs.close();
			prep.close();
			conn.close();
		} catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}
	
	private String toMD5(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(value.getBytes("UTF-8"));
		return DatatypeConverter.printHexBinary(md.digest());
	}
	
	public Employee getEmployee(String username) {
		Employee emp = null;
		try {
			Connection conn = ds.getConnection();
			PreparedStatement prep = conn.prepareStatement("SELECT employeenumber,title,firstname,lastname,sex,email FROM users WHERE username=?");
			prep.setString(1, username);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				LocalEmployee lemp = new LocalEmployee(username);
				lemp.setEmpNr(rs.getString(1));
				lemp.setTitle(rs.getString(2));
				lemp.setFirstname(rs.getString(3));
				lemp.setLastname(rs.getString(4));
				lemp.setSex(rs.getString(5));
				lemp.setEmail(rs.getString(6));
				emp = lemp;
			}
			rs.close();
			prep.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emp;
	}



	@Override
	public boolean authorize(Employee emp) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean authorize(Employee emp, String role) {
		boolean authorized = false;
		try {
			Connection conn = ds.getConnection();
			PreparedStatement prep = conn.prepareStatement("SELECT id FROM roles WHERE username=? and role=?");
			prep.setString(1, emp.getUsername());
			prep.setString(2, role);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				authorized = true;
			}
			rs.close();
			prep.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authorized;
	}

}
