package ru.mirea.work;

import org.springframework.boot.SpringApplication;

/**
 * Главный класс веб-приложения

 */
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {

    /**
     * Входная точка веб-приложения
     * @param args Массив аргументов переданных при запуске веб-приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

}
