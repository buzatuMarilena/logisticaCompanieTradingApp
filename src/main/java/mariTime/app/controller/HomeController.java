package mariTime.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        return "home";
    }

    @GetMapping("/xPage")
    public String userPageX(Model model) {
        return "user/xPage";
    }

//    @GetMapping("/addUsers")
//    public String adminPageZ() {
//        return "admin/addUsers";
//    }
}
