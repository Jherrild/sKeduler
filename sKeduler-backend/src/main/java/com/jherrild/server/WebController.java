/**
 * Copyright 2018 Expedia, Inc. All rights reserved. EXPEDIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jherrild.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jherrild@expedia.com
 * Created on 3/22/18
 */
@RestController
@EnableAutoConfiguration
public class WebController {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(WebController.class, args);
    }

    @RequestMapping(value="/status", method=RequestMethod.GET)
    @ResponseBody
    public String Status() {
        return "Test";
    }
}
