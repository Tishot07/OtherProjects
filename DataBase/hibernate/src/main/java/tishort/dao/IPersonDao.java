package tishort.dao;

import tishort.model.Person;

import java.util.List;

public interface IPersonDao {

    void save(Person p);
    void delete(Person p);
    Person findById(int id);
    Person findByName(String name);
    List<Person> findAll();
    List<Person> findByAllWithPet();
    Person findByNameWithPet(String name);
}
