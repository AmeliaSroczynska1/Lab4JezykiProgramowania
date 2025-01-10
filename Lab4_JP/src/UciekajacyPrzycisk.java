// "uciekający" przycisk, strefa bezpieczna - 40 pikseli od prawej nie powoduje uciekania

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class UciekajacyPrzycisk {
    private JFrame frame;
    private JButton button;
    private Random random = new Random();
    private JPanel panel;

    // Konstruktor
    UciekajacyPrzycisk(String[] args) {
        frame = new JFrame("Uciekający przycisk");
        button = new JButton("Złap mnie");
        panel = new JPanel();
    }

    // Metoda do wyświetlania okna
    public void show() {
        frame.setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);

        button.setBackground(Color.decode("#A3A3A3"));
        panel.setBackground(Color.decode("#DC667C"));

        button.setBounds(250, 150, 100, 50); // Początkowa pozycja i rozmiar przycisku
        panel.add(button);

        // Dodawanie zachowania uciekającego przycisku
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Sprawdzamy, czy mysz znajduje się w strefie bezpiecznej
                if (button.getX() + button.getWidth() + 40 <= panel.getWidth()) {
                    int maxX = panel.getWidth() - button.getWidth();
                    int maxY = panel.getHeight() - button.getHeight();

                    int newX = random.nextInt(maxX);
                    int newY = random.nextInt(maxY);

                    button.setLocation(newX, newY);
                }
            }
        });
        frame.add(panel);
    }

    // Punkt wejścia programu
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UciekajacyPrzycisk(args).show();
            }
        });
    }
}