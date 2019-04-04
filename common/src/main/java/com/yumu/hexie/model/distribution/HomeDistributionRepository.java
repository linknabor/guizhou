package com.yumu.hexie.model.distribution;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomeDistributionRepository extends JpaRepository<HomeDistribution, Long> {
    @Query("select hd.itemId From HomeDistribution hd where hd.itemType="
            +DistributionConstant.HOME_DIS_TYPE +
            " and hd.regionId = ?1 and hd.parentTypeId=?2 order by hd.sort desc")
    public List<Long> queryTypeIdsByParent(long regionId,long typeId);
    @Query("select hd.itemId From HomeDistribution hd where hd.itemType="
            +DistributionConstant.HOME_DIS_ITEM +
            " and hd.regionId = ?1 and hd.parentTypeId=?2 order by hd.sort desc")
    public List<Long> queryItemIdsByParent(long regionId,long typeId);
}
