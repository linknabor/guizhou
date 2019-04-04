package com.yumu.hexie.service.home;

import java.util.List;

import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HomeService;
import com.yumu.hexie.model.market.saleplan.YuyueRule;

public interface ToHomeService {
    /**
     * 查询预约规则信息
     * @param yuyueRuleId
     * @return
     */
    public YuyueRule queryYuyueRuleInfo(long yuyueRuleId);
    public List<HomeService> findHandpickService();
    public List<HomeService> findServiceType(int serviceType);

}
