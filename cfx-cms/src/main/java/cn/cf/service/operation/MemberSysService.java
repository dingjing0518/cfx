package cn.cf.service.operation;

import java.util.List;

import cn.cf.model.ManageAccount;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bDimensionalityDto;
import cn.cf.dto.B2bDimensionalityExtrewardExtDto;
import cn.cf.dto.B2bDimensionalityPresentExDto;
import cn.cf.dto.B2bDimensionalityRelevancyDto;
import cn.cf.dto.B2bDimensionalityRelevancyExtDto;
import cn.cf.dto.B2bDimensionalityRewardExtDto;
import cn.cf.dto.B2bMemberGradeExtDto;
@Transactional
public interface MemberSysService {

	/**
	 * 查询维度列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bDimensionalityDto> searchDimensionalityList(QueryModel<B2bDimensionalityDto> qm);
	
	/**
	 * 查询所有未删除维度
	 * @param qm
	 * @return
	 */
	List<B2bDimensionalityDto> getAllDimensionalityList();
	
	/**
	 * 修改维度
	 * @param dto
	 * @return
	 */
	int updateDimensionality(B2bDimensionalityDto dto);
	
	/**
	 * 根据维度pk查询
	 * @param pk
	 * @return
	 */
	B2bDimensionalityDto geteByDimensionalityPk(String pk);
	
	
	/**
	 * 查询维度类型列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bDimensionalityRelevancyExtDto> searchDimensionalityRelevancyList(QueryModel<B2bDimensionalityRelevancyExtDto> qm);
	
	
	/**
	 * 根据维度查询对应的类型
	 * @param qm
	 * @return
	 */
	List<B2bDimensionalityRelevancyDto> getDimenReByCategory(Integer type);
	
	/**
	 * 更新维度类型表
	 * @param dto
	 * @return
	 */
	int updateDimensionalityRelevancy(B2bDimensionalityRelevancyExtDto dto);
	
	/**
	 * 奖励管理列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bDimensionalityRewardExtDto> searchDimensionalityRewardList(QueryModel<B2bDimensionalityRewardExtDto> qm);
	
	
	/**
	 * 维度赠送列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bDimensionalityPresentExDto> searchDimensionalityPresentList(QueryModel<B2bDimensionalityPresentExDto> qm, ManageAccount account);
	
	/**
	 * 额外赠送列表查询
	 * @param qm
	 * @return
	 */
	PageModel<B2bDimensionalityPresentExDto> searchDimensionalityExtPresentList(QueryModel<B2bDimensionalityPresentExDto> qm,ManageAccount account);
	
	
	/**
	 * 更新奖励管理
	 * @param dto
	 * @return
	 */
	int updateDimensionalityReward(B2bDimensionalityRewardExtDto dto);
	
	
	/**
	 * 额外奖励管理列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bDimensionalityExtrewardExtDto> searchDimenExtrewardList(QueryModel<B2bDimensionalityExtrewardExtDto> qm);
	
	/**
	 * 更新额外奖励管理
	 * @param dto
	 * @return
	 */
	int updateDimenExtreward(B2bDimensionalityExtrewardExtDto dto);
	
	
	/**
	 * 会员等级设置列表
	 * @param qm
	 * @return
	 */
	PageModel<B2bMemberGradeExtDto> searchMemberGradeList(QueryModel<B2bMemberGradeExtDto> qm);
	
	/**
	 * 会员等级设置更新
	 * @param dto
	 * @return
	 */
	int updateMemberGrade(B2bMemberGradeExtDto dto);
	/**
	 * 搜索会员维度赠送列表
	 * @param qm
	 * @return
	 */
	List<B2bDimensionalityPresentExDto> searchDimensionalityPresentExList(
			QueryModel<B2bDimensionalityPresentExDto> qm);
	/**
	 * 删除会员维度奖励管理数据
	 * @param dto
	 * @throws Exception
	 */
	void delDimensionalityReward(B2bDimensionalityRewardExtDto dto) throws Exception;
	/**
	 * 删除会员维度额外奖励管理数据
	 * @param dto
	 * @throws Exception
	 */
	void delDimenExtreward(B2bDimensionalityExtrewardExtDto dto) throws Exception;
	/**
	 * 查询会员维度赠送集合
	 * @param pks
	 * @return
	 */
	List<B2bDimensionalityPresentExDto> exportDimensionalityExtPresentList(String pks,ManageAccount account);
	/**
	 * 根据pks数组条件查询会员维度赠送集合
	 * @param pks
	 * @return
	 * @throws Exception
	 */
    //List<B2bDimensionalityPresentExDto> exportDimensionalityHistoryList(String[] pks,ManageAccount account) throws Exception ;

}
