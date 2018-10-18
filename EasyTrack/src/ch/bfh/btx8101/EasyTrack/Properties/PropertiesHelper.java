package ch.bfh.btx8101.EasyTrack.Properties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PropertiesHelper extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String propertieName;
	
	
	
	
	public PropertiesHelper(String propertie) throws InvalidPropertiesFormatException, SQLException, NamingException, IOException {
		this.propertieName = propertie;
		loadProperties();
	}
	
	
	
	private void loadProperties() throws SQLException, NamingException, InvalidPropertiesFormatException, IOException {
		Connection conn = getConn();
		PreparedStatement prep = conn.prepareStatement("SELECT property FROM properties WHERE name=?");
		prep.setString(1, propertieName);
		ResultSet rs = prep.executeQuery();
		InputStream stream;
		if (rs.next()) {
			String prop = rs.getString(1);
			stream = new ByteArrayInputStream(prop.getBytes(StandardCharsets.UTF_8));
			super.loadFromXML(stream);
			stream.close();
		} else {
			loadPropertiesFromClasspath();
		}
		rs.close();
		prep.close();
		conn.close();
	}
	private void loadPropertiesFromClasspath() throws InvalidPropertiesFormatException, IOException, SQLException, NamingException {
		try(final InputStream stream = this.getClass().getResourceAsStream(propertieName + ".properties")) {
			super.loadFromXML(stream);
		}
		storeToDB();
	}
	
	public void storeToDB() throws IOException, SQLException, NamingException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		super.storeToXML(out, propertieName, "UTF-8");
		String xmlProperties = new String(out.toByteArray(), StandardCharsets.UTF_8);
		Connection conn = getConn();
		PreparedStatement upsert;
		PreparedStatement prep = conn.prepareStatement("SELECT name FROM properties WHERE name=?");
		prep.setString(1, propertieName);
		ResultSet rs = prep.executeQuery();
		if (rs.next()) {
			upsert = conn.prepareStatement("UPDATE properties SET property=? WHERE name=?");
		} else {
			upsert = conn.prepareStatement("INSERT INTO properties (property,name) VALUES (?,?)");
		}
		rs.close();
		prep.close();
		upsert.setString(1, xmlProperties);
		upsert.setString(2, propertieName);
		upsert.execute();
		conn.commit();
		upsert.close();
		conn.close();
	}
	
	private Connection getConn() throws SQLException, NamingException {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MariaDB");
		Connection conn = ds.getConnection();
		return conn;
	}

}
