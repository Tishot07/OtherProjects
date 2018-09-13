package com.java.controller;

import com.java.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

    /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Home() {
        return "Index";
    }
    */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView Home() {
        //Index - куда ходим перейти (страница с этим именем), userJSP - имя объекта типа Model
        //return new ModelAndView("hello", "user", new Model());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userJSP", new Model());
        modelAndView.setViewName("Index");
        return modelAndView;
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public ModelAndView checkUser(@ModelAttribute("userJSP") Model model) {

        System.out.println(model.getUser() + model.getPwd());

        ModelAndView modelAndView = new ModelAndView();
        //имя представления, куда нужно будет перейти
        modelAndView.setViewName("hello");

        //записываем в атрибут userJSP (используется на странице *.jsp объект user
        modelAndView.addObject("userJSP", model);

        return modelAndView;
    }
}
