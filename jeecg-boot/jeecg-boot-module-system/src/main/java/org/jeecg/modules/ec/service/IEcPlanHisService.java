package org.jeecg.modules.ec.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.ec.entity.EcPlanHis;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.jeecg.modules.ec.vo.EcPlanVo;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 14:50:59
 */
public interface IEcPlanHisService {

    /**
     * Save EcPlan to history.
     * @param planVo planVo
     */
    void saveEcPlanHis(EcPlanVo planVo);

    IPage<EcPlanHis> page(Page<EcPlanHis> page, QueryWrapper<EcPlanHis> queryWrapper);

    void deletePlanHis(String id);
}
