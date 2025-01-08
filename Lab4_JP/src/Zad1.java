//Zaprojektuj interfejs Swing z polem tekstowym. Po kliknięciu myszą w to pole, aplikacja powinna
// wyświetlić współrzędne kliknięcia w konsoli.

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Zad1 {
    private final JFrame frame;
    private final JPanel panel;
    private final JTextField textField;

    Zad1(String[] args) {
        frame = new JFrame("Interfejs z polem tekstowym");
        panel = new JPanel();
        textField = new JTextField();
    }

    public void show() {
        // Ustawienia głównego okna
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);                      // Ustawienie okna na środku ekranu

        panel.setBackground(Color.decode("#DC667C"));       // Ustawienie koloru tła
        textField.setBackground(Color.decode("#DC667C"));
        textField.setFont(new Font("Arial", Font.BOLD, 24));     // Ustawienie czcionki
        textField.setForeground(Color.WHITE); // Ustawienie koloru tekstu
        textField.setText("Kliknij w to pole, aby pokazać współrzędne kliknięcia.");
        panel.add(textField);
        frame.setVisible(true);

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                int newX = e.getX();
                int newY = e.getY();

                System.out.println("Współrzędna X kliknięcia: " + newX + "\nWspółrzędna Y: " + newY);
            }
        });
    }

    public static void main(final String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Zad1(args).show();
            }
        });
    }
}