package mariTime.app.controller;

import mariTime.app.entity.Role;
import mariTime.app.entity.User;
import mariTime.app.repository.RoleRepository;
import mariTime.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private UserService userService;
    private RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/addUsers")
    public String showAddUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        // Transmite toate rolurile disponibile către view
        model.addAttribute("roles", roleRepository.findAll());

        return "admin/addUsers";  // Asigură-te că template-ul Thymeleaf este corect
    }



    @PostMapping("/addUsers")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roleIds") Long[] roleIds) {
        Set<Role> roles = Arrays.stream(roleIds)
                .map(roleId -> roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/addUsers?success";
    }


}
