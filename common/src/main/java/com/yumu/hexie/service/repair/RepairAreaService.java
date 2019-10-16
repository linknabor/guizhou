package com.yumu.hexie.service.repair;

import java.util.List;

import com.yumu.hexie.model.localservice.repair.RepairArea;

public interface RepairAreaService {
	
	public List<RepairArea> getRepairArea(RepairArea repairArea);

	public void saveRepairArea(List<RepairArea> list, boolean isSect);
	
}
