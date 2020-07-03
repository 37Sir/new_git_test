package test.findway;

import java.util.ArrayList;
import java.util.List;

public class AutoFindWay {
	public static FangKuaiPosition beginFk = null;
	public static FangKuaiPosition endFk = null;
	
	

	private static boolean top = false;
	private static boolean down = false;
	private static boolean left = false;
	private static boolean right = false;

	public static void main(String[] args) {
		AutoFindWay afw = new AutoFindWay();
		MyPanel cat = new MyPanel(4,6);
		MyPanel fish = new MyPanel(10,10);
		afw.getWayLine(cat,fish);
	}
	
	/**
	 * ��ȡ·�߷������
	 * @param cat	���
	 * @param fish	�յ�
	 * @return	·�߼���List<FangKuaiPosition>
	 */
	public List<FangKuaiPosition> getWayLine(MyPanel cat, MyPanel fish){
		//���巵�صĽ��
		List<FangKuaiPosition> wayList = new ArrayList<>();
		//���ķ�������ܷ��鼯��
		List<FangKuaiPosition> tmpList = null;
		
		//�������յ�ת��Ϊfangkuaiposition����
		beginFk = new FangKuaiPosition(cat);
		beginFk.setG(0);
		endFk = new FangKuaiPosition(fish);
		
		//��ȡ���ķ��飨��㣩���ܵķ���
		
		tmpList = aroundFk(beginFk);
		//�������û�з��������ķ��飬��˵������·
		if(tmpList == null || tmpList.size() == 0){
			return wayList;
		}
		//����openlist�У���Ϊ������ɢ�����ķ���
		BasePanel.openList.addAll(tmpList);
		
		//����openlist����ÿ��������Ϊ���ķ��飬������ɢ
		for(int i = 0; i < BasePanel.openList.size(); i++){
			//��ȡ�µ����ķ��飬����ȡ���ܷ���
			FangKuaiPosition tmpFk = BasePanel.openList.get(i);
			tmpList = aroundFk(tmpFk);
			
			//��Χ����Ϊ�գ�˵�� ����·��������һ�� ����
			if(tmpList == null || tmpList.size() == 0){
				//���openlist�Ѿ��������ˣ���û�����ܷ��飬��Ҫ��forѭ�������ж�waylist�Ƿ�����յ㣬
				//������������򵽴ﲻ���յ�
				continue;
			}
			
			//�����Χ��������յ㷽�飬�����Ѱ·
			if(tmpList.contains(endFk)){
				//������ܷ�������յ㣬���յ���ӵ�closelist�У�������openlistѭ�����Ѿ������յ㣩
				for(FangKuaiPosition obj : tmpList){
					if(obj.equals(endFk)){
						BasePanel.closedList.add(obj);
						break;
					}
				}
				break;
			}
			
			/**
			 * �����ķ������Χ������ӵ�openlist����
			 * ע�⣺���openlist���Ѿ����ڣ��� ��Ҫ���������ٵķ�����µ� openlist��
			 */
			for(FangKuaiPosition fk : tmpList){
				if(BasePanel.openList.contains(fk)){
					for(FangKuaiPosition openFk : BasePanel.openList){
						if(openFk.equals(fk)){
							if(openFk.getG() > fk.getG()){
								openFk.setG(fk.getG());
								openFk.setF(openFk.getG() + openFk.getH());
								openFk.setPreviousFK(fk.getPreviousFK());
								break;
							}
						}
					}
				}else{
					BasePanel.openList.add(fk);
				}
			}
			
			//ɾ��openlist�еĵ�ǰ���ķ��飬������ȡ��������һ��
			BasePanel.openList.remove(i);
			i--;
		}
		
		/**
		 * �� closedlist�л�ȡ��·��
		 * �Ȼ�ȡ�յ㣬Ȼ�����fangkuaiposition.previousFk��ȡ��һ�����飬һֱ��ȡ�����
		 */
		for(int i = 0; i < BasePanel.closedList.size(); i++){
			//���wayList<=0,˵����û�л�ȡ����һ������(�յ�)�����wayList>0,˵���Ѿ���ȡ����һ������(�յ�)��������ִ����һ��if���
			if(wayList.size() > 0){
				if(wayList.get(wayList.size() - 1).getPreviousFK().equals(BasePanel.closedList.get(i))){
					wayList.add(BasePanel.closedList.get(i));
					//�����ȡ���ķ�������㣬������forѭ��
					if(BasePanel.closedList.get(i).equals(beginFk)){
						break;
					}
					//��ȡ��һ������󣬽��÷����closedlist��ɾ����Ȼ���0��ʼ����closedlist�ҵ���һ�������previousfk��
					//������Ҫ��ֵi=-1,��Ϊcontinue��ʱ���ִ��һ��i++
					BasePanel.closedList.remove(BasePanel.closedList.get(i));
					i = -1;
					
				}
				continue;
			}
			
			//��һ������Ϊ�յ㣬��ȡ��һ������󣬽��÷����closedlist��ɾ����Ȼ���0��ʼ����closedlist�ҵ���һ�������previousfk��
			//������Ҫ��ֵi=-1,��Ϊcontinue��ʱ���ִ��һ��i++
			if(BasePanel.closedList.get(i).equals(endFk)){
				wayList.add(BasePanel.closedList.get(i));
				BasePanel.closedList.remove(BasePanel.closedList.get(i));
				i = -1;
				continue;
			}
		}
		
		return wayList;
	}
	
	/**
	 * ��ȡ��Χ����
	 * ���ж��Ƿ�Խ�߽�
	 * ���ж��Ƿ����ϰ���/�Ѽ�����ķ���
	 * @param fk	���ķ��� 
	 * @return	��Χ����Ἧ��
	 */
	public List<FangKuaiPosition> aroundFk(FangKuaiPosition fk){
		//����
		top = false;
		down = false;
		left = false;
		right = false;
		List<FangKuaiPosition> list = new ArrayList<FangKuaiPosition>();
		//�ж�����ķ����Ƿ��������
		//�ж��Ƿ񳬹�Խ�߽�
		if(fk.getY() - 1 >= 0){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX(), fk.getY() - 1, fk);
			//�ж��Ƿ����ϰ���/�Ѽ�����ķ���
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				top = true;//�Ϸ������������
			}
		}
		
		//�ж�����ķ����Ƿ��������
		if(fk.getY() + 1 < BasePanel.heightLength){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX(), fk.getY() + 1, fk);
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				down = true;//�·������������
			}
		}
		
		//�ж�����ķ����Ƿ��������
		if(fk.getX() - 1 >= 0){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() - 1, fk.getY(), fk);
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				left = true;//�󷽷����������
			}
		}
		//�ж�����ķ����Ƿ��������
		if(fk.getX() + 1 < BasePanel.widthLength){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY(), fk);
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				right = true;//�ҷ������������
			}
		}
		
		//���Ϸ�����У��(����һ�񲻳����߽�&&����һ�񲻳����߽�&&��߷����������&&�ϱ߷���������������������ԭ��)
		if(fk.getX() - 1 >= 0 && fk.getY() - 1 >= 0 && left && top){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() - 1, fk.getY() - 1, fk);
			//�ж��Ƿ����ϰ���/�Ѽ�����ķ���
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		
		//���Ϸ�����У��
		if(fk.getX() + 1 < BasePanel.widthLength && fk.getY() - 1 >= 0 && right && top){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY() - 1, fk);
			//�ж��Ƿ����ϰ���/�Ѽ�����ķ���
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		
		//���·�����У��
		if(fk.getX() + 1 < BasePanel.widthLength && fk.getY() + 1 < BasePanel.heightLength && right && down){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY() + 1, fk);
			//�ж��Ƿ����ϰ���/�Ѽ�����ķ���
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		
		//���·�����У��
		if(fk.getX() - 1 >= 0 && fk.getY() + 1 < BasePanel.heightLength && left && down){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY() + 1, fk);
			//�ж��Ƿ����ϰ���/�Ѽ�����ķ���
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		//�����ķ�����ӵ��Ѵ�����ļ�����
		BasePanel.closedList.add(fk);
		getFGH(list,fk);
		return list;
	}
	
	/**
	 * �������е�ÿ����������FGH��ֵ
	 * @param list
	 */
	public void getFGH(List<FangKuaiPosition> list,FangKuaiPosition currFk){
		if(list != null && list.size() > 0){
			for(FangKuaiPosition fk : list){
				fk.setG(currFk.getG() + 1);
				fk.setH(toGetH(fk,endFk));
				fk.setF(fk.getG() + fk.getH());
			}
		}
	}
	
	/**
	 * ��ȡ��һ�����鵽��һ��������ƶ�����������������㣩
	 * @param currentFangKuai
	 * @param targetFangKuai
	 * @return
	 */
	public int toGetH(FangKuaiPosition currentFangKuai,FangKuaiPosition targetFangKuai){
		int h = 0;
		h += Math.abs(currentFangKuai.getX() - targetFangKuai.getX());
		h += Math.abs(currentFangKuai.getY() - targetFangKuai.getY());
		return h;
	}
}
