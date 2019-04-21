package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.PermissionService;
import com.itheima.service.impl.PermissionServiceImpl;
import com.itheima.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
   PermissionService permissionService;

    @RequestMapping("/findAll.do")
    public ModelAndView findall(@RequestParam(name = "page",defaultValue = "1")Integer page,@RequestParam(name = "size",defaultValue = "4") Integer size){
        ModelAndView mv=new ModelAndView();
        List<Permission> permissionList = permissionService.findAll(page,size);
        PageInfo pageInfo_per=new PageInfo(permissionList);
        mv.addObject("pageInfo_per",pageInfo_per);
        mv.setViewName("permission-list");
        return mv;

    }
    @RequestMapping("/save.do")
    public String save(Permission permission){
        permissionService.saveper(permission);
        return "redirect:findAll.do";
    }
}
