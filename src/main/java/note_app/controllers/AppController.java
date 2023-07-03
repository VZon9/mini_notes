package note_app.controllers;

import note_app.entities.AppUser;
import note_app.entities.UserRole;
import note_app.repositorys.FolderRepository;
import note_app.repositorys.NoteRepository;
import note_app.repositorys.UserRepository;
import note_app.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class AppController {

    @Autowired
    UserRepository users;
    @Autowired
    FolderRepository folders;
    @Autowired
    NoteRepository notes;

    @GetMapping("/main")
    String getMain(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            AppUser user = users.findByLogin(userDetails.getLogin());
            model.addAttribute("isAuth", true);
            model.addAttribute("user", user);
        }
        else {
            model.addAttribute("isAuth", false);
            model.addAttribute("user", null);
        }
        return "main";
    }

    @GetMapping("/login")
    String getLogin(){
        return "login";
    }

    @GetMapping("/sing_up")
    public String getSingUp(){
        return "sing_up";
    }

    @PostMapping("/sing_up")
    public String singUp(Model model,
                         @RequestParam(name = "username") String name,
                         @RequestParam(name = "login") String login,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "passwordRep") String passwordRep){
        if(users.findByLogin(login) == null){
            if(Objects.equals(password, passwordRep)){
                AppUser user = new AppUser();
                user.setRole(UserRole.REGULAR);
                user.setName(name);
                user.setLogin(login);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                users.save(user);
            }
            else {
                model.addAttribute("passErr", true);
                return "sing_up";
            }
        }
        else {
            model.addAttribute("loginErr", true);
            return "sing_up";
        }

        return "redirect:/login";
    }

}
