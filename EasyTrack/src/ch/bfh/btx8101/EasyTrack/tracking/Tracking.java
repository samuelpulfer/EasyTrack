package ch.bfh.btx8101.EasyTrack.tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tracking {
	
	public static class WifiNetwork implements Comparable<WifiNetwork> {
		public String BSSID;
		public String SSID;
		public int level;
		
		@Override
		public int compareTo(WifiNetwork wifi) {
			return wifi.level - level;
		}
	}
	
	public static String extract(String ssid, JSONObject jo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Currently connected to: " + jo.getString("CurrentBSSID") + "\n");
		JSONArray ja = jo.getJSONArray("WifiNetworks");
		Iterator<Object> iter = ja.iterator();
		ArrayList<WifiNetwork> wifis = new ArrayList<>();
		while(iter.hasNext()) {
			JSONObject elem = (JSONObject) iter.next();
			WifiNetwork wifi = new WifiNetwork();
			wifi.BSSID = elem.getString("BSSID");
			wifi.SSID = elem.getString("SSID");
			wifi.level = elem.getInt("level");
			wifis.add(wifi);
		}
		
		Collections.sort(wifis);
		for(WifiNetwork wifi:wifis) {
			if(wifi.SSID.equals(ssid))
				sb.append("SSID: " + wifi.SSID + " BSSID: " + wifi.BSSID + " Level: " + wifi.level + "\n");
		}
		
		
		return sb.toString();
	}
	
	public static ArrayList<WifiNetwork> extractList(String ssid, JSONObject jo) {
		JSONArray ja = jo.getJSONArray("WifiNetworks");
		Iterator<Object> iter = ja.iterator();
		ArrayList<WifiNetwork> wifis = new ArrayList<>();
		while(iter.hasNext()) {
			JSONObject elem = (JSONObject) iter.next();
			if(elem.getString("SSID").equals(ssid)) {
				WifiNetwork wifi = new WifiNetwork();
				wifi.BSSID = elem.getString("BSSID");
				wifi.SSID = elem.getString("SSID");
				wifi.level = elem.getInt("level");
				wifis.add(wifi);
			}
		}
		return wifis;
	}

}
