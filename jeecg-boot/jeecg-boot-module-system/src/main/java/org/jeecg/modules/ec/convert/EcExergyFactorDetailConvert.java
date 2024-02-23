package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.entity.*;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailReqVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailVo;
import org.jeecg.modules.ec.vo.EcExergyFactorHisRespVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EcExergyFactorDetailConvert {
    EcExergyFactorDetailConvert instance = Mappers.getMapper(EcExergyFactorDetailConvert.class);

    EcExergyFactorDetailVo convertToEcExergyFactorDetailVo(EcExergyFactorDetail ecExergyFactorDetail);

    EcExergyFactorDetailRespVo convertToEcExergyFactorDetailRespVo(EcExergyFactorDetail ecExergyFactorDetail);

    List<EcExergyFactorDetailVo> convertToEcExergyFactorDetailVoList(List<EcExergyFactorDetail> ecExergyFactorDetailList);

    EcExergyFactorDetail convertToEcExergyFactorDetail(EcExergyFactorDetailReqVo ecExergyFactorDetailReqVo);

    EcExergyFactorHisRespVo convertToEcExergyFactorHisRespVo(EcExergyHis ecExergy);

    List<EcExergyFactorHisRespVo> convertToEcExergyFactorHisRespVoList(List<EcFactorHis> ecFactors);
}
