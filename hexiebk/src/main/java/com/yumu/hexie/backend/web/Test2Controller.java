package com.yumu.hexie.backend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.backend.web.dto.BaseResult;

@Controller(value = "abcController")
public class Test2Controller extends BaseController{
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	@ResponseBody
    public BaseResult<String> test2() throws Exception {
        return BaseResult.successResult("测试成功");
    }

}
