package test.findway;

public class FangKuaiPosition {
public FangKuaiPosition(){}
	
	/**
	 * ���ݷ����������ɷ��飨���������ָ������������,�����������꣩
	 * @param x	x����ķ��鵥λ����x��������/size��
	 * @param y	y����ķ��鵥λ����y��������/size��
	 */
	public FangKuaiPosition(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * ���ݷ����������ɷ��飨���������ָ�����������꣩
	 * @param x	x����ķ��鵥λ����x��������/size��
	 * @param y	y����ķ��鵥λ����y��������/size��
	 * @param fk	ǰһ�����飨�������飩
	 */
	public FangKuaiPosition(int x,int y, FangKuaiPosition fk){
		this.x = x;
		this.y = y;
		this.previousFK = fk;
	}
	
	/**
	 * ����jpanel���ɷ���
	 * @param myPpanel	mypanel����
	 */
	public FangKuaiPosition(MyPanel myPpanel){
		this.x = myPpanel.getX() / MyPanel.size;
		this.y = myPpanel.getY() / MyPanel.size;
	}
	
	static public final int size = 50;//һ�����鵥λΪ50����
	private int x;//x����ķ��鵥λ����x��������/size��
	private int y;//y����ķ��鵥λ����y��������/size��
	private int F;//��ֵ��G+H
	private int G;//�õ㵽��������ƶ���
	private int H;//�õ굽Ŀ�ĵ�Ĺ����ƶ���
	private FangKuaiPosition previousFK;//���ڵ�
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
	 * ��дequals�������ж����������Ƿ���ȣ��Ƚ������������X��Y�Ƿ����
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
