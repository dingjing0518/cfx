package cn.cf.service.yarn;


import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SxTechnologyExtDto;

public interface SxTechnologyService {

    PageModel<SxTechnologyExtDto> searchTechnology(QueryModel<SxTechnologyExtDto> qm);

    int updateTechnology(SxTechnologyExtDto sxTechnologyExtDto);

    int updateTechnologyExt(SxTechnologyExtDto sxTechnologyExtDto);

   

}
