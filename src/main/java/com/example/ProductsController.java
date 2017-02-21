package com.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@Controller
public class ProductsController {

    // TODO(mona): Add a simple UI?
    @RequestMapping("/")
    @ResponseBody
    String home() {
      return "Hello World!";
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.GET)
    @ResponseBody
    String getProductInfo(@PathVariable("id") int id) {
      return "Some product info for id=" + id;
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.POST)
    @ResponseBody
    String updateProductInfo(@PathVariable("id") int id) {
      return "Updating product info for id=" + id;
    }

}