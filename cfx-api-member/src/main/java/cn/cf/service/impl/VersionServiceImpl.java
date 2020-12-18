package cn.cf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.SysVersionManagementDao;
import cn.cf.dto.SysVersionManagementDto;
import cn.cf.service.VersionService;
@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    SysVersionManagementDao sysVersionManagementDao;

    @Override
    public SysVersionManagementDto searchVersionRecent(String type) {
            Map<String, Object> map = new HashMap<String, Object>();
            String terminal="";
            if (type.equals("ios")){
                terminal="2";
            }else{
                terminal="1";
            }
            map.put("terminal",terminal);
            return sysVersionManagementDao.searchOne(map);
        }
    }

