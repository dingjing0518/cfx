package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bBindDto;
import cn.cf.dto.B2bBindDtoEx;
import cn.cf.dto.PageModelBind;
import cn.cf.model.B2bBind;

public interface B2bBindService {
    /**
     * 分页查询拼团活动列表
     *
     * @param map
     * @return
     */
	PageModelBind<B2bBindDtoEx> searchBindList(Map<String, Object> map);


    /**
     * 根据bindPK 查询拼团详情
     */
    B2bBindDto getBind(String pk);
    
    
    /**
     * 添加拼团活动
     * @param bindDto
     * @return
     */
    boolean addBind(B2bBind model);
    
    
    /**
     * 编辑拼团活动
     * @param bindDto
     * @return
     */
    boolean updateEx(B2bBind model);
    
    /**
     * 删除拼团活动
     * @param pk
     * @return
     */
    boolean delBind(String pk);

    
    /**
     * 编辑拼团活动
     * @param bindDto
     * @return
     */
    B2bBindDto editBind(B2bBindDto bindDto);
    
    /**
     * 上下架拼团活动
     * @param bindPk
     * @param isUpDown
     * @return
     */
    RestCode upDownBind(String bindPk,int isUpDown);
    
    
    /**
     * 拼团活动下拉列表
     * @param map
     * @return
     */
    List<B2bBindDto> getBindLists(Map<String, Object> map);
}
