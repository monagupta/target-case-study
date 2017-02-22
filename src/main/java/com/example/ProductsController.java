package com.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.http.*;
import org.springframework.core.*;

import com.google.gson.*;

@Controller
public class ProductsController {

    private final static String API_URL = "http://redsky.target.com/v1/pdp/tcin/";
    private final static String API_EXCLUDES = "taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    private RestTemplate restTemplate = new RestTemplate();
    private DbHelper dbHelper = DbHelper.getInstance();

    // TODO(mona): Add a simple UI?
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.GET)
    @ResponseBody
    Product getProductInfo(@PathVariable("id") int id) {
        String name = fetchNameForId(id);
        Price currentPrice = dbHelper.fetchCurrentPriceForId(id);
        return new Product(id, name, currentPrice);
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.PUT)
    @ResponseBody
    Product updateProductInfo(@PathVariable("id") int id, @RequestBody Price price) {
        dbHelper.updatePriceForId(id, price);
        return getProductInfo(id);
    }

    private String constructProductNameUrl(int id) {
        return API_URL + id + "?excludes=" + API_EXCLUDES;
    }

    private String fetchNameForId(int id) {
        String url = constructProductNameUrl(id);
        String name;
        try {
            String response = restTemplate.getForObject(url, String.class);
            name = parseNameFromJson(response);
        } catch (HttpClientErrorException|JsonParseException|IllegalStateException e) {
            name = "Unable to fetch product name";
        }

        return name;
    }

    private String parseNameFromJson(String jsonAsString) throws JsonParseException, IllegalStateException {
        JsonParser parser = new JsonParser();
        JsonObject element = (JsonObject) parser.parse(jsonAsString);

        String name = element.get("product").getAsJsonObject()
                            .get("item").getAsJsonObject()
                            .get("product_description").getAsJsonObject()
                            .get("title")
                            .getAsString();
        return name;
    }

}
