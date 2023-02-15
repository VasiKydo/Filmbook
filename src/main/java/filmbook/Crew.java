package filmbook;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class Crew {

    public static final String ACTOR = "Actor";
    public static final String DIRECTOR = "Director";

    @DatabaseField(generatedId = true)
    private int crewID;
    @DatabaseField
    private String name;
    @DatabaseField
    private String surname;
    @DatabaseField
    private Date dateOfBirth;
    @DatabaseField
    private String nationality;

    public Crew() {}

    public Crew(String name, String surname, Date dateOfBirth, String nationality) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public int getCrewID() {
        return crewID;
    }

    public void setCrewID(int crewID) {
        this.crewID = crewID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString(){
        return String.format("%s %s",  getName(), getSurname());
    }

}
