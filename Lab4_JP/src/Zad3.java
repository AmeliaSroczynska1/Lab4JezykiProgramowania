// Stwórz prosty interfejs z polem tekstowym. Dodaj obsługę zdarzeń klawiatury (KeyListener), aby:
//
//Po naciśnięciu klawisza "A" tekst zmieniał kolor na czerwony.
//Po naciśnięciu klawisza "B" tekst zmieniał kolor na niebieski.
//Po naciśnięciu klawisza "C" wyczyść pole tekstowe.
//Haczyk: pole tekstowe jest polem edycyjnym, więc jeśli jesteśmy w nim to litery mają swoje normalne znaczenie.

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Zad3 {
    private final JFrame frame;
    private final JPanel panel;
    private final JTextField textField;

    Zad3(String[] args) {
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

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'A') {
                    textField.setForeground(Color.RED);
                } else if (e.getKeyChar() == 'B') {
                    textField.setForeground(Color.BLUE);
                }
                else if (e.getKeyChar() == 'C') {
                    textField.setText("");
                }
            }
        });
    }

    public static void main(final String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Zad3(args).show();
            }
        });
    }
}
