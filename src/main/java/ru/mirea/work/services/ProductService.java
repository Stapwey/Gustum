package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Product;
import ru.mirea.work.repositories.IProductRepository;


import java.util.List;

/**
 * Класс-сервис для передачи данных из таблицы Бд с продуктами в контроллер

 */
@Service
@RequiredArgsConstructor
public class ProductService {
    /**
     * Интерфейс для получения информации о продуктах из таблиц базы данных
     */
    private IProductRepository ipr;

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param ipr Интерфейс для получения информации о продуктах из таблиц базы данных
     */
    @Autowired
    public ProductService(IProductRepository ipr){
        this.ipr=ipr;
    }

    /**
     * Метод получения всех продуктов определенного вида
     * @param typesId Идентификатор вида изделия
     * @return Возвращает список продуктов
     */
    public List<Product> getAllProductsByTypesId(int typesId) {
        return ipr.findAllByTypesId(typesId);
    }

    /**
     * Метод получения всех продуктов определенного вида и с определенной страной-производителем
     * @param typesId Идентификатор вида изделия
     * @param countriesId Идентификатор страны-производителя
     * @return Возвращает список продуктов
     */
    public List<Product> getAllProductsByTypesIdAndCountriesId(int typesId, int countriesId) {
        return ipr.findAllByTypesIdAndCountriesId(typesId, countriesId);
    }

    /**
     * Метод получения продукта по идентификатору
     * @param id Идентификатор продукта
     * @return Возвращает определенный продукт
     */
    public Product getProduct(int id){
        return ipr.findById(id);
    }

    /**
     * Метод получения всех продуктов
     * @return Возвращает список продуктов
     */
    public List<Product> getAll(){
        return ipr.findAll();
    }

    /**
     * Метод сохраняет продукт в базу данных
     * @param product Объект продукта
     */
    public void saveProduct(Product product){
        ipr.save(product);
    }

    /**
     * Метод удаления продукта по идентификатору
     * @param id Идентификатор продукта
     */
    public void deleteById(int id){
        ipr.deleteById(id);
    }
}
