package com.game.project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 天空类
 * 共有属性继承超类
 * @author Administrator
 *
 */
public class Sky extends Super {
	BufferedImage image1;
	BufferedImage image2;
	private int x1;
	Random rand = new Random();

	public Sky() {
		super(0, 0, 1000, 250);
		x1 = this.width;

	}
	/*
	 * 继承并重写父类移动方法
	 * 继承并重写父类画图方法，背景图不变，前景图随机轮换播放
	 */

	/**天空移动*/
	private int index = 0;
	public void moveTo(int speed) {
		if(index==0) {
			image1 = Images.fg[0];
			image2 = Images.fg[1];
			index++;
		}
		x -= speed;
		x1 -= speed;
		if (x<-this.width) {
			image1 = Images.fg[rand.nextInt(7)];
			x = this.width;
		}
		if (x1<-this.width) {
			image2 = Images.fg[rand.nextInt(7)];
			x1 = this.width;
		}
	}
	
	/**画天空*/
	public void paintObject(Graphics g) {
		g.drawImage(image1,x,y,null);
		g.drawImage(image2,x1,y,null);
	}

	@Override
	public void cut() {
	}
}
