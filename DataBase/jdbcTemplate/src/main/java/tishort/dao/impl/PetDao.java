package tishort.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import tishort.dao.IPetDao;
import tishort.model.Pet;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("PetDao")
public class PetDao implements IPetDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Pet> findAll() {
        String sql = "select * from pet";
        return jdbcTemplate.query(sql, new PetRowMapper());
    }

    public List<Pet> findByPerson(int personId) {
        String sql = "select person_id, vid, id from pet where person_id = ?";
        return jdbcTemplate.query(sql, new PetRowMapper(), personId);
    }

    public int insert(Pet pet) {
        KeyHolder id = new GeneratedKeyHolder();
        String sql = "insert into pet (vid, person_id) values (:vid, :personId)";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("vid", pet.getType());
        param.addValue("personId", pet.getPersonId());
        namedJdbcTemplate.update(sql, param, id);
        return id.getKey().intValue();
    }

    public void update(Pet pet) {

    }

    public void delete(int id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        namedJdbcTemplate.update("delete from pet where person_id=:id", param);
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from pet", Integer.class);
    }

    private static final class PetRowMapper implements RowMapper<Pet> {

        public Pet mapRow(ResultSet resultSet, int i) throws SQLException {
            Pet pet = new Pet();
            pet.setPersonId(resultSet.getInt("person_id"));
            pet.setType(resultSet.getString("vid"));
            pet.setId(resultSet.getInt("id"));
            return pet;
        }
    }
}
