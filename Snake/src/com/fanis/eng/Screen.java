
package com.fanis.eng;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;


public class Screen extends JPanel implements Runnable{
    
    public static final int WIDTH = 500, HEIGHT = 500;
    private Thread thread;
    private boolean running = false, stop = false;
    private BodyPart b;
    private ArrayList<BodyPart> snake;
    private int xCoor = 10, yCoor = 10;
    private int ax, ay;
    private int size = 10;
    private boolean left = false, right = false, up = false, down = true;
    private int tick;
    private Random ran;
    public Key key;
    private int speed;
    
    private Apple apple;
    private ArrayList<Apple> apples;
    
    public Screen(){
        ran = new Random();
        setFocusable(true);
        key = new Key();
        speed = 90000;
        addKeyListener(key);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake = new ArrayList<BodyPart>();
        
        apples = new ArrayList<Apple>();
        start();
    }
    
    public void start(){
        running  = true;
        thread = new Thread(this, "game start");
        thread.start();
    }
    
    public void stop(){
        for(int i = 0; i < snake.size(); i++){
            if(i != snake.size() -1)
                if(snake.get(i).getx() == snake.get(snake.size() - 1).getx() && snake.get(i).gety() == snake.get(snake.size() - 1).gety()){
                    stop = true;
                }
            
        }
    }
    
    public void tick(){
        if(apples.size() == 0){
            ax = ran.nextInt(50);
            ay = ran.nextInt(50);
            apple = new Apple(ax, ay, 10);
            apples.add(apple);
        }
        if(snake.size() == 0){
            b = new BodyPart(xCoor, yCoor, 10);
            snake.add(b);
        }
        
        tick++;
        
        if(tick > speed){
            
            if(right) xCoor++;
            if(left) xCoor--;
            if(up) yCoor--;
            if(down) yCoor++;
            
            tick = 0;
            
            if(xCoor>49)
                xCoor = 0;
            if(xCoor<0)
                xCoor = 49;
            if(yCoor>49)
                yCoor = 0;
            if(yCoor<0)
                yCoor = 49;
            b = new BodyPart(xCoor, yCoor, 10);
            snake.add(b);
            stop();
        }
        if(snake.size() >= size){
            snake.remove(0);
        }
        if(ax == xCoor && ay == yCoor){
            apples.clear();
            ax = ran.nextInt();
            ay = ran.nextInt();
            
            size++;
        }
        
        
        
    }
    
    
    
    @Override
    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        if(!stop){
            g.setColor(Color.pink);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            for(int i = 0; i < snake.size(); i++){
                try{
                    
                    snake.get(i).draw(g);
                }catch(Exception e){
                    System.err.println(e.getMessage() + "snake error");
                }
            }

            try{
                apples.get(0).draw(g);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        else{
            
            g.drawString("YOU LOST", 250, 250);
        }
    }
    
    @Override
    public void run(){
        while(running){
            tick();
            repaint();
        }
    }
    
    private class Key implements KeyListener{

        @Override
        public void keyTyped(KeyEvent ke) {

        } 
        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                left = false;
                down = true;
            }
            if(key == KeyEvent.VK_S){
                speed -= 100000;
                if(speed<=300000){
                    speed = 900000;
                }
            }
        }
        

        @Override
        public void keyReleased(KeyEvent ke) {

        }
    
    }
}

