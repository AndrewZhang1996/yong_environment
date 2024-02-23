package org.jeecg.modules.ec.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.ec.entity.EcExergy;
import org.jeecg.modules.ec.vo.EcExergyReqVo;
import org.jeecg.modules.ec.vo.EcExergyVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.jeecg.modules.ec.vo.EcPlanVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:20:46
 */
public interface IEcExergyService {

    /**
     * 新增
     * @param exergy exergy
     * @return ID
     */
    String insert(EcExergy exergy);

    List<EcExergyVo> getAllExergy();

    IPage<EcExergyVo> page(Page<EcExergy> page, QueryWrapper<EcExergy> queryWrapper);

    void addExergy(EcExergyReqVo ecExergyReqVo);

    void editExergy(EcExergyReqVo ecExergyReqVo);

    void deleteExergy(String id);
}
