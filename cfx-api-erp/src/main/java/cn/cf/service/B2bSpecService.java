package cn.cf.service;

import cn.cf.dto.B2bSpecDto;

public interface B2bSpecService {

	B2bSpecDto getByNameParent(String specName);

	String createNewSpec(String parentPk, String specName);

	B2bSpecDto getBySeariesName(String seriesName);

}
