package cn.cf.service.yarn;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxMaterialExtDto;

import java.util.List;

public interface SxMaterialService {

    PageModel<SxMaterialExtDto> searchRawMaterialList(QueryModel<SxMaterialExtDto> qm);

    PageModel<SxMaterialExtDto> searchRawMaterialListTwo(QueryModel<SxMaterialExtDto> qm);

    int updateMaterail(SxMaterialExtDto sxMaterialExtDto);

    int updateMaterailEx(SxMaterialExtDto sxMaterialExtDto);

    List<SxMaterialDto> searchParentRawMaterial(SxMaterialDto dto);
    
	List<SxMaterialDto> getchangeSecondMaterial(String rawMaterialPk);

}
