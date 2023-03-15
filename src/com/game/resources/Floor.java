package com.game.resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.project.Images;
import com.game.project.Super;

/**
 * 台阶
 * 生成多种台阶类型（越多越好^_^）
 * 台阶有三种类型，长、短、小
 * @author Administrator
 *
 */
public class Floor extends Super {
	BufferedImage image;
	
	private int type;		//台阶种类(长,短,小)
	private int num;		//台阶颜色(3种)
	Random rand = new Random();
	
	public Floor(int x,int y,int width,int height,int type) {
		super(x,y,width,height);
		this.type = type;
		num = rand.nextInt(3);
	}
	
	/**图片切换*/
	public void cut() {
		image = Images.floor[4*num];
		
	}
	
	/**图片移动*/
	public void moveTo(int speed) {
		x-=speed;
	}
	
	public void paintObject(Graphics g) {
		switch(type) {
		case 0:		//小台阶
			g.drawImage(Images.floor[4*num+3], x, y, null);
			break;
		case 1:		//短台阶
			g.drawImage(Images.floor[4*num], x, y, null);
			int j=0;
			for(;j<=type;j++) {
				g.drawImage(Images.floor[4*num+1], x-1+image.getWidth()+(Images.floor[4*num+1].getWidth()*j), y, null);
			}
			g.drawImage(Images.floor[4*num+2], x-1+image.getWidth()+(Images.floor[4*num+1].getWidth()*j), y, null);
			break;
		case 2:		//长台阶
			g.drawImage(image, x, y, null);
			int i=0;
			for(;i<=type*2;i++) {
				g.drawImage(Images.floor[4*num+1], x-1+image.getWidth()+(Images.floor[4*num+1].getWidth()*i), y, null);
			}
			g.drawImage(Images.floor[4*num+2], x-1+image.getWidth()+(Images.floor[4*num+1].getWidth()*i), y, null);
			break;
		}
	}
}