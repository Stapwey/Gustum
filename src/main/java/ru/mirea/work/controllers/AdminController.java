package ru.mirea.work.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.work.models.*;
import ru.mirea.work.services.*;

import javax.persistence.Tuple;

/**
 *
 * Данный класс является контроллером админ-панели

 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    /**
     * Сервис для видов изделий
     */
    private final TypeService typeService;
    /**
     * Сервис для продуктов
     */
    private final ProductService productService;
    /**
     * Сервис для стран, которые изготавливают определенный тип изделия
     */
    private final CountryTypeService countryTypeService;
    /**
     * Сервис для стран-изготовителей
     */
    private final CountryService countryService;
    /**
     * Сервис для пользователей
     */
    private final UserService userService;

    /**
     * Конструктор для контроллера админ-панели
     * @param typeService Сервис для видов изделий
     * @param productService Сервис для продуктов
     * @param countryTypeService Сервис для стран, которые изготавливают определенный тип изделия
     * @param countryService Сервис для стран-изготовителей
     * @param userService Сервис для пользователей
     */
    @Autowired
    public AdminController(TypeService typeService,
                           ProductService productService,
                           CountryTypeService countryTypeService,
                           CountryService countryService,
                           UserService userService) {
        this.typeService = typeService;
        this.productService = productService;
        this.countryTypeService = countryTypeService;
        this.countryService = countryService;
        this.userService = userService;
    }

    /**
     * Метод принимающий GET запросы /admin
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает основную страницу админ-панели
     */
    @GetMapping
    public String index(Authentication authentication, Model model) {
        model.addAttribute("userName", authentication.getName());
        return "AdminController/admin";
    }

    /**
     * Метод принимающий GET запросы /admin/types
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу админ-панели с видами изделий
     */
    @GetMapping("/types")
    public String types(Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        return "AdminController/admin-types";
    }

    /**
     * Метод принимающий POST запросы /admin/types/create создает новый вид изделий
     * @param name Название вида изделия
     * @return Возвращает страницу админ-панели с видами изделий
     */
    @PostMapping("/types/create")
    public String typesCreate(@RequestParam(name = "name") String name) {
        Type newType = new Type();
        newType.setName(name);
        typeService.saveType(newType);
        return "redirect:/admin/types";
    }

    /**
     * Метод принимающий POST запросы /admin/types/delete удаляет вид изделий
     * @param id Идентификатор вида изделия
     * @return Возвращает страницу админ-панели с видами изделий
     */
    @PostMapping("/types/delete")
    public String typesDelete(@RequestParam(name = "id") int id) {
        typeService.deleteById(id);
        return "redirect:/admin/types";
    }

    /**
     * Метод принимающий GET запросы /admin/countries
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу админ-панели со странами-изготовителями
     */
    @GetMapping("/countries")
    public String countries(Model model){
        model.addAttribute("countries", countryService.getAllCountries());
        return "AdminController/admin-countries";
    }

    /**
     * Метод принимающий POST запросы /admin/countries/create создает новую страну-изготовителя
     * @param name Название страны
     * @return Возвращает страницу админ-панели со странами-изготовителями
     */
    @PostMapping("/countries/create")
    public String countriesCreate(@RequestParam(name = "name") String name){
        Country newCountry = new Country();
        newCountry.setName(name);
        countryService.saveCountry(newCountry);
        return "redirect:/admin/countries";
    }

    /**
     * Метод принимающий POST запросы /admin/countries/delete удаляет страну-изготовителя
     * @param id Идентификатор страны-изготовителя
     * @return Возвращает страницу админ-панели со странами-изготовителями
     */
    @PostMapping("/countries/delete")
    public String countriesDelete(@RequestParam(name = "id") int id){
        countryService.deleteById(id);
        return "redirect:/admin/countries";
    }

    /**
     * Метод принимающий GET запросы /admin/products
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу админ-панели с продуктами
     */
    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products", productService.getAll());
        return "AdminController/admin-products";
    }

    /**
     * Метод принимающий POST запросы /admin/products/create создает новый продукт
     * @param typesId Идентификатор вида изделия
     * @param name Название продукта
     * @param price Цена продукта
     * @param weight Вес продукта
     * @param description Описание продукта
     * @param countriesId Идентификатор страны-изготовиителя
     * @return Возвращает страницу админ-панели с продуктами
     */
    @PostMapping("/products/create")
    public String createProduct(@RequestParam(name = "typesId") int typesId,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "price") int price,
                                @RequestParam(name = "weight") int weight,
                                @RequestParam(name =  "description") String description,
                                @RequestParam(name = "countriesId") int countriesId){
        Product newProduct = new Product();
        newProduct.setTypesId(typesId);
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setWeight(weight);
        newProduct.setDescription(description);
        newProduct.setCountriesId(countriesId);
        productService.saveProduct(newProduct);
        return "redirect:/admin/products";
    }

    /**
     * Метод принимающий POST запросы /admin/products/delete удаляет продукт
     * @param id Идентификатор продукта
     * @return Возвращает страницу админ-панели с продуктами
     */
    @PostMapping("/products/delete")
    private String deleteProduct(@RequestParam(name = "id")int id){
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    /**
     * Метод принимающий GET запросы /admin/users
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу админ-панели с пользователями
     */
    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", userService.getAll());
        return "AdminController/admin-users";
    }

    /**
     * Метод принимающий POST запросы /admin/users/create создает нового пользователя
     * @param email Электронная почта пльзователя
     * @param username Имя поьзователя
     * @param password Пароль пользователя
     * @param role Роль пользователя
     * @return Возвращает страницу админ-панели с пользователями
     */
    @PostMapping("/users/create")
    public String createUser(@RequestParam(name = "email")String email,
                             @RequestParam(name = "username")String username,
                             @RequestParam(name = "password")String password,
                             @RequestParam(name = "role")String role){
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        userService.saveUser(newUser);
        return "redirect:/admin/users";
    }

    /**
     * Метод принимающий POST запросы /admin/users/delete удаляет пользователя
     * @param id Идентификатор пользователя
     * @return Возвращает страницу админ-панели с пользователями
     */
    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam(name = "id")int id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    /**
     * Метод принимающий GET запросы /admin/typesCountries
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу админ-панели со связью стран-изготовителей и видами изделий
     */
    @GetMapping("/typesCountries")
    public String typesCountries(Model model){
        model.addAttribute("typesCountries", countryTypeService.getAll());
        return "AdminController/admin-typesCountries";
    }

    /**
     * Метод принимающий POST запросы /admin/typesCountries/create создает новую связь между страной и типом изделия
     * @param typesId Идентификатор типа изделия
     * @param countriesId Идентификатор страны-изготовителя
     * @return Возвращает страницу админ-панели со связью стран-изготовителей и видами изделий
     */
    @PostMapping("/typesCountries/create")
    public String typesCountriesCreate(@RequestParam(name = "typesId") int typesId,
                                       @RequestParam(name = "countriesId") int countriesId){
        CountryType newCountryType = new CountryType();
        newCountryType.setTypesId(typesId);
        newCountryType.setCountriesId(countriesId);
        countryTypeService.saveCountryType(newCountryType);
        return "redirect:/admin/typesCountries";
    }

    /**
     * Метод принимающий POST запросы /admin/typesCountries/delete удаляет связь между страной и типом изделия
     * @param id Идентификатор связи между страной и типом изделия
     * @return Возвращает страницу админ-панели со связью стран-изготовителей и видами изделий
     */
    @PostMapping("/typesCountries/delete")
    public String typesCountriesDelete(@RequestParam(name = "id") int id){
        countryTypeService.deleteById(id);
        return "redirect:/admin/typesCountries";
    }
}
