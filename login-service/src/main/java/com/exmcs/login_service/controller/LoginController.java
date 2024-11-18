package com.exmcs.login_service.controller;

import com.exmcs.login_service.common.base.BaseRequest;
import com.exmcs.login_service.common.base.BaseResponse;
import com.exmcs.login_service.model.request.PostSigninRequest;
import com.exmcs.login_service.model.response.PostSigninResponse;
import com.exmcs.login_service.service.PostSigninService;
import com.exmcs.login_service.service.PostSignoutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final PostSigninService postSigninService;

    private final PostSignoutService postSignoutService;

    public LoginController(PostSigninService postSigninService,
                           PostSignoutService postSignoutService) {
        this.postSigninService = postSigninService;
        this.postSignoutService = postSignoutService;
    }

    @PostMapping("/signin")
    public PostSigninResponse signing(@RequestBody PostSigninRequest request){
        return postSigninService.execute(request);
    }

    @PostMapping("/signout")
    public BaseResponse logout(){
        return postSignoutService.execute(new BaseRequest());
    }

}
