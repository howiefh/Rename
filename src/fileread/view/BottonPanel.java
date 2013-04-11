package fileread.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BottonPanel extends JPanel implements ActionListener {
	/** 
	* @Fields serialVersionUID : TODO 
	*/ 
	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel;
	private JButton quitButton;
	private JButton aboutButton;
	/** 
	* 初始化底部的按钮面板
	*/
	public BottonPanel() {
		setLayout(new BorderLayout());

		buttonPanel = new JPanel();
		quitButton = new JButton("退出");
		aboutButton = new JButton("关于");
		
		aboutButton.setToolTipText("关于");
		aboutButton.addActionListener(this);
		quitButton.addActionListener(this);
		quitButton.setToolTipText("退出当前程序");
		buttonPanel.add(quitButton);
		buttonPanel.add(aboutButton);

		add("East", buttonPanel);
	}

	/* (非 Javadoc) 
	* <p>Title actionPerformed</p> 
	* <p>Description 处理退出和关于按钮事件</p> 
	* @param e 
	* @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) 
	*/
	public void actionPerformed(ActionEvent e) {
		// 关闭整个应用程序.如果只是是想关闭当前窗口,可以用
		// dispose();
		if (e.getSource() == quitButton) {
			System.exit(0);
		} else if (e.getSource() == aboutButton) { 
			JOptionPane.showMessageDialog(aboutButton.getRootPane(), 
					"<html>文件重命名的利器，谢谢您的使用!<br>" +
					"本软件基于<a href=\"http:\\poi.apache.org\">Apache POI</a>" +
					"和<a href=\"http://sourceforge.net/projects/pdfbox/files/\">PDFBox</a></html>。",
					"关于",JOptionPane.INFORMATION_MESSAGE);
		}
	}
} 
