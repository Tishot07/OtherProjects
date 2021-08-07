package tishort.dao;

import tishort.model.Person;

import java.util.List;

public interface IPersonDao {

    List<Person> findAll();

    Person findByFirstName(String firstName);

    int insert(Person person);

    void update(Person person);

    void delete(int id);

    List<Person> findAllWithPet();

    Person findByFirstNameWithPetByName(String firstName);

    int insertWithPet(Person person);
}
