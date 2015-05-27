import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class Test here.
 * 
 * @author Alex
 * @version 0
 */
public class Test extends JPanel implements Runnable,ActionListener,KeyListener
{
    private int n,score,combo,current;
    private Parser p;
    private Timer timer;
    private int v;
    private Thread thread;
    private Song song;
    private JFrame f;
    private ArrayList<Integer> times;
    
    public static void main(String[] args) throws Exception
    {
        Test r = new Test("4d.osu");
        Thread current = new Thread(r);
        r.thread = current;
        current.start();
    }

    public Test(String file)
    {
        p = new Parser(file);
        song = Song.makeNew("f.wav", p.delay());
        n=1;
        v = score = current = 0;
        f=new JFrame("1kmania");
        f.addKeyListener(this);
        f.setContentPane(this);
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        timer=new Timer(20,this);
        timer.start();
    }

    public void run()
    {
        times = p.times();
        song.start();

        try{
            thread.sleep(song.getDelay());
            //song.notify();
            thread.sleep(times.get(0));
            v=255;
        }catch(Exception e){}
        while(n<times.size()){
            try{
                int diff = times.get(n)-times.get(n-1);
                thread.sleep(diff/2);
                current=n;
                thread.sleep(diff/2);
                v=255;
            }
            catch(Exception e){}
            n++;
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        setBackground(new Color(v,v,v));
        g.drawString(Long.toString(song.getms()), 400, 300);
        g.setColor(Color.red);
        g.drawString(score+"", 1, 20);
        g.drawString(current+"", 1, 40);
        g.drawString(n+"", 1, 60);
        g.drawString(song.getms()+"", 1, 80);
        g.drawString(times.get(current)+"", 1, 100);
        v=v<3?0:v-2;
    }

    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    public void keyPressed(KeyEvent e)
    {
        if(true){//e.getKeyCode()==KeyEvent.VK_SPACE){
            long off = Math.abs(times.get(current)-song.getms());
            if(off<300)
            {
                current++;
                score+=300;
                combo++;
            }
        }
    }

    public void keyReleased(KeyEvent e)
    {

    }

    public void keyTyped(KeyEvent e){}
}
