package filmbook.gui;

import filmbook.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;

import javax.swing.JSeparator;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MovieWindow extends Window {
    private DefaultListModel<CrewOfMovie> obj;
    private JPanel contentPane;
    private JTextField movietitleField;
    private JTextField movieLengthField;
    private JTextField movieYearField;
    private JTextField txtAddCrew;
    private JTextField textField;
    private JList listBox;
    private boolean isNew;

    public void LoadCrewOfMovieList(Movie movie, Api api) throws Exception{
        List<CrewOfMovie> crewOfMovieList = api
                .getCrewOfMovieDao()
                .queryBuilder()
                .join(api.getCrewDao().queryBuilder())
                        .where()
                        .eq(CrewOfMovie.MOVIEID, movie.getMovieID())
                        .query();

        listBox.removeAll();
        obj.removeAllElements();
        for(CrewOfMovie com : crewOfMovieList){
            obj.addElement(com);
        }
        listBox.repaint();
    }

    public MovieWindow(Movie movie, boolean isNew) throws Exception {
        super(isNew ? "Create new movie" : "Edit movie");
        this.isNew = isNew;
        Api api = new Api(new DatabaseHelper());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 487, 814);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(70, 130, 180));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        obj = new DefaultListModel<>();
        listBox = new JList(obj);
        JScrollPane scrollPane = new JScrollPane(listBox);
        scrollPane.setVisible(true);
        scrollPane.setBounds(184, 551, 232, 92);
        contentPane.add(scrollPane);
        JLabel movietitleLabel = new JLabel("Title:");
        movietitleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        movietitleLabel.setForeground(Color.WHITE);
        movietitleLabel.setBounds(32, 108, 116, 25);
        contentPane.add(movietitleLabel);

        movietitleField = new JTextField();
        movietitleField.setBorder(new CompoundBorder());
        movietitleField.setBounds(184, 108, 232, 28);
        contentPane.add(movietitleField);
        movietitleField.setColumns(10);
        movietitleField.setText(movie.getTitle());

        JLabel movieLengthLabel = new JLabel("Movie Length (minutes):");
        movieLengthLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        movieLengthLabel.setForeground(Color.WHITE);
        movieLengthLabel.setBounds(32, 265, 142, 37);
        contentPane.add(movieLengthLabel);

        movieLengthField = new JTextField();
        movieLengthField.setBorder(new CompoundBorder());
        movieLengthField.setColumns(10);
        movieLengthField.setBounds(184, 268, 232, 28);
        contentPane.add(movieLengthField);
        movieLengthField.setText(Integer.toString(movie.getDuration()));

        JLabel movieTypeLabel = new JLabel("Type:");
        movieTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        movieTypeLabel.setForeground(Color.WHITE);
        movieTypeLabel.setBounds(32, 152, 123, 31);
        contentPane.add(movieTypeLabel);

        JComboBox movieTypeBox = new JComboBox(new Object[]{});
        movieTypeBox.setModel(new DefaultComboBoxModel(new String[] {"Action", "Drama", "Comedy", "Romance", "History", "Thriller"}));
        movieTypeBox.setBounds(184, 159, 232, 31);
        movieTypeBox.setSelectedIndex(-1);
        contentPane.add(movieTypeBox);
        movieTypeBox.setSelectedItem(movie.getType());

        JLabel movieRatingLabel = new JLabel("Rating:");
        movieRatingLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        movieRatingLabel.setForeground(Color.WHITE);
        movieRatingLabel.setBounds(32, 209, 107, 31);
        contentPane.add(movieRatingLabel);

        JComboBox movieRatingBox = new JComboBox(new Object[]{});
        movieRatingBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
        movieRatingBox.setBounds(184, 215, 232, 31);
        movieRatingBox.setSelectedIndex(-1);
        contentPane.add(movieRatingBox);
        movieRatingBox.setSelectedItem(Integer.toString(movie.getRating()));

        JLabel label = new JLabel("Photos:");
        label.setFont(new Font("Tahoma", Font.BOLD, 11));
        label.setForeground(Color.WHITE);
        label.setBounds(32, 361, 116, 31);
        contentPane.add(label);

        JPanel photoPanel1 = new JPanel();
        photoPanel1.setBounds(184, 361, 46, 44);
        contentPane.add(photoPanel1);
        photoPanel1.setLayout(null);

        JButton uploadPhoto1 = new JButton((Icon) null);
        uploadPhoto1.setBounds(10, 11, 26, 20);
        photoPanel1.add(uploadPhoto1);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 0, 1, 2);
        photoPanel1.add(separator);

        JPanel photoPanel2 = new JPanel();
        photoPanel2.setLayout(null);
        photoPanel2.setBounds(279, 361, 46, 44);
        contentPane.add(photoPanel2);

        JButton uploadPhoto2 = new JButton((Icon) null);
        uploadPhoto2.setBounds(10, 11, 26, 20);
        photoPanel2.add(uploadPhoto2);

        JPanel photoPanel3 = new JPanel();
        photoPanel3.setLayout(null);
        photoPanel3.setBounds(370, 361, 46, 44);
        contentPane.add(photoPanel3);

        JButton uploadPhoto3 = new JButton((Icon) null);
        uploadPhoto3.setBounds(10, 11, 26, 20);
        photoPanel3.add(uploadPhoto3);

        JLabel label_1 = new JLabel("Year:");
        label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        label_1.setForeground(Color.WHITE);
        label_1.setBounds(32, 420, 101, 31);
        contentPane.add(label_1);

        movieYearField = new JTextField();
        movieYearField.setBorder(new CompoundBorder());
        movieYearField.setColumns(10);
        movieYearField.setBounds(184, 421, 232, 28);
        contentPane.add(movieYearField);
        movieYearField.setText(Integer.toString(movie.getYear()));

        JLabel movieInfoLabel = new JLabel("Summary:");
        movieInfoLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        movieInfoLabel.setForeground(Color.WHITE);
        movieInfoLabel.setBounds(32, 478, 116, 31);
        contentPane.add(movieInfoLabel);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBorder(new CompoundBorder());
        scrollPane_1.setBounds(184, 473, 232, 55);
        contentPane.add(scrollPane_1);

        JTextArea movieInfoArea = new JTextArea();
        movieInfoArea.setBorder(new CompoundBorder());
        scrollPane_1.setViewportView(movieInfoArea);
        movieInfoArea.setText(movie.getDescription());

        JLabel movieLabel = new JLabel(isNew ? "Create Movie" : "Edit Movie");
        movieLabel.setHorizontalAlignment(SwingConstants.CENTER);
        movieLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
        movieLabel.setForeground(Color.WHITE);
        movieLabel.setBounds(136, 36, 190, 44);
        contentPane.add(movieLabel);

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(10, 697, 461, 16);
        contentPane.add(separator_2);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        goBackButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        goBackButton.setBounds(32, 724, 155, 37);
        contentPane.add(goBackButton);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                if(isNew){
                    try{
                        api.deleteMovie(movie);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        saveChangesButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        saveChangesButton.setBounds(285, 724, 155, 37);
        contentPane.add(saveChangesButton);

        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    movie.setTitle(movietitleField.getText());
                    movie.setType((String)movieTypeBox.getSelectedItem());
                    movie.setRating(Integer.parseInt((String)movieRatingBox.getSelectedItem()));
                    movie.setDuration(Integer.parseInt(movieLengthField.getText()));
                    movie.setDescription(movieInfoArea.getText());
                    movie.setYear(Integer.parseInt(movieYearField.getText()));

                    if(!isNew){
                        showMessageDialog(null, "Movie edited successfully");
                    } else {
                        dispose();
                        showMessageDialog(null, "Movie created successfully");
                    }
                    api.editMovie(movie);
                    dispose();

                } catch (Exception e) {
                    e.printStackTrace();
                    showMessageDialog(null, e.getMessage());
                }
            }
        });

        txtAddCrew = new JTextField();


        txtAddCrew.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtAddCrew.setText("-");
        txtAddCrew.setOpaque(false);
        txtAddCrew.setHorizontalAlignment(SwingConstants.CENTER);
        txtAddCrew.setForeground(Color.WHITE);
        txtAddCrew.setFont(new Font("Nirmala UI", Font.PLAIN, 17));
        txtAddCrew.setEditable(false);
        txtAddCrew.setColumns(10);
        txtAddCrew.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        txtAddCrew.setBounds(384, 654, 32, 32);
        contentPane.add(txtAddCrew);
        txtAddCrew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    CrewOfMovie selectedCrewOfMovie = obj.getElementAt(listBox.getSelectedIndex());
                    api.deleteCrewOfMovie(selectedCrewOfMovie);
                    LoadCrewOfMovieList(movie, api);
                } catch (Exception ex){
                    ex.printStackTrace();
                    showMessageDialog(null, ex.getMessage());
                }
            }
        });

        textField = new JTextField();
        textField.setText("+");
        textField.setOpaque(false);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Nirmala UI", Font.PLAIN, 17));
        textField.setEditable(false);
        textField.setColumns(10);
        textField.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        textField.setBounds(341, 654, 32, 32);
        contentPane.add(textField);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    AddCrew aCrew = new AddCrew(movie, MovieWindow.this, api);
                    aCrew.setVisible(true);
                    aCrew.setLocationRelativeTo(null);
                } catch (Exception ex){
                    ex.printStackTrace();
                    showMessageDialog(null, ex.getMessage());
                }
            }
        });

        JLabel lblNewLabel = new JLabel("Crew:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(32, 551, 76, 25);
        contentPane.add(lblNewLabel);

        setVisible(true);
        setLocationRelativeTo(null);

        LoadCrewOfMovieList(movie, api);

    }
}
