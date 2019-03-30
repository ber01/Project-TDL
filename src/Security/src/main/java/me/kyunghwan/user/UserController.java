package me.kyunghwan.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    public  UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/create")
    public User create(){
        User user = new User();
        user.setEmail("minkh@gmail.com");
        user.setPassword("password");
        return userService.save(user);
    }
}
