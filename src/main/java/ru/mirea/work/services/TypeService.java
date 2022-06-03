package ru.mirea.work.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.work.models.Type;
import ru.mirea.work.repositories.ITypeRepository;


import java.util.List;

/**
 * Класс-сервис для передачи данных из таблицы Бд с видами изделий в контроллер

 */
@Service
@RequiredArgsConstructor
public class TypeService {
    /**
     * Интерфейс для получения информации о видая изделий из таблиц базы данных
     */
    private ITypeRepository itr;

    /**
     * Конструктор присваивает значение для объекта интерфейса-репозитория
     * @param itr Интерфейс для получения информации о видая изделий из таблиц базы данных
     */
    @Autowired
    public TypeService( ITypeRepository itr){
        this.itr=itr;
    }

    /**
     * Метод получения всех видрв изделий
     * @return Возвращает список видов изделий
     */
    public List<Type> getAllTypes() {
        return itr.findAll();
    }

    /**
     * Метод сохранения вмда изделия в таблицу БД
     * @param type Объект вида изделия
     */
    public void saveType(Type type) {
        itr.save(type);
    }

    /**
     * Метод удаления вида изделий по идентификатору
     * @param id Идентификатор вида изделий
     */
    public void deleteById(int id) {
        itr.deleteById(id);
    }
}
