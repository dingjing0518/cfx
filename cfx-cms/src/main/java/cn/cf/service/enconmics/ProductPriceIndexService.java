package cn.cf.service.enconmics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bProductExtDto;
import cn.cf.dto.B2bProductPriceIndexDto;
import cn.cf.dto.B2bProductPriceIndexExtDto;
import cn.cf.entity.PricProductIndexParams;
import cn.cf.entity.ProductPriceIndexEntry;
import cn.cf.model.B2bProductPriceIndex;
import cn.cf.model.ManageAccount;

import java.util.List;

public interface ProductPriceIndexService {

    /**
     * 查询品名价格指数列表
     * @param qm
     * @return
     */
    PageModel<B2bProductPriceIndexExtDto> searchProductPriceIndexLis(QueryModel<B2bProductPriceIndexExtDto> qm);

    /**
     * 查询所有的品名
     * @return
     */
    List<B2bProductDto> getProductList();

    /**
     * 根据pk查询品名价格指数
     * @param pk
     * @return
     */
    B2bProductPriceIndexDto getProductIndexByPk(String pk);

    /**
     * 更新产品价格指数
     * @param dto
     * @return
     */
    int updateProductIndex(B2bProductPriceIndex dto);

    /**
     * 查询品名价格指数维护列表
     * @param qm
     * @return
     */
    PageModel<ProductPriceIndexEntry> searchTransactionProductPriceIndexLis(QueryModel<ProductPriceIndexEntry> qm);

    /**
     * 导出价格指数excel数据
     * @param params
     * @param account
     */
    void saveProductPriceIndexExcelToOss(PricProductIndexParams params, ManageAccount account);
}
