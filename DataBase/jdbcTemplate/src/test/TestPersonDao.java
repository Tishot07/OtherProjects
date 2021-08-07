import org.junit.After;
import org.junit.Assert;
import tishort.dao.IPersonDao;
import tishort.dao.IPetDao;
import tishort.dao.impl.PersonDao;
import tishort.dao.impl.PetDao;
import tishort.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tishort.model.Pet;

import java.util.List;


public class TestPersonDao {

    ApplicationContext context;
    IPersonDao daoPerson;
    IPetDao daoPet;

    Person Jon;
    Person Joy;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("SpringContext.xml");
        daoPerson = (PersonDao) context.getBean("PersonDao");
        daoPet = (PetDao) context.getBean("PetDao");
        Jon = new Person("Smitt", "Jon", 30);
        Joy = new Person("Ivanova", "Joy", 40);
    }

    @Test
    public void test() {

        int id = daoPerson.insert(Jon);
        Jon.setId(id);

        daoPet.insert(new Pet("cat", id));

        Person p = daoPerson.findByFirstName(Jon.getFirstName());
        Assert.assertEquals(Jon.getFirstName(), p.getFirstName());
        Assert.assertEquals(Jon.getLastName(), p.getLastName());
        Assert.assertEquals(Jon.getAge(), p.getAge());

        List<Person> personList = daoPerson.findAll();
        Assert.assertEquals(personList.size(), 1);

        id = daoPerson.insert(Joy);
        Joy.setId(id);
        daoPet.insert(new Pet("rat", id));
        daoPet.insert(new Pet("dog", id));

        int count = daoPerson.getCount();
        Assert.assertEquals(count, 2);

        List<Person> personsWithPets = daoPerson.findAllWithPet();
        for (Person pp:
                personsWithPets) {
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

        int countPet = daoPet.count();
        Assert.assertEquals(countPet, 3);

        List<Pet> petsJoy = daoPet.findByPerson(Joy.getId());
        Assert.assertEquals(petsJoy.size(), 2);

        List<Pet> pets = daoPet.findAll();
        Assert.assertEquals(pets.size(), 3);

        daoPet.delete(Joy.getId());
        countPet = daoPet.count();
        Assert.assertEquals(countPet, 1);
    }



    @After
    public void destroy() {

        daoPerson.delete(Jon.getId());
        daoPerson.delete(Joy.getId());
    }

}
