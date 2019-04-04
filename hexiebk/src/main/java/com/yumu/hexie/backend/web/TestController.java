/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.backend.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: TestController.java, v 0.1 2016年5月8日 下午10:59:16  Exp $
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String home() {
        System.out.println("ttttttttttttttttttttttttttttttttttttttttt");
        return "Hello World!";
    }
}
