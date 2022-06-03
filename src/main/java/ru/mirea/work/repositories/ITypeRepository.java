package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Type;

/**
 * Интерфейс для получения информации о видая изделий из таблиц базы данных

 */
@Repository
public interface ITypeRepository extends JpaRepository<Type, Integer> {
    /**
     * Метод удаления вида изделий по идентификатору
     * @param id Идентификатор вида изделий
     * @return Возвращает рузультат удаления
     */
    Long deleteById(int id);
}
