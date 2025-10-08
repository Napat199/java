import java.awt.*;
import javax.swing.*;
import java.time.*;
import java.awt.event.*;

public class StillClock extends JPanel {
  private int hour;
  private int minute;
  private int second;
  private ZoneId zone;

  /** สร้างนาฬิกาพร้อมโซนเวลา */
  public StillClock(ZoneId zone) {
    this.zone = zone;
    setCurrentTime();
  }

  /** อัปเดตเวลาตามโซน */
  public void setCurrentTime() {
    ZonedDateTime now = ZonedDateTime.now(zone);
    this.hour = now.getHour();
    this.minute = now.getMinute();
    this.second = now.getSecond();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int clockRadius = (int)(Math.min(getWidth(), getHeight()) * 0.8 * 0.5);
    int xCenter = getWidth() / 2;
    int yCenter = getHeight() / 2;

    // วาดวงกลมนาฬิกา
    g.setColor(Color.BLACK);
    g.drawOval(xCenter - clockRadius, yCenter - clockRadius,
      2 * clockRadius, 2 * clockRadius);
    g.drawString("12", xCenter - 5, yCenter - clockRadius + 12);
    g.drawString("9", xCenter - clockRadius + 3, yCenter + 5);
    g.drawString("3", xCenter + clockRadius - 10, yCenter + 3);
    g.drawString("6", xCenter - 3, yCenter + clockRadius - 3);

    // เข็มวินาที
    int sLength = (int)(clockRadius * 0.8);
    int xSecond = (int)(xCenter + sLength *
      Math.sin(second * (2 * Math.PI / 60)));
    int ySecond = (int)(yCenter - sLength *
      Math.cos(second * (2 * Math.PI / 60)));
    g.setColor(Color.RED);
    g.drawLine(xCenter, yCenter, xSecond, ySecond);

    // เข็มนาที
    int mLength = (int)(clockRadius * 0.65);
    int xMinute = (int)(xCenter + mLength *
      Math.sin(minute * (2 * Math.PI / 60)));
    int yMinute = (int)(yCenter - mLength *
      Math.cos(minute * (2 * Math.PI / 60)));
    g.setColor(Color.BLUE);
    g.drawLine(xCenter, yCenter, xMinute, yMinute);

    // เข็มชั่วโมง
    int hLength = (int)(clockRadius * 0.5);
    double hourPosition = (hour % 12 + minute / 60.0) * (2 * Math.PI / 12);
    int xHour = (int)(xCenter + hLength * Math.sin(hourPosition));
    int yHour = (int)(yCenter - hLength * Math.cos(hourPosition));
    g.setColor(Color.GREEN);
    g.drawLine(xCenter, yCenter, xHour, yHour);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(200, 200);
  }

  /** โปรแกรมหลัก */
  public static void main(String[] args) {
    JFrame frame = new JFrame("World Clocks");

    // สร้าง Clock Panels
    ClockPanel thailandClock = new ClockPanel("Thailand", ZoneId.of("Asia/Bangkok"));
    ClockPanel japanClock = new ClockPanel("Japan", ZoneId.of("Asia/Tokyo"));
    ClockPanel londonClock = new ClockPanel("London", ZoneId.of("Europe/London"));

    // จัด layout 3 ช่องแนวนอน
    frame.setLayout(new GridLayout(1, 3));
    frame.add(thailandClock);
    frame.add(japanClock);
    frame.add(londonClock);

    // อัปเดตทุกวินาที
    javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        thailandClock.updateClock();
        japanClock.updateClock();
        londonClock.updateClock();
      }
    });
    timer.start();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

/** คลาสย่อย สำหรับรวม Clock + Label */
class ClockPanel extends JPanel {
  private StillClock clock;
  private JLabel label;

  public ClockPanel(String title, ZoneId zone) {
    setLayout(new BorderLayout());
    clock = new StillClock(zone);
    label = new JLabel(title, JLabel.CENTER);
    add(clock, BorderLayout.CENTER);
    add(label, BorderLayout.SOUTH);
  }

  public void updateClock() {
    clock.setCurrentTime();
    clock.repaint();
  }
}
