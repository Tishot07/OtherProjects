import tishort.dao.impl.PersonDao;

public class Main {

/*
Работает без создания spring контекста. Все подключение прописано в DAO
 */
    public static void main(String[] args) {
        PersonDao dao = new PersonDao();
    }
}
