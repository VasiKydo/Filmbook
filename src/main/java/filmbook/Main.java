package filmbook;

import filmbook.gui.LoginWindow;

public class Main {

    public static void main(String[] args){
        try {
            Api api = new Api(new DatabaseHelper());
            LoginWindow login = new LoginWindow(api);
            login.setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
