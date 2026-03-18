package com.platform.modules.app.controller;

import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallFeedbackEntity;
import com.platform.modules.app.request.ApiFeedbackSaveRequest;
import com.platform.modules.mall.service.MallFeedbackService;
import com.platform.modules.wx.entity.WxUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * <p>
 * 描述: AppFeedbackController <br>
 */
@Tag(name = "会员反馈-AppFeedbackController")
@RestController
@RequestMapping("/app/feedback")
public class AppFeedbackController extends AppBaseController {
    @Autowired
    private MallFeedbackService feedbackService;

    /**
     * 添加反馈
     */
    @Operation(summary = "添加反馈")
    @PostMapping("save")
    public RestResponse<Map<String, Object>> save(@LoginUser WxUserEntity loginUser, @RequestBody ApiFeedbackSaveRequest request) {
        if (null != request) {
            MallFeedbackEntity feedbackVo = new MallFeedbackEntity();
            feedbackVo.setUserId(loginUser.getId().intValue());
            feedbackVo.setUserName(loginUser.getNickname());
            feedbackVo.setMobile(request.getMobile());
            feedbackVo.setFeedType(request.getIndex());
            feedbackVo.setStatus(1);
            feedbackVo.setContent(request.getContent());
            feedbackVo.setAddTime(new Date());
            feedbackService.save(feedbackVo);
            return RestResponse.ok("感谢你的反馈");
        }
        return RestResponse.fail("反馈失败");
    }
}
