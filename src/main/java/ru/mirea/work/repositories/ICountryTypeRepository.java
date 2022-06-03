package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.CountryType;

import java.util.List;

/**
 * Интерфейс для получения информации о связи между странами-производителями и видами изделий из таблиц базы данных

 */
@Repository
public interface ICountryTypeRepository extends JpaRepository<CountryType, Integer> {
    /**
     * Метод для поиска всех стран, которые производят данный вид товаров
     * @param typesId Идентификатор вида изделия
     * @return Возваращает список стран-производителей
     */
    List<CountryType> findAllByTypesId(int typesId);

    /**
     * Метод для удаления связи между страной-производителем и видом изделия
     * @param id Идентификатор строки в таблице
     * @return Возвращает результат удаления
     */
    Long deleteById(int id);
}
