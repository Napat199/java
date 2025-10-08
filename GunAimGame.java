import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class GunAimGame extends JPanel implements MouseMotionListener, MouseListener, ActionListener {

    private double gunAngle = 0;
    private int score = 0;
    private Point mousePos = new Point(400, 300);
    private Timer timer;
    private Random random = new Random();

    // ข้อมูลเป้า
    private int targetX, targetY, targetRadius = 25;

    // กระสุนทั้งหมด
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public GunAimGame() {
        setBackground(Color.white);
        addMouseMotionListener(this);
        addMouseListener(this);

        spawnTarget();

        timer = new Timer(16, this); // 60 FPS
        timer.start();
    }

    private void spawnTarget() {
        targetX = random.nextInt(800 - 2 * targetRadius) + targetRadius;
        targetY = random.nextInt(400) + targetRadius;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // วาดเป้า
        g2.setColor(Color.RED);
        g2.fillOval(targetX - targetRadius, targetY - targetRadius, targetRadius * 2, targetRadius * 2);

        // คำนวณตำแหน่งปืน
        int gunBaseX = width / 2;
        int gunBaseY = height - 60;
        int gunLength = 80;
        int gunWidth = 14;

        double dx = mousePos.x - gunBaseX;
        double dy = mousePos.y - gunBaseY;
        gunAngle = Math.atan2(dy, dx);

        // วาดกระบอกปืน
        g2.setColor(Color.black);
        g2.translate(gunBaseX, gunBaseY);
        g2.rotate(gunAngle);
        g2.fillRect(0, -gunWidth / 2, gunLength, gunWidth);
        g2.rotate(-gunAngle);
        g2.translate(-gunBaseX, -gunBaseY);

        // วาดฐานปืน
        g2.setColor(Color.black);
        g2.fillRect(gunBaseX - 20, gunBaseY, 40, 30);

        // วาดกระสุน
        g2.setColor(Color.YELLOW);
        for (Bullet b : bullets) {
            g2.fillOval((int) b.x - 5, (int) b.y - 5, 10, 10);
        }

        // วาดคะแนน
        g2.setColor(Color.black);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("Score: " + score, 10, 30);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int gunBaseX = getWidth() / 2;
        int gunBaseY = getHeight() - 60;
        double dx = mousePos.x - gunBaseX;
        double dy = mousePos.y - gunBaseY;
        double angle = Math.atan2(dy, dx);

        // จุดเริ่มต้นกระสุน = ปลายปืน
        double startX = gunBaseX + Math.cos(angle) * 80;
        double startY = gunBaseY + Math.sin(angle) * 80;

        bullets.add(new Bullet(startX, startY, angle));
    }

    // Bullet class
    class Bullet {
        double x, y, angle, speed = 10;

        Bullet(double x, double y, double angle) {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }

        void update() {
            x += Math.cos(angle) * speed;
            y += Math.sin(angle) * speed;
        }

        boolean isOffScreen(int width, int height) {
            return x < 0 || x > width || y < 0 || y > height;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // อัปเดตกระสุน
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update();

            // ตรวจว่ากระสุนโดนเป้าไหม
            double dist = Math.hypot(b.x - targetX, b.y - targetY);
            if (dist < targetRadius) {
                score++;
                spawnTarget();
                it.remove();
                continue;
            }

            // ถ้าออกนอกหน้าจอ ให้ลบออก
            if (b.isOffScreen(getWidth(), getHeight())) {
                it.remove();
            }
        }

        repaint();
    }

    // ไม่ใช้บาง event แต่ต้อง override ไว้
    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gun Aim Game");
        GunAimGame game = new GunAimGame();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
