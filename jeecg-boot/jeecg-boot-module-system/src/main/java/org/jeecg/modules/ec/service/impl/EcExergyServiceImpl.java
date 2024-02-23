package org.jeecg.modules.ec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.ec.convert.EcExergyConvert;
import org.jeecg.modules.ec.entity.EcExergy;
import org.jeecg.modules.ec.entity.EcExergyFactorDetail;
import org.jeecg.modules.ec.entity.EcPlan;
import org.jeecg.modules.ec.mapper.EcExergyFactorDetailMapper;
import org.jeecg.modules.ec.mapper.EcExergyMapper;
import org.jeecg.modules.ec.mapper.EcPlanMapper;
import org.jeecg.modules.ec.service.IEcExergyService;
import org.jeecg.modules.ec.vo.EcExergyReqVo;
import org.jeecg.modules.ec.vo.EcExergyVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 09:10:06
 */
@Slf4j
@Service
public class EcExergyServiceImpl implements IEcExergyService {

    @Resource
    private EcExergyMapper ecExergyMapper;

    @Resource
    private EcPlanMapper ecPlanMapper;

    @Resource
    private EcExergyFactorDetailMapper ecExergyFactorDetailMapper;

    /**
     * 新增
     *
     * @param exergy exergy
     * @return ID
     */
    @Override
    public String insert(EcExergy exergy) {
        log.info("[insert] exergy: {}", exergy);
        int effectedLines = 0;
        try {
            effectedLines = ecExergyMapper.insert(exergy);
        } catch (DuplicateKeyException duplicateKeyException) {
            log.error("duplicate key, exergy.name = {}", exergy.getName());
        }
        if (effectedLines < 1) {
            log.error("insert new exergy error! exergy.name = {}", exergy.getName());
        }
        return exergy.getId();
    }

    @Override
    public List<EcExergyVo> getAllExergy() {
        log.info("[getAllExergy] try to get all exergy");
        return EcExergyConvert.instance.convertToEcExergyVoList(ecExergyMapper.selectList(new QueryWrapper<>()));
    }

    @Override
    public IPage<EcExergyVo> page(Page<EcExergy> page, QueryWrapper<EcExergy> queryWrapper) {
        IPage<EcExergy> pageList = ecExergyMapper.selectPage(page, queryWrapper);
        return pageList.convert(EcExergyConvert.instance::convertToEcExergyVo);
    }

    @Override
    @Transactional
    public void addExergy(EcExergyReqVo ecExergyReqVo) {
        ecExergyMapper.insert(EcExergyConvert.instance.convertToEcExergy(ecExergyReqVo));
    }

    @Override
    @Transactional
    public void editExergy(EcExergyReqVo ecExergyReqVo) {
        EcExergy exergy = EcExergyConvert.instance.convertToEcExergy(ecExergyReqVo);
        exergy.setId(ecExergyReqVo.getId());
        ecExergyMapper.updateById(exergy);

        // update all related plan status to 0
        List<String> planIdList = ecExergyMapper.selectRelatedPlanIdListByExergyId(exergy.getId());
        planIdList.forEach(item -> {
            EcPlan plan = ecPlanMapper.selectById(item);
            if (plan != null && StringUtils.isNotEmpty(plan.getId())) {
                plan.setStatus(0);
                ecPlanMapper.updateById(plan);
            }
        });
    }

    @Override
    public void deleteExergy(String id) {
        if (ecExergyFactorDetailMapper.selectCount(new QueryWrapper<EcExergyFactorDetail>().eq("exergy_id", id)) > 0) {
            log.error("与现有影响因子存在关联，无法删除");
            throw new RuntimeException("与现有影响因子存在关联，无法删除");
        }
        ecExergyMapper.deleteById(id);
    }
}
