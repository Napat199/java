import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ต้องใช้ชื่อคลาสหลักเป็นชื่อเดียวกับไฟล์ (MainClockApp.java)
public class MainClockApp {
    
    // คลาสหลักที่มี main method สำหรับรันโปรแกรม
    public static void main(String[] args) {
        // 1. สร้าง Clock Panel
        StillClock clock = new StillClock();

        // 2. สร้าง Frame และตั้งค่า
        JFrame frame = new JFrame("Live Ticking Clock");
        frame.add(clock);
        frame.setSize(250, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        // 3. สร้าง Timer สำหรับการทำ Animation (การเดินของเข็มนาฬิกา)
        // Timer จะทำงานทุก 1000 มิลลิวินาที (1 วินาที)
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // อัปเดตเวลาปัจจุบันใน StillClock
                clock.setCurrentTime();
                
                // สั่งให้ JPanel วาดใหม่ (repaint)
                clock.repaint();
            }
        });

        // 4. เริ่ม Timer
        timer.start();
    }

    // คลาส StillClock ย้ายมาเป็น Static Inner Class
    public static class StillClock extends JPanel {
        private int hour;
        private int minute;
        private int second;

        /** Construct a default clock with the current time */
        public StillClock() {
            setCurrentTime();
        }

        // Getters และ Setters ถูกละไว้เพื่อความกระชับ

        /** Set time to current time */
        public void setCurrentTime() {
            // Construct a calendar for the current date and time
            Calendar calendar = new GregorianCalendar();

            // Set current hour, minute and second
            this.hour = calendar.get(Calendar.HOUR_OF_DAY);
            this.minute = calendar.get(Calendar.MINUTE);
            this.second = calendar.get(Calendar.SECOND);
        }

        @Override /** Draw the clock */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Initialize clock parameters
            int clockRadius = (int)(Math.min(getWidth(), getHeight()) * 0.8 * 0.5);
            int xCenter = getWidth() / 2;
            int yCenter = getHeight() / 2;

            // Draw circle and numbers
            g.setColor(Color.black);
            g.drawOval(xCenter - clockRadius, yCenter - clockRadius, 2 * clockRadius, 2 * clockRadius);
            g.drawString("12", xCenter - 5, yCenter - clockRadius + 12);
            g.drawString("9", xCenter - clockRadius + 3, yCenter + 5);
            g.drawString("3", xCenter + clockRadius - 10, yCenter + 3);
            g.drawString("6", xCenter - 3, yCenter + clockRadius - 3);

            // -----------------------------------------------------------
            // Draw second hand
            // -----------------------------------------------------------
            int sLength = (int)(clockRadius * 0.8);
            // Angle: second * (2*PI / 60)
            double sAngle = second * (2 * Math.PI / 60);
            int xSecond = (int)(xCenter + sLength * Math.sin(sAngle));
            int ySecond = (int)(yCenter - sLength * Math.cos(sAngle));
            g.setColor(Color.red);
            g.drawLine(xCenter, yCenter, xSecond, ySecond);

            // -----------------------------------------------------------
            // Draw minute hand (ปรับให้เดินแบบต่อเนื่อง)
            // -----------------------------------------------------------
            int mLength = (int)(clockRadius * 0.65);
            // Angle: (minute + second/60.0) * (2*PI / 60)
            double mAngle = (minute + second / 60.0) * (2 * Math.PI / 60); 
            int xMinute = (int)(xCenter + mLength * Math.sin(mAngle));
            int yMinute = (int)(yCenter - mLength * Math.cos(mAngle));
            g.setColor(Color.blue);
            g.drawLine(xCenter, yCenter, xMinute, yMinute);

            // -----------------------------------------------------------
            // Draw hour hand (ปรับให้เดินแบบต่อเนื่อง)
            // -----------------------------------------------------------
            int hLength = (int)(clockRadius * 0.5);
            // Angle: (hour % 12 + minute/60.0) * (2*PI / 12)
            double hAngle = (hour % 12 + minute / 60.0) * (2 * Math.PI / 12);
            int xHour = (int)(xCenter + hLength * Math.sin(hAngle));
            int yHour = (int)(yCenter - hLength * Math.cos(hAngle));
            g.setColor(Color.green);
            g.drawLine(xCenter, yCenter, xHour, yHour);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    }
}
