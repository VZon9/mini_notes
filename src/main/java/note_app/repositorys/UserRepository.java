package note_app.repositorys;

import note_app.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByName(String name);

    AppUser findByLogin(String login);
}
