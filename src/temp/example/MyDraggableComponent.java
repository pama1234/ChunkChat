package temp.example;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

public class MyDraggableComponent extends JTextArea{
  private static final long serialVersionUID=-6720199829611598351L;
  private volatile int screenX=0;
  private volatile int screenY=0;
  private volatile int startX=0;
  private volatile int startY=0;
  private volatile boolean move;
  public MyDraggableComponent() {
    setBackground(Color.WHITE);
    setFont(new Font(Font.MONOSPACED,Font.PLAIN,24));
    setBounds(0,0,100,100);
    //    setBorder(new LineBorder(Color.GREEN,3));
    MatteBorder temp=new MatteBorder(20,5,5,5,Color.red);
    setBorder(temp);
    //        temp.
    //    setOpaque(false);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        move=e.getY()<20;
        if(!move) return;
        requestFocus();
        screenX=e.getXOnScreen();
        screenY=e.getYOnScreen();
        startX=getX();
        startY=getY();
      }
    });
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        if(!move) return;
        int deltaX=e.getXOnScreen()-screenX;
        int deltaY=e.getYOnScreen()-screenY;
        setLocation(startX+deltaX,startY+deltaY);
      }
      @Override
      public void mouseMoved(MouseEvent e) {
        //        setCursor(Cursor.getDefaultCursor());
        if(e.getY()<20) setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        else setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
      }
    });
  }
  public static void main(String[] args) {
    JFrame f=new JFrame("Swing Hello World");
    // by doing this, we prevent Swing from resizing
    // our nice component
    f.setLayout(null);
    MyDraggableComponent mc=new MyDraggableComponent();
    f.add(mc);
    f.setSize(500,500);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setVisible(true);
  }
}
