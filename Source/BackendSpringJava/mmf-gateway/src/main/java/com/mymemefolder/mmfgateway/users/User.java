package com.mymemefolder.mmfgateway.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Boolean folderIsPublic;
    private Boolean imagesArePublic;
    @Column(columnDefinition = "TEXT")
    private String folderStructure;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
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
        return true;
    }

    private static final List<SimpleGrantedAuthority> authorities =
            List.of(new SimpleGrantedAuthority("USER"));

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String passhash) {
        this.password = passhash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFolderStructure() {
        if (folderStructure == null)
            folderStructure = "[]";
        return folderStructure;
    }

    public void setFolderStructure(String folderStructure) {
        this.folderStructure = folderStructure;
    }

    public Boolean getFolderIsPublic() {
        if (folderIsPublic == null)
            folderIsPublic = true;
        return folderIsPublic;
    }

    public void setFolderIsPublic(Boolean folderIsPublic) {
        this.folderIsPublic = folderIsPublic;
    }

    public Boolean getImagesArePublic() {
        if (imagesArePublic == null)
            imagesArePublic = true;
        return imagesArePublic;
    }

    public void setImagesArePublic(Boolean imagesArePublic) {
        this.imagesArePublic = imagesArePublic;
    }
}
