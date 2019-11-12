package com.ssn.ssijs.workflow.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class List extends Control {
	private String entity;
	private String whereClause;
	@XmlElement(name = "column")
	private java.util.List<Column> columns;

	@XmlAttribute
	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	@XmlAttribute
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public java.util.List<Column> getColumn() {
		return columns;
	}

	public void setColumns(java.util.List<Column> columns) {
		this.columns = columns;
	}

	public void addColumn(Column column) {
		try {
			if (columns == null) {
				columns = new ArrayList<>();
			}
			columns.add(column);

		} catch (Exception e) {
		}
	}

	@Override
	public String toString() {
		return "List [entity=" + entity + ", columns=" + columns + ", id=" + id + "]";
	}

}
