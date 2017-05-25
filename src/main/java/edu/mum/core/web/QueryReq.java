package edu.mum.core.web;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class QueryReq implements Serializable {

	/**
	 * JPQL_QUERY = 1
	 */
	public final static int JPQL_QUERY = 1;

	//	/**
	//	 * NAMED_QUERY = 2
	//	 */
	//	public final static int NAMED_QUERY = 2;

	/**
	 * NATIVE_QUERY = 3
	 */
	public final static int NATIVE_QUERY = 3;

	/**
	 * The default type is JPQL_QUERY.
	 */
	private int type = JPQL_QUERY; // default

	private String select = null; // for JPA and Native query

	//	/**
	//	 * Name of the query. For NAMED_QUERY only.
	//	 */
	//	private String queryName = null; // for NamedQuery only

	/**
	 * The where clause.
	 */
	private String where = null;

	/**
	 * Object array of the parameters.
	 */
	private Object[] params = null;

	private Class cls = null; // for NativeQuery only

	protected String sortDirection;

	protected String sortCriterion;	
	/**
	 * The order by clause.
	 */
	private String orderBy = null;

	private String groupBy = null;

	/**
	 * 放置前台临时的一些查询条件，
	 * 需要在service中将这些查询条件合并到查询语句中
	 */
	private Map tempParameters;
	public QueryReq() {
	}

	public QueryReq(int type) {
		this.type = type;
	}

	/**
	 * Gettr for the query type. These are the available types:
	 */
	public int getType() {
		return type;
	}

	/**
	 * Setter for the query type. The default is JPQL_QUERY, though, NAMED_QUERY and NATIVE_QUERY are available as well.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 放置前台临时的一些查询条件，
	 * 需要在service中将这些查询条件合并到查询语句中
	 * @return tempParameters 取值。
	 */
	public Map getTempParameters() {
		return tempParameters;
	}

	/**
	 * @param tempParameters tempParameters 设定。
	 */
	public void setTempParameters(Map tempParameters) {
		this.tempParameters = tempParameters;
	}

	public Class getCls() {
		return cls;
	}

	public void setCls(Class cls) {
		this.cls = cls;
	}

	/**
	 * Get the query condition of where clause.
	 * 
	 * @return String
	 */
	public String getWhere() {
		return where;
	}

	/**
	 * Setter. Set the query condition.
	 */
	public void setWhere(String where) {
		this.where = where;
	}

	//	/**
	//	 * Getter. Get the name of the query.
	//	 * 
	//	 * @return
	//	 */
	//	public String getQueryName() {
	//		return queryName;
	//	}
	//
	//	/**
	//	 * Setter. Set the name of the query.
	//	 * 
	//	 * @param queryName
	//	 */
	//	public void setQueryName(String queryName) {
	//		this.queryName = queryName;
	//	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	/**
	 * Getter. Get the ojbect array of the parameters.
	 * 
	 * @return Object[]
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * Setter. Set the object array of the parameters.
	 * 
	 * @param params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

	/**
	 * Get the order by clause.
	 * 
	 * @return String
	 */
	public String getOrderBy() {
		if (StringUtils.isNotBlank(getSortCriterion())) {
			setOrderBy(getSortCriterion() + " " + getSortDirection());
		}
		return orderBy;
	}

	/**
	 * @return sortDirection 取值。
	 */
	public String getSortDirection() {
		return sortDirection;
	}

	/**
	 * @param sortDirection sortDirection 设定。
	 */
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	/**
	 * @return sortCriterion 取值。
	 */
	public String getSortCriterion() {
		return sortCriterion;
	}

	/**
	 * @param sortCriterion sortCriterion 设定。
	 */
	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	/**
	 * Set the order by clause.
	 * 
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * This method will concatenate the string of query request-- including "type", "queryName", "where" clause and
	 * "orderBy" clause ... etc.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("QueryReq[");
		sb.append("type:").append(type).append(",");
		sb.append("select:").append(select).append(",");
		//		sb.append("queryName:").append(queryName).append(",");
		sb.append("where:").append(where).append(",");
		sb.append("params:").append(paramsToString()).append(",");
		sb.append("orderBy:").append(orderBy);
		sb.append("groupBy:").append(groupBy);
		sb.append("sortCriterion:").append(sortCriterion);
		sb.append("sortDirection:").append(sortDirection);
		sb.append("]");
		return sb.toString();
	}

	private String paramsToString() {
		if (params == null || params.length == 0) {
			return "";
		}
		else {
			StringBuilder sb = new StringBuilder("<");
			for (int i = 0; i < params.length; i++) {
				if (params[i] == null) {
					sb.append("NULL");
				}
				else {
					sb.append(params[i].getClass().getSimpleName()).append("(").append(params[i]).append(")");
				}
				if (i < params.length - 1) {
					sb.append(",");
				}
			}
			sb.append(">");
			return sb.toString();
		}
	}
}