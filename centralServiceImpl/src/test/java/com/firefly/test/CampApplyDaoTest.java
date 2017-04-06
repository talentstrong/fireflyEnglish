package com.firefly.test;

import com.firefly.model.CampApply;
import com.firefly.service.CampApplyService;
import com.firefly.service.dao.CampApplyDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/central-service-dao.xml",
        "classpath*:config/central-service-test-datasource.xml",
        "classpath*:config/central-service-test-property.xml"})
public class CampApplyDaoTest {
    @Autowired
    CampApplyDao campApplyDao;
    @Autowired
    CampApplyService campApplyService;

    @Test
    public void createCustomerRewardTest() throws Exception {
        CampApply campApply = new CampApply();
        campApply.setMobile("13829301938");
        campApply.setCanSpeakEnglish("会");
        campApply.setChildAge("11");
        campApply.setWeixinAccount("test");
        campApply.setName("姓名");
        long id = campApplyService.createCampApply(campApply);
        System.out.println(id);
    }
}
