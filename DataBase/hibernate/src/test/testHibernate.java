import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import tishort.AppConfig;
import tishort.dao.IPersonDao;
import tishort.dao.IPetDao;
import tishort.model.Person;
import tishort.model.Pet;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class, loader=AnnotationConfigContextLoader.class)
public class testHibernate {

    @Autowired
    IPetDao petDao;

    @Autowired
    IPersonDao personDao;

    Person Jon;
    Person Joy;

    Pet cat;
    Pet rat;
    Pet dog;


    @Test
    public void testDao() {

        Jon = new Person("Smitt", "Jon", 30);
        personDao.save(Jon);
        Assert.assertNotNull(Jon.getId());

        Assert.assertEquals(Jon.getFirstName(), personDao.findById(Jon.getId()).getFirstName());
        cat = new Pet("cat", Jon.getId());
        petDao.save(cat);

        Person p = personDao.findByName(Jon.getFirstName());
        Assert.assertEquals(Jon.getFirstName(), p.getFirstName());
        Assert.assertEquals(Jon.getLastName(), p.getLastName());
        Assert.assertEquals(Jon.getAge(), p.getAge());

        List<Person> personList = personDao.findAll();
        Assert.assertEquals(personList.size(), 1);


        Joy = new Person("Ivanova", "Joy", 40);
        personDao.save(Joy);
        rat = new Pet("rat", Joy.getId());
        petDao.save(rat);
        dog = new Pet("dog", Joy.getId());
        petDao.save(dog);


        List<Person> personsWithPets = personDao.findByAllWithPet();
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


        Person JoyTest = personDao.findByNameWithPet(Joy.getFirstName());
        List<Pet> JoyPets = JoyTest.getPets();
        Assert.assertEquals(JoyPets.size(), 2);

        List<Pet> pets = petDao.findAll();
        Assert.assertEquals(pets.size(), 3);

        List<Pet> petsJoy = petDao.findAllByPerson(Joy.getId());
        Assert.assertEquals(petsJoy.size(), 2);

        petDao.delete(petsJoy.get(0));
        petsJoy = petDao.findAllByPerson(Joy.getId());
        Assert.assertEquals(petsJoy.size(), 1);
        petDao.delete(petsJoy.get(0));

    }

    @After
    public void destroy() {
        petDao.delete(cat);
        personDao.delete(Jon);
        personDao.delete(Joy);
    }

}

