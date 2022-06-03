package ru.mirea.work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.work.models.Type;
import ru.mirea.work.models.User;

/**
 * Интерфейс для получения информации о пользователях из таблиц базы данных

 */
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    /**
     * Метод поиска пользователя по имени
     * @param username Имя пользователя
     * @return Возвращает пользователя
     */
    User findByUsername(String username);

    /**
     * Метод удаления пользователя по идентификатору
     * @param id Идентификатор пользователя
     * @return Возвращает результат удаления
     */
    Long deleteById(int id);
}
