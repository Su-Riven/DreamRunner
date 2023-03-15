package com.game.project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 主角（一个骑士，去拯救被劫持的公主）
 * 共有属性继承超类
 * @author Administrator
 */
public class Knight extends Super {
	public static final int RUN = 0;
	public static final int WEAP = 1;
	public static final int BOMB = 2;
	public static final int PICK = 3;
	public static final int FLY = 4;
	public static final int JUMP = 5;
	public static final int JUMP_TWO = 6;
	public static final int SHOOT = 7;
	public static final int SLIDE = 8;
	public static final int ATTACK = 9;
	public static final int WEAP_JUMP = 10;
	public int state = JUMP_TWO;	//初始状态
	boolean jump;	//一段跳开关
	boolean jump2 = true;	//二段跳开关
	boolean slide;	//下滑开关
	boolean attack;	//攻击开关
	boolean fly;	//飞的开关
	boolean bomb;	//爆炸开关
	boolean weap;	//武器开关
	boolean shoot;	//子弹开关
	
	BufferedImage image;		//英雄图
	public BufferedImage image1;	//爆分图
	double g;		//重力加速度
	double t;		//时间
	double v0;		//初速度
	double s;		//位移
	long time = 0;	//飞行时间
	long time1 = 0;	//爆分时间
	int bullet;
	
	public Knight() {
		super(50,120,90,130);		//骑士的图片属性
		g = 10;	
		t = 0.4;
		v0 = 0;
		s = 0;
	}
	
	/** 爆分*/
	public void crashing(int type) {
			time1++;
			switch(type) {
			case 0:
				image1 = Images.scorepicture[1];
				break;
			case 1:
				image1 = Images.scorepicture[2];
				break;
			}
			if(time1>=50) {
				image1 = Images.scorepicture[0];
				time1 = 0;
			}
	}
	
	/**选择英雄*/
	public void pick(int x,int y) {
		this.x = x;
		this.y = y;
		image = Images.k_pick[index++%12];
	}
	
	/** 骑士移动*/
	public void moveTo(int speed) {
		if(x+this.width/2>=RunningMain.WIDTH/2) {
			x = RunningMain.WIDTH/2-this.width/2;
		}else {
			x+=speed;
		}
	}
	
	/**飞*/
	int in=0;
	public void flying() {
		if(fly) {
			time++;
			state = FLY;
			this.y = 180;
			if(time==160) {
				jump2=true;
				v0 = 0;
				fly=false;
				time=0;
			}
		}
	}
	
	/** 骑士向下掉*/
	public void down() {
		double v = v0;
		s = v*t + 0.5*g*t*t;
		y = (int) (y - s);
		v0 = v - g*t;
	}
	
	/**跳*/
	public void jumping() {
		if(jump&&!jump2) {
			Music.asynPlay("music/jump.wav");
			if(weap) {
				state = WEAP_JUMP;
			}else {
				state = JUMP;
			}
			v0 = 35;
			jump2 =true;
		}
	}
	
	/**二段跳*/
	public void jumpingTwo() {
		if(jump2&&!jump) {
			Music.asynPlay("music/jump2.wav");
			state = JUMP_TWO;
			v0 = 30;
			jump2 =false;
		}
	}
	
	/**骑士下滑*/
	public void slideing() {
		if(slide) {
			state = SLIDE;
		}
	}
	
	/**骑士跑*/
	public void running() {
		if(weap) {
			state = WEAP;
		}else {
			state = RUN;
		}
	}
	
	/**碰炸弹*/
	public void bombing() {
		image = Images.k_bomb[index++/5%3];
		if(index>=Images.bomb.length) {
			bomb = true;
		}
	}
	
	/** 攻击*/
	List<Bullet> list = new ArrayList<>();
	public void attacking() {
		if(attack) {
			if(weap) {
				state = SHOOT;
				Music.asynPlay("music/shoot.wav");
				list.add(new Bullet(x,y));
				bullet-=1;
				if(bullet==0) {
					weap = false;
				}
			}else {
				Music.asynPlay("music/attack1.wav");
				state = ATTACK;
			}
		}
	}
	
	/** 骑士图片的切换(像素小鸟fly方法)*/
	int index = 0;
	public void cut() {
		image1 = Images.scorepicture[0];
		switch(state) {
		case 0:		//骑士跑(循环)
			image = Images.k_run[index++%10];
			break;
		case 1:		//骑士拿着枪跑(循环)
			image = Images.k_weap[index++%10];
			break;
		case 2:		//骑士爆炸(一次)
			image = Images.k_bomb[index++%6];
			break;
		case 3:		//选骑士界面(循环)
			image = Images.k_pick[index++%12];
			break;
		case 4:		//骑士飞(循环)
			image = Images.k_fly[index++%8];
			break;
		case 5:		//骑士跳(循环)
			image = Images.k_jump[index++%6];
			break;
		case 6:		//骑士二段跳(循环)
			image = Images.k_jump2[index++%8];
			break;
		case 7:		//骑士开枪(一次)
			image = Images.k_shoot[index++%5];
			break;
		case 8:		//骑士下滑(循环)
			image = Images.k_slide[index++%6];
			break;
		case 9:		//骑士攻击(一次)
			image = Images.k_attack[index++%6];
			break;
		case 10:		//骑士攻击(一次)
			image = Images.k_weap[0];
			break;
		}
	}
	
	/** 画骑士*/
	public void paintObject(Graphics g) {
		g.drawImage(image, x, y, null);
		g.drawImage(image1, x+100, y+50, null);
	}

	
}
