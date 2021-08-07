package tishort.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tishort.dao.IPetDao;
import tishort.model.Pet;

import java.util.List;

@Transactional
@Repository
public class PetDaoImpl implements IPetDao {

    private SessionFactory factory;

    @Autowired
    public PetDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pet> findAll() {
        return getSession().createQuery("from Pet p").list();
    }

    private Session getSession() {
        return factory.getCurrentSession();
    }

    @Override
    public List<Pet> findAllByPerson(int personId) {
        return getSession().createQuery("select p from Pet p where p.personId = :personId")
                .setParameter("personId", personId).list();
    }

    @Override
    public void save(Pet pet) {
        getSession().save(pet);
    }

    @Override
    public void delete(Pet pet) {
        getSession().delete(pet);
    }
}
