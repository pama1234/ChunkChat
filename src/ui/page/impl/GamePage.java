package ui.page.impl;

import app.UtilApp;
import processing.core.PConstants;
import ui.component.TextInput;
import ui.page.UtilAppPage;
import util.game.Ship;
import util.game.World;
import util.math.Tools;
import util.state.StateEngine;

public class GamePage extends UtilAppPage{
  World world;
  TextInput text;
  public GamePage(UtilApp parent,StateEngine state) {
    super(parent,state);
  }
  @Override
  public void init() {
    world=new World(p,this,0);
    world.init();
    text=new TextInput();
    world.text=text;
    text.frameResized(p.width,p.height);
  }
  @Override
  public void update() {
    world.update();
  }
  @Override
  public void display() {
    p.background(0,103,153);
    p.translate(p.width/2,p.height/2);
    //---
    p.scale(world.cam.scale);
    p.translate(-world.cam.pos.x*world.rectUnit,-world.cam.pos.y*world.rectUnit);
    //---
    world.display();
    //---
    p.resetMatrix();
    p.textSize(textSizeUnit);
    p.textAlign(PConstants.LEFT,PConstants.TOP);
    p.fill(0,127);
    p.rect(p.width/64f,p.height/32f,p.g.textSize*14,p.g.textSize*5.5f);
    p.fill(255);
    p.text("frameRate="+
      Tools.cutToLastDigit(p.frameRate)+
      //      "\nme.pox.x="+
      //      Tools.cutToLastDigit(world.me.point.pos.x)+
      //      "\nme.pox.y="+
      //      Tools.cutToLastDigit(world.me.point.pos.y)+
      "\ncam.scale="+
      world.cam.scale+
      "\nselect.info="+
      (world.me.select.subject==null?' ':world.me.select.subject.getData())+
      ":0x"+
      (world.me.select.subject==null?"0":Integer.toHexString(world.me.select.subject.getData()))+
      "\nmouseAuto="+
      world.me.select.mouseAuto+
      "\ntext.firstChar="+
      getTextFirstCharData(),
      p.width/32f,p.height/32f);
  }
  public String getTextFirstCharData() {
    String temp=text.getText();
    if(temp.length()<1) return " :0x0";
    char out=temp.charAt(0);
    return out+":0x"+Integer.toHexString(out);
  }
  @Override
  public void dispose() {
    world.dispose();
  }
  @Override
  public void start() {
    super.start();
    //---
    world.me=new Ship(p,this,world,text,"admin",0,0,0);
    world.cam=world.me.cam;
    //---
    world.start();
    //---
    p.frame.add(text,0);
  }
  @Override
  public void end() {
    super.end();
    world.end();
    p.frame.remove(text);
  }
  @Override
  public void mousePressed() {
    super.mousePressed();
  }
  @Override
  public void mouseReleased() {
    super.mouseReleased();
  }
  @Override
  public void mouseClicked() {
    super.mouseClicked();
  }
  @Override
  public void mouseMoved() {
    super.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    super.mouseDragged();
  }
  @Override
  public void mouseWheel(int c) {
    super.mouseWheel(c);
  }
  @Override
  public void keyPressed() {
    super.keyPressed();
  }
  @Override
  public void keyReleased() {
    super.keyReleased();
  }
  @Override
  public void keyTyped() {
    super.keyTyped();
  }
  @Override
  public void frameMoved(int x,int y) {}
  @Override
  public void frameResized(int w,int h) {
    super.frameResized(w,h);
    if(text!=null) text.frameResized(w,h);
  }
}
