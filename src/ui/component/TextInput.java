package ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

import processing.core.PApplet;
import util.math.Tools;

public class TextInput extends JTextArea{
  private static final long serialVersionUID=-6720199829611598351L;
  public static final Color BACKGROUND=new Color(0xffDDF4C4),FOREGROUND=new Color(0xffD53569);
  private volatile int screenX=0;
  private volatile int screenY=0;
  private volatile int startX=0;
  private volatile int startY=0;
  private volatile boolean move;
  public TextInput() {
    setBackground(BACKGROUND);
    setForeground(FOREGROUND);
    Dimension td=Toolkit.getDefaultToolkit().getScreenSize();
    setFont(new Font(Font.MONOSPACED,Font.PLAIN,td.height/48));
    int ts=td.height/128;
    setBorder(new MatteBorder(td.height/48,ts,ts,ts,Color.BLACK));
    setLineWrap(true);
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
        setLocation(startX+(e.getXOnScreen()-screenX),startY+(e.getYOnScreen()-screenY));
      }
      //      @Override
      //      public void mouseMoved(MouseEvent e) {
      //        if(getCursor().getType()==Cursor.MOVE_CURSOR) {
      //          if(e.getY()>20) setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
      //        }else {
      //          if(e.getY()<20) setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
      //        }
      //      }
    });
    addComponentListener(new ComponentAdapter() {
      //      @Override
      //      public void componentResized(ComponentEvent e) {}
      @Override
      public void componentMoved(ComponentEvent e) {
        if(getParent()==null) return;
        int tw=getParent().getWidth()-getWidth(),th=getParent().getHeight()-getHeight();
        if(!Tools.inBox(getX(),getY(),0,0,tw,th)) setLocation(
          PApplet.constrain(getX(),0,tw),
          PApplet.constrain(getY(),0,th));
      }
    });
    addFocusListener(new FocusListener() {
      @Override
      public void focusLost(FocusEvent e) {
        setBackground(BACKGROUND);
        setForeground(FOREGROUND);
      }
      @Override
      public void focusGained(FocusEvent e) {
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
      }
    });
  }
  public void frameResized(int w,int h) {
    setBounds(0,h/8*7,w,h/8);
  }
}
