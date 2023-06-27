package notes_app.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userName;
    private String userLogin;
    private String userPassword;
    private UserRole role;
    private UserStatus status;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Folder> userFolders = new HashSet<>();

    public void addFolder(Folder folder){
        userFolders.add(folder);
        folder.setOwner(this);
    }

    public void deleteFolder(Folder folder){
        userFolders.remove(folder);
        folder.setOwner(null);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<Folder> getUserFolders() {
        return userFolders;
    }

    public void setUserFolders(Set<Folder> userFolders) {
        this.userFolders = userFolders;
    }
}



