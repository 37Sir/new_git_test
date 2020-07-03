package test.findway;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
private static final long serialVersionUID = 1L;
	
	//定义panel的默认size为50像素
	public final static int size = 50;
	
	
	public MyPanel(){}
	
	/**
	 * 根据方块的位置设置panel的位置属性
	 * @param x	方块的X单位
	 * @param y	方块的Y单位
	 */
	public MyPanel(int x, int y){
		this.setBounds(x * size, y * size, size, size);
	}
	
	/**
	 * 根据fangkuaiposition类设置panel的位置属性
	 * @param fk	fangkuaiposition对象
	 */
	public MyPanel(FangKuaiPosition fk){
		this.setBounds(fk.getX() * size, fk.getY() * size, size, size);
	}
}
