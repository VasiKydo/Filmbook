package filmbook.gui;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import filmbook.Api;
import filmbook.DatabaseHelper;
import filmbook.Movie;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SearchForm extends JFrame {
    private JCheckBox movieTypeLabel;
    private JComboBox movieTypeBox;
    private JLabel movieCrewLabel;
    private JComboBox movieCrewBox;
    private JCheckBox movieRatingLabel;
    private JComboBox movieRatingBox;
    private JPanel panel;
    private JButton searchButton;
    private JButton backButton;
    private JCheckBox movieTypeCheck;
    private Api api;

    public SearchForm(Api api){
        super("Filmbook - Search Movies");
        this.api = api;

        //Search by Movie Type
        movieTypeLabel=new JCheckBox("Search by Movie Type:");
        movieTypeLabel.setForeground(Color.WHITE);
        movieTypeLabel.setBackground(new Color(70, 130, 180));
        String[] typeList = { "","Action","Drama","Comedy","Romance", "History", "Thriller" };
        movieTypeBox = new JComboBox(typeList);
        movieTypeBox.setEnabled(false);
        movieTypeLabel.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    movieTypeBox.setEnabled(true);
                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){
                    movieTypeBox.setEnabled(false);
                    movieTypeBox.setSelectedItem("");
                }
            }
        });

        //Search by Actor/Director
        JCheckBox movieCrewLabel=new JCheckBox("Search by Actor/Director:");
        movieCrewLabel.setForeground(Color.WHITE);
        movieCrewLabel.setBackground(new Color(70, 130, 180));
        String[] CrewList = new String[] { "","Russell Crowe", "Leonardo DiCaprio", "Sam Worthington", "Tom Hanks",
                "Ron Howard", "James Cameron", "Robert Zemeckis" };
        JComboBox movieCrewBox = new AutoCompleteComboBox(CrewList);
        movieCrewBox.setEnabled(false);
        movieCrewLabel.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    movieCrewBox.setEnabled(true);
                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){
                    movieCrewBox.setEnabled(false);
                    movieCrewBox.setSelectedItem("");
                }
            }
        });

        //Search by Rating
        movieRatingLabel=new JCheckBox("Search by Rating:");
        movieRatingLabel.setForeground(Color.WHITE);
        movieRatingLabel.setBackground(new Color(70, 130, 180));
        Integer[] ratingList = { null,5,4,3,2,1 };
        JComboBox movieRatingBox = new JComboBox(ratingList);
        movieRatingBox.setEnabled(false);
        movieRatingLabel.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    movieRatingBox.setEnabled(true);
                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){
                    movieRatingBox.setEnabled(false);
                }
            }
        });

        //Buttons
        backButton= new JButton("Go Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //check for empty criteria
                    if(movieTypeLabel.isSelected()==false && movieCrewLabel.isSelected()==false && movieRatingLabel.isSelected()==false){
                        JOptionPane.showMessageDialog(null,"Please select at least one search criterion");
                    }
                    else{

                        //declare select condition
                        String selectData = "";
                        String whereClause = "";

                        //flag to check if where was used
                        boolean typeFlag = false;
                        if(movieTypeLabel.isSelected()){
                            if(!movieTypeBox.getSelectedItem().equals("")) {
                                typeFlag = true;
                                selectData += "(type = '" + movieTypeBox.getSelectedItem() + "')";
                                whereClause += "type = '" + movieTypeBox.getSelectedItem() + "'";
                                dispose();
                            }else if(movieTypeBox.getSelectedItem().equals("") && movieRatingBox.getSelectedItem()==null && movieCrewBox.getSelectedItem().equals("")){
                                JOptionPane.showMessageDialog(null,"Please select a movie type");
                            }
                        }

                        boolean ratingFlag = false;
                        if(movieRatingLabel.isSelected()){
                            if(movieRatingBox.getSelectedItem()!=null){
                                ratingFlag = true;
                                if(typeFlag){
                                    selectData += "+";
                                    whereClause += " OR ";
                                }
                                selectData += "(rating >= '" + movieRatingBox.getSelectedItem() + "')";
                                whereClause += "rating >= '" + movieRatingBox.getSelectedItem() + "'";
                            }
                            else if(movieTypeBox.getSelectedItem().equals("") && movieRatingBox.getSelectedItem()==null && movieCrewBox.getSelectedItem().equals("")){
                                JOptionPane.showMessageDialog(null,"Please select a ranking");
                            }
                        }

                        selectData += "as feat";
                        String query = String.format("SELECT *, %s FROM `movie` WHERE(%s) ORDER BY `feat` DESC", selectData, whereClause);
                        Dao<Movie, Integer> movieDao = api.getMovieDao();

                        java.util.List<String[]> rawMovies = movieDao.queryRaw(query).getResults();
                        java.util.List<Movie> movies = new ArrayList<>();

                        for(String[] rawMovie : rawMovies){
                            movies.add(new Movie(rawMovie));
                        }

                        if (movieCrewLabel.isSelected()){
                            if(!movieCrewBox.getSelectedItem().equals("")){

                            }
                            else if(movieTypeBox.getSelectedItem().equals("") && movieRatingBox.getSelectedItem()==null && movieCrewBox.getSelectedItem().equals("")){
                                JOptionPane.showMessageDialog(null,"Please select crew");
                            }
                        }

                        SearchPanel.MyTableModel model = new SearchPanel.MyTableModel();
                        model.addSearchResults(movies);
                        new SearchPanel(api).createAndShowGUI(model);
                        dispose();
                    }

                } catch (Exception e){
                    e.printStackTrace();

                }


            }
        });




        //Panel
        panel=new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,50,10,50));
        panel.setLayout(new GridLayout(4,2,7,7));
        panel.setBackground(new Color(70, 130, 180));
        panel.add(movieTypeLabel);
        panel.add(movieTypeBox);
        panel.add(movieCrewLabel);
        panel.add(movieCrewBox);
        panel.add(movieRatingLabel);
        panel.add(movieRatingBox);
        panel.add(backButton);
        panel.add(searchButton);
        getContentPane().add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

}