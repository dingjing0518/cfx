package cn.cf.service;

import cn.cf.PageModel;
import cn.cf.dto.LgUserAddressDto;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

/**
 * Created by Thinkpad on 2017/10/23.
 */
public interface LgUserAddressService {
	
	/**
	 * 查询列表地址
	 * @param map 参数
	 * @return
	 */
    PageModel<LgUserAddressDto> searchAddress(Map<String, Object> map);

    
    /**
     * 根据pk查询地址
     * @param pk 地址pk
     * @return
     */
    LgUserAddressDto searchAddressByPk(String pk);

    /**
     * 添加地址
     * @param dto 地址
     * @return
     */
    String addAddress(LgUserAddressDto dto);

    /**
     * 更新地址
     * @param dto 参数
     * @return
     */
    @Transactional
    String updateAddress(LgUserAddressDto dto);

    
    /**
     * 删除地址
     * @param pk 地址pk
     */
    void delAddress(String pk);

    /**
     * 查询地址是否存在
     * @param dto 地址参数
     * @return
     */
    Integer searchEntity(LgUserAddressDto dto);
    
}

