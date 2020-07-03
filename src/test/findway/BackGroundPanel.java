package test.findway;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 背景panel，用于划线
 * @author NING MEI
 *https://blog.csdn.net/leq3915/article/details/80714902
 */
public class BackGroundPanel extends JPanel{
	
	private static final long serialVersionUID = 8195337324862727029L;
	
	public BackGroundPanel() {
		this.setLayout(null);
	}
	

	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		graphics.drawLine(50, 0, 50, 700);
		graphics.drawLine(100, 0, 100, 700);
		graphics.drawLine(150, 0, 150, 700);
		graphics.drawLine(200, 0, 200, 700);
		graphics.drawLine(250, 0, 250, 700);
		graphics.drawLine(300, 0, 300, 700);
		graphics.drawLine(350, 0, 350, 700);
		graphics.drawLine(400, 0, 400, 700);
		graphics.drawLine(450, 0, 450, 700);
		graphics.drawLine(500, 0, 500, 700);
		graphics.drawLine(550, 0, 550, 700);
		graphics.drawLine(600, 0, 600, 700);
		graphics.drawLine(650, 0, 650, 700);
		graphics.drawLine(700, 0, 700, 700);
		graphics.drawLine(750, 0, 750, 700);
		graphics.drawLine(800, 0, 800, 700);
		
		graphics.drawLine(0, 50, 850, 50);
		graphics.drawLine(0, 100, 850, 100);
		graphics.drawLine(0, 150, 850, 150);
		graphics.drawLine(0, 200, 850, 200);
		graphics.drawLine(0, 250, 850, 250);
		graphics.drawLine(0, 300, 850, 300);
		graphics.drawLine(0, 350, 850, 350);
		graphics.drawLine(0, 400, 850, 400);
		graphics.drawLine(0, 450, 850, 450);
		graphics.drawLine(0, 500, 850, 500);
		graphics.drawLine(0, 550, 850, 550);
		graphics.drawLine(0, 600, 850, 600);
		graphics.drawLine(0, 650, 850, 650);
	}

}
