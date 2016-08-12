/**
 * 
 */
package com.github.rb.csv_reader_service.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.relique.jdbc.csv.CsvDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.rb.csv_reader_service.Config;

/**
 * Repository to access data
 * @author ricardobaumann
 *
 */
@Repository
public class DataRepository {
	
	@Autowired
	private Config config;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(("yyyy-MM-dd"));
	
	public Map<String, Object> fetchSessionsBy(String field, Object value,Date from, Date to, String namespace) throws Exception {
		
		Properties props = new java.util.Properties();
		props.put("separator", config.getSeparator());
		
		Class.forName("org.relique.jdbc.csv.CsvDriver");
		Map<String, Object> results = new LinkedHashMap<>();
		
		try (Connection conn = DriverManager.getConnection("jdbc:relique:csv:"+config.getCsvFilePath(),props);) {
		
		    Statement stmt = conn.createStatement();

		    String query = String.format("select date1,count(eid) as count, city "
		    		+ "from %s where date1 between '%s' and '%s' and %s like '%s'"
		    		+ "group by date1, city", 
		    		namespace,DATE_FORMAT.format(from),DATE_FORMAT.format(to),field,value+"%");
		    
		    ResultSet rs = stmt.executeQuery(query);

		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    while (rs.next()) {
		    	for (int pos=1;pos<=columnCount;pos++) {
		    		results.put(rsmd.getColumnName(pos), rs.getObject(pos));
		    	}
		    	
		    }
		    rs.close();  
		} 	
		return results;
	
	}
	
}
