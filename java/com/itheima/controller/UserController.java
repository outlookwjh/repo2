package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    userService userService;



    /**
     * 查询所有用户
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "4") Integer size){
        List<UserInfo> userlist=userService.findAll(page,size);
        PageInfo pageInfo=new PageInfo(userlist);
        ModelAndView mv=new ModelAndView();
        mv.addObject("pageInfo_user",pageInfo);
        mv.setViewName("user-list");
        return mv;
    }
    /**
     * 添加用户
     * @return
     */
    @RolesAllowed("ADMIN")
    @RequestMapping("/save.do")
    public String save(UserInfo userInfo){
        userService.save(userInfo);
       return "redirect:findAll.do";
    }


    /**
     * 查询用户详情
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/findById.do")
    public ModelAndView finddetail(String id){
       UserInfo userInfo=userService.findById(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("user",userInfo);
        mv.setViewName("user-show1");
        return mv;
    }

    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(String id){
        ModelAndView mv=new ModelAndView();
        UserInfo info = userService.findById(id);
        List<Role> roleList=userService.findrole(id);
        mv.addObject("user",info);
        mv.addObject("roleList",roleList);
        mv.setViewName("user-role-add");
        return mv;
    }

    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId") String uid,@RequestParam(name = "ids") String[] ids){
        userService.insertrole(uid,ids);
        return "redirect:findAll.do";
    }
}
