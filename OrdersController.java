package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Orders;
import com.itheima.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    /*
        不带分页实现方式

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        List<Orders> orderslist = ordersService.findall();
        ModelAndView mv=new ModelAndView();
        System.out.println(orderslist);
        mv.addObject("ordersList",orderslist);
        mv.setViewName("orders-list");
        return mv;

    }*/
    //带分页效果
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "4") Integer size) {

        List<Orders> orderslist = ordersService.findall(page, size);
        PageInfo pageinfo=new PageInfo(orderslist);
        ModelAndView mv = new ModelAndView();
        System.out.println(orderslist);
        mv.addObject("pageinfo", pageinfo);
        mv.setViewName("orders-list");
        return mv;
    }
    //订单详情
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true) String id) {

        Orders orders = ordersService.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("orders", orders);
        mv.setViewName("orders-show");
        return mv;
    }
}
