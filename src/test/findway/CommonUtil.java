package test.findway;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CommonUtil {
	/**
	 * ��ȡ��Ļ�ߴ�
	 * @return	Dimension    
	 * Dimension.getWidth()     
	 * Dimension.getHeight()
	 */
	public static Dimension getScreenSize(){
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	/**
	 * ��windows���չʾ
	 */
	public static void windowsStyle(){
		String windows="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		 try {
			UIManager.setLookAndFeel(windows);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
