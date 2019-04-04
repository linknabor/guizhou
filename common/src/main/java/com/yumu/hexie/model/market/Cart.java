package com.yumu.hexie.model.market;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.BaseModel;

//类似购物车，用来暂存商品项，以后可扩展成购物车
public class Cart extends BaseModel {

	private static final long serialVersionUID = 8850980148126766715L;
	
	private List<OrderItem> items;
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	@JsonIgnore
	public List<Long> getProductIds(){
		List<Long> ids = new ArrayList<Long>();
		if(items == null) {
			return ids;
		}
		for(OrderItem item : items) {
			ids.add(item.getProductId());
		}
		return ids;
	}

	@JsonIgnore
	public List<Long> getMerchantIds(){
		List<Long> ids = new ArrayList<Long>();
		if(items == null) {
			return ids;
		}
		for(OrderItem item : items) {
			ids.add(item.getMerchantId());
		}
		return ids;
	}

	/**
	 * 由于存在满减优惠，实际金额通常小于该金额
	 * @return
	 */
	@JsonIgnore
	public Float getTotalAmount() {
		Float totalAmount = 0f;
		for(OrderItem item : items) {
			totalAmount+=item.getAmount();
		}
		return totalAmount;
	}

	//如果是0，则不存在订单项或者为混合购物车，混合购物车暂时不做处理
	@JsonIgnore
	public int getOrderType(){
		int orderType = -1;
		for(OrderItem item : items) {
			if(orderType == -1){
				orderType = item.getOrderType();
			}else if(orderType != item.getOrderType()){
				return -1;
			}
		}
		return orderType;
	}
	
	//只考虑一个组合的问题
	@JsonIgnore
	public long getCollocationId(){
		long result = 0l;
		for(OrderItem item : items) {
			if(result == 0){
				result = item.getCollocationId();
			}else if(result != item.getCollocationId()){
				return 0l;
			}
		}
		return result;
	}
}
