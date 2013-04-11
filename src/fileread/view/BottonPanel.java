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
	* ��ʼ���ײ��İ�ť���
	*/
	public BottonPanel() {
		setLayout(new BorderLayout());

		buttonPanel = new JPanel();
		quitButton = new JButton("�˳�");
		aboutButton = new JButton("����");
		
		aboutButton.setToolTipText("����");
		aboutButton.addActionListener(this);
		quitButton.addActionListener(this);
		quitButton.setToolTipText("�˳���ǰ����");
		buttonPanel.add(quitButton);
		buttonPanel.add(aboutButton);

		add("East", buttonPanel);
	}

	/* (�� Javadoc) 
	* <p>Title actionPerformed</p> 
	* <p>Description �����˳��͹��ڰ�ť�¼�</p> 
	* @param e 
	* @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) 
	*/
	public void actionPerformed(ActionEvent e) {
		// �ر�����Ӧ�ó���.���ֻ������رյ�ǰ����,������
		// dispose();
		if (e.getSource() == quitButton) {
			System.exit(0);
		} else if (e.getSource() == aboutButton) { 
			JOptionPane.showMessageDialog(aboutButton.getRootPane(), 
					"<html>�ļ���������������лл����ʹ��!<br>" +
					"���������<a href=\"http:\\poi.apache.org\">Apache POI</a>" +
					"��<a href=\"http://sourceforge.net/projects/pdfbox/files/\">PDFBox</a></html>��",
					"����",JOptionPane.INFORMATION_MESSAGE);
		}
	}
} 
