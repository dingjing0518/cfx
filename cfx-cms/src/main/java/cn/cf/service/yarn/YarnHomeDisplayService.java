package cn.cf.service.yarn;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.*;
import cn.cf.model.SxSpec;

import java.util.List;
import java.util.Map;

public interface YarnHomeDisplayService {


    /**
     * 全部未删除的工艺
     * 
     * @return
     */
    List<SxTechnologyDto> getAllTechnologyList();

    /**
     * 全部未删除未禁用的工艺
     * 
     * @return
     */
    List<SxTechnologyDto> getNoIsVisTechnologyList();


    /**
     * 查询未删除的原料
     * 
     * @return
     */
    List<SxMaterialDto> getAllMaterialList();

    /**
     * 查询未删除，未禁用的一级原料
     * 
     * @return
     */
    List<SxMaterialDto> getNoIsVisMaterialList(String parentPk);


    /**
     * 根据条件查询不同等级原料
     *
     * @return
     */
    List<SxMaterialDto> getMaterialListByGrade(Map<String,Object> map);


    /**
     * 查询规格
     *
     * @return
     */
    List<SxSpecDto> getSxSpecList();


}
