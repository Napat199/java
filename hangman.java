
package hangman;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Hangman extends JPanel{
    public int hangmanX = 220;
    public int gallowsX = 120;
    public int armAngle = 45;
    public int armLength = 65;
    public int legLength = 80;
    public Hangman() {
        setFocusable(true);
        requestFocusInWindow();
    }
    
    @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            // เซ็ตสีดำสำหรับวาดตะแลงแกง
            g.setColor(Color.BLACK);

            // วาดตะแลงแกง (ไม่ขยับ)
            // วาดแท่นหิน (ฐานของตะแลงแกง)
            g.drawArc(gallowsX - 40, 220, 80, 40, 0, 180);

            // วาดเสาตั้ง
            g.drawLine(gallowsX, 220, gallowsX, 20);

            // วาดคาน (แนวนอน)
            g.drawLine(gallowsX, 20, gallowsX + 100, 20);

            // วาดเชือกแขวน - ยาวลงมาถึงตำแหน่งหุ่น
            g.drawLine(220, 20,220, 40);

            // เซ็ตสีแดงสำหรับหุ่นแขวน
            g.setColor(Color.RED);

            // วาดหุ่นแขวน (ขยับได้)
            int radius = 20;

            // head
            g.drawOval(hangmanX - radius, 40, 2 * radius, 2 * radius);

            // handleft
            g.drawLine(hangmanX,60 + radius,
                      hangmanX - (int)(armLength * Math.cos(Math.toRadians(armAngle))),
                      hangmanX - (int)(armLength * Math.cos(Math.toRadians(armAngle))));

            // handright
            g.drawLine(hangmanX,60 + radius,
                      hangmanX + (int)(armLength * Math.cos(Math.toRadians(armAngle))),
                      hangmanX - (int)(armLength * Math.cos(Math.toRadians(armAngle))));

            // body
            g.drawLine(hangmanX, 40 + 2 * radius,
                       hangmanX, 40 + radius + 80);

            // legleft
             g.drawLine(hangmanX,40 + radius + 80,
                      hangmanX - (int)(legLength * Math.cos(Math.toRadians(70))),
                      hangmanX - (int)(legLength * Math.cos(Math.toRadians(70))));

            // legright
            g.drawLine(hangmanX,40 + radius + 80,
                      hangmanX + (int)(legLength * Math.cos(Math.toRadians(70))),
                      hangmanX - (int)(legLength * Math.cos(Math.toRadians(70))));

            // Advise
            g.setColor(Color.BLUE);
            g.drawString("Use LEFT and RIGHT arrow keys to move the hangman", 20, 350);
            g.drawString("Hangman X position: " + hangmanX, 20, 370);
            g.drawString("Gallows X position: " + gallowsX + " (fixed)", 20, 390);
        }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Graphics Example");
        Hangman panel = new Hangman();
        
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    panel.moveHangman(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    panel.moveHangman(1);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.add(panel);
        frame.setVisible(true);
    }
    public void moveHangman(int dx){
        hangmanX += dx;
        armAngle += dx*0.5;
        if(armAngle < 30) armAngle = 30;
        if (hangmanX < 215) {
            hangmanX = 215;
        } else if (hangmanX > 225) {
            hangmanX = 225;
        }
        repaint();
    }
}
