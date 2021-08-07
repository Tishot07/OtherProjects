package tishort.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="pet")
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "vid")
    private String type;
    @Column(name = "person_id")
    private int personId;

    /*
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
*/
    public Pet() {
    }

    public Pet(String type, int personId) {
        this.type = type;
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "type='" + type + '\'' +
                ", personId=" + personId +
                '}';
    }
/*
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
