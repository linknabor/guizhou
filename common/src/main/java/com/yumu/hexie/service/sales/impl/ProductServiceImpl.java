package com.yumu.hexie.service.sales.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.commonsupport.info.ProductRepository;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.sales.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Inject
	private ProductRepository productRepository;

	@Override
	public Product getProduct(long productId) {
		return productRepository.findOne(productId);
	}

	@Override
	public void checkSalable(Product product, int count) {
		if(product.getStatus() != ModelConstant.PRODUCT_ONSALE){
			throw new BizValidateException("您晚到了一步，商品已下架！");
		} else if(product.getCanSaleNum()<=0) {
			throw new BizValidateException("您晚到了一步，商品已卖完！");
		} else if(product.getCanSaleNum()<count) {
			throw new BizValidateException("库存不足，请减少购买数量！");
		} else if(product.getEndDate()!=null&&product.getEndDate().getTime()<System.currentTimeMillis()){
			throw new BizValidateException("您晚到了一步，商品已下架！");
		}
	}

	@Override
	public void freezeCount(Product product, int count) {
		product.setFreezeNum(product.getFreezeNum() +count);
		productRepository.save(product);
	}

	@Override
	public void saledCount(long productId, int count) {
		Product product = productRepository.findOne(productId);
		product.setSaledNum(product.getSaledNum()+count);
		product.setFreezeNum(product.getFreezeNum()-count);
		productRepository.save(product);
	}

	@Override
	public void unfreezeCount(long productId, int count) {
		Product product = productRepository.findOne(productId);
		product.setFreezeNum(product.getFreezeNum()-count);
		productRepository.save(product);
	}
}
