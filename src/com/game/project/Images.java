package com.game.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 图片类
 * 整个游戏所有图片
 * 金币，怪物，障碍物，台阶的图片种类越多越好
 * @author Administrator
 *
 */
public class Images {
	
	public static BufferedImage[] many = new BufferedImage[2];		//多人对战按钮
	public static BufferedImage[] bg = new BufferedImage[2];		//背景图片
	public static BufferedImage[] gold = new BufferedImage[25];		//金币图片
	public static BufferedImage[] monster = new BufferedImage[18];	//怪物图片
	public static BufferedImage[] award = new BufferedImage[2];		//武器图片
	public static BufferedImage[] floor = new BufferedImage[12];	//台阶图片
	public static BufferedImage[] fg = new BufferedImage[8];		//前景图片
	public static BufferedImage[] barrier = new BufferedImage[4];	//障碍物图片
	public static BufferedImage[] bomb = new BufferedImage[4];		//炸弹
	
	public static BufferedImage[] k_run = new BufferedImage[10];	//骑士跑
	public static BufferedImage[] k_weap = new BufferedImage[10];	//骑士拿枪跑
	public static BufferedImage[] k_fall = new BufferedImage[16];	//骑士摔倒
	public static BufferedImage[] k_pick = new BufferedImage[12];	//选骑士
	public static BufferedImage[] k_fly = new BufferedImage[8];		//骑士飞的图片
	public static BufferedImage[] k_jump = new BufferedImage[6];	//跳
	public static BufferedImage[] k_jump2 = new BufferedImage[8];	//二段跳
	public static BufferedImage[] k_shoot = new BufferedImage[5];	//开枪
	public static BufferedImage[] k_slide = new BufferedImage[6];	//下滑
	public static BufferedImage[] k_attack = new BufferedImage[6];	//攻击
	public static BufferedImage[] k_bomb = new BufferedImage[3];	//碰炸弹
	
	public static BufferedImage[] g_score=new BufferedImage[10];	//吃金币分数
	public static BufferedImage scoreframe;		//分数框
	public static BufferedImage[] scorepicture = new BufferedImage[3];	//爆分图
	
	public static BufferedImage go;			//继续
	public static BufferedImage gameover;	//结束
	public static BufferedImage pause;		//暂停
	public static BufferedImage paused;		//暂停
	public static BufferedImage protect;	//护盾
	public static BufferedImage bullet;		//子弹
	public static BufferedImage title;		//标题
	public static BufferedImage wait;		//标题
	
	static {
		try {
			wait = ImageIO.read(new File("images/knight/wait.png"));
			title = ImageIO.read(new File("images/back/title.png"));
			bullet = ImageIO.read(new File("images/bullet/bullet.png"));
			protect = ImageIO.read(new File("images/back/protect.png"));
			many[0] = ImageIO.read(new File("images/back/many0.png"));
			many[1] = ImageIO.read(new File("images/back/many1.png"));
			bg[0] = ImageIO.read(new File("images/back/back1.png"));
			bg[1] = ImageIO.read(new File("images/back/background.png"));
			award[0] = ImageIO.read(new File("images/award/a_0.png"));
			award[1] = ImageIO.read(new File("images/award/a_1.png"));
			scoreframe=ImageIO.read(new File("images/score/back.png"));	//分数框图片
			go = ImageIO.read(new File("images/state/go.png"));
			gameover = ImageIO.read(new File("images/state/gameover.png"));
			pause = ImageIO.read(new File("images/state/pause.png"));
			paused = ImageIO.read(new File("images/state/paused.png"));
			
			for (int i = 0; i < k_bomb.length; i++) {
				k_bomb[i] = ImageIO.read(new File("images/knight/bom"+i+".png"));
				scorepicture[i]=ImageIO.read(new File("images/gold/"+i+".png"));
			}
			for (int i = 0; i < monster.length; i++) {
				gold[i] = ImageIO.read(new File("images/gold/g_"+i+".png"));
				monster[i] =ImageIO.read(new File("images/monster/mo_"+i+".png"));
			}
			for (int i = 0; i < gold.length; i++) {
				gold[i] = ImageIO.read(new File("images/gold/g_"+i+".png"));
			}
			for(int i=0;i<floor.length;i++) {
				floor[i] = ImageIO.read(new File("images/floor/fl_"+i+".png"));
			}
			for(int i=0;i<fg.length;i++) {
				fg[i] = ImageIO.read(new File("images/back/fg_"+i+".png"));
			}
			for(int i=0;i<barrier.length;i++) {
				barrier[i] = ImageIO.read(new File("images/barrier/ba_"+i+".png"));
				bomb[i] =ImageIO.read(new File("images/bomb/bo_"+i+".png"));
				
			}
			for(int i=0;i<k_run.length;i++) {
				k_run[i] = ImageIO.read(new File("images/knight/Run_00"+i+".png"));
				k_weap[i] = ImageIO.read(new File("images/knight/RunWithGun_00"+i+".png"));
			}
			for(int i=0;i<k_fly.length;i++) {
				k_fly[i] = ImageIO.read(new File("images/knight/Jetpack_00"+i+".png"));
				k_jump2[i] = ImageIO.read(new File("images/knight/Roll_00"+i+".png"));
			}
			for(int i=0;i<k_jump.length;i++) {
				k_jump[i] = ImageIO.read(new File("images/knight/Jump_00"+i+".png"));
				k_slide[i] = ImageIO.read(new File("images/knight/Sliding_00"+i+".png"));
				k_attack[i] = ImageIO.read(new File("images/knight/Attack"+i+".png"));
			}
			for(int i=0;i<k_fall.length;i++) {
				k_fall[i] = ImageIO.read(new File("images/knight/Die_"+i+".png"));
			}
			for(int i=0;i<k_pick.length;i++) {
				k_pick[i] = ImageIO.read(new File("images/knight/Idle_"+i+".png"));
			}
			for(int i=0;i<k_shoot.length;i++) {
				k_shoot[i] = ImageIO.read(new File("images/knight/Shot_00"+i+".png"));
			}
			//数字图片
			for (int i = 0; i < g_score.length; i++) {
				g_score[i]=ImageIO.read(new File("images/score/"+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
