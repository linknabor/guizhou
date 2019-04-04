package com.yumu.hexie.web.common;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.model.commonsupport.logistics.Logistics;
import com.yumu.hexie.service.common.LogisticsService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "logisticsController")
public class LogisticsController extends BaseController{
	@Inject
    private LogisticsService logisticsService;

	@RequestMapping(value = "/logistics/{nu}/{com}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<Logistics>> queryLogisticsInfo( @PathVariable String nu , @PathVariable String com) throws Exception {
		List<Logistics> queryKuaidi = new ArrayList<Logistics>();
		String n[] = nu.split(",");
		String c[] = com.split(",");
		for(int i=0 ; i<n.length ; i++){
				Logistics logistics =logisticsService.queryLogisticsInfo(n[i] , c[i]);
				if(logistics.getDescription()!=null){
					logistics.setDescription(logistics.getDescription());	
				}
		    	queryKuaidi.add(logistics);	
		}
		BaseResult<List<Logistics>> result =  BaseResult.successResult(queryKuaidi);
		return result;
	}
}
