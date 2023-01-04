package temp.tools;

import processing.core.PApplet;

public class Temp1 extends PApplet{
  @Override
  public void setup() {
//    int[] data= {
//      0xffFFFFFF,
//      0xffFFFF00,
//      0xffFF00FF,
//      0xffFF0000,
//      0xffC0C0C0,
//      0xff808080,
//      0xff808000,
//      0xff800080,
//      0xff800000,
//      0xff00FFFF,
//      0xff008080,
//      0xff008000,
//      0xff0000FF,
//      0xff000080,
//      0xff000000};
//    for(int i=0;i<data.length;i++) {
//      fill(data[i]);
//      rect(i*4,0,width,height);
//    }
    System.out.println(Integer.toHexString(color(40,189,174)));
  }
  public static void main(String[] args) {
    new Temp1().runSketch();
  }
}
