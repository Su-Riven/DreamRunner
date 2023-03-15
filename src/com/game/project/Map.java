package com.game.project;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.game.resources.Award;
import com.game.resources.Barrier;
import com.game.resources.Bomb;
import com.game.resources.Floor;
import com.game.resources.Gold;
import com.game.resources.Monster;

/**
 * 组合
 * 金币，怪物，台阶等的组合
 * @author Administrator
 *
 */
public class Map extends Super {
	List<List<Super>> su = new LinkedList<List<Super>>();
	Random rand = new Random();
	/**读文件,读到一个字符时,
	 *  把字符存入char数组，
	 *  并在Super集合的该位置存入一个指定对象
	**/
	public Map(int x,int num) {
		super(x,0,1000,600);
		BufferedReader br = null;
		try {
			FileInputStream fis = new FileInputStream("./map/map_"+num+".txt");
			InputStreamReader isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String line=null;
			int j = 0;
			while((line = br.readLine()) != null) {
				List<Super> list= new LinkedList<>();
				for (int i = 0; i < line.length(); i++) {
					switch(line.charAt(i)) {
					case 48:		//0	:	空白
						list.add(null);
						break;
					case 49:		//1	:	金币1
						list.add(new Gold(x+i*40,j*40,33,33,0));
						break;
					case 50:		//2	:	其他几种金币
						list.add(new Gold(x+i*40,j*40,50,46,1));
						break;
					case 51:		//3	:	怪物
						list.add(new Monster(x+i*40,j*40-40,60,30));
						break;
					case 52:		//4	:	障碍物
						list.add(new Barrier(x+i*40,j*40-20,68,320));
						break;
					case 53:		//5	:	炸弹
						list.add(new Bomb(x+i*40,j*40-40,60,75));
						break;
					case 54:		//6	:	台阶0（小台阶）
						list.add(new Floor(x+i*40,j*40,40,130,0));
						break;
					case 55:		//7	:	台阶1（短台阶）
						list.add(new Floor(x+i*40,j*40,300,150,1));
						break;
					case 56:		//8	:	台阶2（长台阶）
						list.add(new Floor(x+i*40,j*40,710,150,2));
						break;
					case 57:		//9	:	奖励枪
						if(rand.nextInt(3)==0) {
							list.add(new Award(x+i*40-20,j*40,70,62,0));
						}else {
							list.add(new Gold(x+i*40,j*40,33,33,0));
						}
						break;
					case 97:		//a	:	奖励飞行器
						if(rand.nextInt(3)==0) {
							list.add(new Award(x+i*40,j*40,70,70,1));
						}else {
							list.add(new Gold(x+i*40,j*40,50,46,1));
						}
						break;
					}
				}
				su.add(list);
				j++;
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	/**map里的所有对象都在移动*/
	public void moveTo(int speed) {
		x-=speed;
		for (int i = 0; i < su.size(); i++) {
			List<Super> list= su.get(i);
			for (Super s : list) {
				if(s!=null) {
					s.moveTo(speed);
				}
			}
		}
	}
	
	/**map里的所有对象的切换图片方法*/
	public void cut() {
		for (int i = 0; i < su.size(); i++) {
			List<Super> list= su.get(i);
			for (Super s : list) {
				if(s!=null) {
					s.cut();
				}
			}
		}
	}
	
	/**画map里的所有对象*/
	public void paintObject(Graphics g) {
		for (int i = 0; i < su.size(); i++) {
			List<Super> list= su.get(i);
			for (int j = 0; j < list.size(); j++) {
				Super s = list.get(j);
				if (s != null) {
					s.paintObject(g);
				}
			}
		}
	}
}
