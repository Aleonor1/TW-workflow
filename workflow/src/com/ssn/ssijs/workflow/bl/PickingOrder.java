package com.ssn.ssijs.workflow.bl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class PickingOrder implements Serializable {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private static final long serialVersionUID = 1L;
	private String orderNr;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pickingOrder")
	private List<PickingOrderLine> lines = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	private PickingOrderStatus status;

	// loadunit is needed

	public PickingOrder() {
	}

	public PickingOrder(String orderNr, PickingOrderStatus status) {
		this.orderNr = orderNr;
		this.status = status;
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PickingOrderLine> getLines() {
		return lines;
	}

	public void setLines(List<PickingOrderLine> lines) {
		this.lines = lines;
	}

	public PickingOrderStatus getStatus() {
		return status;
	}

	public void setStatus(PickingOrderStatus status) {
		this.status = status;
	}

	public void addToList(PickingOrderLine pol) {
		lines.add(pol);
		pol.setPickingOrder(this);
	}

	@Override
	public String toString() {
		return "PickingOrder [id=" + id + ", orderNr=" + orderNr + ", lines=" + lines + ", status=" + status + "]";
	}

}
