package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Country;
import ru.mirea.work.models.CountryType;
import ru.mirea.work.repositories.ICountryRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из таблицы Бд со странами-производителями в контроллер

 */
@Service
@RequiredArgsConstructor
public class CountryService {
    /**
     * Интерфейс для получения информации о странах-производителях из таблиц базы данных
     */
    private ICountryRepository icr;

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param icr Интерфейс для получения информации о странах-производителях из таблиц базы данных
     */
    @Autowired
    public CountryService(ICountryRepository icr){
        this.icr=icr;
    }

    /**
     * Метод получает список всех стран-производителей из репозитория и возвращает соответствующий результат
     * @return Возвращает список всех стран-производителей
     */
    public List<Country> getAllCountries() {
        return icr.findAll();
    }

    /**
     * Метод получает из репозитория страну-производителя по идентификатору и возвращает соответствующий результат
     * @param id Идентификатор страны-производителя
     * @return Возвращает соответсвующую страну-производителя
     */
    public Country getCountryById(int id) {
        return icr.findById(id);
    }

    /**
     * Метод сохраняет страну-производителя в базу данных
     * @param country Объект страны-производителя
     */
    public  void saveCountry(Country country){icr.save(country);}

    /**
     * Метод удаляет страну-производителя по идентификатору из базы данных
     * @param id Идентификатор страны-производителя
     */
    public void deleteById(int id){icr.deleteById(id);}
}
