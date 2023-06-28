package note_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Class describing the folder entity.
 */
@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AppUser owner;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Note> folderNotes = new HashSet<>();

    /**
     * Add note to folderNotes and set this folder to note folder
     * @param note - set note value
     */
    public void addNote(Note note){
        folderNotes.add(note);
        note.setFolder(this);
    }

    /**
     * Delete note from folderNotes and set note's folder null
     * @param note - set note value
     */
    public void deleteNote(Note note){
        folderNotes.remove(note);
        note.setFolder(null);
    }

    /**
     *
     * @return id of this folder
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id for this folder
     * @param id - set id value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return title of this folder
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title for this folder
     * @param title - set title value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return owner of this folder
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * Sets owner for this folder
     * @param owner - set owner value
     */
    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    /**
     *
     * @return set of notes of this folder
     */
    public Set<Note> getFolderNotes() {
        return folderNotes;
    }

    /**
     * Sets set of note for this folder
     * @param folderNotes - value of set of notes for this folder
     */
    public void setFolderNotes(Set<Note> folderNotes) {
        this.folderNotes = folderNotes;
    }
}
