package note_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Class describing the note entity.
 */
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String noteText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Folder folder;

    /**
     *
     * @return id of this note
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id to this note
     * @param id - set id value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return title of this note
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title to this note
     * @param title - set title value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return note text of this note
     */
    public String getNoteText() {
        return noteText;
    }

    /**
     * Sets text for this note
     * @param noteText - set text value
     */
    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    /**
     *
     * @return the folder to which this note belongs
     */
    public Folder getFolder() {
        return folder;
    }

    /**
     * Sets folder to which this note belongs
     * @param folder - set folder value
     */
    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
