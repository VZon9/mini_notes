package note_app.repositorys;

import note_app.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findByTitle(String title);
}
