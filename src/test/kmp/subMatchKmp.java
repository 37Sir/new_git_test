package test.kmp;

public class subMatchKmp {
		
		public static void main(String[] args) {
			String s = "abababbcdfgr";
			String t = "cdfl";
			System.out.println(matchSubStr(s,t));
		}
		/**
		 * kmp�㷨���߼�
		 * @param s
		 * @param t
		 * @return
		 */
		public static int matchSubStr(String s, String t) {
			char[] s_arr = s.toCharArray();
			char[] t_arr =t.toCharArray();
			int j =0,i=0;
			int[] next =getNext(t);
			while (i < s.length() && j < t.length()) {
				//���j�ǳ�ʼλ�û������ַ���ȣ�������һλ
				if (j == -1 || s_arr[i] == t_arr[j]) {
					i++;
					j++;
				} else {
					//����ȣ���ȡ��ƫ����
					j = next[j];
				}
			}
			if (j>= t.length()) {
				return (i -j);
			}
			return 0;			
		}
		
		/**
		 * kmp�㷨Next�������ȡ
		 * @param t
		 * @return
		 */
		public static int[] getNext(String t) {
			int[] next = new int[t.length()];
			char[] t_arr = t.toCharArray();
			next[0] = -1;
			next[1] = 0;
			int k;
			for (int j=2;j<t.length(); j++) {
				k = next[j-1];
				//�жϵ��ʼ��˵��û����
				while (k!=-1) {
					//k��ʾ֮ǰ����ƥ�䴮�������������Ϊ+1��ֱ�ӽ���
					if (t_arr[j-1] == t_arr[k]) {
						next[j] = k+1;
						break;
					} else {
						//�õ���С���ִ�
						k = next[k];
					}
					next[j] = 0;
				}
			}
			return next;
		}
}
