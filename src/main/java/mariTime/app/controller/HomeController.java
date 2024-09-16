package mariTime.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);

        return "home";
    }

    @GetMapping("/x")
    public String userPageX(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);
        return "user/x";
    }
//
//    @GetMapping("/user/y")
//    public String userPageY() {
//        return "user/y";  // Returnează fișierul user/y.html
//    }

    @GetMapping("/z")
    public String adminPageZ() {
        return "z";
    }
}
