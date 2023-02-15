package filmbook.gui;

import filmbook.Api;
import filmbook.DatabaseHelper;
import filmbook.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private JLabel logo;
    private JButton searchMoviesButton;
    private JButton addMovieButton;
    private JButton watchedMoviesButton;
    private JButton logoutButton;
    private JPanel panel;
    private JLabel emptyLabel;

    public MainWindow(Api api){
        super("Filmbook - User panel");

        logo=new JLabel("Filmbook");

        emptyLabel=new JLabel(" ");

        //Logo
        logo.setFont (new Font("Verdana", Font.BOLD, 50));
        logo.setForeground(Color.white);

        //SearchMovies Button
        searchMoviesButton = new JButton ("Search Movies");
        searchMoviesButton.setFont (new Font("Arial", Font.BOLD, 20));
        searchMoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    SearchForm swFrame = new SearchForm(api);
                    swFrame.setVisible(true);
                    swFrame.setLocationRelativeTo(null);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //Add new Movie Button
        addMovieButton = new JButton ("Add New Movie");
        addMovieButton.setFont (new Font("Arial", Font.BOLD, 20));
        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    Movie newMovie = new Movie();
                    api.createMovie(newMovie);
                    new MovieWindow(newMovie, true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //List of Watched Movies Button
        watchedMoviesButton = new JButton ("List of Watched Movies");
        watchedMoviesButton.setFont (new Font("Arial", Font.BOLD, 20));

        //Logout Button
        logoutButton = new JButton ("Logout");
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                LoginWindow login = new LoginWindow(api);
                login.setVisible(true);
            }
        });
        logoutButton.setFont (new Font("Arial", Font.BOLD, 20));

        //Panel
        panel=new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5,100,30,100));
        panel.setLayout(new GridLayout(0,1, 10,10));
        panel.setBackground(new Color(70, 130, 180));
        panel.add(logo);
        panel.add(searchMoviesButton);
        panel.add(addMovieButton);
        panel.add(watchedMoviesButton);
        panel.add(emptyLabel);
        panel.add(logoutButton);
        getContentPane().add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

}
