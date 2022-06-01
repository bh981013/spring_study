package hello.hellospring.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @GetMapping("image/new")
    @ResponseBody
    @RequestBody
    String uploadImage(@RequestParam String link){
        return link;
    }
}
