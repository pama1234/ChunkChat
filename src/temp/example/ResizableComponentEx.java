package temp.example;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ResizableComponentEx extends JFrame{
  private static final long serialVersionUID=-4905045562976270730L;
  public ResizableComponentEx() {
    Resizable res;
    JPanel pnl=new JPanel(null);
    add(pnl);
    JPanel area=new JPanel();
    area.setBackground(Color.white);
    res=new Resizable(area);
    res.setBounds(50,50,200,150);
    pnl.add(res);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent me) {
        requestFocus();
        res.repaint();
      }
    });
    setSize(550,400);
    setTitle("Resizable component");
    //    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public static void main(String[] args) {
    ResizableComponentEx ex=new ResizableComponentEx();
    ex.setVisible(true);
  }
}