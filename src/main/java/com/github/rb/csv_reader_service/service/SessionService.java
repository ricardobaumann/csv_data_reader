/**
 * 
 */
package com.github.rb.csv_reader_service.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.rb.csv_reader_service.Config;
import com.github.rb.csv_reader_service.repository.DataRepository;

/**
 * Service controller to expose the queries through http
 * @author ricardobaumann
 *
 */
@RestController
public class SessionService {
	
	private DataRepository dataRepository;
	
	private Config config;
	
	@Autowired
	public SessionService(DataRepository dataRepository, Config config) {
		this.dataRepository = dataRepository;
		this.config = config;
	}

	@RequestMapping(path="cities",method=RequestMethod.GET)
	public Map<String, Object> getCitiesAmount(
			@RequestParam("from") String from, 
			@RequestParam("to") String to, 
			@RequestParam("city") String city) throws Exception {
		return dataRepository.fetchSessionsBy(getCityNameField(), 
				city, config.getDateFormat().parse(from), 
				config.getDateFormat().parse(to), config.getNamespace());
	}

	protected String getCityNameField() {
		return "city";
	}
		
	
	
}
