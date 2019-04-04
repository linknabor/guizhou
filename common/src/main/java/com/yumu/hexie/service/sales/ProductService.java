package com.yumu.hexie.service.sales;

import com.yumu.hexie.model.commonsupport.info.Product;


public interface ProductService {
	
	public Product getProduct(long productId);
	
	public void checkSalable(Product product, int count) ;

	public void freezeCount(Product product,int count);
	public void unfreezeCount(long productId,int count);
	public void saledCount(long productId,int count);
}
