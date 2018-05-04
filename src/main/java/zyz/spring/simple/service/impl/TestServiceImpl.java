package zyz.spring.simple.service.impl;

import zyz.spring.annotations.MyService;
import zyz.spring.annotations.MyValue;
import zyz.spring.simple.service.TestService;

@MyService
public class TestServiceImpl implements TestService {

    @MyValue("hello")
    String hello;
    public String getHello() {
        return "get from properties: "+hello;
    }

    public String handlerWord(String word) {
        return word;
    }
}
