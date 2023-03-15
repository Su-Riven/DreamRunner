package com.game.project;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.game.resources.Award;
import com.game.resources.Barrier;
import com.game.resources.Bomb;
import com.game.resources.Floor;
import com.game.resources.Gold;
import com.game.resources.Monster;

/**
 * 主方法
 * @author Administrator
 *
 */
public class RunningMain extends JPanel{
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAME_OVER = 2;
	public static final int PAUSE = 3;
	private boolean UP, DOWN,ATK,P;
	public int state = START;
	static JFrame frame;
	BufferedImage image;
	int score;
	List<Bullet> list;
	
	Sky sky= new Sky();		//背景
	Knight night = new Knight();
	Map map;
	Map map1;
	int speed;
	Random rand = new Random();
	
	public RunningMain() {
		speed = 15;
		map = new Map(0,7);
		map1 = new Map(1000,rand.nextInt(9));
	}
	
	public void mapMove() {
		if(score>=3000) {
			speed = (15+score/3000); 
		}
		map.moveTo(speed);
		map1.moveTo(speed);
		if(map.x <=-map.width) {
			map = new Map(1000,rand.nextInt(9));
		}
		if(map1.x <=-map1.width) {
			map1 = new Map(1000,rand.nextInt(9));
		}
		
	}
	
	/**碰撞检测*/
	public void CollideHit(List<List<Super>> ss) {
		for (int i = 0; i < ss.size(); i++) {
			Iterator<Super> it = ss.get(i).iterator();
			out:while (it.hasNext()) {
				Super s = it.next();
				if (s instanceof Floor) {	//台阶碰撞
					if (night.y + night.height >= s.y + 20 && night.y + night.height <= s.y + 50
							&& night.x + night.width >= s.x && night.x <= s.x + s.width) {
						night.y = s.y - night.height + 20;
						night.v0 = 0;
						night.jump = true;
						night.jump2 = false;
						night.slide = true;
						night.attack=true;
						if(DOWN&&night.y == (s.y - night.height + 20)) {
							night.slideing();
						}else if(ATK&&night.y == (s.y - night.height + 20)) {
							night.attacking();
						}else{
							night.running();
						}
					} else if (night.y + night.height >= s.y + 20 && s.hit(night)) {
						night.x  = s.x - night.width;
					}
				} else if (s instanceof Gold) {	//金币碰撞
					if(night.fly) {
						
					}
					if (s.hit(night)) {
						Music.asynPlay("music/gold.wav");
						Gold g=(Gold) s;
						night.crashing(g.type);
						score += g.getScore();
						it.remove();
						
					}
				} else if (s instanceof Monster) {	//怪物碰撞
					if(night.weap) {
						if (s.hit(night)) {
							Music.asynPlay("music/dead.wav");
							night.v0 = 1;
							state = GAME_OVER;
						}
						Iterator<Bullet> l = list.iterator();
						while (l.hasNext()) {
							Bullet b = l.next();
							if(s.hit(b)) {
								Music.asynPlay("music/defeat1.wav");
								l.remove();
								it.remove();
								
								continue out;
							}
						}
					}else {
						if (s.hit(night)) {
							if(ATK&&night.attack) {
								Monster m = (Monster) s;
								night.crashing(1);
								Music.asynPlay("music/defeat1.wav");
								it.remove();
								score += m.getScore();
							}else {
								Music.asynPlay("music/dead.wav");
								night.v0 = 1;
								state = GAME_OVER;
							}
						}
					}
				} else if (s instanceof Bomb) {	//炸弹碰撞
					if (s.hit(night)) {
						Music.asynPlay("music/bomb.wav");
						Bomb b = (Bomb) s;
						b.y -= 40;
						b.image = Images.bomb[3];
						night.bombing();
						state = GAME_OVER;
						Music.asynPlay("music/bom.wav");
					}
				}else if(s instanceof Barrier) {	//障碍物碰撞
					if (s.hit(night)&&night.fly==false) {
						night.x  = s.x - night.width;
						if (night.slide) {
							s.height-=10;
						}
					}
				}else if(s instanceof Award){		//奖励碰撞
					if(s.hit(night)) {
						Award a = (Award) s;
						if(a.type==0) {
							night.weap = true;
							night.bullet = 10;
							it.remove();
						}else {
							Music.asynPlay("./music/flying.wav");
							night.fly=true;
							night.flying();
							it.remove();
						}
					}
				}
			}
		}
	}
	
	public void bulletMove() {
		list = night.list;
		for(Bullet b: list) {
			b.moveTo(speed);
		}
	}

	/** 越界删除 */
	public void outOfBang() {
		if(night.x<=-night.width||night.y>=HEIGHT) {
			state = GAME_OVER;
			Music.asynPlay("music/die.wav");
		}
		Iterator<Bullet> it = list.iterator();
		while (it.hasNext()) {
			Bullet b = it.next();
			if(b.x>WIDTH) {
				it.remove();
			}
		}
	}
	
	public void music() {
		
		switch(state) {
		case RUNNING:
		case PAUSE:
			Music.play("music/backmusic.wav");
			Music.start();
			break;
		case GAME_OVER:
			Music.pause();
		}
	}
	
	/** 键盘延迟 */
	private void knightKeyStep() {
		if (DOWN&&!ATK) {
			night.slide=true;
			night.jump=true;
		}else{
			night.slide=false;
		}
	}
	
	private void keyFlag(KeyEvent e, boolean flag) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			UP = flag;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			DOWN = flag;
			break;
		case KeyEvent.VK_SPACE:
			ATK = flag;
			break;
		case KeyEvent.VK_P:
			P = flag;
			break;
		}
	}
	
	int nx = 100;//人物初始x坐标
	int ny = 305;//人物初始y坐标
	boolean start =false;	//游戏是否能开始
	//程序启动入口
	public void action() {
		//键盘监听
		KeyListener k = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {//键入
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {//释放
				keyFlag(e, false);
				night.jump=false;
				night.attack=false;
			}
			
			@Override
			public void keyPressed(KeyEvent e) {//按下
				keyFlag(e, true);
				if(UP&&!ATK&&!P){
					night.jumping();
					night.jumpingTwo();
				}
				if(ATK&&!DOWN){
					night.attacking();
					if(UP){
						night.jumping();
						night.jumpingTwo();
					}
				}else {
					night.attack=false;
				}
				if(!UP&&!ATK&&P&&state==RUNNING) {
					image = Images.pause;
					state = PAUSE;
				}else if(!UP&&!ATK&&P&&state==PAUSE) {
					image = Images.pause;
					state = RUNNING;
				}
			}
		};
		frame.addKeyListener(k);
		
		//鼠标监听
		MouseAdapter l = new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				if(x>=140&&x<=205&&y>=320&&y<=445) {
					Music.asynPlay("music/start.wav");
					nx = 725;
					ny = 125;
					start = true;
				}
				if(x>=700&&x<=915&&y>=350&&y<=425&&start==true) {
					Music.asynPlay("music/pick.wav");
					night.x = 50;
					night.y = 120;
					state = RUNNING;
					start = false;
					night.jump2 = true;
				}
				switch(state) {
				case RUNNING:
					if(x>=500&&x<=550&&y>=10&&y<=60) {
						state = PAUSE;
						Music.asynPlay("music/pause.wav");
						image=Images.pause;
					}
					break;
				case PAUSE:
					if(x>=500&&x<=550&&y>=10&&y<=60) {
						state = RUNNING;
						image=Images.go;
					}
				case GAME_OVER:
					if(x>=370&&x<=420&&y>=370&&y<=420) {
						sky= new Sky();	
						night = new Knight();
						map = new Map(0,7);
						map1 = new Map(1000,rand.nextInt(9));
						nx = 100;
						ny = 305;
						score = 0;
						state=START;
					}else if(x>=450&&x<=500&&y>=370&&y<=420) {
						sky= new Sky();	
						night = new Knight();
						map = new Map(0,7);
						map1 = new Map(1000,rand.nextInt(9));
						night.x = 50;
						night.y = 120;
						speed = 15;
						score = 0;
						state=RUNNING;
						night.jump2 = true;
					}else if(x>=530&&x<=580&&y>=370&&y<=420) {
						System.exit(0);
					}
					break;
				}
			}
		};
		this.addMouseListener(l);	//处理鼠标的操作事件
		
		//启动线程
		while (true) {
			music();
			switch (state) {
			case START:
				night.pick(nx, ny);
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case RUNNING:
				sky.moveTo(1);
				mapMove();
				map.cut();
				map1.cut();
				night.moveTo(3);
				night.cut();
				night.down();
				night.flying();
				bulletMove();
				outOfBang();

				knightKeyStep();
				CollideHit(map.su);
				CollideHit(map1.su);
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				image = Images.go;
				break;
			case PAUSE:
				image=Images.pause;
				repaint();
				break;
			case GAME_OVER:
				if (night.bomb) {
					night.bombing();
				}
				night.image1 = Images.scorepicture[0];
				night.v0 = -25;
				night.down();
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
				default :
					break;
			}
		}
	}
	
	public void paint(Graphics g){
		switch(state) {
		case START:
			g.drawImage(Images.bg[0], 0, 0, null);
			g.drawImage(Images.title, 50, 50, null);
			g.drawImage(Images.wait, 390, 260, null);
			night.paintObject(g);
			break;
		case GAME_OVER:
		case PAUSE:
		case RUNNING:
			g.drawImage(Images.bg[1], 0, 0, null);
			sky.paintObject(g);
			map.paintObject(g);
			map1.paintObject(g);
			night.paintObject(g);
			for(Bullet b: list) {
				b.paintObject(g);
			}
			
			g.drawImage(image, 500, 10, null);
			if(state ==PAUSE) {
				g.drawImage(Images.paused, 350, 200, null);
			}else if(state ==GAME_OVER) {
				g.drawImage(Images.gameover, 350, 200, null);
			}
			g.drawImage(Images.scoreframe, 0, 0, null);
			int length=20;
			String str=String.valueOf(score);
			char[] ar=str.toCharArray();//转换为数组
			for (int j = 0; j < ar.length; j++) {
				switch(ar[j]) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':g.drawImage(Images.g_score[ar[j]-48],length,18, null);break;
				}
				length+=25;
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		RunningMain run = new RunningMain();
		frame = new JFrame("Dream Runner");
		frame.add(run);
		frame.setSize(WIDTH,HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		run.action();
		
	}
}
