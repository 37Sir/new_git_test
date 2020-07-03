package test.findway;

public class FangKuaiPosition {
public FangKuaiPosition(){}
	
	/**
	 * 根据方块坐标生成方块（这里的坐标指的是网格坐标,不是像素坐标）
	 * @param x	x方向的方块单位（即x方向像素/size）
	 * @param y	y方向的方块单位（即y方向像素/size）
	 */
	public FangKuaiPosition(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 根据方块坐标生成方块（这里的坐标指的是网格坐标）
	 * @param x	x方向的方块单位（即x方向像素/size）
	 * @param y	y方向的方块单位（即y方向像素/size）
	 * @param fk	前一个方块（父级方块）
	 */
	public FangKuaiPosition(int x,int y, FangKuaiPosition fk){
		this.x = x;
		this.y = y;
		this.previousFK = fk;
	}
	
	/**
	 * 根据jpanel生成方块
	 * @param myPpanel	mypanel对象
	 */
	public FangKuaiPosition(MyPanel myPpanel){
		this.x = myPpanel.getX() / MyPanel.size;
		this.y = myPpanel.getY() / MyPanel.size;
	}
	
	static public final int size = 50;//一个方块单位为50像素
	private int x;//x方向的方块单位（即x方向像素/size）
	private int y;//y方向的方块单位（即y方向像素/size）
	private int F;//和值，G+H
	private int G;//该点到出发点的移动量
	private int H;//该店到目的点的估算移动辆
	private FangKuaiPosition previousFK;//父节点
	public int getF() {
		return F;
	}
	public void setF(int f) {
		F = f;
	}
	public int getG() {
		return G;
	}
	public void setG(int g) {
		G = g;
	}
	public int getH() {
		return H;
	}
	public void setH(int h) {
		H = h;
	}
	public FangKuaiPosition getPreviousFK() {
		return previousFK;
	}
	public void setPreviousFK(FangKuaiPosition previousFK) {
		this.previousFK = previousFK;
	}
	
	/**
	 * 重写equals方法，判断两个网格是否相等，比较这两个网格的X和Y是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if(((FangKuaiPosition)obj).getX() == this.x && ((FangKuaiPosition)obj).getY() == this.y){
			return true;
		}else{
			return false;
		}
	}
	
	public int getX() {
		return x;
	}
 
	public void setX(int x) {
		this.x = x;
	}
 
	public int getY() {
		return y;
	}
 
	public void setY(int y) {
		this.y = y;
	}
}
