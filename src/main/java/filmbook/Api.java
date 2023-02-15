package filmbook;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class Api {

    //Controller

    JdbcConnectionSource jdbc;

    /*zhtame na dw8ei ena connection me th vash, wste na mporesoume na arxikopoihsoume
    to ekastote DAO (gia to antistoixo modelo, px user, h movie, ktl)*/
    public Api(JdbcConnectionSource jdbc) {
        this.jdbc = jdbc;
    }

    public Api(DatabaseHelper db) throws Exception{
        db.createJdbcConnection(DatabaseHelper.DB_NAME);
        jdbc = db.getJdbc();
    }

    public Dao<Movie, Integer> getMovieDao() throws Exception {
        return DaoManager.createDao(jdbc, Movie.class);
    }

    public List<Movie> getMovies() throws Exception{
        Dao<Movie, Integer> movieDao = getMovieDao();
        return movieDao.queryForAll();
    }

    public Movie getMovieById(Integer id) throws Exception {
        Dao<Movie, Integer> movieDao = getMovieDao();
        return movieDao.queryForId(id);
    }

    public Movie getMovieByName(String name) throws Exception {
        Dao<Movie, Integer> movieDao = getMovieDao();

        return movieDao.queryBuilder()
                .where()
                .eq("title", name)
                .queryForFirst();
    }

    //Create movie (status?)
    public void createMovie(Movie movie) throws Exception {
        Dao<Movie, Integer> movieDao = getMovieDao();
        //ousiastika edw to DAO dexetai ena antikeimeno Movie, to opoio eswterika
        //kanei serialize se query.
        movieDao.create(movie);
    }

    //Edit movie
    public void editMovie(Movie movie) throws Exception {
        Dao<Movie, Integer> movieDao = getMovieDao();
        movieDao.update(movie);
    }

    //Delete movie
    public void deleteMovie(Movie movie) throws Exception {
        Dao<Movie, Integer> movieDao = getMovieDao();
        deleteMovieRating(movie);
        movieDao.delete(movie);
    }

    public Dao<Crew, Integer> getCrewDao() throws Exception {
        return DaoManager.createDao(jdbc, Crew.class);
    }

    public void getCrewById(Integer id) throws Exception {
        Dao<Crew, Integer> crewDao = getCrewDao();
        crewDao.queryForId(id);
    }

    public List<Crew> getCrews() throws Exception {
        Dao<Crew, Integer> crewDao = getCrewDao();
        return crewDao.queryForAll();
    }

    //Create filmbook.Crew
    public void createCrew(Crew crew) throws Exception {
        Dao<Crew, Integer> movieDao = getCrewDao();
        movieDao.create(crew);
    }

    //Edit filmbook.Crew
    public void editCrew(Crew crew) throws Exception {
        Dao<Crew, Integer> movieDao = getCrewDao();
        movieDao.update(crew);
    }

    //Delete filmbook.Crew
    public void deleteCrew(Crew crew) throws Exception {//delete ton crew
        Dao<Crew, Integer> crewDao = getCrewDao();
        deleteCrewCascade(crew);
        crewDao.delete(crew);
    }

    public void deleteCrewOfMovie(Crew crew, Movie movie) throws Exception{
        Dao<CrewOfMovie, Integer> crewOfMovies = DaoManager.createDao(jdbc, CrewOfMovie.class);
        DeleteBuilder<CrewOfMovie, Integer> crewOfMovieDeleteBuilder = crewOfMovies.deleteBuilder();
        crewOfMovieDeleteBuilder.where().eq(CrewOfMovie.CREWID, crew.getCrewID()).and().eq(CrewOfMovie.MOVIEID, movie.getMovieID());
        crewOfMovieDeleteBuilder.delete();
    }

    public void deleteCrewOfMovie(CrewOfMovie com) throws Exception{
        Dao<CrewOfMovie, Integer> crewOfMovies = DaoManager.createDao(jdbc, CrewOfMovie.class);
        crewOfMovies.delete(com);
    }

    public void deleteCrewCascade(Crew crew) throws Exception{
        Dao<CrewOfMovie, Integer> crewOfMovies = DaoManager.createDao(jdbc, CrewOfMovie.class);
        DeleteBuilder<CrewOfMovie, Integer> crewOfMovieDeleteBuilder = crewOfMovies.deleteBuilder();
        crewOfMovieDeleteBuilder.where().eq(CrewOfMovie.CREWID, crew.getCrewID());
        crewOfMovieDeleteBuilder.delete();
    }

    //Crew of movie
    public void addCrewToMovie(Crew crew, Movie movie, String type) throws Exception{
        Dao<CrewOfMovie, Integer> crewOfMovies = DaoManager.createDao(jdbc, CrewOfMovie.class);
        //INSERT INTO `crewofmovie`(`movie_id`, `crew_id`, `type`) VALUES(1, 4, "actor")
        crewOfMovies.create(new CrewOfMovie(movie, crew, type));
    }

    public Dao<CrewOfMovie, Integer> getCrewOfMovieDao() throws Exception{
        return DaoManager.createDao(jdbc, CrewOfMovie.class);
    }

    public List<CrewOfMovie> getCrewOfMovie(Movie movie) throws Exception{
        Dao<CrewOfMovie, Integer> crewOfMovies = DaoManager.createDao(jdbc, CrewOfMovie.class);
        return crewOfMovies
                .queryBuilder()
                .where()
                .eq(CrewOfMovie.MOVIEID, movie.getMovieID()).query();
    }

    //Many To Many
    public Dao<MovieRating, Integer> getMovieRatingDao() throws Exception {
        return DaoManager.createDao(jdbc, MovieRating.class);
    }

    //Add movie rating of user (many to many)
    public void userRateMovie(User user, Movie movie, int rating) throws Exception {
        MovieRating found = getMovieRating(user, movie);
        Dao<MovieRating, Integer> movieRatings = getMovieRatingDao();
        if (found != null) { //an vre8ei to rating, tote exoume hdh etoimo dhmiourghmeno filmbook.MovieRating
            found.setRating(rating); //kai apla epekseragazomaste to rating noumero
            movieRatings.update(found);
        } else { //alliws prepei na dhmiourghsoume ena kainourio antikeimeno sysxetishs.
            MovieRating newRating = new MovieRating(user, movie, rating);
            movieRatings.create(newRating);
        }
    }

    public MovieRating getMovieRating(User user, Movie movie) throws Exception {
        Dao<MovieRating, Integer> movieRatings = getMovieRatingDao();
        //prepei na doume arxika an yparxei rating apo to xrhsth gia th sygkekrimenh tainia
        Where<MovieRating, Integer> where = movieRatings.queryBuilder()
                .where()
                .eq(MovieRating.MOVIEID, movie.getMovieID())
                .and()
                .eq(MovieRating.USERID, user.getUserID());
        return where.queryForFirst(); //gyrnaei eite null, eite ena filmbook.MovieRating (ef oswn yparxei)
    }

    public void deleteMovieRating(Movie movie) throws Exception {
        Dao<MovieRating, Integer> movieRatings = getMovieRatingDao();
        DeleteBuilder<MovieRating, Integer> movieRatingDeleteBuilder = movieRatings.deleteBuilder();
        movieRatingDeleteBuilder.where().eq(MovieRating.MOVIEID, movie.getMovieID());
        movieRatingDeleteBuilder.delete();
    }

    public Double getMovieAverageRating(Movie movie) throws Exception{
        Dao<MovieRating, Integer> movieRatings = getMovieRatingDao();
        GenericRawResults<String[]> genericRawResults = movieRatings.queryRaw(
                "SELECT AVG(rating) FROM movies WHERE movies.movieID = '" + movie.getMovieID() + "'");
        //TODO: Prosoxh gia to ti gyrnaei
        return Double.parseDouble(genericRawResults.getFirstResult()[0]);
    }

    public void userWatchMovie(User user, Movie movie) throws Exception {
        Dao<MoviesWatched, Integer> moviesWatched = DaoManager.createDao(jdbc, MoviesWatched.class);
        moviesWatched.create(new MoviesWatched(user, movie));
    }

    //user actions
    public boolean userLogin(String userName, String password) throws Exception {
        Dao<User, Integer> users = DaoManager.createDao(jdbc, User.class);
        User found = users.queryBuilder().where().eq("username", userName).queryForFirst();
        if (found != null) {
            return found.getPassword().equals(stringToSHA256String(password));
        }
        return false;
    }

    public static String stringToSHA256String(String input) throws Exception {
        /*String x = "";
        x.getBytes();*/
        MessageDigest digestAlgorithm = MessageDigest.getInstance("SHA-256"); // xrhsimopoioume ton algori8mo SHA-256
        byte[] hash = digestAlgorithm.digest(input.getBytes(StandardCharsets.UTF_8)); //pairnoume ta bytes tou input (utf8) kai amesws kanoume digest
        //to apotelesma tou digest einai kai auto bytes (opws to getBytes(StandardCharsets.UTF_8))
        StringBuffer hexString = new StringBuffer(); //xrhsimopoiume stringbuffer gia optimization

        /*StringBuffer x = new StringBuffer();x.append("kati");
        x.append(" allo");
        x.toString(); edw den xreiazetai na kanie klhsh o Garbage Collector. Epeidh den vgainoun apo to scope
        ta antistoixa strings, ta opoia menoun orfana sth mnhmh.*/

        for (int i = 0; i < hash.length; i++) { //kai edw me mia for metatrepoume ton ari8mo se string wste na apo8hkeu8ei sth vash.
            String hex = Integer.toHexString(0xff & hash[i]); //255 -> FF
            if (hex.length() == 1) hexString.append('0'); //FF0A21AB12...53 -> siga siga xtizei olo to hash se string
            hexString.append(hex);
        }
        return hexString.toString();
    }
}