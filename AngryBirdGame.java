import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AngryBirdGame extends JFrame {
    // ============= AngryBird Class =============
    static class AngryBird {
        private double y;      // ตำแหน่ง y เริ่มต้น
        private double speed;  // ความเร็วเริ่มต้น
        private double angle;  // มุมยิง (degree)

        public AngryBird(double y, double speed, double angle) {
            this.y = y;
            this.speed = speed;
            this.angle = angle;
        }

        // คืนค่าเส้นทางการเคลื่อนที่ของนก (x,y) หลายๆ จุด
        public double[][] getTrajectory(int steps, double g) {
            double[][] traj = new double[steps][2];
            double rad = Math.toRadians(angle);
            double vx = speed * Math.cos(rad);
            double vy = speed * Math.sin(rad);

            for (int i = 0; i < steps; i++) {
                double t = i * 0.1;
                double x = vx * t;
                double yy = y + vy * t - 0.5 * g * t * t;
                if (yy < 0) break;
                traj[i][0] = x;
                traj[i][1] = yy;
            }
            return traj;
        }
    }

    // ============= AngryBirdGame Class =============
    private JTextField txtY, txtSpeed, txtAngle;
    private JLabel lblScore;
    private GamePanel panel;
    private int score = 0;

    public AngryBirdGame() {
        setTitle("Angry Birds Java GUI");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Scene + Score)
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel lblScene = new JLabel("SCENE 1: At Tokyo", JLabel.LEFT);
        lblScene.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(lblScene, BorderLayout.WEST);

        lblScore = new JLabel("SCORE: 0", JLabel.RIGHT);
        lblScore.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(lblScore, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Game Panel (Canvas)
        panel = new GamePanel();
        add(panel, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4,2));
        inputPanel.add(new JLabel("Bird Position (y-axis):"));
        txtY = new JTextField("0");
        inputPanel.add(txtY);

        inputPanel.add(new JLabel("Shooting speed (m/s):"));
        txtSpeed = new JTextField("50");
        inputPanel.add(txtSpeed);

        inputPanel.add(new JLabel("Angle (degree):"));
        txtAngle = new JTextField("45");
        inputPanel.add(txtAngle);

        JButton btnShoot = new JButton("OK");
        btnShoot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });
        inputPanel.add(btnShoot);

        add(inputPanel, BorderLayout.SOUTH);
    }

    private void shoot() {
    try {
        double y = Double.parseDouble(txtY.getText());
        double speed = Double.parseDouble(txtSpeed.getText());
        double angle = Double.parseDouble(txtAngle.getText());

        AngryBird bird = new AngryBird(y, speed, angle);
        double[][] traj = bird.getTrajectory(200, 9.8);

        panel.animateBird(traj, () -> {
            score++;
            lblScore.setText("SCORE: " + score);
            JOptionPane.showMessageDialog(this, "🎯 HIT! คุณยิงโดนหมูแล้ว!");
            panel.randomizePig(); // ✅ ย้ายหมูไปที่ใหม่
        });

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Incorrect");
    }
}


    // =======================
    // Game Panel (วาดกราฟิก)
    // =======================
    class GamePanel extends JPanel {
        private int birdX = 50, birdY = 300;
        private int pigX = 500, pigY = 250;
        private int birdSize = 50, pigSize = 60;
        private Image background;
        private Image birdImg;
        private Image pigImg;
        public void randomizePig() {
            java.util.Random rand = new java.util.Random();
            // สุ่มตำแหน่ง X,Y โดยให้หมูไม่เกินขอบจอ
            pigX = 200 + rand.nextInt(getWidth() - 250); 
            pigY = 100 + rand.nextInt(getHeight() - 200);
            repaint();
        }
        public GamePanel() {
            try {
                // โหลดภาพจาก URL
                URL url = new URL("https://itravelforthestars.com/wp-content/uploads/2020/07/tokyo_24.jpg");
                background = ImageIO.read(url);

                url = new URL("https://www.pavconhecimento.pt/includes/angrybirds/angry/img/red2.png");
                birdImg = ImageIO.read(url);

                url = new URL("https://wallpapers.com/images/hd/angry-birds-green-pig-character-vfw2ohmc57fyj3so.png");
                pigImg = ImageIO.read(url);

            } catch (Exception e) {
                System.out.println("โหลดภาพไม่สำเร็จ: " + e.getMessage());
                background = null;
            }
        }

        public void animateBird(double[][] traj, Runnable onHit) {
    new Thread(() -> {
        for (double[] point : traj) {
            if (point[1] == 0 && point[0] == 0) break;
            birdX = 50 + (int) point[0];
            birdY = 350 - (int) point[1];
            repaint();

            // ✅ เช็คการชนทุกเฟรม
            if (checkCollision()) {
                if (onHit != null) onHit.run();
                break; // หยุดทันทีที่ชน
            }

            try { Thread.sleep(30); } catch (InterruptedException ex) {}
        }
    }).start();
}

        public boolean checkCollision() {
            Rectangle birdRect = new Rectangle(birdX, birdY, birdSize, birdSize);
            Rectangle pigRect = new Rectangle(pigX, pigY, pigSize, pigSize);
            return birdRect.intersects(pigRect);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // ถ้ามีรูป background ให้แสดงเต็มจอ
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            } else {
                setBackground(Color.cyan); // fallback
            }

            // วาดหมู
            if (pigImg != null) {
                g.drawImage(pigImg, pigX, pigY, pigSize, pigSize, this);
            }

            // วาดนก
            if (birdImg != null) {
                g.drawImage(birdImg, birdX, birdY, birdSize, birdSize, this);
            }
        }
    }

    // ============= Main Method =============
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AngryBirdGame().setVisible(true);
        });
    }
}
