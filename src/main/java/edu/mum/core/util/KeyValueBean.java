/*
 * $Id: KeyValueBean.java 54929 2004-10-16 16:38:42Z germuska $ 
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.mum.core.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;

/**

 *
 */

/**
 * A simple JavaBean to represent label-value pairs. This is most commonly used when constructing user interface
 * elements which have a label to be displayed to the user, and a corresponding value to be returned to the server. One
 * example is the <code>&lt;html:options&gt;</code> tag.
 * 
 * 不希望用数据库的函数来转换数字型 对前端用户而言，返回的对象是什么类型没有关系， 只需关心label，value,since 2006
 * 
 * @author MZ
 *         <p>
 *         Note: this class has a natural ordering that is inconsistent with equals.
 *         </p>
 * 
 * @version $Rev: 54929 $ $Date: 2004-10-16 17:38:42 +0100 (Sat, 16 Oct 2004) $
 */
public class KeyValueBean implements Comparable, Serializable {
	private static final String PARSE_SEPARATOR = "/";

	private static final String DEFAULT_RETURN_VALUE = "";

	/**
	 * Comparator that can be used for a case insensitive sort of <code>KeyValueBean</code> objects.
	 */
	public static final Comparator CASE_INSENSITIVE_ORDER = new Comparator() {
		public int compare(Object o1, Object o2) {
			String label1 = ((KeyValueBean)o1).getKey();
			String label2 = ((KeyValueBean)o2).getKey();
			return label1.compareToIgnoreCase(label2);
		}
	};

	// ----------------------------------------------------------- Constructors

	/**
	 * Default constructor.
	 */
	public KeyValueBean() {
		super();
	}

	/**
	 * Construct an instance with the supplied property values.
	 * 
	 * @param label The label to be displayed to the user.
	 * @param value The value to be returned to the server.
	 */
	public KeyValueBean(String label, String value) {
		this.key = label;
		this.value = value;
	}

	public KeyValueBean(Object label, Object value) {
		this(label.toString(), value.toString());
	}

	// ------------------------------------------------------------- Properties

	/**
	 * The property which supplies the option label visible to the end user.
	 */
	private String key = null;

	public String getKey() {
		return this.key != null ? this.key.trim() : DEFAULT_RETURN_VALUE;
	}

	public void setKey(String label) {
		this.key = label;
	}

	/**
	 * The property which supplies the value returned to the server.
	 */
	private String value = null;

	public String getValue() {
		return this.value != null ? this.value.trim() : DEFAULT_RETURN_VALUE;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String[] getValueArr() {
		return getValue().split(PARSE_SEPARATOR);
	}

	private String getValueI(int index) {
		if (index < 0) {
			return DEFAULT_RETURN_VALUE;
		}
		String[] values = getValueArr();
		if (ArrayUtils.isEmpty(values)) {
			return DEFAULT_RETURN_VALUE;
		}
		if(index>values.length-1){
			//should log something
			return DEFAULT_RETURN_VALUE;
		}
		return values[index];
	}

	public String getValue0() {
		return getValueI(0);
	}

	public String getValue1() {
		return getValueI(1);
	}

	public String getValue2() {
		return getValueI(2);
	}

	public String getValue3() {
		return getValueI(3);
	}

	public String getValue4() {
		return getValueI(4);
	}

	public String getValue5() {
		return getValueI(5);
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Compare KeyValueBeans based on the label, because that's the human viewable part of the object.
	 * 
	 * @see Comparable
	 */
	public int compareTo(Object o) {
		// Implicitly tests for the correct type, throwing
		// ClassCastException as required by interface
		String otherLabel = ((KeyValueBean)o).getKey();

		return this.getKey().compareTo(otherLabel);
	}

	/**
	 * Return a string representation of this object.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("KeyValueBean[");
		sb.append(this.key);
		sb.append(", ");
		sb.append(this.value);
		sb.append("]");
		return (sb.toString());
	}

	/**
	 * KeyValueBeans are equal if their values are both null or equal.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof KeyValueBean)) {
			return false;
		}

		KeyValueBean bean = (KeyValueBean)obj;
		int nil = (this.getValue() == null) ? 1 : 0;
		nil += (bean.getValue() == null) ? 1 : 0;

		if (nil == 2) {
			return true;
		}
		else if (nil == 1) {
			return false;
		}
		else {
			return this.getValue().equals(bean.getValue());
		}

	}

	/**
	 * The hash code is based on the object's value.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (this.getValue() == null) ? 17 : this.getValue().hashCode();
	}

	public static void main(String[] ar) {
		KeyValueBean l = new KeyValueBean(1, 10);
		System.out.println(l.getKey() + ";;" + l.getValue());
		System.out.println(Locale.getDefault());
	}
}
