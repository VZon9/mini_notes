package note_app.controllers;

import note_app.entities.AppUser;
import note_app.entities.Folder;
import note_app.entities.Note;
import note_app.repositorys.FolderRepository;
import note_app.repositorys.NoteRepository;
import note_app.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @Autowired
    UserRepository users;
    @Autowired
    FolderRepository folders;
    @Autowired
    NoteRepository notes;

    @GetMapping("/test/home")
    public String getHome(Model model){
        model.addAttribute("userSet", users.findAll());
        return "testHome";
    }

    @GetMapping("/test/addUser")
    public String getAddUser(Model model){
        model.addAttribute("user", new AppUser());
        return "testAddUser";
    }

    @PostMapping("/test/addUser")
    public String addUser(@ModelAttribute AppUser user){
        users.save(user);
        return "redirect:/test/home";
    }

    @GetMapping("/test/addFolder")
    public String getAddFolder(Model model){
        model.addAttribute("folder", new Folder());
        return "testAddFolder";
    }

    @PostMapping("/test/addFolder")
    public String addFolder(@ModelAttribute Folder folder, @RequestParam(name = "userName") String userName){
        users.findByName(userName).addFolder(folder);
        folders.save(folder);
        return "redirect:/test/home";
    }

    @GetMapping("/test/addNote")
    public String getAddNote(Model model){
        model.addAttribute("note", new Note());
        return "testAddNote";
    }

    @PostMapping("/test/addNote")
    public String addNote(@ModelAttribute Note note, @RequestParam(name = "folderTitle") String folderTitle){
        folders.findByTitle(folderTitle).addNote(note);
        notes.save(note);
        return "redirect:/test/home";
    }

}
