package cn.cf.dao;

import cn.cf.dto.B2bContractExtDto;
import cn.cf.dto.B2bOrderExtDto;

import java.util.List;
import java.util.Map;

public interface MarketingOrderMemberExtDao extends  MarketingOrderMemberDao {
    int searchOrderCount(Map<String, Object> map);

    List<B2bOrderExtDto> searchOrderList(Map<String, Object> map);

	List<B2bOrderExtDto> exportOrderList(Map<String, Object> map);

	int searchContactCount(Map<String, Object> map);

	List<B2bContractExtDto> searchContactList(Map<String, Object> map);
}
