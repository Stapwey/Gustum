package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Product;

import java.util.List;

/**
 * Интерфейс для получения информации о продуктах из таблиц базы данных

 */
@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    /**
     * Метод для поиска продуктов по идентификатору вида изделий
     * @param typesId Идентификатор вида изделий
     * @return Возвращает список продуктов, соответствующих виду
     */
    List<Product> findAllByTypesId(int typesId);

    /**
     * Метод для поиска продуктов по виду изделий и стране-производителю
     * @param typesId Идентификаторвида изделий
     * @param countriesId Идентификатор страны-производителя
     * @return Возвращает список соответствующих продуктов
     */
    List<Product> findAllByTypesIdAndCountriesId(int typesId, int countriesId);

    /**
     * Метод поиска продукта по идентификатору
     * @param id Идентификатор продукта
     * @return Возвращает продукт
     */
    Product findById(int id);

    /**
     * Метод удаления продукта по идентификатору
     * @param id Идентификатор продукта
     * @return Возвращает результат удаления
     */
    Long deleteById(int id);
}
