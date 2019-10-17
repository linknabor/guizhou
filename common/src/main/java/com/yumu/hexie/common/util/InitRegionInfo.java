package com.yumu.hexie.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.yumu.hexie.model.distribution.region.Region;
import com.yumu.hexie.service.user.RegionService;

@Component
public class InitRegionInfo {
	@Inject
	RegionService regionService ;
	private static Map<String, Long> paraMap = new HashMap<>(128);
	
	public Map<String, Long> initRegionParam(){
		if (paraMap.isEmpty()) {
			synchronized (paraMap) {
				List<Region> list=regionService.getRegionAll();
				for (Region region : list) {
					paraMap.put(region.getName(), region.getId());
				}
			}
		}
		return paraMap;
		
	}
	

}
