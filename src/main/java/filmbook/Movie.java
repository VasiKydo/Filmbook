package filmbook;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

@DatabaseTable
public class Movie {

    //Bean (Grass pattern)

    @DatabaseField(generatedId = true)
    private int movieID;
    @DatabaseField
    private String title;
    @DatabaseField
    private String type;
    @DatabaseField
    private int rating;
    @DatabaseField
    private int duration;
    @DatabaseField
    private String description;
    @DatabaseField
    private int year;
    @DatabaseField
    private boolean watched;
    @DatabaseField
    private Date watchDay;

    public Movie() {}

    public Movie(String title, String type, int rating, int duration, String description, int year) {
        this.title = title;
        this.type = type;
        this.rating = rating;
        this.duration = duration;
        this.description = description;
        this.year = year;
    }

    public Movie(String[] rawResult){
        movieID = Integer.parseInt(rawResult[0]);
        title = rawResult[1];
        type = rawResult[2];
        rating = Integer.parseInt(rawResult[3]);
        duration = Integer.parseInt(rawResult[4]);
        description = rawResult[5];
        year = Integer.parseInt(rawResult[6]);
        watched = Boolean.parseBoolean(rawResult[7]);
        //watchDay = Date.valueOf(rawResult[8]);
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
