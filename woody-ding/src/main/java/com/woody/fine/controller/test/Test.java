package com.woody.fine.controller.test;

import com.woody.fine.service.test.TestService;
import com.woody.framework.redis.RedisManager;
import com.woody.framework.utils.ConfigUitl;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/test")
public class Test {

    @Autowired
    private TestService bookService;

    @RequestMapping("/book")
    @ResponseBody
    public String queryBookPrice(String name) {

        return bookService.queryBookpriceByName(name);
    }

    @RequestMapping("/info")
    @ResponseBody
    public void bookInfo() {
        System.out.println("Hi");
        System.out.println(ConfigUitl.getValue("redis.port"));
    }

    @RequestMapping("/bookPage")
    public ModelAndView queryBookPage(String name) {

        String price = bookService.queryBookpriceByName(name);

        ModelAndView md = new ModelAndView();
        md.addObject("bookPirce", price);
        md.addObject("bookName", name);
        md.setViewName("book");

        return md;
    }

    @RequestMapping("/redis")
    @ResponseBody
    public String redisTest() throws Exception {

        Jedis jedis = RedisManager.getJedisPool();
        jedis.set("test","20180825");

        return jedis.get("test");
    }

    //创建数据库表
    @RequestMapping("/table")
    @ResponseBody
    public String  createDataTable(String tableName) {
        String result = bookService.createTable(tableName);

        return result;
    }
}
