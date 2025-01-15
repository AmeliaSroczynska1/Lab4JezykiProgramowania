import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Zad6 extends JPanel {
    private final JFrame frame;          // Główne okno aplikacji
    private final JTextArea textArea;
    private String currentShape = "circle"; // Domyślny kształt
    private final List<ColoredShape> shapes = new ArrayList<>(); // Lista przechowująca rysowane kształty z kolorami
    private ColoredShape selectedShape = null; // Zaznaczona figura
    private boolean isCtrlPressed = false; // Flaga do wykrycia stanu klawisza Ctrl

    // Konstruktor
    public Zad6(String[] args) {
        frame = new JFrame("Kanwa do rysowania");
        textArea = new JTextArea();

        this.setFocusable(true);
    }

    // Metoda do tworzenia i wyświetlania GUI
    public void showCanva() {
        // Ustawienia głównego okna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.add(this);

        // Konfiguracja tekstu na panelu
        textArea.setEditable(false);
        textArea.setBackground(Color.decode("#DC667C"));
        textArea.setFont(new Font("Arial", Font.BOLD, 20));
        textArea.setForeground(Color.WHITE);
        textArea.setText("Kliknij \"1\" dla narysowania kółka, \"2\" dla kwadratu. \nSpacja dla zmiany koloru figury po najechaniu na nią. \nCtrl dla białego obramowania.");
        this.add(textArea);

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

                Shape shape;
                if (currentShape.equals("circle")) {
                    shape = new Ellipse2D.Double(x - 25, y - 25, 50, 50);
                } else {
                    shape = new Rectangle2D.Double(x - 25, y - 25, 50, 50);
                }
                shapes.add(new ColoredShape(shape, Color.BLUE));
                repaint();
            }
        });

        // Obsługa myszy do detekcji najazdu na figurę
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                selectedShape = null;
                for (ColoredShape shape : shapes) {
                    if (shape.getShape().contains(e.getPoint())) {
                        selectedShape = shape;
                        break;
                    }
                }
                repaint();
            }
        });

        // Obsługa klawiatury do zmiany koloru zaznaczonej figury
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && selectedShape != null) {
                    selectedShape.setColor(Color.GREEN);
                    repaint();
                }
                if(e.isControlDown() && selectedShape != null){
                    isCtrlPressed = true;
                    repaint(); // Odśwież rysunek, aby pokazać obramowanie
                }
            }

            @Override
            public void keyReleased(KeyEvent e){
                if(selectedShape!=null){
                selectedShape.setColor(Color.BLUE);
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    isCtrlPressed = false;
                }
                repaint();
            }}
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

        // Ustawienie koloru tła
        g2d.setColor(Color.decode("#DC667C"));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Rysowanie wszystkich figur
        for (ColoredShape coloredShape : shapes) {
            g2d.setColor(coloredShape.getColor());
            g2d.fill(coloredShape.getShape());
        }

        // Podświetlenie zaznaczonej figury
        if (selectedShape != null) {
            g2d.setColor(Color.RED);
            g2d.draw(selectedShape.getShape());
        }

        // Rysowanie obramowania zaznaczonej figury, jeśli Ctrl jest wciśnięty
        if (isCtrlPressed && selectedShape != null) {
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3)); // Grubość obramowania
            g2d.draw(selectedShape.getShape());
        }
    }

    // Punkt wejścia programu
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Zad6(args).showCanva();
            }
        });
    }

    // Klasa pomocnicza do przechowywania figur z kolorami
    private static class ColoredShape {
        private final Shape shape;
        private Color color;

        public ColoredShape(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public Shape getShape() {
            return shape;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}
