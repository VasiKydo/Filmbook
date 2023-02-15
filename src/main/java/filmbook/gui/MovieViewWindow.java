package filmbook.gui;

import filmbook.Api;
import filmbook.Movie;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.border.MatteBorder;


public class MovieViewWindow extends Window {

    private JPanel contentPane;

    public MovieViewWindow(Movie movie) {
        super("Filmbook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 928, 480);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("rwq v213.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(21, 31, 321, 388);
        Image dimg = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        imgLabel.setIcon(imageIcon);
        contentPane.add(imgLabel);

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setBounds(376, 31, 332, 66);
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Calibri Light", Font.BOLD, 48));
        contentPane.add(titleLabel);

        JLabel yearLabel = new JLabel(Integer.toString(movie.getYear()));
        yearLabel.setBounds(376, 98, 63, 29);
        yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
        yearLabel.setFont(new Font("Calibri Light", Font.BOLD, 15));
        yearLabel.setForeground(Color.WHITE);
        contentPane.add(yearLabel);

        JLabel lblNewLabel = new JLabel(".");
        lblNewLabel.setBounds(433, 99, 22, 21);
        lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 37));
        lblNewLabel.setForeground(Color.WHITE);
        contentPane.add(lblNewLabel);

        JLabel lblMins = new JLabel(String.format("%d min", movie.getDuration()));
        lblMins.setBounds(465, 98, 63, 29);
        lblMins.setHorizontalAlignment(SwingConstants.LEFT);
        lblMins.setForeground(Color.WHITE);
        lblMins.setFont(new Font("Calibri Light", Font.BOLD, 15));
        contentPane.add(lblMins);

        JLabel label = new JLabel(".");
        label.setBounds(537, 98, 22, 21);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.PLAIN, 37));
        contentPane.add(label);

        JLabel lblType = new JLabel(movie.getType());
        lblType.setBounds(567, 98, 176, 29);
        lblType.setHorizontalAlignment(SwingConstants.LEFT);
        lblType.setForeground(Color.WHITE);
        lblType.setFont(new Font("Calibri Light", Font.BOLD, 15));
        contentPane.add(lblType);

        JLabel labelRating = new JLabel(String.format("%d/5", movie.getRating()));
        labelRating.setBounds(753, 98, 63, 29);
        labelRating.setHorizontalAlignment(SwingConstants.LEFT);
        labelRating.setForeground(Color.WHITE);
        labelRating.setFont(new Font("Calibri Light", Font.BOLD, 15));
        contentPane.add(labelRating);

        JLabel label_2 = new JLabel(".");
        label_2.setBounds(721, 98, 22, 21);
        label_2.setVerticalAlignment(SwingConstants.BOTTOM);
        label_2.setForeground(Color.WHITE);
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 37));
        contentPane.add(label_2);

        JLabel lblDescription = new JLabel("");
        lblDescription.setBounds(361, 164, 455, 75);
        lblDescription.setText(String.format("<html>%s</html>", movie.getDescription()));
        lblDescription.setVerticalAlignment(SwingConstants.TOP);
        lblDescription.setFont(new Font("Calibri Light", Font.BOLD, 15));
        lblDescription.setForeground(Color.WHITE);
        contentPane.add(lblDescription);

        Checkbox checkbox = new Checkbox("Watched");
        checkbox.setBounds(376, 337, 95, 22);
        checkbox.setFont(new Font("Calibri Light", Font.BOLD, 13));
        checkbox.setForeground(Color.WHITE);
        contentPane.add(checkbox);

        JButton btnGoBackButton = new JButton("Go Back");
        btnGoBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        btnGoBackButton.setBounds(785, 390, 106, 29);
        btnGoBackButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        btnGoBackButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        contentPane.add(btnGoBackButton);

        JButton btnEditMovie = new JButton("Edit Movie");
        btnEditMovie.setBounds(361, 390, 106, 29);
        btnEditMovie.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditMovie.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        contentPane.add(btnEditMovie);
        btnEditMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new MovieWindow(movie, false);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        JLabel lblDirector = new JLabel("Director :" );
        lblDirector.setBounds(358, 250, 63, 29);
        lblDirector.setForeground(Color.WHITE);
        lblDirector.setFont(new Font("Calibri Light", Font.BOLD, 15));
        contentPane.add(lblDirector);

        JLabel lblActors = new JLabel("Actors :");
        lblActors.setBounds(368, 284, 49, 29);
        lblActors.setForeground(Color.WHITE);
        lblActors.setFont(new Font("Calibri Light", Font.BOLD, 15));
        contentPane.add(lblActors);

        JLabel lblDirectorField = new JLabel("The wachowski brothers");
        lblDirectorField.setBounds(424, 250, 407, 29);
        lblDirectorField.setForeground(Color.WHITE);
        lblDirectorField.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        contentPane.add(lblDirectorField);

        JLabel lblActorsField = new JLabel("");
        lblActorsField.setText("<html>"+" Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss"+"<html>");
        lblActorsField.setVerticalAlignment(SwingConstants.TOP);
        lblActorsField.setForeground(Color.WHITE);
        lblActorsField.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        lblActorsField.setBounds(424, 284, 407, 47);
        contentPane.add(lblActorsField);



    }


}
