package temp.example;

import app.UtilApp;
import processing.event.MouseEvent;
import util.game.Cam;

public class Example1 extends UtilApp{
  Cam cam;
  @Override
  public void setup() {
    //    super.setup();
    cam=new Cam(0,0);
  }
  @Override
  public void draw() {
    cam.update();
    //---
    translate(width/2,height/2);
    scale(cam.scale);
    translate(cam.pos.x,cam.pos.y);
    //---
    background(191);
    rect(-320,-320,640,640);
  }
  @Override
  public void mouseDragged() {
    System.out.println("yes");
    cam.des.add((mouseX-pmouseX)/cam.scale,(mouseY-pmouseY)/cam.scale);
  }
  @Override
  public void mouseWheel(MouseEvent e) {
    cam.scale+=e.getCount()/8f;
  }
  public static void main(String[] args) {
    new Example1().runSketch();
  }
}
