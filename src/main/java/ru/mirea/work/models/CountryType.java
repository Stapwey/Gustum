package ru.mirea.work.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс модели представления для стран, которые изготавливают определенный тип изделия

 */
@Getter
@Setter
@Entity
@Table(name = "types_countries")
public class CountryType {
    /**
     * Идентификатор для стран, которые изготавливают определенный тип изделия
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
     * Идентификатор страны-изготовителя
     */
    @Column(name="countries_id")
    private int countriesId;

    /**
     * Переопределенный метод ToString() - строковое представление данных
     * @return Возращает информацию о связи между странами-производителями и определенными типами товаров в строковом формате
     */
    @Override
    public String toString() {
        return "CountryType{" +
                "id=" + id +
                ", typesId=" + typesId +
                ", countriesId=" + countriesId +
                '}';
    }
}
