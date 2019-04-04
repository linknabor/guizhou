package com.yumu.hexie.service.home.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HomeService;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HomeServiceRepository;
import com.yumu.hexie.model.market.saleplan.YuyueRule;
import com.yumu.hexie.model.market.saleplan.YuyueRuleRepository;
import com.yumu.hexie.service.home.ToHomeService;

@Service("toHomeService")
public class ToHomeServiceImpl implements ToHomeService {

    @Inject
    private YuyueRuleRepository yuyueRuleRepository;
       
    @Inject
    private HomeServiceRepository homeServiceRepository;

    @Override
    public List<HomeService> findHandpickService() {
        return homeServiceRepository.findByisHandpick();
    }

    @Override
    public List<HomeService> findServiceType(int serviceType) {
        return homeServiceRepository.findByServiceType(serviceType);
    }

    @Override
    public YuyueRule queryYuyueRuleInfo(long yuyueRuleId) {
        return yuyueRuleRepository.findOne(yuyueRuleId);
    }
}
