package cn.cf.dao;


import cn.cf.dto.LgTrackDto;
import java.util.List;

public interface  LgTrackDetailExDao extends LgTrackDetailDao{

    List<LgTrackDto> selectTrackDetailBydeliveryPk(String pk);

    int insert(LgTrackDto lgTrackDto);

}
