import tishort.dao.IPersonDao;
import tishort.dao.IPetDao;
import tishort.model.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tishort.model.Pet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDao {

    IPersonDao daoPerson;
    IPetDao daoPet;

    Person Jon;
    Person Joy;


    @Before
    public void init() {
        daoPet = new tishort.dao.impl.PetDao();
        daoPerson = new tishort.dao.impl.PersonDao();

        Jon = new Person("Smitt", "Jon", 30);
        Joy = new Person("Ivanova", "Joy", 40);
    }


    @Test
    public void insertTest() {

        int id = daoPerson.insert(Jon);
        daoPet.insert(new Pet("cat", id));
        Jon.setId(id);
        id = daoPerson.insert(Joy);
        daoPet.insert(new Pet("rat", id));
        daoPet.insert(new Pet("dog", id));
        Joy.setId(id);

        //check person
        List<Person> personList = daoPerson.findAll();
        Assert.assertEquals(personList.size(), 2);

        int count = daoPet.count();
        Assert.assertEquals(count, 3);

        List<Pet> petsJoy = daoPet.findByPerson(Joy.getId());
        Assert.assertEquals(petsJoy.size(), 2);

        Person p = daoPerson.findByFirstName(Jon.getFirstName());
        Assert.assertEquals(Jon.getFirstName(), p.getFirstName());

        List<Person> personsWithPet = daoPerson.findAllWithPet();
        for (Person pp:
             personsWithPet) {
            if(pp.getId() == Jon.getId()) {
                Assert.assertEquals(pp.getPets().size(), 1);
                Assert.assertEquals(pp.getPets().get(0).getType(), "cat");
            }
            else if(pp.getId() == Joy.getId()) {
                Assert.assertEquals(pp.getPets().size(), 2);
            }
        }

        Person JoyTest = daoPerson.findByFirstNameWithPetByName(Joy.getFirstName());
        List<Pet> JoyPets = JoyTest.getPets();
        Assert.assertEquals(JoyPets.size(), 2);


    }

    @After
    public void destroy() {

        daoPerson.delete(Jon.getId());
        daoPerson.delete(Joy.getId());
    }


}
