package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.annotation.MappingIgnore;
import org.jeecg.modules.ec.entity.EcExergyFactorDetailHis;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EcExergyFactorDetailHisConvert {
    EcExergyFactorDetailHisConvert instance = Mappers.getMapper(EcExergyFactorDetailHisConvert.class);

    @MappingIgnore
    EcExergyFactorDetailHis convertToEcExergyFactorDetailHis(EcExergyFactorDetailVo exergyFactorDetailVo);
}
