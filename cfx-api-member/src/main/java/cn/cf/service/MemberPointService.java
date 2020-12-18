package cn.cf.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import cn.cf.entity.MangoMemberPoint;

public interface MemberPointService {
	
	/**
	 * 插入会员基础值
	 * @param memberPk
	 * @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作  
	 * @return
	 */
	@Transactional
	RestCode  addPoint (String memberPk,String companyPk,Integer pointType,String active);

	/**
	 * 插入会员基础值
	 * @param memberPk
	 * @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作
	 * @return
	 */
	@Transactional
	RestCode  addPoint3 (String memberPk,String companyPk,Integer pointType,String active, String companyType);

	
	/**
	 * 插入会员基础值(重载方法 勿删)
	 * @param memberPk
	 * @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作  
	 * @param flag 只新增一次  
	 * @return
	 */
	@Transactional
	RestCode  addPoint2 (String memberPk,String companyPk,Integer pointType,String active,Boolean flag);
	/**
	 * 收藏商品/店铺
	 * @param memberPk
	 * @param companyPk
	 * @param thing
	 * @param active
	 * @return
	 */
	RestCode  addPointForThing (String memberPk,String companyPk,String thing,String active);
	/**
	 * 插入会员基础值(额外奖励)
	 * @param memberPk
	 * @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作  
	 * @return
	 */
	@Transactional
	RestCode  addPointForExtReward (String memberPk,String companyPk,Integer pointType,String active);
	
	/**
	 * 插入交易维度
	 * @param memberPk
	 *  @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作
	 * @param total 总金额
	 * @return
	 */
	@Transactional
	RestCode  addPointForOrder (String orderNumber,int pointType);
	
	
	/**
	 * 给会员加额外奖励积分
	 * @param orderNumber  订单编号
	 * @param pointType  1增加分值；2减少分值
	 * @return
	 */
	@Transactional
	RestCode  addExtPointForOrder (String orderNumber,int pointType);
	
	
	/**
	 * 取消订单
	 * @param orderNumber
	 */
	void cancelOrder(String orderNumber);
	/**
	 * 取消收藏商品
	 * @param goodsPk
	 * @param member
	 */
	void cancelThing(String thingPk,String member);
	/**
	 * 查询会员基础值
	 * @param map(memberPk,companyPk,active)
	 * @return
	 */
	List<MangoMemberPoint>  searchPointList (Map<String, Object> map);
	
	/**
	 * 查询会员个人总分值
	 * @param memberPk
	 * @return
	 */
	Map<String, Object> getTotalPointByMember(String memberPk);

	List<B2bDimensionalityExtrewardDto> getDimensionalityExtReward(String value);
	
	
	
	
}

