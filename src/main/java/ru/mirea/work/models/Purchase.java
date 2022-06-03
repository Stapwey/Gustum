package ru.mirea.work.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс модели представления для товаров в корзине

 */
@Getter
@Setter
@Entity
@Table(name = "basket")
public class Purchase {
    /**
     * Идентификатор товара в корзине
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Идентификатор пользователя
     */
    @Column(name="user_id")
    private int userId;
    /**
     * Идентификатор продукта
     */
    @Column(name="product_id")
    private int productId;
    /**
     * Колиество продуктов
     */
    @Column(name="product_count")
    private int productCount;

    /**
     * Переопределенный метод ToString() - строковое представление данных
     * @return Возращает информацию о товарах из корзины в строковом формате
     */
    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", productCount=" + productCount +
                '}';
    }
}
