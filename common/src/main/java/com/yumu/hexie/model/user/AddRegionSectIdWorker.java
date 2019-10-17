package com.yumu.hexie.model.user;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.common.util.TransactionUtil;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.service.shequ.WuyeService;

public class AddRegionSectIdWorker implements Runnable{
	
	
	private static final Logger log = LoggerFactory.getLogger(AddUserSectIdWorker.class);

	private Region region;
	private WuyeService wuyeService;
	@SuppressWarnings("rawtypes")
	private TransactionUtil transactionUtil;
	
    private AtomicInteger success;
    
    private AtomicInteger fail;
	@Override
	public void run() {
		deal();
	}
	
	public AddRegionSectIdWorker() {
		super();
	}

	public AddRegionSectIdWorker(Region region, WuyeService wuyeService,
			@SuppressWarnings("rawtypes") TransactionUtil transactionUtil,AtomicInteger success,AtomicInteger fail) {
		super();
		this.region = region;
		this.wuyeService = wuyeService;
		this.transactionUtil = transactionUtil;
		this.success=success;
		this.fail=fail;
	}
	@SuppressWarnings("unchecked")
	public void deal(){
			String  sectId = wuyeService.getSectIdByRegionName(region.getName());
			if(StringUtil.isNotEmpty(sectId)){
				boolean isSuccess = transactionUtil.transact(s -> wuyeService.saveRegionSectId(region, sectId));	
				if(!isSuccess){
			    	fail.incrementAndGet();
			    	log.error("失败小区: " + region.getName());
			    }else{
			        success.incrementAndGet();
			    }	
			}else{
				fail.incrementAndGet();
		    	log.error("失败小区: " + region.getName()+",原因：没有查到sectId");
			}
		 }
}
