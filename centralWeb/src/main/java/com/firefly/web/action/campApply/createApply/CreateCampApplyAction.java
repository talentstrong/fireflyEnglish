package com.firefly.web.action.campApply.createApply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.firefly.dto.CustomerDto;
import com.firefly.model.CampApply;
import com.firefly.service.CampApplyService;
import com.firefly.service.framework.BasicCode;
import com.firefly.web.framework.PageCookie;
import com.firefly.web.framework.PageSession;
import com.firefly.web.framework.action.SimpleJsonAction;
import com.firefly.web.framework.model.BaseJsonModel;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j
public class CreateCampApplyAction extends SimpleJsonAction<CreateCampApplyInputVo, BaseJsonModel> {
    private static final String PAGE_URL_PARTTEN = "/user/sessionUser";

    @Autowired
    private CampApplyService campApplyService;

    @Override
    protected BaseJsonModel doBiz(CreateCampApplyInputVo inputVo, PageCookie cookie, PageSession session, CustomerDto customer) throws Exception {
        BaseJsonModel result = new BaseJsonModel();
        try {
            Assert.isTrue(!StringUtils.isEmpty(inputVo.getName()), "姓名必须填写");
//        Assert.isTrue(!StringUtils.isEmpty(inputVo.getWeixinAccount()), "微信号必须填写");
            Assert.isTrue(!StringUtils.isEmpty(inputVo.getMobile()), "手机号必须填写");
            Assert.isTrue(!StringUtils.isEmpty(inputVo.getChildAge()), "孩子年龄必须填写");
            Assert.isTrue(!StringUtils.isEmpty(inputVo.getCanSpeakEnglish()), "孩子是否有英语基础");
        } catch (IllegalArgumentException e) {
            result.setResult(BasicCode.FAILED);
            result.setRespMsg(e.getMessage());
        }

        CampApply campApply = new CampApply();
        BeanUtils.copyProperties(inputVo, campApply);

        if (customer != null) {
            campApply.setUserId(customer.getId());
        }
        campApplyService.createCampApply(campApply);

        result.setResult(BasicCode.SUCCESS);

        return result;
    }

    @ResponseBody
    @RequestMapping(PAGE_URL_PARTTEN)
    public Object handleRequest(HttpServletRequest request, HttpServletResponse response, CreateCampApplyInputVo inputVo)
            throws Exception {
        return super.handleRequest(request, response, inputVo);
    }
}
