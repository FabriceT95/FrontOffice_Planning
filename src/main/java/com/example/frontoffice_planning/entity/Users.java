package com.example.frontoffice_planning.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "Users")
@Table(name = "users")
public class Users implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user")
    private Long idUser;

    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "photo")
    private String photo;

    @Basic
    @Column(name = "date_last_login")
    private LocalDateTime dateLastLogin;

    @Basic
    @Column(name = "is_activated", nullable = false)
    private boolean isActivated;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
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

    @OneToMany(mappedBy = "users")
    private List<Event> events = new ArrayList<>();

    public Users() {
    }

    public Users(String email) {
        this.email = email;
    }

    public Users(Long idUser, String username, String email, String password, String photo, LocalDateTime dateLastLogin, Address address, Planning planning) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.dateLastLogin = dateLastLogin;
        this.address = address;
        this.planning = planning;
    }


    public Users(Long idUser, String username, String email, String password, String photo, Address address, Planning planning) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.address = address;
        this.planning = planning;
    }

    public Users(String username, String email, String password, String photo, Address address, Planning planning) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.address = address;
        this.planning = planning;
    }

    public Users(Long idUser, String username, String email, LocalDateTime dateLastLogin) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.dateLastLogin = dateLastLogin;
    }

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void deleteRole(Role role) {
        if (this.getRoles().contains(role)) {
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

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.roles.stream().anyMatch(role -> Objects.equals(role.getName(), "BASIC"));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDateTime getDateLastLogin() {
        return dateLastLogin;
    }

    public void setDateLastLogin(LocalDateTime dateLastLogin) {
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

    public void addShare(Share newShare) {
        this.share.add(newShare);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        if (!Objects.equals(idUser, users.idUser)) return false;
        if (!Objects.equals(username, users.username)) return false;
        if (!Objects.equals(email, users.email)) return false;
        if (!Objects.equals(password, users.password)) return false;
        if (!Objects.equals(photo, users.photo)) return false;
        if (!Objects.equals(dateLastLogin, users.dateLastLogin))
            return false;

        return true;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    @Override
    public int hashCode() {
        int result = idUser.intValue();
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (dateLastLogin != null ? dateLastLogin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Users{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
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
