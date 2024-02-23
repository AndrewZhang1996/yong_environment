package org.jeecg.modules.ec.service.impl;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.ec.entity.EcFactor;
import org.jeecg.modules.ec.mapper.EcFactorMapper;
import org.jeecg.modules.ec.service.IEcFactorService;
import org.jeecg.modules.ec.vo.EcExergyFactorReqVo;
import org.jeecg.modules.ec.vo.EcExergyFactorRespVo;
import org.jeecg.modules.ec.vo.EcFactorVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 09:10:06
 */
@Slf4j
@Service
public class EcFactorServiceImpl implements IEcFactorService {

    @Resource
    private EcFactorMapper ecFactorMapper;

    /**
     * 新增
     *
     * @param factor factor
     * @return ID
     */
    @Override
    public String insert(EcFactor factor) {
        log.info("[insert] factor: {}", factor);
        int effectedLines = 0;
        try {
            effectedLines = ecFactorMapper.insert(factor);
        } catch (DuplicateKeyException duplicateKeyException) {
            log.error("duplicate key, factor.name = {}", factor.getName());
        }
        if (effectedLines < 1) {
            log.error("insert new factor error! factor.name = {}", factor.getName());
        }
        return factor.getId();
    }

    @Override
    public List<EcExergyFactorRespVo> getAllFactorsByExergyFactorDetailId(String exergyFactorDetailId) {
        log.info("[getAllFactorsByExergyFactorDetailId] exergyFactorDetailId: {}", exergyFactorDetailId);
        List<EcExergyFactorRespVo> result = ecFactorMapper.getAllFactorsByExergyFactorDetailId(exergyFactorDetailId);
        result.forEach((item) -> item.setKey(UUID.fastUUID().toString(true)));
        return result;
    }
}
