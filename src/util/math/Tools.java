package util.math;

public class Tools{
  public static boolean inBox(float a,float b,float x,float y,float w,float h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
  }
  public static boolean inBox(int a,int b,int x,int y,int w,int h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
  }
  public static boolean intersects(float x1,float y1,float w1,float h1,float x2,float y2,float w2,float h2) {
    if(w2<=0||h2<=0||w1<=0||h1<=0) return false;
    w2+=x2;
    h2+=y2;
    w1+=x1;
    h1+=y1;
    return ((w2<x2||w2>x1)&&
      (h2<y2||h2>y1)&&
      (w1<x1||w1>x2)&&
      (h1<y1||h1>y2));
  }
  public static boolean intersects(int x1,int y1,int w1,int h1,int x2,int y2,int w2,int h2) {
    if(w2<=0||h2<=0||w1<=0||h1<=0) return false;
    w2+=x2;
    h2+=y2;
    w1+=x1;
    h1+=y1;
    return ((w2<x2||w2>x1)&&
      (h2<y2||h2>y1)&&
      (w1<x1||w1>x2)&&
      (h1<y1||h1>y2));
  }
  public static float cutToLastDigit(float in) {
    return (int)(in*10)/10f;
  }
}
