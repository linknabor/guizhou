package com.yumu.hexie.service.repair;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.localservice.repair.RepairArea;
import com.yumu.hexie.model.localservice.repair.RepairAreaRepository;

@Service
public class RepairAreaServiceImpl implements RepairAreaService {

	@Autowired
	private RepairAreaRepository repairAreaRepository;
	
	@Override
	public List<RepairArea> getRepairArea(RepairArea repairArea) {

		return repairAreaRepository.findByCspIdAndSectid(repairArea.getCspId(), repairArea.getSectId());
	}	

	@Transactional
	@Override
	public void saveRepairArea(List<RepairArea> list, boolean isSect) {
		
		/*
		 * 1.物业公司先删除这个物业下所有的小区，然后重新添加
		 * 2.小区只删除本小区的，然后重新添加
		 */
		List<RepairArea> existList = new ArrayList<>();
		if (!isSect) {
			String cspId = list.get(0).getCspId();
			existList = repairAreaRepository.findByCspId(cspId);
		}else {
			List<RepairArea> loopList = null;
			for (RepairArea existArea : list) {
				loopList = repairAreaRepository.findByCspIdAndSectid(existArea.getCspId(), existArea.getSectId());
				if (loopList != null) {
					existList.addAll(loopList);
				}
			}
			
		}
		if (existList != null) {
			for (RepairArea existArea : existList) {	//这里用主键删除。其实可以直接delete一个list,但是可能多人操作会有锁表
				repairAreaRepository.delete(existArea.getId());
			}
		}

		for (RepairArea area : list) {
			if (StringUtil.isEmpty(area.getSectId())) {
				continue;
			}
			repairAreaRepository.save(area); 
		}
		
	}

}
