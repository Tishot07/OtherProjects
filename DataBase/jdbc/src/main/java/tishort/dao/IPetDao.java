package tishort.dao;

import tishort.model.Pet;

import java.util.List;

public interface IPetDao {

    List<Pet> findAll();

    List<Pet> findByPerson(int personId);

    int insert(Pet pet);

    void update(Pet pet);

    void delete(int id);

    int count();

}
