package note_app.controllers;

import note_app.entities.*;
import note_app.repositorys.FolderRepository;
import note_app.repositorys.NoteRepository;
import note_app.repositorys.UserRepository;
import note_app.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
                user.setStatus(UserStatus.ACTIVE);
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

    @GetMapping("/user/{userId}/profile")
    public String getProfile(@PathVariable Long userId, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            model.addAttribute("user", user);
            Set<Folder> folderSet = user.getUserFolders();
            int numOfNotes = 0;
            for(Folder folder: folderSet){
                for(Note note: folder.getFolderNotes()){
                    if(!note.isDeleted()){
                        numOfNotes++;
                    }
                }
            }
            model.addAttribute("numOfNotes", numOfNotes);
        }
        return "profile";
    }

    @PostMapping("/user/{userId}/addFolder")
    public String addFolder(@PathVariable Long userId, @RequestParam(name = "folderTitle") String folderTitle, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            Folder folder = new Folder();
            folder.setTitle(folderTitle);
            AppUser user = userOptional.get();
            user.addFolder(folder);
            folders.save(folder);
        }
        return "redirect:/main";
    }

    @GetMapping("/user/{userId}/folder/{folderId}")
    public String getFolder(@PathVariable Long userId, @PathVariable Long folderId, Model model){
        Optional<Folder> folderOptional = folders.findById(folderId);
        if(folderOptional.isPresent()){
            model.addAttribute("userId", userId);
            model.addAttribute("folder", folderOptional.get());
        }
        return "folder";
    }

    @PostMapping("/user/{userId}/folder/{folderId}/addNote")
    public String addNote(@PathVariable Long userId, @PathVariable Long folderId,
                          @RequestParam(name = "noteTitle") String noteTitle,
                          @RequestParam(name = "noteText") String noteText,
                          Model model){
        Optional<Folder> folderOptional = folders.findById(folderId);
        if(folderOptional.isPresent()){
            Note note = new Note();
            note.setTitle(noteTitle);
            note.setNoteText(noteText);
            note.setDeleted(false);
            Folder folder = folderOptional.get();
            folder.addNote(note);
            notes.save(note);
        }
        return "redirect:/user/" + userId + "/folder/" + folderId;
    }

    @GetMapping("/user/{userId}/folder/{folderId}/note/{noteId}/toBinNote")
    public String getDelNote(@PathVariable Long userId, @PathVariable Long folderId, @PathVariable Long noteId, Model model){
        Optional<Folder> folderOptional = folders.findById(folderId);
        if(folderOptional.isPresent()){
            model.addAttribute("userId", userId);
            model.addAttribute("folder", folderOptional.get());
            model.addAttribute("noteId", noteId);
        }
        return "toBinNote";
    }

    @PostMapping("/user/{userId}/folder/{folderId}/note/{noteId}/toBinNote")
    public String delNote(@PathVariable Long userId, @PathVariable Long folderId, @PathVariable Long noteId, Model model){
        Optional<Note> noteOptional = notes.findById(noteId);
        if(noteOptional.isPresent()){
            Note note = noteOptional.get();
            note.setDeleted(true);
            notes.save(note);
        }
        return "redirect:/user/" + userId + "/folder/" + folderId;
    }

    @GetMapping("/user/{userId}/bin")
    public String getBin(@PathVariable Long userId, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            model.addAttribute("user", user);
        }
        return "bin";
    }

    @PostMapping("/user/{userId}/cleanBin")
    public String cleanBin(@PathVariable Long userId, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            for(Folder folder: user.getUserFolders()){
                for(Note note: folder.getFolderNotes()){
                    if(note.isDeleted()){
                        note.setFolder(null);
                        notes.delete(note);
                    }
                }
                folder.getFolderNotes().clear();
            }
        }
        return "redirect:/user/" + userId +"/bin";
    }

    @PostMapping("/user/{userId}/note/{noteId}/recover")
    public String recoverNote(@PathVariable Long userId, @PathVariable Long noteId, Model model){
        Optional<Note> noteOptional = notes.findById(noteId);
        if(noteOptional.isPresent()){
            Note note = noteOptional.get();
            note.setDeleted(false);
            notes.save(note);
        }
        return "redirect:/user/" + userId +"/bin";
    }

    @GetMapping("/user/{userId}/folder/{folderId}/delFolder")
    public String getDelFolder(@PathVariable Long userId, @PathVariable Long folderId, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            model.addAttribute("user", user);
            model.addAttribute("folderId", folderId);
        }
        return "delFolder";
    }

    @PostMapping("/user/{userId}/folder/{folderId}/delFolder")
    public String delFolder(@PathVariable Long userId, @PathVariable Long folderId, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        Optional<Folder> folderOptional = folders.findById(folderId);
        if(userOptional.isPresent() && folderOptional.isPresent()){
            AppUser user = userOptional.get();
            Folder folder = folderOptional.get();
            user.deleteFolder(folder);
            folders.delete(folder);
            users.save(user);
        }
        return "redirect:/main";
    }

    @PostMapping("/user/{userId}/folder/{folderId}/note/{noteId}/rewrite")
    public String rewriteNote(@PathVariable Long userId, @PathVariable Long folderId, @PathVariable Long noteId,
                              @RequestParam(name = "noteText") String noteText, Model model){
        Optional<Note> noteOptional = notes.findById(noteId);
        if(noteOptional.isPresent()){
            Note note = noteOptional.get();
            note.setNoteText(noteText);
            notes.save(note);
        }
        return "redirect:/user/" + userId + "/folder/" + folderId;
    }

    @PostMapping("/user/{userId}/rename")
    public String renameUser(@PathVariable Long userId, @RequestParam(name = "newName") String newName, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            user.setName(newName);
            users.save(user);
        }
        return "redirect:/user/" + userId + "/profile";
    }

    @GetMapping("/user/{userId}/userList")
    public String getUserList(@PathVariable Long userId, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            if(user.getRole() != UserRole.ADMIN){
                return "redirect:/user/" + userId + "/profile";
            }
            model.addAttribute("userList", users.findAll());
            model.addAttribute("userId", userId);
        }
        return "userList";
    }

    @PostMapping("/user/{userId}/change/admin/{adminId}")
    public String changeUser(@PathVariable Long userId, @PathVariable Long adminId,
                             @RequestParam(name="userRole") String userRole, @RequestParam(name="userStatus") String userStatus, Model model){
        Optional<AppUser> userOptional = users.findById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            if(Objects.equals(userStatus, "ACTIVE")){
                user.setStatus(UserStatus.ACTIVE);
            }
            else {
                user.setStatus(UserStatus.BLOCKED);
            }
            if(Objects.equals(userRole, "ADMIN")){
                user.setRole(UserRole.ADMIN);
            }
            else {
                user.setRole(UserRole.REGULAR);
            }
            users.save(user);
        }
        return "redirect:/user/" + adminId + "/userList";
    }
}
