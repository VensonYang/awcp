package org.szcloud.framework.core.mybatis.sqlnode;

import java.util.List;

import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.session.Configuration;

public class OrderBySqlNode extends TrimSqlNode {

	public OrderBySqlNode(Configuration configuration, SqlNode contents) {
		super(configuration, contents, "ORDER  BY", (List<String>) null, null,
				null);
	}
}
