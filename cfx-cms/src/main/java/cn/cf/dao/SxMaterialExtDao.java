package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxMaterialExtDto;
import org.apache.ibatis.annotations.Param;

public interface SxMaterialExtDao extends SxMaterialDao {

    SxMaterialDto getByMap(Map<String, Object> map);

    int searchGridExtCount(Map<String, Object> map);

    List<SxMaterialExtDto> searchGridExt(Map<String, Object> map);


    int searchGridExtTwoCount(Map<String, Object> map);

    List<SxMaterialExtDto> searchGridTwoExt(Map<String, Object> map);

    int updateMaterial(SxMaterialExtDto sxMaterialExtDto);


    List<SxMaterialDto> getSecondMaterialList(@Param("parentPk") String parentPk);

    int check(Map<String, Object> m);

}
