package de.ads.timer.webservice.Notification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class GCMNotificationService {
	private static String url = "https://android.googleapis.com/gcm/send";
	
	private HttpURLConnection connection = null;
	
	public GCMNotificationService() {
		// TODO Auto-generated constructor stub
	}
	
	public void send(String message, List<String> tokens) {
		try {
			connection = (HttpURLConnection)new URL(url).openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "key=AIzaSyBujbZrWEmkXKeQuXSedfUaD7Qfyrmsg7k");
			connection.setDoOutput(true);
			
			new ObjectMapper().writeValue(connection.getOutputStream(), new GCMProtocol(message, tokens));
			
			InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder();
		    String line;
		    while((line = rd.readLine()) != null) {
		    	System.out.println(line);
		    }
		    rd.close();

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
