import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class FanWithBaseControl extends JPanel {
  private double angle = 0;    // มุมใบพัด (เรเดียน)
  private int bladeCount = 4;  // จำนวนใบพัด
  private int bladeLength = 80;
  private int bladeWidth = 25;

  private javax.swing.Timer timer;
  private JButton toggleButton;
  private boolean isRunning = true;

  public FanWithBaseControl() {
    setLayout(new BorderLayout());

    // Panel วาดพัดลม
    FanPanel fanPanel = new FanPanel();
    add(fanPanel, BorderLayout.CENTER);

    // ปุ่มเปิด/ปิดพัดลม
    toggleButton = new JButton("Stop");
    toggleButton.addActionListener(e -> {
      if (isRunning) {
        timer.stop();
        toggleButton.setText("Start");
      } else {
        timer.start();
        toggleButton.setText("Stop");
      }
      isRunning = !isRunning;
    });

    JPanel controlPanel = new JPanel();
    controlPanel.add(toggleButton);
    add(controlPanel, BorderLayout.SOUTH);

    // Timer สำหรับหมุนใบพัด
    timer = new javax.swing.Timer(50, e -> {
      fanPanel.updateAngle();
    });
    timer.start();
  }

  /** Panel วาดพัดลม */
  private class FanPanel extends JPanel {
    private double angle = 0;

    public void updateAngle() {
      angle += Math.toRadians(5);
      if (angle >= 2 * Math.PI) {
        angle -= 2 * Math.PI;
      }
      repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g.create();

      int width = getWidth();
      int height = getHeight();
      int centerX = width / 2;
      int centerY = height / 2;

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      drawBase(g2d, centerX, centerY + 70);

      g2d.setColor(new Color(150, 150, 150));
      g2d.fillOval(centerX - 20, centerY - 20, 40, 40);

      g2d.setColor(Color.BLUE);

      for (int i = 0; i < bladeCount; i++) {
        double bladeAngle = angle + i * (2 * Math.PI / bladeCount);

        Shape blade = createBladeShape(bladeLength, bladeWidth);

        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(bladeAngle);

        Shape rotatedBlade = transform.createTransformedShape(blade);

        g2d.fill(rotatedBlade);
      }

      g2d.setColor(Color.RED);
      g2d.fillOval(centerX - 8, centerY - 8, 16, 16);

      g2d.dispose();
    }
  }

  private Shape createBladeShape(int length, int width) {
    Path2D.Double blade = new Path2D.Double();
    blade.moveTo(0, 0);
    blade.curveTo(width * 0.5, length * 0.2, width * 0.5, length * 0.8, 0, length);
    blade.curveTo(-width * 0.5, length * 0.8, -width * 0.5, length * 0.2, 0, 0);
    blade.closePath();
    return blade;
  }

  private void drawBase(Graphics2D g2d, int x, int y) {
    Polygon base = new Polygon();
    base.addPoint(x - 50, y);
    base.addPoint(x + 50, y);
    base.addPoint(x, y - 60);

    g2d.setColor(new Color(100, 100, 100));
    g2d.fill(base);

    g2d.setStroke(new BasicStroke(5));
    g2d.drawLine(x - 60, y, x + 60, y);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(300, 300);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Fan with Base and Control");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new FanWithBaseControl());
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    });
  }
}
