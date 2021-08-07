package tishort.model;


public class Pet {

    private int id;
    private String type;
    private int personId;

    public Pet() {
    }

    public Pet(int id, String type, int personId) {
        this.id = id;
        this.type = type;
        this.personId = personId;
    }

    public Pet(String type, int personId) {
        this.type = type;
        this.personId = personId;
    }

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

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", personId=" + personId +
                '}';
    }
}
