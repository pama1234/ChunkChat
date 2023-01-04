package util.state;

import java.util.HashMap;

import app.UtilApp;
import ui.page.Page;
import ui.page.impl.GamePage;
import ui.page.impl.HomePage;
import ui.page.impl.SettingsPage;

public class StateEngine implements Page{
  GameState s;
  Page index;
  HashMap<GameState,Page> map;
  UtilApp parent;
  {
    map=new HashMap<GameState,Page>();
  }
  public StateEngine(UtilApp in,GameState[] arr) {
    parent=in;
    s=GameState.getDefault();
    for(int i=0;i<arr.length;i++) {
      GameState gameState=arr[i];
      map.put(gameState,newPageWithState(gameState));
    }
    index=map.get(s);
  }
  public Page newPageWithState(GameState in) {
    switch(in) {
      case game:
        return new GamePage(parent,this);
      case home:
        return new HomePage(parent,this);
      case settings:
        return new SettingsPage(parent,this);
    }
    return null;
  }
  @Override
  public void init() {
    map.forEach((k,v)->v.init());
  }
  @Override
  public void update() {
    index.update();
  }
  @Override
  public void display() {
    index.display();
  }
  @Override
  public void dispose() {
    index.end();
    map.forEach((k,v)->v.dispose());
  }
  public void change(GameState in) {
    index.end();
    index=map.get(in);
    index.start();
  }
  @Override
  public void start() {
    index.start();
  }
  @Override
  public void end() {
    index.end();
  }
  @Override
  public void mousePressed() {
    index.mousePressed();
  }
  @Override
  public void mouseReleased() {
    index.mouseReleased();
  }
  @Override
  public void mouseClicked() {
    index.mouseClicked();
  }
  @Override
  public void mouseMoved() {
    index.mouseMoved();
  }
  @Override
  public void mouseDragged() {
    index.mouseDragged();
  }
  @Override
  public void mouseWheel(int c) {
    index.mouseWheel(c);
  }
  @Override
  public void keyPressed() {
    index.keyPressed();
  }
  @Override
  public void keyReleased() {
    index.keyReleased();
  }
  @Override
  public void keyTyped() {
    index.keyTyped();
  }
  @Override
  public void frameResized(int w,int h) {
    index.frameResized(w,h);
  }
  @Override
  public void frameMoved(int x,int y) {
    index.frameMoved(x,y);
  }
}
