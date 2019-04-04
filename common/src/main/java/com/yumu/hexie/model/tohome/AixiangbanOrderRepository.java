package com.yumu.hexie.model.tohome;

import org.springframework.data.jpa.repository.JpaRepository;

/** 
 * <p>项目：东湖e家园</p>
 * <p>模块：爱相伴服务</p>
 * <p>描述：</p>
 * <p>版    权: Copyright (c) 2016</p>
 * <p>公    司: 上海奈博信息科技有限公司</p>
 * @author hwb_work 
 * @version 1.0 
 * 创建时间：2016年4月15日 下午2:10:03
 */
public interface AixiangbanOrderRepository extends JpaRepository<AixiangbanOrder, Long> {

	public AixiangbanOrder findByYOrderId(long yOrderId);
}
