package fileread.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class LeftPanel extends JPanel implements ActionListener,ChangeListener
,MouseListener,DocumentListener{
	/** 
	* @Fields serialVersionUID : TODO 
	*/ 
	private static final long serialVersionUID = 1L;
	private ImageIcon tabimage;
	static private JTabbedPane tabbedPane;
	private JButton topButton;
	private JButton leftButton;
	private JButton convertButton;
	private JTextField textField;
	private static String newFileName = null;
//	private ArrayList<String> newfilenames = new ArrayList<String>();
	private static int selectedTab = 0;
	/** 
	* 初始化左部面板  
	*/
	public LeftPanel() {
//		setBorder(BorderFactory.createTitledBorder(""));
		setLayout(new BorderLayout());
		// 创建选项卡的图标
		//从java jar文件包中读资源的常用方法 
		//假设你的图片放在images文件夹 ,如果你的.java文件都在src文件夹下面
		//请将images文件夹放到src下面再打包
		URL url=this.getClass().getResource("/images/tabimage.gif"); 
		tabimage = new ImageIcon(url);
		// 下面是功能按钮面板的创建过程
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		topButton = new JButton("顶部");
		leftButton = new JButton("左边");
		
		topButton.setToolTipText("在顶部显示标签");
		leftButton.setToolTipText("在左边显示标签");
		// 添加事件监听器
		topButton.addActionListener(this);
		leftButton.addActionListener(this);
		// 把两个功能按钮加入功能按钮面板中
		buttonPanel.add(topButton);
		buttonPanel.add(leftButton);

		// 把选项卡窗格容器和功能按钮面板加入到内容窗格容器中
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		add("South", buttonPanel);
		add("Center", tabbedPane);

		JComponent panel1 = new JPanel();
		JComponent panel2 = new JPanel();
		JComponent panel3 = new JPanel();
		
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		convertButton = new JButton("开始转换");
		convertButton.addActionListener(this);
		convertButton.setToolTipText("将pdf,word,excel,ppt文档转换为txt文档");
		panel3.add(convertButton);
		
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		URL url1=this.getClass().getResource("/images/rename.png"); 
		ImageIcon renameIcon = new ImageIcon(url1);
		JLabel label = new JLabel("<html>此模式下将以文件第一行重命名文件</html>", renameIcon, SwingConstants.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		panel1.add(label);
		
		JLabel label2 = new JLabel("命名规则:");
		panel2.add(label2);
		
		textField = new JTextField("在此输入\"A_#\"则文件名为\"A_<序列号>\"",20);
		textField.addMouseListener(this);
		panel2.add(textField);
		//获取与编辑器关联的模型  
        Document doc = textField.getDocument();  
        //添加DocumentListener监听器  
        doc.addDocumentListener(this);  
        
		JLabel label3 = new JLabel("<html>此模式下将以填写的命名规则重命名文件" +
				"<br>添加文件之前请先填写命名规则" +
				"<br>使用 * 插入原文件名" +
				"<br>使用 # 插入序号到指定位置</html>");
		panel2.add(label3);
		
		JLabel label4 = new JLabel("将pdf,word,excel,ppt文档转换为txt文档");
		panel3.add(label4);
		
		//将两个窗格容器添加到tab窗格中
		tabbedPane.addTab("模式1", tabimage, panel1, "以文件第一行重命名文件");
		tabbedPane.addTab("模式2", tabimage, panel2, "用命名规则重命名文件，可加入序号");
		tabbedPane.addTab("文件转换", tabimage, panel3, "文件转换");
		panel1.setSize(300, 200);
		panel2.setSize(300, 200);
		panel3.setSize(300, 200);
		tabbedPane.setPreferredSize(new Dimension(300, 300));
		tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// 设置显示第一个选项卡
		tabbedPane.setSelectedIndex(0);
		//监听选择了哪个tab
		tabbedPane.addChangeListener(this);
	}
    /* (非 Javadoc) 
    * <p>Title insertUpdate</p> 
    * <p>Description 实现DocumentListener接口中insertUpdate方法 
    * 该方法可以跟踪文本框中输入的内容  </p> 
    * @param e 
    * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent) 
    */
    public void insertUpdate(DocumentEvent e) {  
        Document doc = e.getDocument();  
        try {
        	newFileName = doc.getText(0, doc.getLength());
//			System.out.println(s);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //返回文本框输入的内容  
    }  
      
    /* (非 Javadoc) 
    * <p>Title removeUpdate</p> 
    * <p>Description 实现DocumentListener接口removeUpdate方法 
    * 该方法可以跟踪文本框中移除的内容，例如：在文本框中点击Backspace  </p> 
    * @param e 
    * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent) 
    */
    public void removeUpdate(DocumentEvent e) {  
        Document doc = e.getDocument();  
        try {
        	newFileName = doc.getText(0, doc.getLength());
//			System.out.println(s);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //返回文本框输入的内容  
    }  
      
    /* (非 Javadoc) 
    * <p>Title changedUpdate</p> 
    * <p>Description 实现DocumentListener接口changedUpdate方法 
    * 该方法可以跟踪当文本框中已存在的内容改变时，获取相应的值  </p> 
    * @param e 
    * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent) 
    */
    public void changedUpdate(DocumentEvent e) {  
        Document doc = e.getDocument();  
        try {
        	newFileName = doc.getText(0, doc.getLength());
//			System.out.println(s);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //返回文本框输入的内容  
    }  
	/* (非 Javadoc) 
	* <p>Title mouseClicked</p> 
	* <p>Description 鼠标在模式2的文本框第一次单击时将其内容设为空</p> 
	* @param e 
	* @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent) 
	*/
	public void mouseClicked(MouseEvent e) {
		if (textField.getText().equals("在此输入\"A_#\"则文件名为\"A_<序列号>\"")) {
			textField.setText("");
		}
	}
	//未使用的MouseListener方法
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	
	/* (非 Javadoc) 
	* <p>Title stateChanged</p> 
	* <p>Description  监听选择了哪个tab面板 </p> 
	* @param e 
	* @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent) 
	*/
	public void stateChanged(ChangeEvent e) {
		selectedTab = tabbedPane.getSelectedIndex();
		if (selectedTab == 2) {
			RightPanel.disableButton();
		} else {
			RightPanel.enableButton();
		}
	}
	
	/** 
	* 获取当前选择的标签的序号
	* @return    
	* int 返回当前选择的标签的序号
	*/
	public static int getSelectedTab() {
		return selectedTab;
	}
	
	/* (非 Javadoc) 
	* <p>Title actionPerformed</p> 
	* <p>Description  处理按钮事件 </p> 
	* @param e 
	* @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) 
	*/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == topButton) {
			tabbedPane.setTabPlacement(SwingConstants.TOP);
		} else if (e.getSource() == leftButton) {
			tabbedPane.setTabPlacement(SwingConstants.LEFT);
		} else if (e.getSource() == convertButton) {
			RightPanel.writeToTXT();
		}
		// 重新绘制选项卡容器
		tabbedPane.revalidate();
		tabbedPane.repaint();
	}
	
	/** 
	* 返回在命名规则中输入的内容
	* @return    
	* String   返回在命名规则中输入的内容
	*/
	public static String getNewText() {
		return newFileName;
	}
	
	/** 
	* 禁用/启用按钮
	*/
	public static void setEnabledTab(boolean enabled) {
		int tabCount = tabbedPane.getTabCount();
		for (int i = 1; i < tabCount; i++) {
			tabbedPane.setEnabledAt((i+selectedTab)%tabCount, enabled);
		}
		// startRenameButton.removeActionListener(null);
	}
}
