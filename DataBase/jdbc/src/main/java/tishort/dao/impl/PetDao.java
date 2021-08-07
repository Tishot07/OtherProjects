package tishort.dao.impl;

import tishort.dao.IPetDao;
import tishort.model.Person;
import tishort.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDao implements IPetDao {

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
    public List<Pet> findAll() {
        List<Pet> pets = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from pet");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                pets.add(getPetFromStat(resultSet));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return pets;
    }

    @Override
    public List<Pet> findByPerson(int personId) {
        List<Pet> pets = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from pet where person_id = ?");
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                pets.add(getPetFromStat(resultSet));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return pets;
    }

    @Override
    public int insert(Pet pet) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into pet (vid, person_id)" +
                    "values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pet.getType());
            statement.setInt(2, pet.getPersonId());
            statement.execute();

            ResultSet id = statement.getGeneratedKeys();
            if(id.next()) {
                pet.setId(id.getInt(1));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return pet.getId();
    }

    @Override
    public void update(Pet pet) {
        delete(pet.getId());
        insert(pet);
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from pet where id = ?");
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
    public int count() {
        int count = 0;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from pet");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                ++count;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return count;
    }

    private Pet getPetFromStat(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        pet.setId(resultSet.getInt("id"));
        pet.setType(resultSet.getString("vid"));
        pet.setPersonId(resultSet.getInt("person_id"));
        return pet;
    }
}
