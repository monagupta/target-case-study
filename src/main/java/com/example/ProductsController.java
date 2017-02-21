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
    Product getProductInfo(@PathVariable("id") int id) {
      return new Product(id, "test name");
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.POST)
    @ResponseBody
    Product updateProductInfo(@PathVariable("id") int id) {
      return new Product(id, "test name");
    }

}
