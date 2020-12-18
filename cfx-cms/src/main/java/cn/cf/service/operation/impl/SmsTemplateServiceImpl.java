package cn.cf.service.operation.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bRoleExtDao;
import cn.cf.dao.SysSmsRoleExtDao;
import cn.cf.dao.SysSmsTemplateExtDao;
import cn.cf.dto.B2bRoleExtDto;
import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.dto.SysSmsTemplateExtDto;
import cn.cf.service.operation.SmsTemplateService;
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

	@Autowired
	private SysSmsTemplateExtDao sysSmsTemplateExtDao;
	
	@Autowired
	private B2bRoleExtDao b2bRoleExtDao;
	
	@Autowired
	private SysSmsRoleExtDao smsRoleExtDao;

	@Override
	public PageModel<SysSmsTemplateDto> searchsmsTemplateList(QueryModel<SysSmsTemplateDto> qm) {
		
		
		PageModel<SysSmsTemplateDto> pm = new PageModel<SysSmsTemplateDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("freeSignName", qm.getEntity().getFreeSignName());
		map.put("type", qm.getEntity().getType());
		int totalCount = sysSmsTemplateExtDao.searchGridCount(map); 
		List<SysSmsTemplateDto> list = sysSmsTemplateExtDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateSmsTemplate(SysSmsTemplateDto dto) {
		
		SysSmsTemplateExtDto model=new SysSmsTemplateExtDto();
		BeanUtils.copyProperties(dto, model);
		return sysSmsTemplateExtDao.update(model);
	}

	@Override
	public PageModel<B2bRoleExtDto> getRoleListBySms(QueryModel<B2bRoleExtDto> qm, String smsName) {
		Map<String,Object> map = new HashMap<String,Object>();
		PageModel<B2bRoleExtDto> pm = new PageModel<B2bRoleExtDto>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("smsName", smsName);
		map.put("companyType", qm.getEntity().getCompanyType());
		List<B2bRoleExtDto> list = b2bRoleExtDao.getRoleListBySms(map);
		int counts =  b2bRoleExtDao.getRoleCountBySms(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int insertSmsRole(List<Map<String, Object>> list) {
		
		try {
			if(list!= null && list.size()>0){
				String smsName = list.get(0).get("smsName").toString();
				smsRoleExtDao.deleteBysmsName(smsName);
				for (int i = 0; i < list.size(); i++) {
					smsRoleExtDao.insert(list.get(i));
					SysSmsTemplateExtDto dto=sysSmsTemplateExtDao.getByNames(smsName);
					if(null!=dto&&!"".equals(dto)){
						String  roles = list.get(0).get("rolePk").toString();
						if("".equals(roles)){
							dto.setFlag(1);
						}else{
							dto.setFlag(2);
						}
						SysSmsTemplateDto sysSmsTemplate=new SysSmsTemplateDto();
						BeanUtils.copyProperties(dto,sysSmsTemplate );
						sysSmsTemplateExtDao.update(sysSmsTemplate);
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
