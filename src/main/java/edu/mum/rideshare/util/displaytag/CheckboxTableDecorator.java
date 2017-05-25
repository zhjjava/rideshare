/*
 * @(#)CheckboxTableDecorator.java	Jun 24, 20155:20:08 PM
 * Copyright 2015  lc All rights reserved.
 */
package edu.mum.rideshare.util.displaytag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

/**
 * 
 * 类<strong>CheckboxTableDecorator.java</strong>{DisplayTag提供的CheckboxTableDecorator有bug，故重新编写一下}
 *
 * @author: mz 
 * @version: 1.0	Date: Jun 24, 2015 5:20:08 PM     
 */
/**
 * A table decorator which adds checkboxes for selectable rows.
 * 
 * @author Fabrizio Giustina
 * @version $Id: CheckboxTableDecorator.java 1160 2009-01-02 15:17:02Z fgiust $
 */
public class CheckboxTableDecorator extends TableDecorator {

	private String id = "id";

	private List<String> checkedIds;

	private String fieldName = "_chk";

	/**
	 * Setter for <code>id</code>.
	 * 
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Setter for <code>fieldName</code>.
	 * 
	 * @param fieldName The fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @see org.displaytag.decorator.Decorator#init(javax.servlet.jsp.PageContext, java.lang.Object,
	 *      org.displaytag.model.TableModel)
	 */
	public void init(PageContext pageContext, Object decorated, TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		String[] params = pageContext.getRequest().getParameterValues(fieldName);
		checkedIds = params != null ? new ArrayList<String>(Arrays.asList(params)) : new ArrayList<String>(0);
	}

	/**
	 * 
	 * @see org.displaytag.decorator.TableDecorator#finish()
	 */
    public void finish()
    {
     //no need to remember those checked ids
/*        if (!checkedIds.isEmpty())
        {
            JspWriter writer = getPageContext().getOut();
            for (Iterator<String> it = checkedIds.iterator(); it.hasNext();)
            {
                String name = it.next();
                StringBuffer buffer = new StringBuffer();
                buffer.append("<input type=\"hidden\" name=\"");
                buffer.append(fieldName);
                buffer.append("\" value=\"");
                buffer.append(name);
                buffer.append("\">");
                try
                {
                    writer.write(buffer.toString());
                }
                catch (IOException e)
                {
                    // should never happen
                }
            }
        }*/

        super.finish();

    }

	/**
	 * 该类有bug,mz
	 * 
	 * @return
	 */
	public String getCheckbox() {

        String evaluatedId = ObjectUtils.toString(evaluate(id));
        // no need to remember the checked values
       // boolean checked = checkedIds.contains(evaluatedId);

        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type=\"checkbox\" name=\"");
        buffer.append(fieldName);
        buffer.append("\" value=\"");
        buffer.append(evaluatedId);
        buffer.append("\"");
       /* if (checked)
        {
            checkedIds.remove(evaluatedId);
            buffer.append(" checked=\"checked\"");
        }*/
        buffer.append("/>");

        return buffer.toString();
	}

}
