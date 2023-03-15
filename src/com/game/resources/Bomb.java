package com.game.resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.project.Images;
import com.game.project.Super;

/**
 * 炸弹
 * @author Administrator
 *
 */
public class Bomb extends Super {
	public BufferedImage image;
	private int num;
	Random rand = new Random();
	
	public Bomb(int x,int y,int width,int height) {
		super(x,y,width,height);
		num = rand.nextInt(3);
	}
	
	/**图片切换*/
	public void cut() {
		image = Images.bomb[num];
	}
	
	/**图片移动*/
	public void moveTo(int speed) {
		x-=speed;
	}
	
	/**画对象*/
	public void paintObject(Graphics g) {
		g.drawImage(image, x, y, null); 
	}
}

