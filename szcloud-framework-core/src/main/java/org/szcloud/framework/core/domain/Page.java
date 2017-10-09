package org.szcloud.framework.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5859907455479273251L;

	public static int DEFAULT_PAGE_SIZE = 10;

	private int pageSize = DEFAULT_PAGE_SIZE; 

	private long start; 

	private List<T> data; 

	private long totalCount; 


	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	public Page(int pageSize) {
		this(0, 0, pageSize, new ArrayList<T>());
	}

	public Page(long start, long totalSize, int pageSize, List<T> data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<T> getResult() {
		return data;
	}

	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
