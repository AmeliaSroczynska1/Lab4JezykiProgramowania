import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Zad2 extends JPanel {
    private JFrame frame;
    private Ellipse2D.Double kolo1;
    private Ellipse2D.Double kolo2;
    private Rectangle2D.Double kwadrat1;

    // Zmienna przechowująca, która figura jest aktualnie przeciągana
    private Object selectedFigure = null;
    private int offsetX, offsetY;

    // Konstruktor
    public Zad2() {
        // Tworzenie figur
        kolo1 = new Ellipse2D.Double(50, 50, 100, 100);
        kolo2 = new Ellipse2D.Double(200, 50, 100, 100);
        kwadrat1 = new Rectangle2D.Double(50, 200, 100, 100);
        frame = new JFrame("Rysowanie Figur");
    }

    // Nadpisanie metody paintComponent do rysowania figur
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ustawienie tła
        setBackground(Color.decode("#DC667C"));

        // Tworzymy obiekt Graphics2D do rysowania
        Graphics2D g2d = (Graphics2D) g;

        // Rysowanie dwóch kół (Ellipse2D)
        g2d.setColor(Color.BLUE); // Kolor kół
        g2d.fill(kolo1);
        g2d.fill(kolo2);

        // Rysowanie kwadratu (Rectangle2D)
        g2d.fill(kwadrat1);
    }

    // Metoda do utworzenia i pokazania okna
    public void show() {
        // Tworzenie okna JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Ustawienie rozmiaru okna
        frame.add(this); // Dodanie panelu (JPanel) do okna
        frame.setLocationRelativeTo(null); // Ustawienie okna na środku ekranu

        // Dodanie nasłuchiwania na kliknięcia myszy
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Sprawdzenie, czy kliknięcie było na którejkolwiek z figur
                if (kolo1.contains(e.getPoint())) {
                    selectedFigure = kolo1;
                    offsetX = e.getX() - (int) kolo1.getX();
                    offsetY = e.getY() - (int) kolo1.getY();
                } else if (kolo2.contains(e.getPoint())) {
                    selectedFigure = kolo2;
                    offsetX = e.getX() - (int) kolo2.getX();
                    offsetY = e.getY() - (int) kolo2.getY();
                } else if (kwadrat1.contains(e.getPoint())) {
                    selectedFigure = kwadrat1;
                    offsetX = e.getX() - (int) kwadrat1.getX();
                    offsetY = e.getY() - (int) kwadrat1.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Zwalniamy figurę po zakończeniu przeciągania
                selectedFigure = null;
            }
        });

        // Dodanie nasłuchiwania na przeciąganie myszy
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Przesuwanie figury, jeśli jakaś jest przeciągana
                if (selectedFigure != null) {
                    int x = e.getX() - offsetX;
                    int y = e.getY() - offsetY;

                    if (selectedFigure == kolo1) {
                        kolo1.setFrame(x, y, 100, 100);
                    } else if (selectedFigure == kolo2) {
                        kolo2.setFrame(x, y, 100, 100);
                    } else if (selectedFigure == kwadrat1) {
                        kwadrat1.setFrame(x, y, 100, 100);
                    }

                    repaint();  // Ponowne rysowanie panelu
                }
            }
        });

        frame.setVisible(true); // Pokazanie okna
    }

    // Punkt wejścia aplikacji
    public static void main(final String[] args) {
        // Użycie event-dispatching thread do tworzenia i pokazywania GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Zad2().show();
            }
        });
    }
}
