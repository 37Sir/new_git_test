package test.findway;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;

public class BasePanel extends JFrame{
	private static final long serialVersionUID = 1L;
	private static int beginX = 0;//jframe��x����
	private static int beginY = 0;//jframe��y����
	private static int frameWidth = 815;//jframe�Ŀ�
	private static int frameHeight = 635;//jframe�ĸ�
	private static int width = 800;//�ڲ�panel�Ŀ�
	private static int height = 600;//�ڲ�panel�ĸ�
	public static int widthLength = 16;//���鵥λ��y�������ֵ
	public static int heightLength = 12;//���鵥λ��x�������ֵ
	public static BackGroundPanel bgp = new BackGroundPanel();//����panel�����еķ��鶼�������panel�У�Ȼ�����panel��ӵ�jframe��
	
	public static MyPanel cat = new MyPanel(0,0);//���
	public static MyPanel fish = new MyPanel(ThreadLocalRandom.current().nextInt(widthLength),ThreadLocalRandom.current().nextInt(heightLength));//��������յ�
	private final long sleepTime = 10;//�����Զ��ƶ��ļ��ʱ��
	
	public static List<FangKuaiPosition> zhangaiList = new ArrayList<>(); //��ͼ�ϵ��ϰ���/���ɴ�Խ�ط��ļ���
	public static List<FangKuaiPosition> closedList = new ArrayList<>(); //���߹�·�߼���
	public static List<FangKuaiPosition> openList = new ArrayList<>(); //��Ҫ������ɢ�ķ���ļ���
	
	public BasePanel(){
		//��ȡ��Ļ�ߴ������Ϣ
		Dimension dimension = CommonUtil.getScreenSize();
		//�趨JFrame�������� 
		beginX = (int) (dimension.getWidth()/2-400);
		beginY = (int) (dimension.getHeight()/2-300);
		this.setBounds(beginX, beginY, frameWidth, frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		//���panel
		cat.setBackground(Color.green);
		//�յ�panel
		fish.setBackground(Color.red);
		
		bgp.setBounds(0, 0, width, height);
		bgp.add(cat);
		bgp.add(fish);
		
		//���ϰ�������panel
		for(FangKuaiPosition fk : zhangaiList){
			MyPanel panel = new MyPanel(fk);
			panel.setBackground(Color.gray);
			bgp.add(panel);
		}
		
		this.add(bgp);
		this.repaint();
		this.setVisible(true);
	}
	
	public static void main(String[] args) throws InterruptedException {
		getZhangAiList();
		BasePanel bp = new BasePanel();
		AutoFindWay afw = new AutoFindWay();
		List<FangKuaiPosition> wayList = afw.getWayLine(cat, fish);
		bp.movePanel(wayList);
	}
	
	/**
	 * �����ƶ�
	 * @param wayList	�ƶ�·��
	 * @throws InterruptedException
	 */
public void movePanel(List<FangKuaiPosition> wayList) throws InterruptedException{
		
		if(wayList == null || wayList.size() == 0){
			System.out.println("�޷� �����յ� ��");
			return;
		}
		
		for(int i = wayList.size() - 2; i >= 0; i--){
			FangKuaiPosition fk = wayList.get(i);
			
			//�������ƶ�
			while(cat.getY() > fk.getY() * MyPanel.size && cat.getX() > fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() - 2, cat.getY() - 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//�������ƶ�
			while(cat.getY() > fk.getY() * MyPanel.size && cat.getX() < fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() + 2, cat.getY() - 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//�������ƶ�
			while(cat.getY() < fk.getY() * MyPanel.size && cat.getX() < fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() + 2, cat.getY() + 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//�������ƶ�
			while(cat.getY() < fk.getY() * MyPanel.size && cat.getX() > fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() - 2, cat.getY() + 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//����
			while(cat.getY() > fk.getY() * MyPanel.size && cat.getX() == fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX(), cat.getY() - 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//����
			while(cat.getY() < fk.getY() * MyPanel.size && cat.getX() == fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX(), cat.getY() + 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//����
			while(cat.getX() > fk.getX() * MyPanel.size && cat.getY() == fk.getY() * MyPanel.size){
				cat.setBounds(cat.getX() - 2, cat.getY(), MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//����
			while(cat.getX() < fk.getX() * MyPanel.size && cat.getY() == fk.getY() * MyPanel.size){
				cat.setBounds(cat.getX() + 2, cat.getY(), MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
		}
		System.out.println("Ѱ·������");
	}
	
	/**
	 * ��������ϰ�����
	 */
	public static void getZhangAiList(){
		
		//�������60���ϰ�����
		while(zhangaiList.size() < 60){
			
			int x = ThreadLocalRandom.current().nextInt(widthLength);
			int y = ThreadLocalRandom.current().nextInt(heightLength);
			FangKuaiPosition fk = new FangKuaiPosition(x,y);
			//�����ɵķ��鲻���Ѵ���zhangailist�У�Ҳ���ܺ����/�յ��غ�
			if(zhangaiList.contains(fk) || (cat.getX() == fk.getX()*MyPanel.size && cat.getY() == fk.getY() * MyPanel.size) 
					|| (fish.getX() == fk.getX() * MyPanel.size && fish.getY() == fk.getY() * MyPanel.size)){
				continue;
			}
			zhangaiList.add(fk);
		}
	}

}
