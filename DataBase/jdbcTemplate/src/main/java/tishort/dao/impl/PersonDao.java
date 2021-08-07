package tishort.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import tishort.dao.IPersonDao;
import tishort.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import tishort.model.Pet;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("PersonDao")
public class PersonDao implements IPersonDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Person> findAll() {
        String sql = "select * from person";
        return jdbcTemplate.query(sql, new PersonRowMapper());
    }

    //передаем не именнованные параметры (обычный jdbcTemplate)
    public Person findByFirstName(String firstName) {
        String sql = "select first_name, last_name, age, id from person where first_name = ?";
        return jdbcTemplate.queryForObject(sql, new PersonRowMapper(), firstName);
    }

    //передаем именнованные параметры. Используется NamedParameterJdbcTemplate
    public int insert(Person person) {
        KeyHolder id = new GeneratedKeyHolder();
        String sql = "insert into person (first_name, last_name, age) values (:first, :last, :age)";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("first", person.getFirstName());
        param.addValue("last", person.getLastName());
        param.addValue("age", person.getAge());
        namedJdbcTemplate.update(sql, param, id);
        return id.getKey().intValue();
    }

    public void update(Person person) {

    }

    //именнованные параметры - NamedParameterJdbcTemplate
    public void delete(int id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        namedJdbcTemplate.update("delete from pet where person_id=:id", param);
        namedJdbcTemplate.update("delete from person where id=:id", param);
    }

    public List<Person> findAllWithPet() {
        String sql = "select person.id, first_name, last_name, age, " +
                    "pet.id as pet_id, pet.vid from person " +
                    "left join pet on person.id = pet.person_id";
        return namedJdbcTemplate.query(sql,
                new PersonWithDetailExtractor());
    }

    //почему нельзя с ResultSetExtractor возврать одно значение - PersonWithDetailExtractor возвращаем List
    public Person findByFirstNameWithPetByName(String firstName) {
        String sql = "select person.id, first_name, last_name, age, " +
                "pet.id as pet_id, pet.vid from person " +
                "left join pet on person.id = pet.person_id " +
                "where first_name=:first_name";

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("first_name", firstName);

        return namedJdbcTemplate.query(sql, param, new PersonWithDetailExtractor()).get(0);
    }

    public int insertWithPet(Person person) {
        return 0;
    }

    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from person", Integer.class);
    }

    private static final class PersonRowMapper implements RowMapper<Person> {

        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            Person person = new Person();
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
            person.setId(resultSet.getInt("id"));
            person.setAge(resultSet.getInt("age"));
            return person;
        }
    }

    private static final class PersonWithDetailExtractor implements ResultSetExtractor<List<Person>> {

        public List<Person> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Integer, Person> map = new HashMap<>();
            Person person;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                person = map.get(id);
                if (person == null) {
                    person = new Person();
                    person.setId(id);
                    person.setFirstName(resultSet.getString("first_name"));
                    person.setLastName(resultSet.getString("last_name"));
                    person.setAge(resultSet.getInt("age"));
                    person.setPets(new ArrayList<>());
                    map.put(id, person);
                }
                int petId = resultSet.getInt("pet_id");
                if (petId > 0) {
                    Pet pet = new Pet();
                    pet.setId(petId);
                    pet.setType(resultSet.getString("vid"));
                    pet.setPersonId(id);
                    person.addPet(pet);
                }
            }
            return new ArrayList<>(map.values());
        }
    }
}
