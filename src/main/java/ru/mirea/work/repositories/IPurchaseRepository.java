package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.work.models.Product;
import ru.mirea.work.models.Purchase;

import java.util.List;

/**
 * Интерфейс для получения информации о товарах в корзине из таблиц базы данных

 */
@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase, Integer> {
    /**
     * Метод поиска продукта, добавленного в корзину, по идентификаторам пользователя и продукта
     * @param userId Идентификатор пользователя
     * @param productId Идентификатор продукта
     * @return Возвращает продукт из корзины
     */
    Purchase findByUserIdAndProductId(int userId, int productId);

    /**
     * Метод поиска добавленных в корзину товаров по идентификатору пользователя
     * @param userId Идентификатор пользователя
     * @return Возвращает список продуктов из корзины
     */
    List<Purchase> findAllByUserId(int userId);

    /**
     * Метод поиска товара добавленного в корзину по идентификатору
     * @param id Идентификатор продукта в корзине
     * @return Возвращает продукт добавленный в корзину
     */
    Purchase findById(int id);

    /**
     * Метод удаления добавленного товара в корзину по идентификатору
     * @param id Идентификатор продукта в корзине
     * @return Возвращает результат удаления
     */
    Long deleteById(int id);

    /**
     * Метод очищения корзины определенного пользователя
     * @param userId Идентификатор пользователя
     * @return Возвращает результат удаления
     */
    @Transactional
    Long deleteAllByUserId(int userId);
}
