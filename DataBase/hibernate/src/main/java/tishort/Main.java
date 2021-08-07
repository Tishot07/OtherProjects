package tishort;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    /*
    Конфигурация hibernate в ресурсах. В конфиге указываем классы, с которыми должен будет работат hibernate.

    Конфиг взят с сайта hibernate, но там добавлена важная строка, чтобы не было ошибки, что невозможно получить текущую сессию:
        <property name="hibernate.current_session_context_class">thread</property>

    Чтобы работать просто с hibernate:
        SessionFactory factory = new Configuration()
                .configure("hibernate_old.cfg.xml")
                .buildSessionFactory();
    и далее вытаскиваем session. Не забываем про транзакцию.

    В примере - Spring и доступ к БД с помощью hibernate - не надо беспокоится о транзакция
    - нужно только создать бин - transactionManager и указывам @Transactional над классом или методом.
     */

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}