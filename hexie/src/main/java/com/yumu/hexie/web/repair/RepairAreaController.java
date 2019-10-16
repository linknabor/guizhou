package com.yumu.hexie.web.repair;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.hexie.integration.wuye.resp.BaseResponse;
import com.yumu.hexie.integration.wuye.resp.BaseResponseDTO;
import com.yumu.hexie.integration.wuye.vo.BaseRequestDTO;
import com.yumu.hexie.model.localservice.repair.RepairArea;
import com.yumu.hexie.service.exception.IntegrationBizException;
import com.yumu.hexie.service.repair.RepairAreaService;

@RestController
@RequestMapping(value = "/servplat/repairArea")
public class RepairAreaController {

	@Autowired
	private RepairAreaService repairAreaService;
	
	/**
	 * 管理端获取有维修服务的区域
	 * @param baseRequestDTO
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public BaseResponseDTO<List<RepairArea>> getServiceArea(@RequestBody BaseRequestDTO<RepairArea> baseRequestDTO) {
		
		try {
			RepairArea repairArea = baseRequestDTO.getData();
			List<RepairArea> list = repairAreaService.getRepairArea(repairArea);
			return BaseResponse.success(baseRequestDTO.getRequestId(), list);
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
	}
	
	/**
	 * 管理端新增维修区域
	 * @param baseRequestDTO
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public BaseResponseDTO<List<RepairArea>> saveRepairArea(@RequestBody BaseRequestDTO<List<RepairArea>> baseRequestDTO) {
		
		try {
			List<RepairArea> list = baseRequestDTO.getData();
			repairAreaService.saveRepairArea(list, false);
			return BaseResponse.success(baseRequestDTO.getRequestId());
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
		
	}
	
	/**
	 * 管理端新增维修区域(小区用)
	 * @param baseRequestDTO
	 * @return
	 */
	@RequestMapping(value = "/sectSave", method = RequestMethod.POST)
	public BaseResponseDTO<List<RepairArea>> saveSectRepairArea(@RequestBody BaseRequestDTO<List<RepairArea>> baseRequestDTO) {
		
		try {
			List<RepairArea> list = baseRequestDTO.getData();
			repairAreaService.saveRepairArea(list, true);
			return BaseResponse.success(baseRequestDTO.getRequestId());
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
		
	}
	
}
