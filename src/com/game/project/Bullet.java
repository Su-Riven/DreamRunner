package com.game.project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public class Bullet extends Super {
	BufferedImage image;
	public Bullet(int x,int y) {
		super(x,y,43,100);
	}

	@Override
	public void moveTo(int speed) {
		x += (speed+3);
		
	}

	@Override
	public void cut() {
		image = Images.bullet;
	}

	@Override
	public void paintObject(Graphics g) {
		g.drawImage(Images.bullet, x+100, y+80, null);
	}
}
