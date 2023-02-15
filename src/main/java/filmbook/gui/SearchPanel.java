package filmbook.gui;

import filmbook.Api;
import filmbook.DatabaseHelper;
import filmbook.Movie;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class SearchPanel extends JPanel {

    JTable table;
    private Api api;
    //MyTableModel myTableModel = new MyTableModel();

    public SearchPanel(Api api){
        this.api = api;
    }

    public SearchPanel(MyTableModel model, Api api) {
        super(new GridLayout(2,0));
        this.api = api;
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.BOLD, 15));
        table.setPreferredScrollableViewportSize(new Dimension(1300, 180));
        table.setFillsViewportHeight(true);
        table.getColumn("View").setCellRenderer(new ButtonRenderer());
        table.getColumn("View").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.setBackground(new Color(70, 130, 180));
        table.setForeground(Color.white);
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    static class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {
                "Title",
                "Movie Type",
                "Rating",
                "Movie Length",
                "Director",
                "Actors",
                "Year",
                "Watched",
                "View"
        };

        private Object[][] data = {};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public void addSearchResults(java.util.List<Movie> movies){
            data = new Object[movies.size()][columnNames.length];
            int i = 0;
            for(Movie m : movies){
                data[i][0] = m.getTitle();
                data[i][1] = m.getType();
                data[i][2] = (Integer)m.getRating();
                data[i][3] = (Integer)m.getDuration();
                data[i][4] = ""; //TODO: ask ORM for crew
                data[i][5] = "";
                data[i][6] = (Integer)m.getYear();
                data[i][7] = (Boolean)true;
                data[i][8] = "View";
                i++;
            }
        }

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 7) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c) == null ? Object.class : getValueAt(0, c).getClass();
            //Object o = <expression (boolean)> ? assign 1 : assign 2;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

    public void createAndShowGUI(MyTableModel model) {

        //Panel
        JPanel panel=new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,300,10,300));
        panel.setLayout(new GridLayout(4,2,20,10));
        panel.setBackground(new Color(70, 130, 180));
        JLabel label1 = new JLabel("");
        JLabel label2 = new JLabel("");

        //Create and set up the window.
        JFrame frame = new JFrame("Filmbook - Search Results");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SearchPanel newContentPane = new SearchPanel(model, api);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    frame.dispose();
                    SearchForm sfFrame = new SearchForm(api);
                    sfFrame.setVisible(true);
                    sfFrame.setLocationRelativeTo(null);

                }catch (Exception e){

                }
            }
        });
        JButton searchButton = new JButton("Save Changes");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                /*try {
                    DatabaseHelper dbhelper = new DatabaseHelper();
                    dbhelper.createJdbcConnection("filmbook");
                    Api api = new Api(dbhelper.getJdbc());

                    myTableModel.addSearchResults(api.getMovies());
                    myTableModel.fireTableDataChanged();
                    table.setModel(myTableModel);
                } catch (Exception e){

                }*/

            }
        });

        panel.add(label1);
        panel.add(label2);
        panel.add(backButton);
        panel.add(searchButton);
        frame.add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    //"view" Buttons

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
//            if (isSelected) {
//                setForeground(table.getSelectionForeground());
//                setBackground(table.getSelectionBackground());
//            } else {
//                setForeground(table.getForeground());
//                setBackground(UIMnager.getColor("Button.background"));
//            }
            return this;
        }
    }

    //"view"  buttons
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            button = new JButton("View");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String movieTitle = (String)table.getValueAt(table.getSelectedRow(), 0);

                    try{
                        Window mvw = new MovieViewWindow(api.getMovieByName(movieTitle));
                        mvw.setVisible(true);
                        mvw.setLocationRelativeTo(null);
                    } catch (Exception ex){
                        ex.printStackTrace();
                        showMessageDialog(null, ex.getMessage());
                    }

                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
//            if (isSelected) {
//                button.setForeground(table.getSelectionForeground());
//                button.setBackground(table.getSelectionBackground());
//            } else {
//                button.setForeground(table.getForeground());
//                button.setBackground(table.getBackground());
//            }


            return button;
        }
    }
}