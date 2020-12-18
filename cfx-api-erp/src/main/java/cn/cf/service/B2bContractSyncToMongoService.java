package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.ContractSyncToMongo;

public interface B2bContractSyncToMongoService {

    /**
     * 查询
     * @param map
     * @param company
     * @return
     */
    PageModel<ContractSyncToMongo> searchContractSyncToMongoList(Map<String, Object> map, String storePk);

    void syncContractToMongo(String contractNo, B2bStoreDto b2bstore, String detail) throws Exception;
}
