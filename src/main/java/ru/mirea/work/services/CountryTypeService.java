package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.CountryType;
import ru.mirea.work.repositories.ICountryTypeRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из таблицы Бд со связями между странами-производителями и видами изделий в контроллер

 */
@Service
@RequiredArgsConstructor
public class CountryTypeService {
    /**
     * Интерфейс для получения информации о связи между странами-производителями и видами изделий из таблиц базы данных
     */
    private ICountryTypeRepository ictr;

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param ictr Интерфейс для получения информации о связи между странами-производителями и видами изделий из таблиц базы данных
     */
    @Autowired
    public CountryTypeService(ICountryTypeRepository ictr) {
        this.ictr = ictr;
    }

    /**
     * Метод получения всех стран, которые производят данный вид товаров
     * @param typeId Идентификатор вида изделий
     * @return Возваращает список стран-производителей
     */
    public List<CountryType> getCountriesByTypeId(int typeId) {
        return ictr.findAllByTypesId(typeId);
    }

    /**
     * Метод получает список всех связей между странами-производителями и видами изделий из репозитория
     * @return Возвращает список связей между странами-производителями и видами изделий из репозитория
     */
    public List<CountryType> getAll() {
        return ictr.findAll();
    }

    /**
     * Метод сохраняет соответствующую строку в таблицу БД
     * @param countryType Объект связи между страной-производителем и видом изделия
     */
    public void saveCountryType(CountryType countryType){ ictr.save(countryType);}

    /**
     * Метод удаления строки в БД
     * @param id Идентификатор строки
     */
    public void deleteById(int id){ ictr.deleteById(id);}
}
