package ru.mirea.work.models;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс модели представления для вида изделия

 */
@Getter
@Setter
@Entity
@Table(name = "types")
public class Type {
    /**
     * Идентификатор вида продуктов
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Название вида изделий
     */
    @Column(name="name", nullable = false)
    private String name;

    /**
     * Переопределенный метод ToString() - строковое представление данных
     * @return Возращает информацию о виде товара в строковом формате
     */
    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
