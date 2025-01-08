package org.raghav.smartcontactmanager.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "user")
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    private String Id;
    @Column(name = "user_name", nullable = false)
    private String Name;
    @Column(unique = true, nullable = false)
    private String email;
    @Getter(AccessLevel.NONE)
    private String Password;
    @Column(name = "profile_link", length = 5000)
    private String profilePic;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(length = 5000)
    private String about;

    @Getter(AccessLevel.NONE)
    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private boolean emailVerified = false;
    @Builder.Default
    private boolean phoneVerified = false;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private Providers provider = Providers.SELF;
    private String providerUserId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<Contact> Contacts = new ArrayList<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Roles> rolesList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = rolesList.stream()
                .map((role) -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getPassword() {
        return this.Password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}


