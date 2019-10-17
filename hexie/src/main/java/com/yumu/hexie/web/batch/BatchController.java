package com.yumu.hexie.web.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.hexie.service.batch.BatchService;
import com.yumu.hexie.web.BaseController;

@RestController
public class BatchController extends BaseController {

	@Autowired
	private BatchService batchService;
	
	@RequestMapping(name = "/bindHouseInfo", method = RequestMethod.POST)
	public String updateBindHouseInfo(@RequestParam(required = true) String sysCode) {
		
		if ("guizhou".equals(sysCode)) {
			batchService.updateBindHouseInfo();
			return "success";
		}
		return "";
	}
	
}
