package ch.bfh.btx8101.EasyTrack.tracking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONObject;

import ch.bfh.btx8101.EasyTrack.tracking.Tracking.WifiNetwork;

public class WhereAmI {
	
	DataSource ds = null;
	String carrier = null;
	
	public WhereAmI(String carrier) {
		this.carrier = carrier;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MariaDB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getRawLocation() {
		ArrayList<WifiNetwork> wifis = null;
		String ts = null;
		StringBuilder sb = new StringBuilder();
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT varia, created FROM location WHERE username=? ORDER BY created DESC LIMIT 1");
			ps.setString(1, carrier);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				wifis = Tracking.extractList("bfh", new JSONObject(rs.getString(1)));
				ts = rs.getTimestamp(2).toString();
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wifis != null) {
			sb.append("Timestamp: " + ts + "\n");
			for(WifiNetwork wifi:wifis) {
				sb.append("BSSID: " + wifi.BSSID + " Level: " + wifi.level + "\n");
			}
		}
		
		return sb.toString();
	}
	
	public JSONObject getLocation() {
		ArrayList<WifiNetwork> wifis = null;
		JSONObject jo = null;
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT varia, created FROM location WHERE username=? ORDER BY created DESC LIMIT 1");
			ps.setString(1, carrier);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				wifis = Tracking.extractList("bfh", new JSONObject(rs.getString(1)));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(wifis != null) {
			for(int i = 0;i<wifis.size();i++) {
				jo = parse(wifis.get(i));
				if(jo != null) {
					break;
				}
			}
			if(jo == null) {
				jo = new JSONObject();
				jo.put("x", "0.4");
				jo.put("y", "0.4");
				jo.put("image", "static/img/unknown.png");
			}
		} /*
		if(wifis != null && wifis.size() >= 3) {
			jo = parse(wifis.get(0));
			if(jo == null) {
				jo = parse(wifis.get(1));
			}
			if(jo == null) {
				jo = parse(wifis.get(2));
			}
			if(jo == null) {
				jo = new JSONObject();
				jo.put("x", "0.4");
				jo.put("y", "0.4");
				jo.put("image", "static/img/unknown.png");
			}
		} */
		else {
			jo = new JSONObject();
			jo.put("x", "0.4");
			jo.put("y", "0.4");
			jo.put("image", "static/img/unknown.png");
		}
		return jo;
	}
	
	
	private JSONObject parse(WifiNetwork wifi) {
		JSONObject jo = new JSONObject();
		jo.put("SOMETHING", "John");
		switch(wifi.BSSID) {
		// N521
		case "b4:de:31:86:4f:cf":
			jo.put("x", "0.13");
			jo.put("y", "0.3");
			jo.put("image", "static/img/500.png");
			break;
		// Vor N521
		case "b4:de:31:86:4f:c0":
			jo.put("x", "0.07");
			jo.put("y", "0.17");
			jo.put("image", "static/img/500.png");
			break;
		// Treppe 400
		case "b4:de:31:86:0f:00":
			jo.put("x", "0.1");
			jo.put("y", "0.19");
			jo.put("image", "static/img/400.png");
			break;
		// Treppe 300
		case "b4:de:31:9c:d7:cf":
			jo.put("x", "0.16");
			jo.put("y", "0.24");
			jo.put("image", "static/img/300.png");
			break;
		// Vor N321
		case "b4:de:31:68:d1:ef":
			jo.put("x", "0.22");
			jo.put("y", "0.30");
			jo.put("image", "static/img/300.png");
			break;	
		// Lounge
		case "b4:de:31:95:3d:af":
			jo.put("x", "0.33");
			jo.put("y", "0.33");
			jo.put("image", "static/img/300.png");
			break;
		// Vor Labor
		case "6c:b2:ae:2c:f0:8f":
			jo.put("x", "0.415");
			jo.put("y", "0.26");
			jo.put("image", "static/img/300.png");
			break;
		// N319
		case "b4:de:31:9c:d7:c0":
			jo.put("x", "0.05");
			jo.put("y", "0.26");
			jo.put("image", "static/img/300.png");
			break;		
			
			
		default:
			jo = null;
			break;
		}
		return jo;
	}

}
