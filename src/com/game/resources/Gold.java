package com.game.resources;	//金币，怪物，台阶，武器，障碍物的生成	

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.project.Images;
import com.game.project.Super;

/**
 * 金币类
 * 生成多种金币类型（越多越好^_^）
 * 每种金币定义一个常量接收
 * @author Administrator
 *
 */
public class Gold extends Super {
	public BufferedImage image;		//金币图
	public int type; 				//控制金币类型
	int a;			//其他任意金币
	Random rand = new Random();
	
	public Gold(int x,int y,int width,int height,int type) {
		super(x,y,width,height);
		this.type = type;
		a = rand.nextInt(12)+12;		//其他任意金币
	}
	
	/**金币切换*/
	int index = 0;
	public void cut() {
		if(type==0) {
			image = Images.gold[index++/4%11];
		}else {
			image = Images.gold[a];
		}
	}
	
	/**金币移动*/
	public void moveTo(int speed) {
		this.x -= speed;
	}
	
	/**画对象*/
	public void paintObject(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	/** 吃金币得分 */
	public int getScore() {
		if (type==0) {
			return 30;
		}else {
			return 50;
		}
	}
}
