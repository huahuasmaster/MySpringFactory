package zyz.spring.simple.controller;

import zyz.spring.annotations.MyAutowired;
import zyz.spring.annotations.MyController;
import zyz.spring.annotations.MyRequestMapping;
import zyz.spring.annotations.MyValue;
import zyz.spring.simple.service.TestService;
import zyz.spring.simple.service.impl.TestServiceImpl;

@MyController(path = "/test2")
public class TestController {



    @MyAutowired
    private TestService testService;

    @MyRequestMapping(path = "/sayhello")
    public String sayHello() {
        return testService.getHello();
    }

    @MyRequestMapping(path = "/saysth/{word}")
    public String saySth(String word) {
        return testService.handlerWord(word);
    }
}
