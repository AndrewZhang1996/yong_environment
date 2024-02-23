package org.jeecg.modules.ec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.ec.convert.EcExergyFactorDetailConvert;
import org.jeecg.modules.ec.convert.EcFactorConvert;
import org.jeecg.modules.ec.entity.*;
import org.jeecg.modules.ec.mapper.*;
import org.jeecg.modules.ec.service.IEcExergyFactorDetailService;
import org.jeecg.modules.ec.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zys
 * @version 1.0
 * @date 2022/11/07 10:26:15
 */
@Slf4j
@Service
public class EcExergyFactorDetailServiceImpl implements IEcExergyFactorDetailService {

    @Resource
    private EcExergyFactorDetailMapper ecExergyFactorDetailMapper;

    @Resource
    private EcExergyFactorMapper ecExergyFactorMapper;

    @Resource
    private EcExergyMapper ecExergyMapper;

    @Resource
    private EcFactorMapper ecFactorMapper;

    @Resource
    private EcPlanMapper ecPlanMapper;

    @Resource
    private EcPhaseExergyFactorMapper ecPhaseExergyFactorMapper;

    @Override
    public IPage<EcExergyFactorDetailRespVo> page(Page<EcExergyFactorDetail> page, QueryWrapper<EcExergyFactorDetail> queryWrapper) {
        IPage<EcExergyFactorDetail> pageList = ecExergyFactorDetailMapper.selectPage(page, queryWrapper);
        return pageList.convert(item -> {
            EcExergyFactorDetailRespVo ecExergyFactorDetailRespVo = EcExergyFactorDetailConvert.instance.convertToEcExergyFactorDetailRespVo(item);
//            if (StringUtils.isNotEmpty(item.getExergyId())) {
//                EcExergy ecExergy = ecExergyMapper.selectById(item.getExergyId());
//                if (ecExergy != null) {
//                    ecExergyFactorDetailRespVo.setExergyName(ecExergy.getName());
//                }
//            }
            return ecExergyFactorDetailRespVo;
        });
    }

    @Override
    @Transactional
    public void addNewExergyFactor(EcExergyFactorDetailReqVo ecExergyFactorDetailReqVo) {
        ecExergyFactorDetailReqVo.setId(null);
        EcExergyFactorDetail ecExergyFactorDetail = EcExergyFactorDetailConvert.instance.convertToEcExergyFactorDetail(ecExergyFactorDetailReqVo);

        // 1. insert ecExergyFactorDetail
        ecExergyFactorDetailMapper.insert(ecExergyFactorDetail);
        if (StringUtils.isEmpty(ecExergyFactorDetail.getId())) {
            log.error("插入影响因子详情失败");
            throw new RuntimeException("插入影响因子详情失败");
        }

        List<EcExergyFactorReqVo> exergyFactorList = ecExergyFactorDetailReqVo.getExergyFactorList();
        exergyFactorList.forEach(exergyFactor -> {
            exergyFactor.getFactor().setId(null);
            EcFactor ecFactor = EcFactorConvert.instance.convertToEcFactor(exergyFactor.getFactor());

            // 2. insert all ecFactor
            ecFactorMapper.insert((ecFactor));

            // 3. insert all relations
            if (StringUtils.isEmpty(ecFactor.getId())) {
                log.error("插入因子失败");
                throw new RuntimeException("插入因子失败");
            }
            ecExergyFactorMapper.insert(EcExergyFactor.builder().exergyFactorDetailId(ecExergyFactorDetail.getId()).factorId(ecFactor.getId()).exergyId(exergyFactor.getExergyId()).build());
        });
    }

    @Override
    @Transactional
    public void editExergyFactor(EcExergyFactorDetailReqVo ecExergyFactorDetailReqVo) {
        if (StringUtils.isEmpty(ecExergyFactorDetailReqVo.getId())) {
            log.error("请求的ecExergyFactorDetailReqVo的id为空或空白！");
            throw new RuntimeException("请求的ecExergyFactorDetailReqVo的id为空或空白！");
        }
        EcExergyFactorDetail ecExergyFactorDetail = EcExergyFactorDetailConvert.instance.convertToEcExergyFactorDetail(ecExergyFactorDetailReqVo);
        ecExergyFactorDetail.setId(ecExergyFactorDetailReqVo.getId());

        // 1. update ecExergyFactorDetail
        log.info("更新ecExergyFactorDetail: {}", ecExergyFactorDetail);
        ecExergyFactorDetailMapper.updateById(ecExergyFactorDetail);

        List<EcExergyFactorReqVo> exergyFactorList = ecExergyFactorDetailReqVo.getExergyFactorList();
        List<EcExergyFactor> exergyFactorListUpdated = new ArrayList<>();
        exergyFactorList.forEach(exergyFactor -> {
            EcFactor ecFactor = EcFactorConvert.instance.convertToEcFactor(exergyFactor.getFactor());
            ecFactor.setId(exergyFactor.getFactor().getId());

            // 2. update or insert the factor
            if (StringUtils.isEmpty(ecFactor.getId())) {
                ecFactorMapper.insert(ecFactor);
            } else {
                ecFactorMapper.updateById(ecFactor);
            }
            if (StringUtils.isEmpty(ecFactor.getId())) {
                log.error("插入因子失败");
                throw new RuntimeException("插入因子失败");
            }
            exergyFactorListUpdated.add(EcExergyFactor.builder().factorId(ecFactor.getId()).exergyId(exergyFactor.getExergyId()).build());
        });

        // 3. delete all old relations and insert all new relations
//        List<String> needToDeleteFactorsId = ecExergyFactorMapper.selectList(new QueryWrapper<EcExergyFactor>().eq("exergy_factor_detail_id", ecExergyFactorDetail.getId()))
//                .stream()
//                .filter((item) -> exergyFactorListUpdated.stream().filter(exergyFactor -> exergyFactor.getFactorId().equals(item.getFactorId()) && exergyFactor.getExergyId().equals(item.getExergyId())
//                ).collect(Collectors.toList()).size() == 0)
//                .map(EcExergyFactor::getFactorId)
//                .collect(Collectors.toList());
        // delete not existed factors
//        needToDeleteFactorsId.forEach((item) -> ecFactorMapper.deleteById(item));
        ecExergyFactorMapper.delete(new QueryWrapper<EcExergyFactor>().eq("exergy_factor_detail_id", ecExergyFactorDetail.getId()));
        exergyFactorListUpdated.forEach((item) -> {
            ecExergyFactorMapper.insert(EcExergyFactor.builder().exergyFactorDetailId(ecExergyFactorDetail.getId()).factorId(item.getFactorId()).exergyId(item.getExergyId()).build());
        });
        // 4. set all related plan status to 0
        List<String> planIdList = ecPhaseExergyFactorMapper.selectRelatedPlanIdListByExergyFactorDetailId(ecExergyFactorDetail.getId());
        planIdList.forEach(item -> {
            EcPlan plan = ecPlanMapper.selectById(item);
            if (plan != null && StringUtils.isNotEmpty(plan.getId())) {
                plan.setStatus(0);
                ecPlanMapper.updateById(plan);
            }
        });
    }

    @Override
    public List<EcExergyFactorDetailRespVo> getAllExergyFactor() {
        log.info("[getAllExergyFactor] try to get all exergy factor");
        return ecExergyFactorDetailMapper.getAllExergyFactor();
    }

    @Override
    @Transactional
    public void deleteExergyFactor(String id) {
        if (ecPhaseExergyFactorMapper.selectCount(new QueryWrapper<EcPhaseExergyFactor>().eq("exergy_factor_detail_id", id)) > 0) {
            log.error("与现有方案存在关联，无法删除");
            throw new RuntimeException("与现有方案存在关联，无法删除");
        }
        ecExergyFactorDetailMapper.deleteById(id);
        List<EcExergyFactor> exergyFactors = ecExergyFactorMapper.selectList(new QueryWrapper<EcExergyFactor>().eq("exergy_factor_detail_id", id));
        exergyFactors.forEach(item -> {
            ecFactorMapper.deleteById(item.getFactorId());
            ecExergyFactorMapper.deleteById(item.getId());
        });
    }
}
