package cn.cf.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bGoodsEx;

public interface FacadeService {
	@Transactional
	RestCode addGood(B2bGoodsEx goods, Sessions session);

	B2bGoodsDtoEx getB2bGoodsByPk(String pk, String memberPk);

	RestCode importGoods(String strs, Sessions session );

	PageModel<B2bGoodsDtoEx> searchGoodsListBySalesMan(Map<String, Object> map,
			Integer isAdmin, B2bMemberDto memberDto, B2bCompanyDto companyDto);

}
