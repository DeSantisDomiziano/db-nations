package org.java.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		
		final String url = "jdbc:mysql://localhost:3306/db-nations";
		final String user = "root";
		final String password = "root";
		
		Scanner sc = null;
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			sc = new Scanner(System.in);
			
			System.out.println("digit a filter: ");
			String filterQuery = sc.nextLine();	
			
			final String query = " select c.name, c.country_id, c.region_id, c.country_code2 \n "
							   + " from countries c \n "
							   + " where c.name like ? "  
							   + " order by c.name ";
			
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%" + filterQuery + "%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				String name = rs.getString("name");
				int country_id = rs.getInt("country_id");
				int region_id = rs.getInt("region_id");
				String country_code2 = rs.getString("country_code2");
				
				System.out.println("name: " + name + "\n");
				System.out.println("region id: " + region_id);
				System.out.println("country: " + country_id);
				System.out.println("country code: " + country_code2);

				
				System.out.println("\n------------------------------\n");
			}
			
		} catch (Exception e) {
			
			System.out.println("Errore di connessione: " + e.getMessage());
		}finally {
			if(sc != null)
				sc.close();
		}
		
		System.out.println("\n----------------------------------\n");
		System.out.println("The end");
	}
}
