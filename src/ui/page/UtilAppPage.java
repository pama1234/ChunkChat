package ui.page;

import java.util.LinkedList;

import app.UtilApp;
import processing.core.PApplet;
import util.AppEventListener;
import util.state.StateEngine;

public abstract class UtilAppPage extends AppPage{
  public LinkedList<AppEventListener> listenerList;
  public float textSizeUnit,rectUnit,textDescent;
  public UtilAppPage(UtilApp parent,StateEngine state) {
    super(parent,state);
    listenerList=new LinkedList<AppEventListener>();
  }
  @Override
  public void start() {
    p.pushStyle();
    frameResized(p.width,p.height);
  }
  @Override
  public void end() {
    p.popStyle();
  }
  @Override
  public void mousePressed() {
    for(AppEventListener l:listenerList) l.mousePressed();
  }
  @Override
  public void mouseReleased() {
    for(AppEventListener l:listenerList) l.mouseReleased();
  }
  @Override
  public void mouseClicked() {
    for(AppEventListener l:listenerList) l.mouseClicked();
  }
  @Override
  public void mouseMoved() {
    for(AppEventListener l:listenerList) l.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    for(AppEventListener l:listenerList) l.mouseDragged();
  }
  @Override
  public void mouseWheel(int c) {
    for(AppEventListener l:listenerList) l.mouseWheel(c);
  }
  @Override
  public void keyPressed() {
    for(AppEventListener l:listenerList) l.keyPressed();
  }
  @Override
  public void keyReleased() {
    for(AppEventListener l:listenerList) l.keyReleased();
  }
  @Override
  public void keyTyped() {
    for(AppEventListener l:listenerList) l.keyTyped();
  }
  @Override
  public void frameMoved(int x,int y) {
    for(AppEventListener l:listenerList) l.frameMoved(x,y);
  }
  @Override
  public void frameResized(int w,int h) {
    int ts=PApplet.min(w,h);
    textSizeUnit=ts/26.6f;
    rectUnit=ts/20f;
    //---
    p.textSize(textSizeUnit*2);
    textDescent=p.textDescent();
    p.strokeWeight(ts/426.6f);
    //---
    for(AppEventListener l:listenerList) l.frameResized(w,h);
  }
}
