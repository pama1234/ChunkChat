package util;

public interface AppEventListener{
  public void mousePressed();
  public void mouseReleased();
  public void mouseClicked();
  public void mouseMoved();
  public void mouseDragged();
  public void mouseWheel(int c);
  public void keyPressed();
  public void keyReleased();
  public void keyTyped();
  public void frameResized(int w,int h);
  public void frameMoved(int x,int y);
}
