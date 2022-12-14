package com.example.frontoffice_planning.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity(name="Users")
@Table(name = "users")
public class Users {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user")
    private Long idUser;

    @Basic
    @Column(name = "pseudo", nullable = false)
    private String pseudo;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private byte[] password;

    @Basic
    @Column(name = "photo")
    private String photo;

    @Basic
    @Column(name = "date_last_login")
    private LocalDate dateLastLogin;

    @Basic
    @Column(name="is_activated", nullable = false)
    private boolean isActivated;

    @ManyToMany(cascade = {CascadeType.PERSIST}/*, fetch = FetchType.EAGER*/)
    @JoinTable(name="users_roles",
    joinColumns = @JoinColumn(name="id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "id_planning", nullable = false)
    private Planning planning;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Share> share = new ArrayList<>();


    public Users() {
    }

    public Users(String email) {
        this.email = email;
    }

    public Users(Long idUser, String pseudo, String email, byte[] password, String photo, LocalDate dateLastLogin, Address address, Planning planning) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.dateLastLogin = dateLastLogin;
        this.address = address;
        this.planning = planning;
    }


    public Users(Long idUser, String pseudo, String email, byte[] password, String photo, Address address, Planning planning) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.address = address;
        this.planning = planning;
    }

    public Users(String pseudo, String email, byte[] password, String photo,  Address address, Planning planning) {
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.address = address;
        this.planning = planning;
    }

    public Users(Long idUser, String pseudo, String email, LocalDate dateLastLogin){
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.email = email;
        this.dateLastLogin = dateLastLogin;
    }

    public Users(String pseudo, String email, byte[] password) {
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void deleteRole(Role role){
        if (this.getRoles().contains(role)){
            this.roles.remove(role);
        }
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getDateLastLogin() {
        return dateLastLogin;
    }

    public void setDateLastLogin(LocalDate dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }


    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public List<Share> getShare() {
        return share;
    }

    public void setShare(List<Share> share) {
        this.share = share;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        if (!Objects.equals(idUser, users.idUser)) return false;
        if (!Objects.equals(pseudo, users.pseudo)) return false;
        if (!Objects.equals(email, users.email)) return false;
        if (password != null ? !Arrays.equals(password, users.password) : users.password != null) return false;
        if (!Objects.equals(photo, users.photo)) return false;
        if (!Objects.equals(dateLastLogin, users.dateLastLogin))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser.intValue();
        result = 31 * result + (pseudo != null ? pseudo.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? Arrays.hashCode(password) : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (dateLastLogin != null ? dateLastLogin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Users{" +
                "idUser=" + idUser +
                ", pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                ", photo='" + photo + '\'' +
                ", dateLastLogin=" + dateLastLogin +
                ", isActivated=" + isActivated +
                ", roles=" + roles +
                ", address=" + address +
                ", planning=" + planning +
                ", share=" + share +
                '}';
    }
}
