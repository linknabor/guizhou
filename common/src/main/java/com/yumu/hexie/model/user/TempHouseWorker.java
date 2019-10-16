package com.yumu.hexie.model.user;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.TransactionUtil;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.service.shequ.WuyeService;

public class TempHouseWorker implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(TempHouseWorker.class);
	
	private TempHouse tempHouse;
	private UserRepository userRepository;
	private WuyeService wuyeService;
	@SuppressWarnings("rawtypes")
	private TransactionUtil transactionUtil;
	
    private AtomicInteger success;
    
    private AtomicInteger fail;
	
	public TempHouseWorker() {
		super();
	}
	

	public TempHouseWorker(TempHouse tempHouse, WuyeService wuyeService, 
			UserRepository userRepository, @SuppressWarnings("rawtypes") TransactionUtil transactionUtil,AtomicInteger success,AtomicInteger fail) {
		super();
		this.tempHouse = tempHouse;
		this.userRepository = userRepository;
		this.wuyeService = wuyeService;
		this.transactionUtil = transactionUtil;
		this.success=success;
		this.fail=fail;
	}

	@Override
	public void run() {
		deal();
	}
	
	@SuppressWarnings("unchecked")
	public void deal() {
		
		List<User> userList = userRepository.findByWuyeId(tempHouse.getWuyeId());
		log.error("start to add address, wuyeId : " + tempHouse.getWuyeId() + ", hou count : " + userList.size());
		for (User user : userList) {	//这里理论上应该只会出现一条
			HexieUser hexieUser = new HexieUser();
			hexieUser.setUser_id(tempHouse.getWuyeId());
			hexieUser.setCell_addr(tempHouse.getCellAddr());
			hexieUser.setSect_name(tempHouse.getSectName());
			hexieUser.setCity_name(tempHouse.getCityName());
			hexieUser.setRegion_name(tempHouse.getRegionName());
			hexieUser.setProvince_name(tempHouse.getProvinceName());
			boolean result=transactionUtil.transact(s -> wuyeService.setDefaultAddress(user, hexieUser));
		    if(!result){
		    	fail.incrementAndGet();
		    	log.error("失败物业Id: " + tempHouse.getWuyeId());
		    }else{
		        success.incrementAndGet();
		    }
		}
	}

}
