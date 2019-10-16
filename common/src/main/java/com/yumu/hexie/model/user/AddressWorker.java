package com.yumu.hexie.model.user;

import static org.junit.Assert.fail;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.TransactionUtil;
import com.yumu.hexie.integration.wuye.resp.HouseListVO;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.service.shequ.WuyeService;
import com.yumu.hexie.service.user.UserService;

public class AddressWorker implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(AddressWorker.class);

	private TempSect tempSect;

	private UserService userService;
	private WuyeService wuyeService;
	private TempUserRepository tempUserRepository;
	@SuppressWarnings("rawtypes")
	private TransactionUtil transactionUtil;

	public AddressWorker() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public AddressWorker(TempSect tempSect, UserService userService, WuyeService wuyeService,
			TransactionUtil transactionUtil, TempUserRepository tempUserRepository) {
		super();
		this.tempSect = tempSect;
		this.userService = userService;
		this.wuyeService = wuyeService;
		this.transactionUtil = transactionUtil;
		this.tempUserRepository = tempUserRepository;
	}

	@Override
	public void run() {
		assembleData();
	}

	@SuppressWarnings("unchecked")
	public void assembleData() {

		List<TempUser> tempUserlist = tempUserRepository.findBySectid(tempSect.getSectId());
		log.error("start to add address, sect_id : " + tempSect.getSectName() + ", hou count : " + tempUserlist.size());
		int successCount = 0;
		int failCount=0;
		for (TempUser tempUser : tempUserlist) {
			try {
				List<User> userList = userService.getByTel(tempUser.getTel());
				if (userList == null || userList.size() == 0) {
					continue;
				}
				User u = userList.get(0);
				HouseListVO listVo = wuyeService.queryHouse(u.getWuyeId());
				if (listVo != null) {
					if (listVo.getHou_info() != null && listVo.getHou_info().size() > 0) {
						HexieUser hexieUser = new HexieUser();
						hexieUser.setCity_id(listVo.getHou_info().get(0).getCity_id());
						hexieUser.setCity_name(listVo.getHou_info().get(0).getCity_name());
						hexieUser.setProvince_id(listVo.getHou_info().get(0).getProvince_id());
						hexieUser.setProvince_name(listVo.getHou_info().get(0).getProvince_name());
						hexieUser.setRegion_id(listVo.getHou_info().get(0).getRegion_id());
						hexieUser.setRegion_name(listVo.getHou_info().get(0).getRegion_name());
						hexieUser.setCell_addr(listVo.getHou_info().get(0).getCell_addr());
						hexieUser.setSect_name(listVo.getHou_info().get(0).getSect_name());
						hexieUser.setSect_addr(listVo.getHou_info().get(0).getSect_addr());
						boolean isSuccess = transactionUtil.transact(s -> wuyeService.setDefaultAddress(u, hexieUser));
						log.info("cell_adress:" + listVo.getHou_info().get(0).getCell_addr());
						if (!isSuccess) {
							failCount++;
						}
					}
				}
				successCount++;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				failCount++;
			}
		}
		log.error("小区:"+tempSect.getSectName() + ", 成功更新" + successCount + "户。");
		log.error("小区:"+tempSect.getSectName() + ", 更新失败" + failCount + "户。");

	}
}
