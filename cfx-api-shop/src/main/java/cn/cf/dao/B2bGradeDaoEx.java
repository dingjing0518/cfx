package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bGradeDto;

public interface B2bGradeDaoEx extends B2bGradeDao {

	List<B2bGradeDto> searchGradeNameByGradePks(Map<String, Object> map);

}
