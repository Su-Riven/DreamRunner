package com.game.project;

import java.io.File;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Music {
	
	private static Resources r = new Resources();
	
	public static void play(String fileName) {
		Start s = new Music().new Start(r, fileName);
		Pause p = new Music().new Pause(r);
		Thread t1 = new Thread(s);
		Thread t2 = new Thread(p);
		t1.start();
		t2.start();
	}
	
	public static void asynPlay(String fileName) {
		new Thread() {
            public void run() {
            	new Resources().start(fileName);
            }
        }.start();
	}
	
	public static void start() {
		r.flag = false;
	}
	public static void pause() {
		r.flag = true;
	}
	
	
	private static class Resources {
		final Lock lock = new ReentrantLock();
		final Condition c = lock.newCondition();
		final Condition d = lock.newCondition();
		boolean flag;
		public void start(String fileName) {
			AudioInputStream audioInputStream = null;
			SourceDataLine sourceDataLine = null;
			try {
				lock.lock();
		        audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));
	            AudioFormat audioFormat = audioInputStream.getFormat();
	            DataLine.Info dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);
	            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLine_info);

	            byte[] b = new byte[1024 * 10];
	            int len = 0;
	            sourceDataLine.open(audioFormat);
	            sourceDataLine.start();
	            while ((len = audioInputStream.read(b)) > 0) {
	            	if (flag) {
	    				d.signal();
	    				c.await();
	    			}
	            	sourceDataLine.write(b, 0, len);
	            }
			} catch(Exception e){
				e.printStackTrace();
			} finally {
				try {
	                if (audioInputStream != null) {
	                    audioInputStream.close();
	                }
	                if (sourceDataLine != null) {
	                    sourceDataLine.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
				lock.unlock();
			}
			
		}
		public void pause() {
			try {
				lock.lock();
				if (!flag) {
					c.signal();
					d.await();
				}
			} catch(Exception e){
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		
	}

	private class Start implements Runnable {

		private Resources r;
		private String fileName;
		
		Start(Resources r, String fileName) {
			this.r = r;
			this.fileName = fileName;
		}
		
		@Override
		public void run() {
			while(true)
				r.start(fileName);
			
		}
		
	}

	private class Pause implements Runnable {

		private Resources r;
		
		Pause(Resources r) {
			this.r = r;
		}
		
		@Override
		public void run() {
			while(true)
				r.pause();
		}
	}
}
