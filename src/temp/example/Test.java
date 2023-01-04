package temp.example;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

public class Test{
  private static int fixX=500;
  private static int fixY=500;
  private static Robot robot;
  private static JFrame frame;
  public static void main(String[] args) {
    try {
      robot=new Robot();
    }catch(AWTException e) {
      e.printStackTrace();
      return;
    }
    frame=new JFrame("test frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        move(e);
      }
      @Override
      public void mouseMoved(MouseEvent e) {
        move(e);
      }
    });
    frame.addComponentListener(new ComponentListener() {
      @Override
      public void componentShown(ComponentEvent e) {}
      @Override
      public void componentResized(ComponentEvent e) {
        fixX=frame.getWidth()/2;
        fixY=frame.getHeight()/2;
      }
      @Override
      public void componentMoved(ComponentEvent e) {}
      @Override
      public void componentHidden(ComponentEvent e) {}
    });
    frame.setSize(200,200);
    frame.setVisible(true);
  }
  private static void move(MouseEvent e) {
    if(e.getX()==fixX&&e.getY()==fixY) return;
    robot.mouseMove(fixX+frame.getX(),fixY+frame.getY());
    int moveX=e.getX()-fixX;
    int moveY=e.getY()-fixY;
    System.out.println("moved: "+moveX+" "+moveY);
  }
}