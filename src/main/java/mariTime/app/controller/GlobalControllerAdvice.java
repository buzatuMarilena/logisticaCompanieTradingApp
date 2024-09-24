package mariTime.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
//am creat acest controler pentru a avea userul si rolul in toate paginile
@ControllerAdvice  // Aceasta adnotare aplică logică globală pentru toate controloarele
public class GlobalControllerAdvice {
    @ModelAttribute  // Această metodă va injecta automat user-ul și rolurile în toate modelele paginilor
    public void addUserAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificăm dacă există o autentificare validă (user logat)
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));

            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isUser", isUser);

            // Obținem numele de utilizator
            String username = authentication.getName();
            if (authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            }
            model.addAttribute("username", username);
        }
    }
}
