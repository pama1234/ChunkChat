package util.math;

import javax.vecmath.Vector2f;

public class Vec2f extends Vector2f{
  private static final long serialVersionUID=8654339263948261774L;
  public Vec2f(int i,int j) {
    x=i;
    y=j;
  }
  public Vec2f() {}
  public void add(float a,float b) {
    x+=a;
    y+=b;
  }
}
