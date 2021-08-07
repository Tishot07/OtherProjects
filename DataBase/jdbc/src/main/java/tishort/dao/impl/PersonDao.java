package tishort.dao.impl;

import tishort.dao.IPersonDao;
import tishort.dao.IPetDao;
import tishort.model.Person;
import tishort.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao implements IPersonDao {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
       return DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC", "root", "123");
    }

    private void closeConnection(Connection connection) {
        if(connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from person");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                persons.add(getPersonFromStat(resultSet));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return persons;
    }

    @Override
    public Person findByFirstName(String firstName) {
        Connection connection = null;
        Person person = new Person();
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from person where first_name = ?");
            statement.setString(1, firstName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                person = getPersonFromStat(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return person;
    }

    @Override
    public int insert(Person person) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into person (first_name, last_name, age)" +
                    "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getAge());
            statement.execute();

            ResultSet id = statement.getGeneratedKeys();
            if(id.next()) {
                person.setId(id.getInt(1));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return person.getId();
    }

    @Override
    public void update(Person person) {

        delete(person.getId());
        insert(person);
    }

    @Override
    public void delete(int id) {

        List<Pet> pets = getPetByPersonId(id);
        for (Pet p :
             pets) {
            deletePet(p.getId());
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from person where id = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Person> findAllWithPet() {

        List<Person> persons = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from person");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Person person = getPersonFromStat(resultSet);
                List<Pet> pets = getPetByPersonId(person.getId());
                person.setPets(pets);

                persons.add(person);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return persons;
    }

    @Override
    public Person findByFirstNameWithPetByName(String firstName) {
        Connection connection = null;
        Person person = new Person();
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from person where first_name = ?");
            statement.setString(1, firstName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                person = getPersonFromStat(resultSet);
                List<Pet> pets = getPetByPersonId(person.getId());
                person.setPets(pets);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return person;
    }

    @Override
    public int insertWithPet(Person person) {
        List<Pet> pets = person.getPets();
        for (Pet p:
             pets) {
            insertPet(p);
        }
        //pets.stream().forEach(p -> insertPet(p));

        return insert(person);
    }

    private Person getPersonFromStat(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setAge(resultSet.getInt("age"));

        return person;
    }

    private List<Pet> getPetByPersonId(int personId) {
        IPetDao dao = new PetDao();
        return dao.findByPerson(personId);
    }

    private void insertPet(Pet pet) {
        IPetDao dao = new PetDao();
        dao.insert(pet);
    }

    private void deletePet(int id) {
        IPetDao dao = new PetDao();
        dao.delete(id);
    }
}
