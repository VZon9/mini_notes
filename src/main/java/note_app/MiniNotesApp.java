package note_app;

import note_app.entities.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class with the start of the application
 */
@SpringBootApplication
public class MiniNotesApp {
    /**
     * Application start
     * @param args program arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MiniNotesApp.class, args);
    }
}
