package com.yumu.hexie.model.user;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.common.util.TransactionUtil;
import com.yumu.hexie.integration.wuye.resp.HouseListVO;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.service.shequ.WuyeService;

public class AddUserSectIdWorker implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(AddUserSectIdWorker.class);

	private User user;
	private UserRepository userRepository;
	private WuyeService wuyeService;
	@SuppressWarnings("rawtypes")
	private TransactionUtil transactionUtil;
	
    private AtomicInteger success;
    
    private AtomicInteger fail;
	@Override
	public void run() {
		deal();
	}
	
	public AddUserSectIdWorker() {
		super();
	}

	public AddUserSectIdWorker(User user, UserRepository userRepository, WuyeService wuyeService,
			@SuppressWarnings("rawtypes") TransactionUtil transactionUtil,AtomicInteger success,AtomicInteger fail) {
		super();
		this.user = user;
		this.userRepository = userRepository;
		this.wuyeService = wuyeService;
		this.transactionUtil = transactionUtil;
		this.success=success;
		this.fail=fail;
	}
	@SuppressWarnings("unchecked")
	public void deal(){
		if(StringUtil.isEmpty(user.getSectId())){
			HexieUser hexieUser = wuyeService.queryPayUserAndBindHouse(user.getWuyeId());
			if(!StringUtil.isEmpty(hexieUser.getSect_id())){
				boolean isSuccess = transactionUtil.transact(s -> wuyeService.setDefaultAddress(user, hexieUser));	
				if(!isSuccess){
			    	fail.incrementAndGet();
			    	log.error("失败用户Id: " + user.getId());
			    }else{
			        success.incrementAndGet();
			    }	
			}else{
				fail.incrementAndGet();
				log.error("未查询到用户房子信息: " + user.getId());
			}
		 }
	}
}
