package com.java.controller;

import com.java.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
        modelAndView.addObject("userJSP", new User());
        modelAndView.setViewName("Index");
        return modelAndView;
    }

    /* @Valid - чтобы применялась валидация к данным
    * BindingResult - идет после объекта, который проверяется (у нас model), в данный объект (BindingResult) записываютс ошибки, если они есть
    */

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public String checkUser(@Valid @ModelAttribute("userJSP") User model, BindingResult bindingResult, Model modelUI) {

        //если есть ошибки, возвращаем обравно на страницу ввода данных
        if(bindingResult.hasErrors()) {
            return "Index";
        }

        modelUI.addAttribute("userJSP", model);
        /*
        ModelAndView modelAndView = new ModelAndView();
        //имя представления, куда нужно будет перейти
        modelAndView.setViewName("hello");

        //записываем в атрибут userJSP (используется на странице *.jsp объект user
        modelAndView.addObject("userJSP", model);
        */
        return "hello";
    }
}
