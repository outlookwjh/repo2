package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Syslog;
import com.itheima.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "10")Integer size){
       List<Syslog> syslog=sysLogService.findall(page,size);
        ModelAndView mv=new ModelAndView();
        PageInfo pageInfo_log=new PageInfo(syslog);
        mv.addObject("pageInfo_log",pageInfo_log);
        mv.setViewName("syslog-list");
        return mv;
    }
}
