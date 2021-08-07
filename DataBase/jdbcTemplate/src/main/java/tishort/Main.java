package tishort;

public class Main {

    /*
    Для работы с JdbcTempalte нужнен spring context (создан в ресурсах), но проблема в UTM зоне
    поэтому пишем Java-class конфирурацию бина DataSource. И на сеттер ставит Autowired, чтобы взять из бина DataSource.
    Можно прописать ввиде бина и JdbcTeplate и в DAO написать сеттер только для него.
    */

    public static void main(String[] args) {

    }
}
