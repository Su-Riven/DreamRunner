package com.game.resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.project.Images;
import com.game.project.Super;

/**
 * 障碍物
 * 生成多种障碍物类型（越多越好^_^）
 * 随机生成不同类型的障碍物
 * @author Administrator
 *
 */
public class Barrier extends Super {
	BufferedImage image;
	private int num;
	Random rand = new Random();

	public Barrier(int x,int y,int width,int height) {
		super(x,y,width,height);
		num = rand.nextInt(4);
	}
	
	/**图片切换*/
	public void cut() {
		image = Images.barrier[num];
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
