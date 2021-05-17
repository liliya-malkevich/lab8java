package g9varB;

import javax.swing.SwingUtilities;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(3);
                frame.setVisible(true);
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame1 = new MainFrame();
                frame1.setDefaultCloseOperation(3);
                frame1.setVisible(true);
            }
        });
    }
}
