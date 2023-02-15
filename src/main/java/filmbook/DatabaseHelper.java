package filmbook;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.*;

public class DatabaseHelper {

    private JdbcConnectionSource jdbc;

    public final static String DB_NAME = "filmbook";

    //To exception prepei na emfanizetai ws messagebox sth swing
    public void createJdbcConnection(String DatabaseName) throws Exception{
        if(DatabaseName == null || DatabaseName.equals("")){
            throw new Exception("Database cannot be empty or null");
        }

        FileInputStream fis = new FileInputStream("db.prop");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String host = "";
        String port = "";
        String user = "";
        String password = "";

        String line;
        while((line = br.readLine()) != null){
            if(line.contains("host:")){
                host = line.split(":")[1].trim();
            }
            else if(line.contains("port:")){
                port = line.split(":")[1].trim();
            }
            else if(line.contains("user:")){
                user = line.split(":")[1].trim();
            }
            else if(line.contains("password:")){
                password = line.split(":")[1].trim();
            }
        }

        br.close();
        isr.close();
        fis.close();

        jdbc = new JdbcConnectionSource("jdbc:mysql://"+host+":"+port+"/"+DatabaseName, user, password);
        createTables();
        //TODO: create schema
        //seedDatabase();
    }

    public JdbcConnectionSource getJdbc(){
        return jdbc;
    }

    private void createTables() throws Exception{
        TableUtils.createTableIfNotExists(jdbc, User.class);
        TableUtils.createTableIfNotExists(jdbc, Movie.class);
        TableUtils.createTableIfNotExists(jdbc, Crew.class);
        TableUtils.createTableIfNotExists(jdbc, MovieRating.class);
        TableUtils.createTableIfNotExists(jdbc, MoviesWatched.class);
        TableUtils.createTableIfNotExists(jdbc, CrewOfMovie.class);
    }

    private void seedDatabase(){
        try{
            Dao<User, Integer> userDao = DaoManager.createDao(getJdbc(), User.class);
            userDao.createOrUpdate(new User("John","Doe", "john@email.com", "johndoe", "test1234"));
            userDao.createOrUpdate(new User("Filos","Adelfos", "filos@adelfos.com", "filosadelfos", "1234"));
            userDao.createOrUpdate(new User("Jokes","Onyou", "tricky@pic.com", "trickypic", "1234"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
