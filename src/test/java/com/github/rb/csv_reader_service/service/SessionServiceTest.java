/**
 * 
 */
package com.github.rb.csv_reader_service.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.rb.csv_reader_service.Application;
import com.github.rb.csv_reader_service.Config;
import com.github.rb.csv_reader_service.helper.JsonHelper;
import com.github.rb.csv_reader_service.repository.DataRepository;

/**
 * @author ricardobaumann
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, TestContext.class})
@WebIntegrationTest
public class SessionServiceTest {

	private DataRepository dataRepository;
	
	private Config config;
	
	private SessionService sessionService;

	private Map<String, Object> results;

	private MockMvc mockMvc;
	
	@Autowired
	private JsonHelper jsonHelper;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		results = new HashMap<>();
		results.put("COUNT", 1);
		dataRepository = mock(DataRepository.class,new Answer<Map<String, Object>>() {
			@Override
			public Map<String, Object> answer(InvocationOnMock invocation) throws Throwable {
				return results;
			}
		});
		config = mock(Config.class);
		
		sessionService = new SessionService(dataRepository, config);
		
		mockMvc = MockMvcBuilders.standaloneSetup(sessionService).build();
	}

	/**
	 * Test method for {@link com.github.rb.csv_reader_service.service.SessionService#getCitiesAmount(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetCitiesAmount() throws Exception {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String from = dateFormat.format(new Date());
		String to = from;
		String city = "city";
		when(config.getDateFormat()).thenReturn(dateFormat );
		
		assertThat(sessionService.getCitiesAmount(from, to, city), is(results));
		
		String url = String.format("/cities?from=%s&to=%s&city=%s", from,to,city);
		
		mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
		.andExpect(content().json(jsonHelper.objectToString(results)));
		
	}

}
