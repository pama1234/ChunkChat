package ui.page.impl;

import app.UtilApp;
import processing.core.PConstants;
import ui.page.UtilAppPage;
import util.math.Tools;
import util.math.Vec2f;
import util.state.GameState;
import util.state.StateEngine;

public class SettingsPage extends UtilAppPage{
  Vec2f bPos;
  float wordsWidth;
  public SettingsPage(UtilApp parent,StateEngine state) {
    super(parent,state);
  }
  @Override
  public void init() {
    bPos=new Vec2f();
  }
  @Override
  public void update() {}
  @Override
  public void display() {
    p.background(63);
    if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y,wordsWidth,p.g.textSize)) p.fill(95);
    else p.noFill();
    p.rect(bPos.x,bPos.y,wordsWidth,p.g.textSize);
    p.fill(255);
    p.text("返回",p.width/32f,p.height/32f);
  }
  @Override
  public void dispose() {}
  @Override
  public void start() {
    super.start();
    p.stroke(255);
    p.textAlign(PConstants.LEFT,PConstants.TOP);
  }
  @Override
  public void end() {
    super.end();
  }
  @Override
  public void mousePressed() {}
  @Override
  public void mouseReleased() {
    if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y,wordsWidth,p.g.textSize)) state.change(GameState.home);
  }
  @Override
  public void mouseClicked() {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(int c) {}
  @Override
  public void keyPressed() {}
  @Override
  public void keyReleased() {}
  @Override
  public void keyTyped() {}
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void frameResized(int w,int h) {
    super.frameResized(w,h);
    //---
    wordsWidth=p.textWidth("【】");
    bPos.set(w/32f,h/32f+p.textDescent()/2);
    //    System.out.println(p.textDescent());
  }
}
