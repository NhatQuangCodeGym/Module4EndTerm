package com.cg.controller;

import com.cg.model.City;
import com.cg.model.Country;
import com.cg.service.CityService;
import com.cg.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    public ModelAndView getAllCity() {
        ModelAndView modelAndView = new ModelAndView("/cities/list");
        List<City> cities = cityService.findAll();
        modelAndView.addObject("cities",cities);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView addNewCity() {
        ModelAndView modelAndView = new ModelAndView("/cities/create");

        List<Country> countries = countryService.findAll();
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("city", new City());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView handlerAddNewCity(@Valid @ModelAttribute City city, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasFieldErrors()) {
            modelAndView.setViewName("/cities/create");
            modelAndView.addObject("city",city);
            modelAndView.addObject("cities", countryService.findAll());
            modelAndView.addObject("messages","Các trường dữ liệu không hợp lệ");
            return modelAndView;
        } else {
            cityService.save(city);
            modelAndView.setViewName("/list");
            List<City> cities = cityService.findAll();
            modelAndView.addObject("cities",cities);
            modelAndView.addObject("messages","Thêm thành phố mới thành công");
            return modelAndView;
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/cities/update");
        City city = cityService.findById(id);
        modelAndView.addObject("countries", countryService.findAll());
        modelAndView.addObject("city", city);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateCity(@Valid @ModelAttribute City city, BindingResult bindingResult,@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasFieldErrors()) {
            modelAndView.setViewName("/edit/"+id);
            modelAndView.addObject("city",city);
            modelAndView.addObject("nations", countryService.findAll());
            modelAndView.addObject("messages","Các trường dữ liệu không hợp lệ");
            return modelAndView;
        } else {
            city.setId(id);
            cityService.save(city);
            modelAndView.setViewName("/cities/list");
            List<City> cities = cityService.findAll();
            modelAndView.addObject("cities",cities);
            modelAndView.addObject("messages","Sửa thành phố mới thành công");
            return modelAndView;
        }


    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeletePage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/delete");
        City city = cityService.findById(id);
        modelAndView.addObject("countries", countryService.findAll());
        modelAndView.addObject("city", city);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView updateCity(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        cityService.remove(id);
        modelAndView.setViewName("/cities/list");
        List<City> cities = cityService.findAll();
        modelAndView.addObject("cities",cities);
        modelAndView.addObject("messages","Đã xóa thành phố thành công");
        return modelAndView;

    }

}
