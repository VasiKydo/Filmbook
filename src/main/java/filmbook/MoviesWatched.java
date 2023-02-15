package filmbook;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MoviesWatched {

    public static final String USERID = "user_id";
    public static final String MOVIEID = "movie_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = USERID)
    private User user;

    @DatabaseField(foreign = true, columnName = MOVIEID)
    private Movie movie;

    public MoviesWatched() {}

    public MoviesWatched(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
