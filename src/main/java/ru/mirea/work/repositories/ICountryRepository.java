package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Country;

/**
 * Интерфейс для получения информации о странах-производителях из таблиц базы данных

 */
@Repository
public interface ICountryRepository extends JpaRepository<Country, Integer> {
    /**
     * Метод для поиска страны по идентификатору
     * @param id Идентификатор страны-производителя
     * @return Возвращает страну-производителя
     */
    Country findById(int id);

    /**
     * Метод для удаления страны-производителя по идентификатору
     * @param id Идентификатор страны-производителя
     * @return Возвращает результат удаления
     */
    Long deleteById(int id);
}
