package ua.org.tumakha.spdtool;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * Load application properties.
 * 
 * @author Yuriy Tumakha
 */
public class AppPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static final Log LOG = LogFactory.getLog(AppPropertyPlaceholderConfigurer.class);

	private final String SQL_GET_PROPERTIES_LIST = "SELECT `key`, value FROM app_setting";

	private final JdbcTemplate jdbcTemplate;
	private final Map<String, String> propertiesMap = new HashMap<String, String>();

	public AppPropertyPlaceholderConfigurer(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void init() {
		try {
			loadDbProperties();
		} catch (Exception e) {
			LOG.error("Load properties failed.", e);
		}
	}

	/**
	 * Loads application properties from DB.
	 * 
	 * @throws SQLException
	 */
	private void loadDbProperties() throws SQLException {
		LOG.info("Loading properties...");
		jdbcTemplate.query(SQL_GET_PROPERTIES_LIST, SERVER_PROPERTIES_RESULT_SET_EXTRACTOR);
		LOG.info("Loaded " + propertiesMap.size() + " properties.");
	}

	/**
	 * Extractor for result set from table ServerProperties
	 */
	private final ResultSetExtractor<Void> SERVER_PROPERTIES_RESULT_SET_EXTRACTOR = new ResultSetExtractor<Void>() {
		@Override
		public Void extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			while (resultSet.next()) {
				String key = resultSet.getString("key");
				if (key != null) {
					LOG.debug("Adding\\Replacing property=<" + key + ">");
					propertiesMap.put(key, resultSet.getString("value"));
				}
			}
			return null;
		}
	};

	public Properties getProperties() {
		Properties properties = new Properties();
		for (String key : propertiesMap.keySet()) {
			properties.put(key, propertiesMap.get(key));
		}
		return properties;
	}

	public String getProperty(String key) {
		if (key == null) {
			LOG.error("getProperty executed with key=null");
			return null;
		}
		return propertiesMap.get(key);
	}

	@Override
	protected void loadProperties(Properties props) throws IOException {
		props.putAll(getProperties());
		LOG.info("Totally properties in cache = " + propertiesMap.size());
	}

}
