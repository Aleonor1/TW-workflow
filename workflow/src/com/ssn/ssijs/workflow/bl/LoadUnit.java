package com.ssn.ssijs.workflow.bl;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class LoadUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(unique = true)
	private String barcode;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loadUnit")
	private java.util.List<StockItem> stockItems = new ArrayList<>();

	public LoadUnit() {

	}

	public LoadUnit(String barcode) {
		super();
		this.barcode = barcode;
		this.stockItems = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public java.util.List<StockItem> getStockItems() {
		return stockItems;
	}

	public void setStockItems(java.util.List<StockItem> stockItems) {
		this.stockItems = stockItems;
	}

	public void addToList(StockItem stockItem) {
		stockItems.add(stockItem);
		stockItem.setLoadUnit(this);
	}

	@Override
	public String toString() {
		return "LoadUnit [id=" + id + ", barcode=" + barcode + "]";
	}

}
