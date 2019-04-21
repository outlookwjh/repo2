package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class PruductController {

    @Autowired
    ProductService productService;


   /*
    查询产品信息，不带参数
    @RequestMapping("/findAll.do")
    public ModelAndView finAll(){

        ModelAndView mv=new ModelAndView();
        List<Product> ps = productService.findAll();
        mv.addObject("productList",ps);
        mv.setViewName("product-list");
        return mv;
    }*/

    /**
     * 带分页的查询产品信息
     * @return
     */
   @RequestMapping("/findAll.do")
   public ModelAndView finAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "4") Integer size) {
       ModelAndView mv = new ModelAndView();
       List<Product> ps = productService.findAll(page,size);
       PageInfo pageInfo=new PageInfo(ps);
       mv.addObject("pageInfo", pageInfo);
       mv.setViewName("product-list");
       return mv;
   }

    /**
     * 添加产品信息
     * @param product
     * @return
     */
    @RequestMapping("/save.do")
    public String save(Product product){
       productService.save(product);
        return "redirect:findAll.do";
    }
}
