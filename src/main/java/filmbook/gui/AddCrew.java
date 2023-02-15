package filmbook.gui;

import filmbook.Api;
import filmbook.Crew;
import filmbook.DatabaseHelper;
import filmbook.Movie;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;


import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import com.toedter.calendar.JDateChooser;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddCrew extends Window{

    private JPanel contentPane;
    private JTextField nameField;
    private JTextField nationalityField;
    private DefaultListModel<Crew> obj;
    private JList listBox;
    private Api api;

    public void LoadCrewList() throws Exception {
        listBox.removeAll();
        obj.removeAllElements();
        for(Crew c : api.getCrews()){
            obj.addElement(c);
        }
        listBox.repaint();
    }

    public AddCrew(Movie movie, MovieWindow parent, Api api) throws Exception {
        super("Add Crew");
        this.api = api;
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 433, 171);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(70, 130, 180));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        obj = new DefaultListModel<>();
        listBox = new JList(obj);

        JScrollPane scrollPane = new JScrollPane(listBox);
        scrollPane.setVisible(false);
        scrollPane.setBounds(38, 412, 199, 148);
        contentPane.add(scrollPane);

        JLabel addCrewLabel = new JLabel("Add Crew");
        addCrewLabel.setForeground(Color.WHITE);
        addCrewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        addCrewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addCrewLabel.setBounds(74, 11, 271, 50);
        contentPane.add(addCrewLabel);

        JRadioButton directorRadioButton = new JRadioButton("Director");


        directorRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        directorRadioButton.setBackground(new Color(70, 130, 180));
        directorRadioButton.setForeground(Color.WHITE);
        directorRadioButton.setBounds(103, 68, 100, 55);
        contentPane.add(directorRadioButton);

        JRadioButton actorRadioButton = new JRadioButton("Actor");
        actorRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        actorRadioButton.setBackground(new Color(70, 130, 180));
        actorRadioButton.setForeground(Color.WHITE);
        actorRadioButton.setBounds(255, 68, 90, 55);
        contentPane.add(actorRadioButton);

        ButtonGroup group = new ButtonGroup();
        group.add(actorRadioButton);
        group.add(directorRadioButton);

        nameField = new JTextField();
        nameField.setVisible(false);
        nameField.setColumns(10);
        nameField.setBorder(new CompoundBorder());
        nameField.setBounds(222, 184, 125, 28);
        contentPane.add(nameField);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setVisible(false);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        nameLabel.setBounds(103, 184, 109, 29);
        contentPane.add(nameLabel);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setVisible(false);
        dateChooser.setBounds(222, 261, 125, 28);
        contentPane.add(dateChooser);

        JLabel dateOfBirthLabel = new JLabel("Date Of Birth");
        dateOfBirthLabel.setVisible(false);
        dateOfBirthLabel.setForeground(Color.WHITE);
        dateOfBirthLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        dateOfBirthLabel.setBounds(103, 263, 109, 29);
        contentPane.add(dateOfBirthLabel);

        JLabel nationalityLabel = new JLabel("Nationality");
        nationalityLabel.setVisible(false);
        nationalityLabel.setForeground(Color.WHITE);
        nationalityLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        nationalityLabel.setBounds(103, 340, 109, 29);
        contentPane.add(nationalityLabel);

        nationalityField = new JTextField();
        nationalityField.setVisible(false);
        nationalityField.setColumns(10);
        nationalityField.setBorder(new CompoundBorder());
        nationalityField.setBounds(222, 341, 125, 28);
        contentPane.add(nationalityField);

        JButton addButton = new JButton("Add");
        addButton.setVisible(false);
        addButton.setBounds(278, 425, 107, 23);
        contentPane.add(addButton);

        JButton deleteSelectedButton = new JButton("Delete Selected");
        deleteSelectedButton.setVisible(false);
        deleteSelectedButton.setMargin(new Insets(0, 0, 0, 0));
        deleteSelectedButton.setBounds(278, 473, 107, 23);
        contentPane.add(deleteSelectedButton);

        JButton btnNewButton = new JButton("Insert");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try{
                    Crew selectedCrew = obj.getElementAt(listBox.getSelectedIndex());
                    api.addCrewToMovie(selectedCrew, movie, directorRadioButton.isSelected() ? "Director" : "Actor");
                    parent.LoadCrewOfMovieList(movie, api);
                    parent.setVisible(true);
                    parent.setLocationRelativeTo(null);
                } catch (Exception ex){
                    ex.printStackTrace();
                    showMessageDialog(null, ex.getMessage());
                }

            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnNewButton.setBounds(154, 623, 125, 36);
        contentPane.add(btnNewButton);


        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Date date = dateChooser.getDate();
                if (date == null || nationalityField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                }else {
                    try{
                        String[] fullName = nameField.getText().split(" ");
                        Crew c = new Crew(fullName[0], fullName[1], date, nationalityField.getText());
                        api.createCrew(c);
                        LoadCrewList();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });

        deleteSelectedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(listBox.getSelectedIndex()!=-1) {
                    Crew crew = obj.getElementAt(listBox.getSelectedIndex());
                    try{
                        api.deleteCrew(crew);
                        LoadCrewList();
                    } catch (Exception ex){
                        showMessageDialog(null, ex.getMessage());
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Please Select crew");
                }
            }
        });

        ItemListener il = e -> {
            nameField.setVisible(true);
            nameLabel.setVisible(true);
            dateOfBirthLabel.setVisible(true);
            dateChooser.setVisible(true);
            nationalityLabel.setVisible(true);
            nationalityField.setVisible(true);
            addButton.setVisible(true);
            deleteSelectedButton.setVisible(true);
            scrollPane.setVisible(true);
            setBounds(100, 100, 433, 717);
            setLocationRelativeTo(null);
        };

        directorRadioButton.addItemListener(il);
        actorRadioButton.addItemListener(il);

        LoadCrewList();
    }
}
