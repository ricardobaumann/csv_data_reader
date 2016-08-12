/**
 * 
 */
package com.github.rb.csv_reader_service.repository;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.rb.csv_reader_service.Config;

/**
 * @author ricardobaumann
 *
 */
public class DataRepositoryTest {

	@Mock
	private Config config;
	
	@InjectMocks
	private DataRepository dataRepository;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("csv/").getFile());
		MockitoAnnotations.initMocks(this);
		when(config.getCsvFilePath()).thenReturn(file.getAbsolutePath()+"/");
		when(config.getSeparator()).thenReturn(";");
	}

	/**
	 * Test method for {@link com.github.rb.csv_reader_service.repository.DataRepository#fetchSessionsBy(java.lang.String, java.lang.Object, java.util.Date, java.util.Date, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFetchExistentData() throws Exception {
		Calendar from = Calendar.getInstance();
		from.add(Calendar.YEAR, -100);
		Map<String, Object> result = dataRepository.fetchSessionsBy("eid", "2133387660900270440", from.getTime(), new Date(), "test_data");
		assertThat(result, hasEntry("COUNT", 1));
		assertThat(result, hasEntry("CITY", "Neuss"));
	}
	
	@Test
	public void testFetchInexistentData() throws Exception {
		Map<String, Object> result = dataRepository.fetchSessionsBy("eid", "2133387660900270440", new Date(), new Date(), "empty_data");
		assertThat(result.entrySet(), is(empty()));
	}

}
