// Stwórz interfejs z obszarem rysowania (np. JPanel). Dodaj obsługę zdarzeń myszy tak, aby:
//Po najechaniu kursorem na obszar rysowania, wyświetlano informację w panelu bocznym.
//Po opuszczeniu obszaru rysowania kursorem, wyświetlano inną informację w panelu bocznym.
//Po naciśnięciu i zwolnieniu przycisku myszy, wyświetlano komunikat w panelu bocznym.
//Po użyciu kółka myszy, zmieniano rozmiar wybranej figury na obszarze rysowania.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Zad5 extends JPanel {
    private final JFrame frame;          // Główne okno aplikacji
    private final JPanel sidePanel;
    private final JTextArea textArea;
    private final JTextField sideTextField;
    private String currentShape = "circle"; // Domyślny kształt
    private final List<Shape> shapes = new ArrayList<>(); // Lista przechowująca rysowane kształty
    private Shape selectedShape = null; // Zaznaczona figura

    // Konstruktor
    public Zad5(String[] args) {
        frame = new JFrame("Kanwa do rysowania");
        sidePanel = new JPanel();
        textArea = new JTextArea();
        sideTextField = new JTextField(20);

        this.setFocusable(true);
    }

    // Metoda do tworzenia i wyświetlania GUI
    public void showCanva() {
        // Ustawienia głównego okna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        // Panel boczny
        sidePanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Pierwsza kolumna
        gbc.gridy = 0; // Pierwszy wiersz
        gbc.weightx = 1; // Waga w poziomie
        gbc.weighty = 1; // Waga w pionie (taka sama dla obu paneli)
        gbc.fill = GridBagConstraints.BOTH; // Wypełnienie zarówno poziome, jak i pionowe
        frame.add(sidePanel, gbc);

        // Główny panel rysujący
        gbc.gridx = 1; // Druga kolumna
        gbc.weightx = 3; // Waga w poziomie
        this.setBackground(Color.decode("#DC667C"));
        frame.add(this, gbc);

        // Konfiguracja tekstu na panelu
        textArea.setEditable(false);
        textArea.setBackground(Color.decode("#DC667C"));
        textArea.setFont(new Font("Arial", Font.BOLD, 20));
        textArea.setForeground(Color.WHITE);
        textArea.setText("Kliknij \"1\" dla narysowania kółka, \"2\" dla kwadratu");
        this.add(textArea);

        // Konfiguracja panelu bocznego
        sideTextField.setEditable(false);
        sideTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        sideTextField.setForeground(Color.BLACK);
        sideTextField.setBackground(Color.WHITE);
        sideTextField.setText("Stworzono obszar do rysowania");
        sidePanel.add(sideTextField);

        // Obsługa klawiatury do wyboru kształtów
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '1') {
                    currentShape = "circle";
                } else if (e.getKeyChar() == '2') {
                    currentShape = "rectangle";
                }
            }
        });

        // Obsługa myszy do rysowania
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (currentShape.equals("circle")) {
                    shapes.add(new Ellipse2D.Double(x - 25, y - 25, 50, 50));
                } else if (currentShape.equals("rectangle")) {
                    shapes.add(new Rectangle2D.Double(x - 25, y - 25, 50, 50));
                }
                repaint();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sideTextField.setText("Najechano na obszar do rysowania");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sideTextField.setText("Opuszczono obszar do rysowania");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                sideTextField.setText("Naciśnięto i zwolniono przycisk myszy");
            }

            public void mouseWheelMoved(MouseWheelEvent e) {
                if (selectedShape != null) {
                    int notches = e.getWheelRotation();
                    if (selectedShape instanceof Ellipse2D) {
                        Ellipse2D ellipse = (Ellipse2D) selectedShape;
                        double newWidth = ellipse.getWidth() - notches * 5;
                        double newHeight = ellipse.getHeight() - notches * 5;
                        ellipse.setFrame(ellipse.getX(), ellipse.getY(), Math.max(10, newWidth), Math.max(10, newHeight));
                    } else if (selectedShape instanceof Rectangle2D) {
                        Rectangle2D rect = (Rectangle2D) selectedShape;
                        double newWidth = rect.getWidth() - notches * 5;
                        double newHeight = rect.getHeight() - notches * 5;
                        rect.setFrame(rect.getX(), rect.getY(), Math.max(10, newWidth), Math.max(10, newHeight));
                    }
                    repaint();
                }
            }
        });


        // Ustawienie fokusa na panel rysujący
        SwingUtilities.invokeLater(this::requestFocusInWindow);

        // Wyświetlenie okna
        frame.setVisible(true);
    }

    // Przeciążenie metody paintComponent do rysowania kształtów
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Rysowanie wszystkich figur
        g2d.setColor(Color.BLUE);
        for (Shape shape : shapes) {
            g2d.fill(shape);
        }
    }

    // Punkt wejścia programu
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Zad5(args).showCanva();
            }
        });
    }
}
