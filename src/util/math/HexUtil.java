package util.math;

public class HexUtil{
  public static final Type[] index=Type.values();
  public enum Type{
    N_0(0,0xE9ECEC,'0'),N_1(1,0xF07613,'1'),N__2(2,0xBD44B3,'2'),N_3(3,0x3AAFD9,'3'),
    N_4(4,0xF8C627,'4'),N_5(5,0x70B919,'5'),N__6(6,0xED8DAC,'6'),N_7(7,0x3E4447,'7'),
    N_8(8,0x8E8E86,'8'),N_9(9,0x158991,'9'),N_A(10,0x792AAC,'A'),N_B(11,0x35399D,'B'),
    N_C(12,0x724728,'C'),N_D(13,0x546D1B,'D'),N_E(14,0xA12722,'E'),N_F(15,0x141519,'F');
    public int data,color;
    public char word;
    private Type(int data,int color,char word) {
      this.data=data;
      this.color=0xff000000|color;
      this.word=word;
    }
  }
  public static String format(char in) {
    return String.format("%04x",in&0xffff);
  }
}
