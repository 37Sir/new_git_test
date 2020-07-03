package test.findway;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;

public class BasePanel extends JFrame{
	private static final long serialVersionUID = 1L;
	private static int beginX = 0;//jframe的x坐标
	private static int beginY = 0;//jframe的y坐标
	private static int frameWidth = 815;//jframe的宽
	private static int frameHeight = 635;//jframe的高
	private static int width = 800;//内部panel的宽
	private static int height = 600;//内部panel的高
	public static int widthLength = 16;//方块单位的y方向最大值
	public static int heightLength = 12;//方块单位的x方向最大值
	public static BackGroundPanel bgp = new BackGroundPanel();//容器panel，所有的方块都放入这个panel中，然后将这个panel添加到jframe中
	
	public static MyPanel cat = new MyPanel(0,0);//起点
	public static MyPanel fish = new MyPanel(ThreadLocalRandom.current().nextInt(widthLength),ThreadLocalRandom.current().nextInt(heightLength));//随机生成终点
	private final long sleepTime = 10;//方块自动移动的间隔时间
	
	public static List<FangKuaiPosition> zhangaiList = new ArrayList<>(); //地图上的障碍物/不可穿越地方的集合
	public static List<FangKuaiPosition> closedList = new ArrayList<>(); //已走过路线集合
	public static List<FangKuaiPosition> openList = new ArrayList<>(); //需要向外扩散的方块的集合
	
	public BasePanel(){
		//获取屏幕尺寸相关信息
		Dimension dimension = CommonUtil.getScreenSize();
		//设定JFrame基础属性 
		beginX = (int) (dimension.getWidth()/2-400);
		beginY = (int) (dimension.getHeight()/2-300);
		this.setBounds(beginX, beginY, frameWidth, frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		//起点panel
		cat.setBackground(Color.green);
		//终点panel
		fish.setBackground(Color.red);
		
		bgp.setBounds(0, 0, width, height);
		bgp.add(cat);
		bgp.add(fish);
		
		//将障碍物生成panel
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
	 * 方块移动
	 * @param wayList	移动路线
	 * @throws InterruptedException
	 */
public void movePanel(List<FangKuaiPosition> wayList) throws InterruptedException{
		
		if(wayList == null || wayList.size() == 0){
			System.out.println("无法 到达终点 ！");
			return;
		}
		
		for(int i = wayList.size() - 2; i >= 0; i--){
			FangKuaiPosition fk = wayList.get(i);
			
			//向左上移动
			while(cat.getY() > fk.getY() * MyPanel.size && cat.getX() > fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() - 2, cat.getY() - 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//向右上移动
			while(cat.getY() > fk.getY() * MyPanel.size && cat.getX() < fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() + 2, cat.getY() - 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//向右下移动
			while(cat.getY() < fk.getY() * MyPanel.size && cat.getX() < fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() + 2, cat.getY() + 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//向左下移动
			while(cat.getY() < fk.getY() * MyPanel.size && cat.getX() > fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX() - 2, cat.getY() + 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//向上
			while(cat.getY() > fk.getY() * MyPanel.size && cat.getX() == fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX(), cat.getY() - 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//向下
			while(cat.getY() < fk.getY() * MyPanel.size && cat.getX() == fk.getX() * MyPanel.size){
				cat.setBounds(cat.getX(), cat.getY() + 2, MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//向左
			while(cat.getX() > fk.getX() * MyPanel.size && cat.getY() == fk.getY() * MyPanel.size){
				cat.setBounds(cat.getX() - 2, cat.getY(), MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
			//向右
			while(cat.getX() < fk.getX() * MyPanel.size && cat.getY() == fk.getY() * MyPanel.size){
				cat.setBounds(cat.getX() + 2, cat.getY(), MyPanel.size, MyPanel.size);
				Thread.sleep(sleepTime);
			}
			
		}
		System.out.println("寻路结束！");
	}
	
	/**
	 * 随机生成障碍方块
	 */
	public static void getZhangAiList(){
		
		//随机生成60个障碍方块
		while(zhangaiList.size() < 60){
			
			int x = ThreadLocalRandom.current().nextInt(widthLength);
			int y = ThreadLocalRandom.current().nextInt(heightLength);
			FangKuaiPosition fk = new FangKuaiPosition(x,y);
			//新生成的方块不能已存在zhangailist中，也不能喝起点/终点重合
			if(zhangaiList.contains(fk) || (cat.getX() == fk.getX()*MyPanel.size && cat.getY() == fk.getY() * MyPanel.size) 
					|| (fish.getX() == fk.getX() * MyPanel.size && fish.getY() == fk.getY() * MyPanel.size)){
				continue;
			}
			zhangaiList.add(fk);
		}
	}

}
