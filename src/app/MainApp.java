package app;

import processing.core.PFont;
import processing.event.MouseEvent;
import util.state.GameState;
import util.state.StateEngine;

public class MainApp extends UtilApp{
  StateEngine state;
  @Override
  public void settings() {
    super.settings();
    noSmooth();
    //    smooth(8);
    state=new StateEngine(this,GameState.values());
  }
  @Override
  public void setup() {
    super.setup();
    int h=24;
    PFont font=createFont("Monospaced",h);
    if(font!=null) textFont(font);
    textSize(h);
    state.init();
    state.start();
  }
  @Override
  public void draw() {
    //    synchronized(mouse) {
    //    System.out.println(focused);
    state.update();
    //    if(focused) 
    state.display();
    super.draw();
    //    }
  }
  @Override
  public void mousePressed() {
    state.mousePressed();
  }
  @Override
  public void mouseReleased() {
    state.mouseReleased();
  }
  @Override
  public void mouseClicked() {
    state.mouseClicked();
  }
  @Override
  public void mouseMoved() {
    state.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    state.mouseDragged();
  }
  @Override
  public void mouseWheel(MouseEvent e) {
    state.mouseWheel(e.getCount());
  }
  @Override
  public void keyPressed() {
    state.keyPressed();
  }
  @Override
  public void keyReleased() {
    state.keyReleased();
  }
  @Override
  public void keyTyped() {
    state.keyTyped();
  }
  @Override
  public synchronized void frameResized(int w,int h) {
    state.frameResized(w,h);
  }
  @Override
  public synchronized void frameMoved(int x,int y) {
    super.frameMoved(x,y);
    state.frameMoved(x,y);
  }
  @Override
  public void exitActual() {
    state.dispose();
    super.exitActual();
  }
  public static void main(String[] args) {
    System.setProperty("sun.java2d.uiScale","1");
    new MainApp().runSketch();
  }
}
