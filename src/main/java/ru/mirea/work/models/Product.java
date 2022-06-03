package ru.mirea.work.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс модели представления для продукта

 */
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    /**
     * Идентификатор продукта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Идентификатор вида изделия
     */
    @Column(name="types_id")
    private int typesId;
    /**
     * Название продукта
     */
    @Column(name="name", nullable = false)
    private String name;
    /**
     * Цена продукта
     */
    @Column(name="price")
    private int price;
    /**
     * Вес продукта
     */
    @Column(name="weight")
    private int weight;
    /**
     * Описание продукта
     */
    @Column(name="description")
    private String description;
    /**
     * Идентификатор страны производителя
     */
    @Column(name="countries_id")
    private int countriesId;

    /**
     * Переопределенный метод ToString() - строковое представление данных
     * @return Возращает информацию о продукте в строковом формате
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", types_id='" + typesId + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", weight='" + weight + '\'' +
                ", description=" + description +
                ", countries_id=" + countriesId +
                '}';
    }
}
