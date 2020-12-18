package cn.cf.service.yarn;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SxSpecDto;
import cn.cf.dto.SxSpecExtDto;

public interface SxSpecExtService {
    /**
     * 工艺类型列表
     * @param qm
     * @return
     */
    PageModel<SxSpecExtDto> searchSxSpecList(QueryModel<SxSpecExtDto> qm);

    int updateSxSpec(SxSpecExtDto dto);

    int del(String pk);

    int updateSxSpecStatus(SxSpecExtDto dto);

    List<SxSpecDto> searchSxSpecList(Map<String,Object> map);

    List<SxSpecDto> searchAllSxSpecList();
}
