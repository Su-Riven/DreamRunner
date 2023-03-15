package com.game.resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.project.Images;
import com.game.project.Super;

/**
 * 奖励类型
 * 武器，飞行器，保护罩
 * @author Administrator
 *
 */
public class Award extends Super {
	BufferedImage image;
	public int type;
	
	public Award(int x,int y,int width,int height,int type) {
		super(x,y,width,height);
		this.type = type;
	}
	
	/**图片切换*/
	public void cut() {
		switch(type) {
		case 0:
			image = Images.award[0];
			break;
		case 1:
			image = Images.award[1];
			break;
//		case 2:
//			image = Images.award[1];
//			break;
		}
	}
	
	/**图片移动*/
	public void moveTo(int speed) {
		x-=speed;
	}
	
	/**画对象*/
	public void paintObject(Graphics g) {
		g.drawImage(image, x, y, null); 
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
}
