package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame
{
    int weith=2*GameUtil.OFFSET+GameUtil.MAP_W*GameUtil.SQUARE_LENGTH;
    int height=4*GameUtil.OFFSET+GameUtil.MAP_H*GameUtil.SQUARE_LENGTH;
    Image offScreenImage=null;
    MapButtom mapButtom=new MapButtom();
    MapTop mapTop=new MapTop();
    GameSelect gameSelect=new GameSelect();
    //是否开始 f为未开始 t为开始
    boolean begin=false;
    void launch()
    {
        GameUtil.START_TIME=System.currentTimeMillis();
        if(GameUtil.state==3)
        {
            this.setSize(500,500);
        }else
        {
            this.setSize(weith,height);//窗口尺寸
        }
        this.setVisible(true);//窗口可见
        this.setLocationRelativeTo(null);//窗口居中
        this.setTitle("扫雷游戏");//窗口标题
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//关闭窗口的方法
        //鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (GameUtil.state)
                {
                    case 0:
                        if(e.getButton()==1)
                        {
                            GameUtil.MOUSE_X=e.getX();
                            GameUtil.MOUSE_Y=e.getY();
                            GameUtil.LEFT=true;
                        }
                        if(e.getButton()==3)
                        {
                            GameUtil.MOUSE_X=e.getX();
                            GameUtil.MOUSE_Y=e.getY();
                            GameUtil.RIGHT=true;
                        }
                    case 1:
                    case 2:
                        if(e.getButton()==1)
                        {
                            if(e.getX()>GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2)&&
                            e.getX()<GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2)+GameUtil.SQUARE_LENGTH
                            && e.getY()>GameUtil.OFFSET
                            && e.getY()<GameUtil.OFFSET+GameUtil.SQUARE_LENGTH)
                            {
                                mapButtom.reGame();
                                mapTop.reGame();
                                GameUtil.FLAG_NUM=0;
                                GameUtil.START_TIME=System.currentTimeMillis();
                                GameUtil.state=0;
                            }
                        }
                        if(e.getButton()==2)
                        {
                            GameUtil.state=3;
                            begin=true;
                        }
                        break;
                    case 3:
                        if(e.getButton()==1)
                        {
                            GameUtil.MOUSE_X=e.getX();
                            GameUtil.MOUSE_Y=e.getY();
                            begin=gameSelect.hard();
                        }
                        break;
                    default:
                }
            }
        });
        /*
            继承JFrame类，创建运行方法
         */
        while (true)
        {
            repaint();
            begin();
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void begin()
    {
        if(begin)
        {
            begin=false;
            gameSelect.hard(GameUtil.level);
            dispose();
            GameWin gameWin=new GameWin();
            GameUtil.START_TIME=System.currentTimeMillis();
            GameUtil.FLAG_NUM=0;
            mapButtom.reGame();
            mapTop.reGame();
            gameWin.launch();
        }
    }
    @Override
    public void paint(Graphics g) {
        if(GameUtil.state==3)
        {
            g.setColor(Color.white);
            g.fillRect(0,0,500,500);
            gameSelect.paintself(g);
        }else {
            offScreenImage = this.createImage(weith, height);
            Graphics gImage = offScreenImage.getGraphics();
            //设置背景颜色
            gImage.setColor(Color.orange);
            gImage.fillRect(0, 0, weith, height);
            mapButtom.paintSelf(gImage);//调用MapButtom的方法
            mapTop.paintSelf(gImage);
            g.drawImage(offScreenImage, 0, 0, null);
        }
    }

    public static void main(String[] args)
    {
        GameWin gameWin=new GameWin();
        gameWin.launch();
    }
}
