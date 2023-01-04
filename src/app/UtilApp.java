package app;

import java.awt.AWTException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.event.MouseEvent;
import util.math.Vec2i;

public class UtilApp extends PApplet{
  public SmoothCanvas canvas;
  public JFrame frame;
  public Vec2i mouse,pmouse,framePos;
  public boolean grabMouse;
  public int grabCooling;
  //  public boolean focusInside;
  //  public int focusCooling;
  public Robot robot;
  @Override
  public void settings() {
    size(640,640);
    mouse=new Vec2i();
    pmouse=new Vec2i();
    framePos=new Vec2i();
  }
  @Override
  public void setup() {
    //    new Thread() {
    //      @Override
    //      public void run() {
    //        try {
    //          sleep(100);
    //        }catch(InterruptedException e) {
    //          e.printStackTrace();
    //        }
    surface.setResizable(true);
    //      }
    //    }.start();
    try {
      robot=new Robot();
    }catch(AWTException e1) {
      e1.printStackTrace();
    }
    canvas=(SmoothCanvas)surface.getNative();
    canvas.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        frameResized(width,height);
      }
    });
    canvas.addFocusListener(new FocusListener() {
      @Override
      public void focusLost(FocusEvent e) {
        focusedLost();
      }
      @Override
      public void focusGained(FocusEvent e) {
        focusedGained();
      }
    });
    frame=(JFrame)canvas.getFrame();
    frame.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentMoved(ComponentEvent e) {
        Insets tempInsets=frame.getInsets();
        Point tempLocation=frame.getLocation();
        frameMoved(
          tempInsets.left+tempLocation.x,
          tempInsets.top+tempLocation.y);
      }
    });
    //    frame.addFocusListener(new FocusListener() {
    //      @Override
    //      public void focusLost(FocusEvent e) {
    //        focusInside=false;
    //      }
    //      @Override
    //      public void focusGained(FocusEvent e) {
    //        focusInside=true;
    //      }
    //    });
    Insets tempInsets=frame.getInsets();
    Point tempLocation=frame.getLocation();
    frameMoved(
      tempInsets.left+tempLocation.x,
      tempInsets.top+tempLocation.y);
    //    new MethodRegistrant(this);
  }
  //  @Override
  //  public void handleDraw() {
  //    synchronized(mouse) {
  //      super.handleDraw();
  //      pmouse.set(mouse);
  //    }
  //    System.out.println(true);
  //  }
  @Override
  public void draw() {
    pmouse.set(mouse);
  }
  //  @Override
  //  protected void handleMouseEvent(MouseEvent e) {
  //    //    if(e.getAction()==MouseEvent.MOVE&&focusCooling>0) return;
  //    //    if(focusCooling-->0) return;
  //    super.handleMouseEvent(e);
  //  }
  //  @Override
  //  public void mousePressed(MouseEvent event) {
  //    mousePressed();
  //  }
  @Override
  public void mouseMoved(MouseEvent e) {
    if(grabMouse) grabUpdate(e);
    mouseMoved();
  }
  private void grabUpdate(MouseEvent e) {
    int tx=width/2,ty=height/2;
    robot.mouseMove(tx+framePos.x,ty+framePos.y);
    if(e.getX()==tx&&e.getY()==ty||grabCooling-->0) return;
    mouse.add(e.getX()-tx,e.getY()-ty);
  }
  @Override
  public void mouseDragged(MouseEvent e) {
    if(grabMouse) grabUpdate(e);
    mouseDragged();
  }
  public void grabMouse() {
    grabCooling=(int)(dist(mouseX,mouseY,width/2,height/2)/40);
    robot.mouseMove(width/2+framePos.x,height/2+framePos.y);
    grabMouse=true;
    mouse.set(0,0);
    pmouse.set(mouse);
    noCursor();
  }
  public void ungrabMouse() {
    grabMouse=false;
    mouse.set(0,0);
    pmouse.set(mouse);
    cursor();
  }
  public void focusedGained() {
    //    if(frameCount>0&&focusInside) {
    //      focusCooling=3;
    //      System.out.println(true);
    //    }
    //    focusInside=true;
  }
  public void focusedLost() {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {
    framePos.set(x,y);
  }
  @Override
  public void textSize(float size) {
    super.textSize(size);
    textLeading(size);
  }
  //  @Override
  //  public void exitActual() {
  //    //    if(key!=ESC) 
  //    super.exitActual();
  //  }
  public static void main(String[] args) {
    new UtilApp() {
      @Override
      public void setup() {
        super.setup();
      }
      @Override
      public void draw() {
        synchronized(mouse) {
          //          try {
          //            Thread.sleep(30);
          //          }catch(InterruptedException e) {
          //            e.printStackTrace();
          //          }
          ellipse(mouse.x+width/2,mouse.y+height/2,4,4);
          line(mouse.x+width/2,mouse.y+height/2,pmouse.x+width/2,pmouse.y+height/2);
          //        pmouse.set(mouse);
          super.draw();
        }
      }
      @Override
      public void mousePressed() {
        if(mouseButton==LEFT) {
          if(grabMouse) ungrabMouse();
          else grabMouse();
        }else {
          background(204);
        }
      }
    }.runSketch();
  }
}
