package fileread.view;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

public class RenameWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	/** 
	* 初始化主窗口
	* @param title 
	*/
	public RenameWindow(String title) {
		// 设置窗口属性
		final int WIDTH = 650;
		final int HEIGHT = 450;
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		final int LEFT = (screen.width - WIDTH) / 2;
		final int TOP = (screen.height - HEIGHT) / 2;
		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("contentPane = " + this.getContentPane().getClass());
//		System.out.println("contentPane = " + this.getRootPane().getClass());
		this.setLocation(LEFT, TOP);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle(title);
		URL url = rootPane.getClass().getResource("/images/renameicon.png");
		ImageIcon imageIcon = new ImageIcon(url);
		Image image = imageIcon.getImage();
		// 设置图标
		this.setIconImage(image);
		
		setResizable(false);// 禁用最大化

		RightPanel rightPanel = new RightPanel();
		LeftPanel leftPanel = new LeftPanel();
		this.add("West", leftPanel);
//		this.add("East", rightPanel);
		this.add("Center", rightPanel);

		BottonPanel bottonPanel = new BottonPanel();
		this.add("South", bottonPanel);
		// 添加组件

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
