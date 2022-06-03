package ru.mirea.work.controllers;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.work.models.Product;
import ru.mirea.work.models.Purchase;
import ru.mirea.work.models.User;
import ru.mirea.work.services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Данный класс является контроллером для пользователей

 */
@Controller
@RequestMapping("/")
public class UserController {
    /**
     * Сервис для видов
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
     * Сервис для корзины
     */
    private final PurchaseService purchaseService;
    /**
     * Сервис для отправки оповещений на электронную почту
     */
    private final EmailService emailService;
    /**
     * Сервис для поиска продуктов
     */
    private final CriteriaService criteriaService;

    /**
     * Статус для корзины
     */
    private String addPurchaseStatus = "";

    /**
     * Конструктор контроллера для пользователей
     * @param typeService Сервис для видов изделий
     * @param productService Сервис для продуктов
     * @param countryTypeService Сервис для стран, которые изготавливают определенный тип изделия
     * @param countryService Сервис для стран-изготовителей
     * @param userService Сервис для пользователей
     * @param purchaseService Сервис для корзины
     * @param emailService Сервис для отправки оповещений на электронную почту
     * @param criteriaService Сервис для поиска продуктов
     */
    @Autowired
    public UserController(TypeService typeService,
                          ProductService productService,
                          CountryTypeService countryTypeService,
                          CountryService countryService,
                          UserService userService,
                          PurchaseService purchaseService, EmailService emailService,
                          CriteriaService criteriaService) {
        this.typeService = typeService;
        this.productService = productService;
        this.countryTypeService = countryTypeService;
        this.countryService = countryService;
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.emailService = emailService;
        this.criteriaService=criteriaService;
    }

    /**
     * Метод дл яполучения роли пользователя
     * @param authentication Объект идентифицирующий пользователя, обратившегося к методу
     * @return Возвращает роль пользователя
     */
    private String getUserRole(Authentication authentication) {
        if (authentication == null)
            return "GUEST";
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getRole();
    }

    /**
     * Метод для получения идентификатора пользователя
     * @param authentication Объект идентифицирующий пользователя, обратившегося к методу
     * @return Возвращает идентификатор пользователя
     */
    private int getUserId(Authentication authentication) {
        if (authentication == null)
            return 0;
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getId();
    }

    /**
     * Метод обновляет статус корзины
     */
    private void reloadAddPurchaseStatus() {
        addPurchaseStatus = "";
    }

    /**
     * Метод устанавливает статус корзины
     * @param status стату корзины
     */
    private void setAddPurchaseStatus(String status) {
        addPurchaseStatus = status;
    }

    /**
     * Метод для получения общей суммы заказа
     * @param purchases Товары в корзине
     * @return Возвращает общую сумму заказа
     */
    private int getTotalPrice(List<Purchase> purchases) {
        int result = 0;
        for (Purchase purchase: purchases) {
            result += productService.getProduct(purchase.getProductId()).getPrice() * purchase.getProductCount();
        }
        return result;
    }

    /**
     * Метод шаблона оповещения для пользователя
     * @param userPurchases Товары из корзины пользователя
     * @return Возвращает письмо для отправки пользователю
     */
    private String createMessageForUser(List<Purchase> userPurchases) {
        List<Product> userProducts = new ArrayList<>();
        for (Purchase purchase: userPurchases) {
            userProducts.add(productService.getProduct(purchase.getProductId()));
        }
        String result = "Здравствуйте, ваш заказ:<br>";
        for (int i = 0; i < userProducts.size(); i++) {
            result += (i + 1) + ") " + userProducts.get(i).getName() + " (Количество: " + userPurchases.get(i).getProductCount() + ", Стоимость: " + (userProducts.get(i).getPrice()*userPurchases.get(i).getProductCount()) + " р.)<br>";
        }
        result += "Общая стоимость: " + getTotalPrice(userPurchases) + " р.<br>";
        return result;
    }

    /**
     * Метод шаблона оповещения для менеджера
     * @param user Пользователь, который оформил заказ
     * @param address Адрес доставки
     * @param telephone Номер телефона пользователя
     * @param userPurchases Товары из корзины пользователя
     * @return Возвращает письмо для отправки менеджеру
     */
    private String createMessageForManager(User user, String address, String telephone, List<Purchase> userPurchases) {
        List<Product> userProducts = new ArrayList<>();
        for (Purchase purchase: userPurchases) {
            userProducts.add(productService.getProduct(purchase.getProductId()));
        }
        String result = "Информация о заказчике:<br>";
        result += "Почта: " + user.getEmail() + "<br>";
        result += "Имя пользователя: " + user.getUsername() + "<br>";
        result += "Телефон: " + telephone + "<br>";
        result += "Адрес: " + address + "<br>";
        result += "Заказ:<br>";
        for (int i = 0; i < userProducts.size(); i++) {
            result += (i + 1) + ") " + userProducts.get(i).getName() + " (Количество: " + userPurchases.get(i).getProductCount() + ", Стоимость: " + (userProducts.get(i).getPrice()*userPurchases.get(i).getProductCount()) + " р.)<br>";
        }
        result += "Общая стоимость: " + getTotalPrice(userPurchases) + " р.<br>";
        return result;
    }

    /**
     * Метод принимающий GET запросы /
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает главную страницу
     */
    @GetMapping
    public String index(Authentication authentication, Model model) {
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        return "UserController/index";
    }

    /**
     * Метод принимающий GET запросы /products
     * @param typesId Идентификатор вида изделия
     * @param countryId Идентификатор страны-изготовителя
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @return Возвращает страницу с продуктами
     */
    @GetMapping("/products")
    public String products(@RequestParam(name = "typesId") int typesId,
                           @RequestParam(name = "countryId", required = false) Integer countryId,
                           Model model, Authentication authentication) {
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("typesId", typesId);
        model.addAttribute("countryService", countryService);
        model.addAttribute("countryTypes", countryTypeService.getCountriesByTypeId(typesId));
        if (countryId == null)
            model.addAttribute("products", productService.getAllProductsByTypesId(typesId));
        else
            model.addAttribute("products", productService.getAllProductsByTypesIdAndCountriesId(typesId, countryId));
        return "UserController/products";
    }

    /**
     * Метод принимающий GET запросы /product
     * @param productId Идентификатор продукта
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @return Возвращает страницу с определеннным продуктом
     */
    @GetMapping("/product")
    public String product(@RequestParam(name ="productId") int productId,
                          Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("country",countryService.getCountryById(product.getCountriesId()));
        model.addAttribute("Status", addPurchaseStatus);
        reloadAddPurchaseStatus();
        return "UserController/product";
    }

    /**
     * Метод принимающий GET запросы /feedback
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @return Возвращает страницу с обратной связью и информацией о магазине
     */
    @GetMapping("/feedback")
    public String feedback(Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        return "UserController/feedback";
    }

    /**
     * Метод принимающий GET запросы /sign
     * @return Возвращает страницу с регистрацией
     */
    @GetMapping("/sign")
    public String sign() {
        return "UserController/sign";
    }

    /**
     * Метод принимающий POST запросы /sign для регистрации пользователей
     * @param request Объект содержащий запрос, поступивший от пользователя
     * @param email Электронная почта пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу регистрации, если имя пользователя уже существует, иначе главную страницу
     */
    @PostMapping("/sign")
    public String signCreate(HttpServletRequest request,
                             @RequestParam(name = "email") String email,
                             @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             Model model) {
        if (userService.loadUserByUsername(username) != null) {
            model.addAttribute("Status", "user_exists");
            return "UserController/sign";
        }
        else {
            userService.create(email,username,password);
            authWithHttpServletRequest(request, username, password);
            return "redirect:/";
        }
    }

    /**
     * Метод принимающий POST запросы /product добавляет продукты в корзину
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param productId Идентификатор продукта
     * @param productCount Количество продукта
     * @return Возвращает страницу определенного продукта
     */
    @PostMapping("/product")
    public String addToBasket(Authentication authentication,
                              @RequestParam(name = "productId") int productId,
                              @RequestParam(name = "productCount") int productCount) {
        String userRole = getUserRole(authentication);
        String redirectString = "redirect:/product?productId=" + productId;
        if (userRole == "GUEST") {
            setAddPurchaseStatus("user_guest");
            return redirectString;
        }
        else {
            int userId = getUserId(authentication);
            Purchase purchase = purchaseService.getPurchaseByUserIdAndProductId(userId, productId);
            if (purchase == null) {
                Purchase newPurchase = new Purchase();
                newPurchase.setUserId(userId);
                newPurchase.setProductId(productId);
                newPurchase.setProductCount(productCount);
                purchaseService.savePurchase(newPurchase);
                setAddPurchaseStatus("OK");
                return redirectString;
            }
            else {
                if (purchase.getProductCount() + productCount > 10)
                {
                    setAddPurchaseStatus("count_overflow");
                    return redirectString;
                }
                else
                {
                    purchase.setProductCount(purchase.getProductCount() + productCount);
                    purchaseService.savePurchase(purchase);
                    setAddPurchaseStatus("OK");
                    return redirectString;
                }
            }
        }
    }

    /**
     * Метод принимающий GET запросы /basket
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @return Возвращает страницу корзины
     */
    @GetMapping("/basket")
    public String basket(Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        int userId = getUserId(authentication);
        model.addAttribute("totalPrice", getTotalPrice(purchaseService.getPurchasesByUserId(userId)));
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        List<Purchase> purchases = purchaseService.getPurchasesByUserId(userId);
        Collections.sort(purchases, Comparator.comparing(Purchase::getId));
        model.addAttribute("basket", purchases);
        model.addAttribute("productService", productService);

        return "UserController/basket";
    }

    /**
     * Метод принимающий POST запросы /basketOperation для изменения количества товаров в корзине
     * @param purchaseIds Идентификаторы товаров в корзине
     * @param productCounts Количество товаров
     * @return Возвращает страницу корзины
     */
    @PostMapping(value = "/basketOperation", params = "change")
    public String changeBasket(@RequestParam(name = "purchaseId[]") int[] purchaseIds,
                               @RequestParam(name = "productCount[]") int[] productCounts) {
        for (int i = 0; i < purchaseIds.length; i++) {
            Purchase purchase = purchaseService.getPurchaseById(purchaseIds[i]);
            purchase.setProductCount(productCounts[i]);
            purchaseService.savePurchase(purchase);
        }
        return "redirect:/basket";
    }

    /**
     * Метод принимающий POST запросы /basketOperation для удаления товара из корзины
     * @param purchaseToDeleteId Идентификатор товара, который надо удалить
     * @return Возвращает страницу корзины
     */
    @PostMapping(value = "/basketOperation", params = "delete")
    public String deleteBasket(@RequestParam(name = "purchaseToDeleteId") int purchaseToDeleteId) {
        purchaseService.deletePurchaseById(purchaseToDeleteId);
        return "redirect:/basket";
    }

    /**
     * Метод принимающий POST запросы /sendBasket для отпраки оповещений пользователю и менеджеру
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param address Адрес пользователя
     * @param telephone Номер телефона пользователя
     * @return ВОзвращает страницу корзины
     */
    @SneakyThrows
    @PostMapping(value = "/sendBasket")
    public String sendBasket(Authentication authentication,
                             @RequestParam(name = "address") String address,
                             @RequestParam(name = "telephone") String telephone) {
        User user = (User)userService.loadUserByUsername(authentication.getName());
        String userMessage = createMessageForUser(purchaseService.getPurchasesByUserId(user.getId()));
        String managerMessage = createMessageForManager(user, address, telephone, purchaseService.getPurchasesByUserId(user.getId()));
        emailService.sendmail(user.getEmail(), userMessage, false);
        emailService.sendmail("katyabi2010@gmail.com", managerMessage, true);
        purchaseService.deleteAllByUserId(user.getId());
        return "redirect:/basket";
    }

    /**
     * Метод принимающий GET запросы /search
     * @param name Имя продукта для поиска
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @return Возвращает поиска проуктов
     */
    @GetMapping("/search")
    public String searchProduct(@RequestParam(name ="name") String name,
                                       Model model, Authentication authentication){
        String userRole = getUserRole(authentication);
        model.addAttribute("userRole", userRole);
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("products",criteriaService.getAllByName(name));
        return "UserController/search";
    }

    /**
     * Метод для проверки существования пользователя
     * @param request Объект содержащий запрос, поступивший от пользователя
     * @param username Имя пользователя
     * @param password Пароль пользователя
     */
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }
}

