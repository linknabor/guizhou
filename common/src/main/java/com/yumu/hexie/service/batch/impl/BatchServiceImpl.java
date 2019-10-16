package com.yumu.hexie.service.batch.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yumu.hexie.integration.wuye.WuyeUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResult;
import com.yumu.hexie.integration.wuye.vo.HexieHouse;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.batch.BatchService;

public class BatchServiceImpl implements BatchService {

	private static Logger logger = LoggerFactory.getLogger(BatchServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateBindHouseInfo() {

		List<User> userList = userRepository.getBindedUser();
		for (User user : userList) {
			String cellId = user.getCellId();
			if (!StringUtils.isEmpty(cellId)) {
				BaseResult<HexieHouse> result = WuyeUtil.getHouse(user.getWuyeId(), "", cellId);
				HexieHouse house = result.getData();

				if (house != null) {
					logger.info("hosue is :" + house.toString());
					user.setSectId(house.getSect_id());
					user.setCspId(house.getCsp_id());
					userRepository.save(user);
				}

			}
			
		}
	}

}
