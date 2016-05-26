package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntenseLifeController {


    @RequestMapping("/history")
    public History history(@RequestParam(value="name", defaultValue="World") String name) {
        return null;
    }
}
