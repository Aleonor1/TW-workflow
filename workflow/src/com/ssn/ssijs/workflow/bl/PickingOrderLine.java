package com.ssn.ssijs.workflow.bl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class PickingOrderLine implements Serializable {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private static final long serialVersionUID = 1L;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private Article article;
	private int quantity;
	@Enumerated(EnumType.STRING)
	private PickingOrderStatus status;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pickingOrder_id", nullable = true)
	private PickingOrder pickingOrder;
	@Column(nullable = true)
	private int quantityPick;

	public int getQuantityPick() {
		return quantityPick;
	}

	public void setQuantityPick(int quantityPick) {
		this.quantityPick = quantityPick;
	}

	public PickingOrderLine(int quantity, PickingOrderStatus status, Article article) {
		this.quantity = quantity;
		this.status = status;
		this.article = article;
	}

	public PickingOrderLine() {
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public PickingOrderStatus getStatus() {
		return status;
	}

	public void setStatus(PickingOrderStatus status) {
		this.status = status;
	}

	public PickingOrder getPickingOrder() {
		return pickingOrder;
	}

	public void setPickingOrder(PickingOrder pickingOrder) {
		this.pickingOrder = pickingOrder;
	}

	@Override
	public String toString() {
		return "PickingOrderLine [article=" + article + ", quantity=" + quantity + ", status=" + status + "]";
	}

}
