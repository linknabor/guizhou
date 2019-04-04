package com.yumu.hexie.service.common.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.commonsupport.logistics.Logistics;
import com.yumu.hexie.model.commonsupport.logistics.LogisticsRepository;
import com.yumu.hexie.service.common.LogisticsService;



/**
 * Created by Administrator on 2014/12/1.
 */
@Service(value = "logisticsService")
public class LogisticsServiceImpl implements LogisticsService {
	private static final Logger Log = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	@Inject
	public LogisticsRepository logisticsItemRepository;
	
	@Override
	public Logistics queryLogisticsInfo(String nu , String com) {
		Logistics  logistics = logisticsItemRepository.findByLogistics(nu);	
		Log.error("logistice:"+ logistics);
		if(logistics == null){
			logistics = new Logistics();
			logistics.setLogisticsno(nu);
			logistics.setLogisticsname(com);
			logistics.setSignstatus("7");
		}else if (logistics.getDescription() == null) {
			logistics.setLogisticsno(nu);
			logistics.setLogisticsname(com);
			logistics.setSignstatus("8");
		}
		return logistics;
	}
}
