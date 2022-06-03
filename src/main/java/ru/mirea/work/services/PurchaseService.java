package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Purchase;
import ru.mirea.work.repositories.IPurchaseRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из таблицы Бд с добавленными товарами в контроллер
 */
@Service
@RequiredArgsConstructor
public class PurchaseService {
    /**
     * Интерфейс для получения информации о товарах в корзине из таблиц базы данных
     */
    private IPurchaseRepository ipr;

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param ipr Интерфейс для получения информации о товарах в корзине из таблиц базы данных
     */
    @Autowired
    public PurchaseService(IPurchaseRepository ipr) {
        this.ipr = ipr;
    }

    /**
     * Метод получения добавленного в корзину товара по идентификатору пользователя и идентификатору продукта
     * @param userId Идентификатор пользователя
     * @param productId Идентификатор продукта
     * @return Возвращает товар добавленный в корзину
     */
    public Purchase getPurchaseByUserIdAndProductId(int userId, int productId) {
        return ipr.findByUserIdAndProductId(userId, productId);
    }

    /**
     * Метод сохраняет товар добавленный в корзину в БД
     * @param purchase Объект товара добавленного в корзину
     */
    public void savePurchase(Purchase purchase) {
        ipr.save(purchase);
    }

    /**
     * Метод получения добавленных в корзину товаров по идентификатору пользователя
     * @param userId Идентификатору пользователя
     * @return Возвращает список продуктов из корзины
     */
    public List<Purchase> getPurchasesByUserId(int userId) {
        return ipr.findAllByUserId(userId);
    }

    /**
     * Метод получения продукта по идентификатору
     * @param id Идентификатор продукта
     * @return Возвращает продукт из корзины
     */
    public Purchase getPurchaseById (int id) {
        return ipr.findById(id);
    }

    /**
     * Метод удаления добавленного товара в корзину по идентификатору
     * @param id Идентификатор продукта
     */
    public void deletePurchaseById(int id) {
        ipr.deleteById(id);
    }

    /**
     * Метод очищения корзины определенного пользователя
     * @param userId Идентификатор пользователя
     */
    public void deleteAllByUserId(int userId) {
        ipr.deleteAllByUserId(userId);
    }
}
