package note_app.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Class describing the user entity.
 */
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String login;
    private String password;
    private UserRole role;
    private UserStatus status;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Folder> userFolders = new HashSet<>();

    /**
     * Add folder for userFolders and set this user to folder owner
     * @param folder - set folder value
     */
    public void addFolder(Folder folder){
        userFolders.add(folder);
        folder.setOwner(this);
    }

    /**
     * Delete folder from userFolders and set folder's owner null
     * @param folder - set folder value
     */
    public void deleteFolder(Folder folder){
        userFolders.remove(folder);
        folder.setOwner(null);
    }

    /**
     *
     * @return this id of this user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id for this user
     * @param id - set id value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return name of this user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for this user
     * @param name - set name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return login of this user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login for this user
     * @param login - set login value
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return password of this user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password for this user
     * @param password - set password value
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return role of this user
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets role for this user
     * @param role - set role value
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     *
     * @return status of this user
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Sets value for this user
     * @param status - set status value
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     *
     * @return set of folder of this user
     */
    public Set<Folder> getUserFolders() {
        return this.userFolders;
    }

    /**
     * Sets set of folder for this user
     * @param userFolders - value of set of folder for this user
     */
    public void setUserFolders(Set<Folder> userFolders) {
        this.userFolders = userFolders;
    }
}



