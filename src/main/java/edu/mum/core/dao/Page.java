package edu.mum.core.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * This class is used to return a Page of records from query requests
 */
@SuppressWarnings("serial")
public class Page implements Serializable,PaginatedList {
	private SortOrderEnum sortDirection = SortOrderEnum.DESCENDING;

	private String sortCriterion;
	
	private int pageNumber = 0;

	private int pageSize = 0;

	private int totalPages = 0;

	private int totalRecs = 0;

	private List list = new ArrayList();

	/**
	 * Constructor
	 */
	public Page() {
	}
	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Returns page number. Page number starting from 1
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * Returns number of records in a page.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Returns the total number of pages of the query results.
	 */
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * Returns the total number of records of the query results.
	 */
	public int getTotalRecs() {
		return totalRecs;
	}

	public void setTotalRecs(int totalRecs) {
		this.totalRecs = totalRecs;
	}

	/**
	 * Returns the list of records of the current page.
	 */
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("Page{").append("pageSize:").append(
				pageSize).append(", pageNumber:").append(pageNumber).append(
				", totalPages:").append(totalPages).append(", totalRecs:")
				.append(totalRecs).append(", list:").append(list).append("}");
		return sb.toString();
	}

//for displaytag
	public int getFullListSize() {
		return getTotalRecs();
	}

//	public List getList() {
//		return result;
//	}

	public int getObjectsPerPage() {
		return getPageSize();
	}

//	public int getPageNumber() {
//		return super.getPageNumber();
//	}

	public void setSortCriterion(String fieldN) {
		this.sortCriterion = (fieldN == null || "null".equals(fieldN)) ? "1" : fieldN;
	}

	public String getSortCriterion() {
		return this.sortCriterion;
	}

	public void setSortDirection(String dir) {
		if (dir == null || "null".equals(dir) || "asc".equalsIgnoreCase(dir)) {
			sortDirection = SortOrderEnum.ASCENDING;
		}
		else {
			sortDirection = SortOrderEnum.DESCENDING;
		}
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSortDirectionStr() {
		if (sortDirection == SortOrderEnum.ASCENDING)
			return "ASC";
		else
			return "DESC";
	}

	public SortOrderEnum getSortDirection() {
		return sortDirection;
	}

	public String getSearchId() {
		return Integer.toHexString(getPageSize() * 10000 + getPageNumber());
	}	
}