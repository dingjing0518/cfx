package cn.cf.service.yarn;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.model.ManageAccount;

public interface SxGoodsManageService {
    /**
     * 纱线商品
     * @param qm
     * @param account
     * @param i 
     * @return
     */
    PageModel<B2bGoodsExtDto> searchGoodsManageList(QueryModel<B2bGoodsExtDto> qm, ManageAccount account, int i);

	List<B2bGoodsExtDto> getExportGoodsNumbers(QueryModel<B2bGoodsExtDto> qm, ManageAccount account, int type);

    /**
     * 保存导出纱线商品记录
     * @param params
     * @param account
     * @return
     */
    int saveYarnGoodsToOss(GoodsDataTypeParams params, ManageAccount account);



}
