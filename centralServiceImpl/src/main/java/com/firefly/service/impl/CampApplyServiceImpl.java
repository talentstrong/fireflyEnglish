package com.firefly.service.impl;

import com.firefly.service.CampApplyService;
import com.firefly.service.dao.CampApplyDao;
import com.firefly.model.CampApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampApplyServiceImpl implements CampApplyService {

    @Autowired
    private CampApplyDao campApplyDao;

    @Override
    public long createCampApply(CampApply campApply) {

        int i = campApplyDao.createCampApply(campApply);

        if (i == 1) {
            return campApply.getId();
        } else {
            return 0;
        }
    }
}
