package ru.mirea.work.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Класс модели представления для пользователя

 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Электронная почта пользователя
     */
    @Column(name="email")
    private String email;
    /**
     * Имя пользователя
     */
    @Column(name="username")
    private String username;
    /**
     * Пароль пользователя
     */
    @Column(name="password")
    private String password;
    /**
     * РОль пользователя
     */
    @Column(name="role")
    private String role;

    /**
     * Переопределенный метод ToString() - строковое представление данных
     * @return Возращает информацию о пользователе в строковом формате
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
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
}
