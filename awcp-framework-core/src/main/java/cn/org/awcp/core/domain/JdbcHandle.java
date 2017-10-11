package cn.org.awcp.core.domain;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.org.awcp.core.utils.Springfactory;

public class JdbcHandle {

	private static JdbcTemplate jdbcTemplate = null;

	public static JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null)
			jdbcTemplate = Springfactory.getBean("jdbcTemplate");
		return jdbcTemplate;
	}

}
