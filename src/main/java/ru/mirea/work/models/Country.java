package ru.mirea.work.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Класс модели представления для страны-производителя

 */
@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country {
    /**
     * Идентификатор страны-производителя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Название страны-производителя
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * Переопределенный метод ToString() - строковое представление данных
     * @return Возращает информацию о стране-производителе в строковом формате
     */
    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
