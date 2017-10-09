package org.szcloud.framework.core.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.szcloud.framework.core.utils.Springfactory;

public class JdbcHandle {

	private static JdbcTemplate jdbcTemplate = null;

	public static JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null)
			jdbcTemplate = Springfactory.getBean("jdbcTemplate");
		return jdbcTemplate;
	}

}
