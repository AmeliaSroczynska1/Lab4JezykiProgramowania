import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Zad4 extends JPanel {
    private final JFrame frame;
    private final List<Shape> shapes = new ArrayList<>(); // Lista przechowująca rysowane kształty
    private Shape selectedShape = null; // Figura aktualnie przeciągana
    private boolean moveRight = false; // Flaga wskazująca kierunek przesuwania
    private Timer moveTimer;

    public Zad4() {
        // Ustawienia głównego okna
        frame = new JFrame("Kanwa do rysowania");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Ustawienie okna na środku ekranu

        // Dodanie kształtów do listy
        shapes.add(new Ellipse2D.Double(100, 100, 50, 50));
        shapes.add(new Rectangle2D.Double(200, 200, 50, 50));

        // Obsługa myszy do przesuwania figur
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();

                // Sprawdzenie, czy kliknięto na istniejącą figurę
                for (Shape shape : shapes) {
                    if (shape.contains(point)) {
                        selectedShape = shape;

                        // Ustawienie kierunku przesuwania w zależności od miejsca kliknięcia
                        moveRight = point.getX() >= shape.getBounds2D().getCenterX();
                        startMovingShape();
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                stopMovingShape(); // Zatrzymanie przesuwania po zwolnieniu myszy
                selectedShape = null;
            }
        });

        frame.add(this);
        frame.setVisible(true);
    }

    private void startMovingShape() {
        if (moveTimer != null && moveTimer.isRunning()) {
            moveTimer.stop();
        }

        moveTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedShape != null) {
                    Rectangle bounds = selectedShape.getBounds();

                    // Przesuwanie figury w prawo lub w dół
                    if (moveRight) {
                        bounds.x += 5;
                    } else {
                        bounds.y += 5;
                    }

                    if (selectedShape instanceof Ellipse2D) {
                        Ellipse2D ellipse = (Ellipse2D) selectedShape;
                        ellipse.setFrame(bounds);
                    } else if (selectedShape instanceof Rectangle2D) {
                        Rectangle2D rectangle = (Rectangle2D) selectedShape;
                        rectangle.setFrame(bounds);
                    }

                    repaint();
                }
            }
        });

        moveTimer.start();
    }

    private void stopMovingShape() {
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }

    // Przeciążenie metody paintComponent do rysowania kształtów
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Ustawienie koloru tła
        g2d.setColor(Color.decode("#DC667C"));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Rysowanie wszystkich figur
        g2d.setColor(Color.BLUE);
        for (Shape shape : shapes) {
            g2d.fill(shape);
        }
    }

    // Punkt wejścia programu
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Zad4::new);
    }
}