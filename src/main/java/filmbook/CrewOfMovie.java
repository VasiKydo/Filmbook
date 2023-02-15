package filmbook;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CrewOfMovie {

    public static final String CREWID = "crew_id";
    public static final String MOVIEID = "movie_id";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, columnName = MOVIEID, foreignAutoRefresh = true)
    private Movie movie;
    @DatabaseField(foreign = true, columnName = CREWID, foreignAutoRefresh = true)
    private Crew crew;

    //O crew auths ths tainias, phre meros ws type (dhladh actor h director)
    @DatabaseField
    private String type;

    public CrewOfMovie() {}

    public CrewOfMovie(Movie movie, Crew crew, String type) {
        this.movie = movie;
        this.crew = crew;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return String.format("%s %s - %s", crew.getName(), crew.getSurname(), type);
    }

}
