package test.findway;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
private static final long serialVersionUID = 1L;
	
	//����panel��Ĭ��sizeΪ50����
	public final static int size = 50;
	
	
	public MyPanel(){}
	
	/**
	 * ���ݷ����λ������panel��λ������
	 * @param x	�����X��λ
	 * @param y	�����Y��λ
	 */
	public MyPanel(int x, int y){
		this.setBounds(x * size, y * size, size, size);
	}
	
	/**
	 * ����fangkuaiposition������panel��λ������
	 * @param fk	fangkuaiposition����
	 */
	public MyPanel(FangKuaiPosition fk){
		this.setBounds(fk.getX() * size, fk.getY() * size, size, size);
	}
}
