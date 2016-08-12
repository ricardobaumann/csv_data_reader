/**
 * 
 */
package com.github.rb.csv_reader_service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Configuration model for the application
 * @author ricardobaumann
 *
 */
@Component
@PropertySource("classpath:application.properties")
public class Config {

	@Value("${separator}")
	private String separator;
	
	@Value("${csv_file_path}")
	private String csvFilePath;
	
	@Value("${date_format}")
	private String dateFormatString;
	
	@Value("${namespace}")
	private String namespace;
	
	public String getDateFormatString() {
		return dateFormatString;
	}

	public void setDateFormatString(String dateFormatString) {
		this.dateFormatString = dateFormatString;
	}

	public SimpleDateFormat getDateFormat() {
		if (dateFormat==null) {
			dateFormat = new SimpleDateFormat(dateFormatString);
		}
		return dateFormat;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	private SimpleDateFormat dateFormat;

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getCsvFilePath() {
		return csvFilePath;
	}

	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}
	
	
	
}
