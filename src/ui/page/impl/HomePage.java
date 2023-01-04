package ui.page.impl;

import app.UtilApp;
import processing.core.PConstants;
import ui.page.UtilAppPage;
import util.math.Tools;
import util.math.Vec2f;
import util.state.GameState;
import util.state.StateEngine;

public class HomePage extends UtilAppPage{
  String words;
  Vec2f bPos;
  float wordsWidth;
  public HomePage(UtilApp parent,StateEngine state) {
    super(parent,state);
  }
  @Override
  public void init() {
    words="\n开始\n\n设置\n\n退出";
    bPos=new Vec2f();
  }
  @Override
  public void update() {}
  @Override
  public void display() {
    p.background(255);
    if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y,wordsWidth,p.g.textSize)) p.fill(204);
    else p.noFill();
    p.rect(bPos.x,bPos.y,wordsWidth,p.g.textSize);
    if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y+p.g.textSize*2,wordsWidth,p.g.textSize)) p.fill(204);
    else p.noFill();
    p.rect(bPos.x,bPos.y+p.g.textSize*2,wordsWidth,p.g.textSize);
    if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y+p.g.textSize*4,wordsWidth,p.g.textSize)) p.fill(204);
    else p.noFill();
    p.rect(bPos.x,bPos.y+p.g.textSize*4,wordsWidth,p.g.textSize);
    p.fill(0);
    p.textSize(textSizeUnit);
    p.text("v1.0.1\n作者：pama_1234",p.width/2,p.height/8*7);
    p.textSize(textSizeUnit*4);
    p.text("聊矿与采天",p.width/2,p.height/8);
    p.textSize(textSizeUnit*3);
    p.text("MineChat",p.width/2,p.height/4);
    p.textSize(textSizeUnit*2);
    p.text(words,p.width/2,p.height/2);
  }
  @Override
  public void dispose() {}
  @Override
  public void start() {
    super.start();
    p.fill(0);
    p.textAlign(PConstants.CENTER,PConstants.CENTER);
  }
  @Override
  public void end() {
    super.end();
  }
  @Override
  public void mousePressed() {}
  @Override
  public void mouseReleased() {
    if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y,wordsWidth,p.g.textSize)) state.change(GameState.game);
    else if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y+p.g.textSize*2,wordsWidth,p.g.textSize)) state.change(GameState.settings);
    else if(Tools.inBox(p.mouseX,p.mouseY,bPos.x,bPos.y+p.g.textSize*4,wordsWidth,p.g.textSize)) p.exit();
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
  public void frameResized(int w,int h) {
    //    synchronized(this.p) {
    super.frameResized(w,h);
    //---
    wordsWidth=p.textWidth("【】");
    bPos.set((w-wordsWidth)/2,(h+p.textDescent()-p.g.textSize*6)/2+p.g.textSize);
    //    }
  }
  @Override
  public void frameMoved(int x,int y) {}
}
