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
	 * 获取路线方法入口
	 * @param cat	起点
	 * @param fish	终点
	 * @return	路线集合List<FangKuaiPosition>
	 */
	public List<FangKuaiPosition> getWayLine(MyPanel cat, MyPanel fish){
		//定义返回的结果
		List<FangKuaiPosition> wayList = new ArrayList<>();
		//中心方块的四周方块集合
		List<FangKuaiPosition> tmpList = null;
		
		//将起点和终点转换为fangkuaiposition对象
		beginFk = new FangKuaiPosition(cat);
		beginFk.setG(0);
		endFk = new FangKuaiPosition(fish);
		
		//获取中心方块（起点）四周的方块
		
		tmpList = aroundFk(beginFk);
		//如果四周没有符合条件的方块，则说明是死路
		if(tmpList == null || tmpList.size() == 0){
			return wayList;
		}
		//放入openlist中，作为向外扩散的中心方块
		BasePanel.openList.addAll(tmpList);
		
		//遍历openlist，以每个方块作为中心方块，向外扩散
		for(int i = 0; i < BasePanel.openList.size(); i++){
			//获取新的中心方块，并获取四周方块
			FangKuaiPosition tmpFk = BasePanel.openList.get(i);
			tmpList = aroundFk(tmpFk);
			
			//周围方块为空，说明 是死路，继续下一个 方块
			if(tmpList == null || tmpList.size() == 0){
				//如果openlist已经遍历完了，都没有四周方块，则要在for循环外面判断waylist是否包含终点，
				//如果不包含，则到达不了终点
				continue;
			}
			
			//如果周围方块包括终点方块，则结束寻路
			if(tmpList.contains(endFk)){
				//如果四周方块包含终点，则将终点添加到closelist中，并跳出openlist循环（已经到达终点）
				for(FangKuaiPosition obj : tmpList){
					if(obj.equals(endFk)){
						BasePanel.closedList.add(obj);
						break;
					}
				}
				break;
			}
			
			/**
			 * 将中心方块的周围方块添加到openlist集合
			 * 注意：如果openlist中已经存在，则 需要将消耗最少的方块更新到 openlist中
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
			
			//删掉openlist中的当前中心方块，继续获取并处理下一个
			BasePanel.openList.remove(i);
			i--;
		}
		
		/**
		 * 从 closedlist中获取到路线
		 * 先获取终点，然后根据fangkuaiposition.previousFk获取上一个方块，一直获取到起点
		 */
		for(int i = 0; i < BasePanel.closedList.size(); i++){
			//如果wayList<=0,说明还没有获取到第一个方块(终点)；如果wayList>0,说明已经获取到第一个方块(终点)，则不用再执行下一个if语句
			if(wayList.size() > 0){
				if(wayList.get(wayList.size() - 1).getPreviousFK().equals(BasePanel.closedList.get(i))){
					wayList.add(BasePanel.closedList.get(i));
					//如果获取到的方块是起点，则跳出for循环
					if(BasePanel.closedList.get(i).equals(beginFk)){
						break;
					}
					//获取到一个方块后，将该方块从closedlist中删除，然后从0开始遍历closedlist找到第一个方块的previousfk。
					//所以需要赋值i=-1,因为continue的时候会执行一次i++
					BasePanel.closedList.remove(BasePanel.closedList.get(i));
					i = -1;
					
				}
				continue;
			}
			
			//第一个方块为终点，获取到一个方块后，将该方块从closedlist中删除，然后从0开始遍历closedlist找到第一个方块的previousfk。
			//所以需要赋值i=-1,因为continue的时候会执行一次i++
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
	 * 获取周围方块
	 * ①判断是否超越边界
	 * ②判断是否是障碍物/已计算过的方块
	 * @param fk	中心方块 
	 * @return	周围方块结集合
	 */
	public List<FangKuaiPosition> aroundFk(FangKuaiPosition fk){
		//重置
		top = false;
		down = false;
		left = false;
		right = false;
		List<FangKuaiPosition> list = new ArrayList<FangKuaiPosition>();
		//判断上面的方块是否符合条件
		//判断是否超过越边界
		if(fk.getY() - 1 >= 0){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX(), fk.getY() - 1, fk);
			//判断是否是障碍物/已计算过的方块
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				top = true;//上方方块符合条件
			}
		}
		
		//判断下面的方块是否符合条件
		if(fk.getY() + 1 < BasePanel.heightLength){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX(), fk.getY() + 1, fk);
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				down = true;//下方方块符合条件
			}
		}
		
		//判断左面的方块是否符合条件
		if(fk.getX() - 1 >= 0){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() - 1, fk.getY(), fk);
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				left = true;//左方方块符合条件
			}
		}
		//判断右面的方块是否符合条件
		if(fk.getX() + 1 < BasePanel.widthLength){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY(), fk);
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
				right = true;//右方方块符合条件
			}
		}
		
		//左上方方块校验(左移一格不超过边界&&上移一格不超过边界&&左边方块符合条件&&上边方块符合条件，下面的类似原理)
		if(fk.getX() - 1 >= 0 && fk.getY() - 1 >= 0 && left && top){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() - 1, fk.getY() - 1, fk);
			//判断是否是障碍物/已计算过的方块
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		
		//右上方方块校验
		if(fk.getX() + 1 < BasePanel.widthLength && fk.getY() - 1 >= 0 && right && top){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY() - 1, fk);
			//判断是否是障碍物/已计算过的方块
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		
		//右下方方块校验
		if(fk.getX() + 1 < BasePanel.widthLength && fk.getY() + 1 < BasePanel.heightLength && right && down){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY() + 1, fk);
			//判断是否是障碍物/已计算过的方块
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		
		//左下方方块校验
		if(fk.getX() - 1 >= 0 && fk.getY() + 1 < BasePanel.heightLength && left && down){
			FangKuaiPosition tmpFk = new FangKuaiPosition(fk.getX() + 1, fk.getY() + 1, fk);
			//判断是否是障碍物/已计算过的方块
			if(!BasePanel.zhangaiList.contains(tmpFk) 
					&& !BasePanel.closedList.contains(tmpFk)){
				list.add(tmpFk);
			}
		}
		//将中心方块添加到已处理过的集合中
		BasePanel.closedList.add(fk);
		getFGH(list,fk);
		return list;
	}
	
	/**
	 * 给集合中的每个方块计算出FGH的值
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
	 * 获取从一个方块到另一个方块的移动量（按方块个数计算）
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
