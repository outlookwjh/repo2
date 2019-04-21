package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping("/findById.do")
    public ModelAndView finddetail(String id){
        Role role=roleService.findById(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }
    @RequestMapping("/findAll.do")
    public ModelAndView findall(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",defaultValue = "4") Integer size){
        List<Role> roles = roleService.findAll(page,size);
        ModelAndView mv=new ModelAndView();
        PageInfo pageInfo_role=new PageInfo(roles);
        mv.addObject("pageInfo_role",pageInfo_role);
        mv.setViewName("role-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Role role){
        System.out.println(role);
        roleService.saverole(role);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findRoleByIdAndAllPermisson.do")
    public ModelAndView findRoleByIdAndAllPermisson(@RequestParam(name = "id") String roleid){
        Role role = roleService.findById(roleid);
        List<Permission> permissionList=roleService.findpermisson(roleid);
        ModelAndView mv=new ModelAndView();
        mv.addObject("role",role);
        mv.addObject("permissionList",permissionList);
        mv.setViewName("role-permission-add");
        return mv;
    }

    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId") String roleId,@RequestParam(name = "pids") String[] pids){
        roleService.insertper(roleId,pids);
        return "redirect:findAll.do";
    }
}
