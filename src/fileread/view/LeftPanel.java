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
	* ��ʼ�������  
	*/
	public LeftPanel() {
//		setBorder(BorderFactory.createTitledBorder(""));
		setLayout(new BorderLayout());
		// ����ѡ���ͼ��
		//��java jar�ļ����ж���Դ�ĳ��÷��� 
		//�������ͼƬ����images�ļ��� ,������.java�ļ�����src�ļ�������
		//�뽫images�ļ��зŵ�src�����ٴ��
		URL url=this.getClass().getResource("/images/tabimage.gif"); 
		tabimage = new ImageIcon(url);
		// �����ǹ��ܰ�ť���Ĵ�������
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		topButton = new JButton("����");
		leftButton = new JButton("���");
		
		topButton.setToolTipText("�ڶ�����ʾ��ǩ");
		leftButton.setToolTipText("�������ʾ��ǩ");
		// ����¼�������
		topButton.addActionListener(this);
		leftButton.addActionListener(this);
		// ���������ܰ�ť���빦�ܰ�ť�����
		buttonPanel.add(topButton);
		buttonPanel.add(leftButton);

		// ��ѡ����������͹��ܰ�ť�����뵽���ݴ���������
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		add("South", buttonPanel);
		add("Center", tabbedPane);

		JComponent panel1 = new JPanel();
		JComponent panel2 = new JPanel();
		JComponent panel3 = new JPanel();
		
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		convertButton = new JButton("��ʼת��");
		convertButton.addActionListener(this);
		convertButton.setToolTipText("��pdf,word,excel,ppt�ĵ�ת��Ϊtxt�ĵ�");
		panel3.add(convertButton);
		
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		URL url1=this.getClass().getResource("/images/rename.png"); 
		ImageIcon renameIcon = new ImageIcon(url1);
		JLabel label = new JLabel("<html>��ģʽ�½����ļ���һ���������ļ�</html>", renameIcon, SwingConstants.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		panel1.add(label);
		
		JLabel label2 = new JLabel("��������:");
		panel2.add(label2);
		
		textField = new JTextField("�ڴ�����\"A_#\"���ļ���Ϊ\"A_<���к�>\"",20);
		textField.addMouseListener(this);
		panel2.add(textField);
		//��ȡ��༭��������ģ��  
        Document doc = textField.getDocument();  
        //���DocumentListener������  
        doc.addDocumentListener(this);  
        
		JLabel label3 = new JLabel("<html>��ģʽ�½�����д�����������������ļ�" +
				"<br>����ļ�֮ǰ������д��������" +
				"<br>ʹ�� * ����ԭ�ļ���" +
				"<br>ʹ�� # ������ŵ�ָ��λ��</html>");
		panel2.add(label3);
		
		JLabel label4 = new JLabel("��pdf,word,excel,ppt�ĵ�ת��Ϊtxt�ĵ�");
		panel3.add(label4);
		
		//����������������ӵ�tab������
		tabbedPane.addTab("ģʽ1", tabimage, panel1, "���ļ���һ���������ļ�");
		tabbedPane.addTab("ģʽ2", tabimage, panel2, "�����������������ļ����ɼ������");
		tabbedPane.addTab("�ļ�ת��", tabimage, panel3, "�ļ�ת��");
		panel1.setSize(300, 200);
		panel2.setSize(300, 200);
		panel3.setSize(300, 200);
		tabbedPane.setPreferredSize(new Dimension(300, 300));
		tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// ������ʾ��һ��ѡ�
		tabbedPane.setSelectedIndex(0);
		//����ѡ�����ĸ�tab
		tabbedPane.addChangeListener(this);
	}
    /* (�� Javadoc) 
    * <p>Title insertUpdate</p> 
    * <p>Description ʵ��DocumentListener�ӿ���insertUpdate���� 
    * �÷������Ը����ı��������������  </p> 
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
		} //�����ı������������  
    }  
      
    /* (�� Javadoc) 
    * <p>Title removeUpdate</p> 
    * <p>Description ʵ��DocumentListener�ӿ�removeUpdate���� 
    * �÷������Ը����ı������Ƴ������ݣ����磺���ı����е��Backspace  </p> 
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
		} //�����ı������������  
    }  
      
    /* (�� Javadoc) 
    * <p>Title changedUpdate</p> 
    * <p>Description ʵ��DocumentListener�ӿ�changedUpdate���� 
    * �÷������Ը��ٵ��ı������Ѵ��ڵ����ݸı�ʱ����ȡ��Ӧ��ֵ  </p> 
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
		} //�����ı������������  
    }  
	/* (�� Javadoc) 
	* <p>Title mouseClicked</p> 
	* <p>Description �����ģʽ2���ı����һ�ε���ʱ����������Ϊ��</p> 
	* @param e 
	* @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent) 
	*/
	public void mouseClicked(MouseEvent e) {
		if (textField.getText().equals("�ڴ�����\"A_#\"���ļ���Ϊ\"A_<���к�>\"")) {
			textField.setText("");
		}
	}
	//δʹ�õ�MouseListener����
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	
	/* (�� Javadoc) 
	* <p>Title stateChanged</p> 
	* <p>Description  ����ѡ�����ĸ�tab��� </p> 
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
	* ��ȡ��ǰѡ��ı�ǩ�����
	* @return    
	* int ���ص�ǰѡ��ı�ǩ�����
	*/
	public static int getSelectedTab() {
		return selectedTab;
	}
	
	/* (�� Javadoc) 
	* <p>Title actionPerformed</p> 
	* <p>Description  ����ť�¼� </p> 
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
		// ���»���ѡ�����
		tabbedPane.revalidate();
		tabbedPane.repaint();
	}
	
	/** 
	* �������������������������
	* @return    
	* String   �������������������������
	*/
	public static String getNewText() {
		return newFileName;
	}
	
	/** 
	* ����/���ð�ť
	*/
	public static void setEnabledTab(boolean enabled) {
		int tabCount = tabbedPane.getTabCount();
		for (int i = 1; i < tabCount; i++) {
			tabbedPane.setEnabledAt((i+selectedTab)%tabCount, enabled);
		}
		// startRenameButton.removeActionListener(null);
	}
}
