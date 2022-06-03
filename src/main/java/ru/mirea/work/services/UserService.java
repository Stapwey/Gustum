package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.User;
import ru.mirea.work.repositories.IUserRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из табилцы Бд с пользователями в контроллер

 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с пользователями
     */
    private IUserRepository iur;
    /**
     * Реализация кодера паролей, который использует функцию сильного хэширования BCrypt
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param iur Интерфейс-репозиторий получающий данные из таблицы БД с пользователями
     */
    @Autowired
    public UserService(IUserRepository iur) {
        this.iur = iur;
    }
    /**
     * Метод получает пользователя из БД по имени пользователя
     * @param s Имя искомого пользователя
     * @return Возвращает искомого пользователя
     * @throws UsernameNotFoundException Выбрасывается, если реализация UserDetailsService не может найти пользователя по его имени пользователя
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return iur.findByUsername(s);
    }
    /**
     * Метод создает нового пользователя
     * @param email Электронная почта пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     */
    public void create(String email, String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setEmail(email);
        u.setRole("USER");
        iur.save(u);
    }
    /**
     * Метод получает список всех пользователей из репозитория и возвращает соответствующий результат
     * @return Возвращает список всех пользователей
     */
    public List<User> getAll(){
        return iur.findAll();
    }
    /**
     * Метод сохраняет пользователя в БД
     * @param user Объект пользователя
     */
    public  void saveUser(User user){
        iur.save(user);
    }
    /**
     * Метод удаляет пользователя из БД
     * @param id Идентификатор пользователя
     */
    public void deleteUser(int id){
        iur.deleteById(id);
    }
}
