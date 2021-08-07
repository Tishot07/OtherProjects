package tishort.dao;

import tishort.model.Pet;

import java.util.List;

public interface IPetDao {

    List<Pet> findAll();
    List<Pet> findAllByPerson(int personId);
    void save(Pet pet);          //insert and update
    void delete(Pet pet);
}
