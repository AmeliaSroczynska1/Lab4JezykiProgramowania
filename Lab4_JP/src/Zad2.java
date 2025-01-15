// Stwórz prosty interfejs z obszarem rysowania (np. JPanel). Narysuj kilka prostych figur (np.
// kwadrat, koło). Dodaj obsługę zdarzeń myszy (MouseListener), tak aby po kliknięciu myszą na figurę
// można było ją przesunąć.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Zad2 extends JPanel {
    private final JPanel panel;
    private final JFrame frame;
    private final JTextArea textArea;
    private String currentShape = "circle"; // Domyślny kształt
    private final List<Shape> shapes = new ArrayList<>(); // Lista przechowująca rysowane kształty
    private Shape selectedShape = null; // Figura aktualnie przeciągana
    private int offsetX, offsetY; // Różnica pozycji myszy względem figury

    // Konstruktor, ustawienie okna i panelu
    Zad2(String[] args) {
        // Ustawienia głównego okna
        frame = new JFrame("Kanwa do rysowania");
        panel = new JPanel();
        textArea = new JTextArea();
    }

    public void showCanva() {
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
        frame.setVisible(true);

        panel.setBackground(Color.decode("#DC667C")); // Kolor tła

        textArea.setEditable(false);
        textArea.setBackground(Color.decode("#DC667C"));
        textArea.setFont(new Font("Arial", Font.BOLD, 24));     // Ustawienie czcionki
        textArea.setForeground(Color.WHITE); // Ustawienie koloru tekstu
        panel.add(textArea);

        // Obsługa klawiatury do wyboru kształtów
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '1') {
                    currentShape = "circle";
                } else if (e.getKeyChar() == '2') {
                    currentShape = "rectangle";
                }
            }
        });

        // Obsługa myszy do rysowania i przesuwania figur
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();

                // Sprawdzenie, czy kliknięto na istniejącą figurę
                for (Shape shape : shapes) {
                    if (shape.contains(point)) {
                        selectedShape = shape;
                        offsetX = (int) (point.getX() - shape.getBounds2D().getX());
                        offsetY = (int) (point.getY() - shape.getBounds2D().getY());
                        return;
                    }
                }

                // Jeśli nie kliknięto na żadną figurę, rysujemy nową
                int x = e.getX();
                int y = e.getY();

                if (currentShape.equals("circle")) {
                    shapes.add(new Ellipse2D.Double(x - 25, y - 25, 50, 50));
                } else if (currentShape.equals("rectangle")) {
                    shapes.add(new Rectangle2D.Double(x - 25, y - 25, 50, 50));
                }
                repaint(); // Zlecenie ponownego rysowania
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedShape = null; // Po zwolnieniu myszy, figura przestaje być przesuwana
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    // Aktualizacja pozycji figury na podstawie ruchu myszy
                    int newX = e.getX() - offsetX;
                    int newY = e.getY() - offsetY;

                    if (selectedShape instanceof Ellipse2D) {
                        Ellipse2D ellipse = (Ellipse2D) selectedShape;
                        ellipse.setFrame(newX, newY, ellipse.getWidth(), ellipse.getHeight());
                    } else if (selectedShape instanceof Rectangle2D) {
                        Rectangle2D rectangle = (Rectangle2D) selectedShape;
                        rectangle.setFrame(newX, newY, rectangle.getWidth(), rectangle.getHeight());
                    }

                    repaint(); // Odświeżenie rysunku
                }
            }
        });

        panel.setFocusable(true);               // Fokus klawiatury
        panel.requestFocusInWindow();           // Żąda przeniesienia fokusa klawiatury na komponent w ramach jego okna.

        frame.add(panel);
    }

    // Przeciążenie metody paintComponent do rysowania kształtów
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Ustawienie koloru tła na różowy przed rysowaniem kształtów
        g2d.setColor(Color.decode("#DC667C"));
        textArea.setText("Kliknij \"1\" dla narysowania kółka, \"2\" dla kwadratu. \nMożesz do woli przesuwać figury po ich narysowaniu.");
        g2d.fillRect(0, 0, getWidth(), getHeight()); // Rysowanie tła na całym panelu

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
                new Zad2(args).showCanva();
            }
        });
    }
}
