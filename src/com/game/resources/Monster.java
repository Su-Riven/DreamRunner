package com.game.resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.project.Images;
import com.game.project.Super;
/**
 * 怪物
 * 生成多种怪物类型（越多越好^_^）
 * 随机生成不同类型的怪物
 * @author Administrator
 *
 */
public class Monster extends Super {
	BufferedImage image;
	Random rand = new Random();
	int type;
	/*
	 * 继承（com.property.game.Super）包的父类的变量并重写方法
	 */
	public Monster(int x,int y,int width,int height) {
		super(x,y,width,height);
		type = rand.nextInt(3);
	}
	int index = 6;
	
	/** 怪物图片切换 */
	public void cut() {
		switch(type) {
		case 0:
			image = Images.monster[index++/2%6];
			break;
		case 1:
			image = Images.monster[index++/2%6+6];
			break;
		case 2:
			image = Images.monster[index++/2%6+12];
			break;
		}
	}
	
	/** 怪物移动*/
	public void moveTo(int speed) {
		this.x-=(speed+2);
	}
	
	/**画对象*/
	public void paintObject(Graphics g) {
		g.drawImage(image, x, y, null); 
	}
	/** 打怪得分 */
	public int getScore() {
		return 50;
	}
}

