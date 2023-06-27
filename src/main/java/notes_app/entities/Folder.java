package notes_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long folderId;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AppUser owner;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Note> folderNotes = new HashSet<>();

    public void addNote(Note note){
        folderNotes.add(note);
        note.setFolder(this);
    }

    public void deleteNote(Note note){
        folderNotes.remove(note);
        note.setFolder(null);
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public Set<Note> getFolderNotes() {
        return folderNotes;
    }

    public void setFolderNotes(Set<Note> folderNotes) {
        this.folderNotes = folderNotes;
    }
}
