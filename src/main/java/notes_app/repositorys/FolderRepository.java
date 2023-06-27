package notes_app.repositorys;

import notes_app.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Folder findByTitle(String title);
}
