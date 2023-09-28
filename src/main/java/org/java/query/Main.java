package org.java.query;

import java.math.BigInteger;
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
				
				System.out.println("name: " + name);
				System.out.println("region id: " + region_id);
				System.out.println("country: " + country_id);
				System.out.println("country code: " + country_code2);
				
				System.out.println("\n------------------------------\n");
			}
			
			System.out.println("digit an id: ");
			int idSearch = Integer.valueOf(sc.nextLine());	
			
			String query2 = " select distinct c.name, l.`language`, cs.`year` , cs.population, cs.gdp \r\n "
					+ " from country_languages cl \r\n "
					+ " join languages l on cl.language_id = l.language_id \r\n "
					+ " join countries c on cl.country_id = c.country_id \r\n "
					+ " join country_stats cs on c.country_id = cs.country_id \r\n "
					+ " where c.name like ? "
					+ " and c.country_id = ? "
					+ " and cs.`year` > 2017";
			
			PreparedStatement ps2 = con.prepareStatement(query2);
			ps2.setString(1, "%" + filterQuery + "%");
			ps2.setInt(2, idSearch);
			ResultSet rs2 = ps2.executeQuery();
			
			while(rs2.next()) {
				
				
				String country = rs2.getString("name");
				String language = rs2.getString("language");
				int year = rs2.getInt(3);
				int population = rs2.getInt(4);
				long gdp = rs2.getLong(5);
				
				System.out.println("country: " + country);
				System.out.println("language: " + language);
				System.out.println("year: " + year);
				System.out.println("population: " + population);
				System.out.println("gdp: " + gdp);
				
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
