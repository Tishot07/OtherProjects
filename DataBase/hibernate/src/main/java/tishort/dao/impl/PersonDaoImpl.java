package tishort.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tishort.dao.IPersonDao;
import tishort.model.Person;

import java.util.List;

@Transactional
@Repository
public class PersonDaoImpl implements IPersonDao {

    private SessionFactory factory;

    @Autowired
    public PersonDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    private Session getSession() {
        return factory.getCurrentSession();
    }

    @Override
    @Transactional
    public void save(Person p) {
        getSession().save(p);
    }

    @Override
    public void delete(Person p) {
        getSession().delete(p);
    }

    @Override
    public Person findById(int id) {
        //именно по primary key
        return getSession().get(Person.class, id);
    }

    @Override
    public Person findByName(String name) {
        return (Person) getSession()
                .createQuery("select p from Person p where p.firstName = :name")
                .setParameter("name", name).uniqueResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> findAll() {
        return getSession().createQuery("from Person p").list();
    }

    @Override
    public List<Person> findByAllWithPet() {
        return (List<Person>) getSession().getNamedQuery("Person.findByAllWithPet").list();
    }

    @Override
    public Person findByNameWithPet(String name) {
        return (Person) getSession()
                .getNamedQuery("Person.findByNameWithPet")
                .setParameter("name", name).uniqueResult();
    }
}
