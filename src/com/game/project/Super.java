package com.game.project;

import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 超类
 * @author Administrator
 *
 */
public abstract class Super {
	/*
	 * 图片属性：x,y,width,height
	 * 共同方法：移动，画图片. . .
	 * 
	 */
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public Super(int x,int y,int width,int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void moveTo(int speed);	//移动方法
	public abstract void cut();		//切换图片
	public abstract void paintObject(Graphics g);
	
	public boolean hit(Super s) {
		Rectangle r1 = new Rectangle(this.x, this.y, this.width, this.height);
		Rectangle r2 = new Rectangle(s.x+30, s.y-5, s.width-20, s.height-10);
		return r1.intersects(r2);
	}
}
