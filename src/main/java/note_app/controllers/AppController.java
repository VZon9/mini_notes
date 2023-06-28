package note_app.controllers;

import note_app.repositorys.FolderRepository;
import note_app.repositorys.NoteRepository;
import note_app.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {

    @Autowired
    UserRepository users;
    @Autowired
    FolderRepository folders;
    @Autowired
    NoteRepository notes;

}
