package fileread.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import fileread.util.*;

public class RightPanel extends JPanel implements ActionListener,
		DropTargetListener {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2687429764601043217L;
	private static DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton clearButton;
	private static JButton startRenameButton;
	// private JButton convertButton;
	private final JFileChooser chooser = new JFileChooser("."); // �ڵ�ǰĿ¼�£������ļ�ѡ����
	private static ArrayList<File> files = new ArrayList<File>();
	private static ArrayList<String> newfilenames = new ArrayList<String>();
	// �Ѿ�����������ת�����ļ���
	private static int renamedListSize = 0;
	private int tab;

	/**
	 * ��ʼ���Ҳ����
	 */
	public RightPanel() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		// ��������߿���ʾ�����ļ��б�
		setBorder(BorderFactory.createTitledBorder("�ļ��б�"));
		// ������ʼ��tableģ��
		final String[] columnNames = { "ԭ�ļ���", "Ԥ��", "���" };
		final Object[][] data = {};
		// ����tableģ��
		tableModel = new DefaultTableModel(data, columnNames);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(300, 300));
		// �������ڹ��������
		scrollPane = new JScrollPane(table);
		// �����������ӵ��ұߵ����
		add(scrollPane, BorderLayout.CENTER);
		// ������ť��壬�������ĸ���ť
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		addButton = new JButton("���");
		deleteButton = new JButton("�Ƴ�");
		clearButton = new JButton("���");
		startRenameButton = new JButton("������");

		addButton.setToolTipText("����ļ�");
		deleteButton.setToolTipText("ɾ���б��е�ѡ����");
		clearButton.setToolTipText("����б�");
		startRenameButton.setToolTipText("��ʼ�������ļ�");

		buttonPanel.add(startRenameButton);
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(clearButton);
		add("South", buttonPanel);
		// Ϊ�ĸ���ťע�������
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		startRenameButton.addActionListener(this);
		addButton.addActionListener(this);

		// convertButton = new JButton("ת��");
		// convertButton.addActionListener(this);

		chooser.setMultiSelectionEnabled(true);// ��ѡ
		// �����ļ�������
		FileFilter wordFilter = new FileNameExtensionFilter(
				"Word File (*.doc;*.docx)", "doc", "docx");
		FileFilter excelFilter = new FileNameExtensionFilter(
				"Excel File (*.xls;*.xlsx)", "xls", "xlsx");
		FileFilter pptFilter = new FileNameExtensionFilter(
				"PPT File (*.ppt;*.pptx)", "ppt", "pptx");
		FileFilter pdfFilter = new FileNameExtensionFilter("PDF File (*.pdf)",
				"pdf");
		chooser.addChoosableFileFilter(wordFilter); // ����word�ļ�������
		chooser.addChoosableFileFilter(excelFilter); // ����excel�ļ�������
		chooser.addChoosableFileFilter(pptFilter); // ����ppt�ļ�������
		chooser.addChoosableFileFilter(pdfFilter); // ����pdf�ļ�������

		// chooser.setFileFilter(wordFilter);
		// //����Ĭ�ϵ��ļ������������������,�������ӵ��ļ�������ΪĬ�Ϲ�����
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
	}

	public void dragOver(DropTargetDragEvent dtde) {
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	public void dragExit(DropTargetEvent dte) {
	}

	/*
	 * (�� Javadoc) <p>Title drop</p> <p>Description ���������ק�ļ������ڵ��¼�</p>
	 * 
	 * @param dtde
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent dtde) {
		try {
			// Transferable tr = dtde.getTransferable();

			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				List list = (List) (dtde.getTransferable()
						.getTransferData(DataFlavor.javaFileListFlavor));
				Iterator iterator = list.iterator();

				int oldsize = files.size();

				Integer index = 0;
				String text = LeftPanel.getNewText();
				tab = LeftPanel.getSelectedTab();
				// tab���ǵڶ���������������Ϊ��
				if (tab != 1 || text != null && !text.equals("")) {
					while (iterator.hasNext()) {
						File f = (File) iterator.next();
						// f���ļ�������Ŀ¼
						if (f.isFile()) {
							if (addFile(f, index, oldsize, text)) {
								if (tab == 1) {
									index++;
								}
								oldsize++;
							}
						}
					}
					LeftPanel.setEnabledTab(false);
				} else {
					JOptionPane.showMessageDialog(deleteButton.getRootPane(),
							"����������������!", "����", JOptionPane.WARNING_MESSAGE);
				}

				dtde.dropComplete(true);
				this.updateUI();
			} else {
				dtde.rejectDrop();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (UnsupportedFlavorException ufe) {
			ufe.printStackTrace();
		}
	}

	/*
	 * (�� Javadoc) <p>Title actionPerformed</p> <p>Description ����ť�¼�</p>
	 * 
	 * @param e
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startRenameButton) {// ��ʼ������
			int size = files.size();
			if (size == 0) {
				JOptionPane.showMessageDialog(startRenameButton.getRootPane(),
						"��ǰ�ļ��б�û���ļ����������!", "����", JOptionPane.WARNING_MESSAGE);
			} else {
				// for (int i = 0; i < renamedListSize; i++) {
				// tableModel.setValueAt(files.get(i).getName(), i, 0);
				// tableModel.setValueAt("δ�ı�", i, 2);
				// }
				for (; renamedListSize < size; renamedListSize++) {// ֻ�������ӵ��б���ļ�������
					boolean isChange = false;
					// String newfilefullname=
					// newfilenames.get(renamedListSize);
					File newfile = new File(newfilenames.get(renamedListSize));
					// String newfilename = newfile.getName();
					File oldFile = files.get(renamedListSize);
					// �¾��ļ�����ͬ����������
					if (!newfile.equals(oldFile)) {
						if (oldFile.renameTo(newfile)) {
							isChange = true;
						}
					}

					files.set(renamedListSize, newfile);
					// ���±�������
					if (isChange) {
						tableModel.setValueAt(newfile.getName(),
								renamedListSize, 0);
						tableModel.setValueAt("�ɹ�", renamedListSize, 2);
					} else {
						tableModel.setValueAt("δ�ı�", renamedListSize, 2);
					}
					LeftPanel.setEnabledTab(true);
				}
			}
		} else if (e.getSource() == clearButton) {// ����б�
			int rowCount = table.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				tableModel.removeRow(0);
			}
			// ɾ���ļ��������еĶ�Ӧ��
			files.clear();
			newfilenames.clear();
			renamedListSize = 0;// ���
			LeftPanel.setEnabledTab(true);
		} else if (e.getSource() == deleteButton) {// �Ƴ�ѡ�е��б���
			try {
				// ȡ����ѡ�����鳤��;
				int numrow = table.getSelectedRows().length;
				if (numrow == 0) {
					JOptionPane.showMessageDialog(deleteButton.getRootPane(),
							"����ѡ���ļ�!", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					int size = files.size();
					int count = size - renamedListSize;
					for (int i = 0; i < numrow; i++) {
						// ɾ����ѡ��;
						int selectedRow = table.getSelectedRow();
						tableModel.removeRow(selectedRow);
						files.remove(selectedRow);
						newfilenames.remove(selectedRow);
						if (selectedRow < renamedListSize) {// ���ɾ���������Ѿ���������
							renamedListSize--;
						} else {
							count--;
						}
						// ɾ���ļ��������еĶ�Ӧ��
					}
					// �ļ�ȫ������ջ�������ӵĲ��һ�û��������ת�����ļ����Ƴ�
					if (files.size() == 0 || count == 0) {
						LeftPanel.setEnabledTab(true);
					}
				}
			} catch (ArrayIndexOutOfBoundsException e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(deleteButton.getRootPane(),
						"����Խ��!", "����", JOptionPane.WARNING_MESSAGE);
			}

		} else if (e.getSource() == addButton) {
			tab = LeftPanel.getSelectedTab();
			if (tab == 1
					&& (LeftPanel.getNewText() == null
							|| LeftPanel.getNewText().equals("") || LeftPanel
							.getNewText().equals("�ڴ�����\"A_#\"���ļ���Ϊ\"A_<���к�>\""))) {
				JOptionPane.showMessageDialog(deleteButton.getRootPane(),
						"����������������!", "����", JOptionPane.WARNING_MESSAGE);
			} else {
				chooseFiles();
				int size = files.size();
				//����ӵ��ļ�����Ϊ0
				if (size - renamedListSize != 0) {
					LeftPanel.setEnabledTab(false);
				}
			}
		}
	}

	/**
	 * ѡ���ļ��Ի���ѡ���ļ�
	 */
	public void chooseFiles() {
		int returnVal = chooser.showOpenDialog(addButton.getRootPane()); // ��ʾ����
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			for (int i = 0; i < renamedListSize; i++) {// ��֮ǰ��ӵ���ʾδ�ı�
				tableModel.setValueAt(files.get(i).getName(), i, 0);
				tableModel.setValueAt("δ�ı�", i, 2);
			}
			addFiles();
		}
	}

	/**
	 * ��ȡ��������ӵ��ļ������ļ�������������ӵ�newfilenames�б���
	 */
	public void addFiles() {

		File[] filenames = chooser.getSelectedFiles();
		int oldsize = files.size();

		Integer index = 0;
		String text = LeftPanel.getNewText();
		for (File file : filenames) {
			if (addFile(file, index, oldsize, text)) {
				if (tab == 1) {
					index++;
				}
				oldsize++;
			}
		}
	}

	/**
	 * ���ļ�file�����ļ�����ӵ�newfilenames�б���
	 * 
	 * @param file
	 *            Ҫ��ӵ��ļ�
	 * @param index
	 *            ģʽ2���滻#�����
	 * @param oldsize
	 *            DefaultTableModelģ����Ҫ������е�������
	 * @param text
	 *            ģʽ2�������������������
	 * @return boolean
	 */
	public boolean addFile(File file, Integer index, int oldsize, String text) {
		if (!files.contains(file)) {// �Ѿ���ӵ��ļ������ظ����
			files.add(file);
			// file�ļ���ȫ·����
			String fileFullName = file.getName();
			// file�ļ�����Ŀ¼
			String fileParent = file.getParent();
			// file�ļ���׺
			String fileExt = FileInfoReader.getExtension(file);
			String newFileName = null;
			// file���ļ���(��������׺)
			String filename = fileFullName.substring(0,
					fileFullName.lastIndexOf(fileExt) - 1);
			if (tab == 0) {
				try {
					if (fileExt.equalsIgnoreCase("doc")) {
						newFileName = WordReader.getFirstLineTextFromDoc(file
								.toString());
					} else if (fileExt.equalsIgnoreCase("docx")) {
						newFileName = WordReader.getFirstLineTextFromDocx(file
								.toString());
					} else if (fileExt.equalsIgnoreCase("xls")) {
						newFileName = ExcelReader.getFirstLineTextFromXls(file
								.toString());
					} else if (fileExt.equalsIgnoreCase("xlsx")) {
						newFileName = ExcelReader.getFirstLineTextFromXlsx(file
								.toString());
					} else if (fileExt.equalsIgnoreCase("ppt")) {
						newFileName = PPTReader.getFirstLineTextFromPpt(file
								.toString());
					} else if (fileExt.equalsIgnoreCase("pptx")) {
						newFileName = PPTReader.getFirstLineTextFromPptx(file
								.toString());
					} else if (fileExt.equalsIgnoreCase("pdf")) {
						PdfReader pdfReader = new PdfReader(file.toString());
						newFileName = pdfReader.getFirstLineText();
						pdfReader.closeAll();
					} else if (fileExt.equalsIgnoreCase("txt")) {
						newFileName = TxtReader.getFirstLineText(file
								.toString());
					}
					// } catch (IOException e1) {
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					// org.apache.poi.poifs.filesystem.OfficeXmlFileException:
					// The supplied data appears to be in the Office 2007+ XML.
					// You are calling the part of POI that deals with OLE2
					// Office Documents. You need to call a different part of
					// POI to process this data (eg XSSF instead of HSSF)
					// e1.printStackTrace();
					// �е��ļ����׳��쳣
					newFileName = null;
				}
			} else if (tab == 1) {// ����������������
				newFileName = text.replaceAll("\\*", filename);
				newFileName = newFileName.replaceAll("#", (index).toString());

			} else if (tab == 2) {// ת�����ļ��ĸ�ʽΪtxt
				newFileName = filename + ".txt";
			}
			if (tab != 2) { // ��������������������һЩ����Ԥ�ϵ��������newFileNameΪ��ָ��
				if (newFileName == null) {
					newFileName = fileFullName;
				} else {
					newFileName = newFileName + "." + fileExt;
				}
			}

			// �����ļ�����ȫ·����ӵ�newfilenames�б���
			newfilenames.add(fileParent + "/" + newFileName);
			Vector<String> row = new Vector<String>();
			row.add(fileFullName);
			row.add(newFileName);
			tableModel.insertRow(oldsize, row);
			return true;// ����ļ��ɹ�������
		}
		int indexoffile = files.indexOf(file);
		tableModel.setValueAt("�ظ���Ӵ��ļ�", indexoffile, 2);
		return false;
	}

	/**
	 * �����ĵ��ж���������д��txt�ļ���
	 */
	public static void writeToTXT() {
		int size = files.size();
		if (size == 0) {
			// JOptionPane.showMessageDialog(this.getRootPane(),
			// "��ǰ�ļ��б�û���ļ����������!", "����", JOptionPane.WARNING_MESSAGE);
			JOptionPane.showMessageDialog(null, "��ǰ�ļ��б�û���ļ����������!", "����",
					JOptionPane.WARNING_MESSAGE);
		} else {
			for (; renamedListSize < size; renamedListSize++) {// ֻ�������ӵ��б���ļ�ת��
				File file = new File(newfilenames.get(renamedListSize));
				// ���֮ǰ���������Ĳ���δִ�У���������Щ,ֻ�Ժ���ӵ���Ҫת��Ϊtxt���ļ�����
				if (!WordReader.getExtension(file).equalsIgnoreCase("txt")) {
					continue;
				}
				String fileExt = WordReader.getExtension(files
						.get(renamedListSize));
				String fileText = null;
				try {
					if (fileExt.equalsIgnoreCase("doc")) {
						fileText = WordReader.getTextFromDoc(files.get(
								renamedListSize).toString());
					} else if (fileExt.equalsIgnoreCase("docx")) {
						fileText = WordReader.getTextFromDocx(files.get(
								renamedListSize).toString());
					} else if (fileExt.equalsIgnoreCase("xls")) {
						fileText = ExcelReader.getTextFromXls(files.get(
								renamedListSize).toString());
					} else if (fileExt.equalsIgnoreCase("xlsx")) {
						fileText = ExcelReader.getTextFromXlsx(files.get(
								renamedListSize).toString());
					} else if (fileExt.equalsIgnoreCase("ppt")) {
						fileText = PPTReader.getTextFromPpt(files.get(
								renamedListSize).toString());
					} else if (fileExt.equalsIgnoreCase("pptx")) {
						fileText = PPTReader.getTextFromPptx(files.get(
								renamedListSize).toString());
					} else if (fileExt.equalsIgnoreCase("pdf")) {
						PdfReader pdfReader = new PdfReader(files.get(
								renamedListSize).toString());
						fileText = pdfReader.getTextFromPdf();
						pdfReader.closeAll();
					} else if (fileExt.equalsIgnoreCase("txt")) {
						// JOptionPane.showMessageDialog(this, "txt�ļ�����ת��!");
						JOptionPane.showMessageDialog(null, "txt�ļ�����ת��!");
					}
					// } catch (IOException e1) {
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					// org.apache.poi.poifs.filesystem.OfficeXmlFileException:
					// The supplied data appears to be in the Office 2007+ XML.
					// You are calling the part of POI that deals with OLE2
					// Office Documents. You need to call a different part of
					// POI to process this data (eg XSSF instead of HSSF)
					// e1.printStackTrace();
					// �е��ļ����׳��쳣
					fileText = null;
				}
				if (fileText != null) {
					try {
						if (creatTxtFile(file)) {
							BufferedWriter br = new BufferedWriter(
									new OutputStreamWriter(
											new FileOutputStream(file)));
							br.write(fileText);
							br.flush();
							tableModel.setValueAt("�ɹ�", renamedListSize, 2);
						}

					} catch (Exception e) {
						// JOptionPane.showMessageDialog(this,"�ļ�����ת��!");
						JOptionPane.showMessageDialog(null, "�ļ�����ת��!");
					}
				}
				LeftPanel.setEnabledTab(true);
			}
		}
	}

	/**
	 * ���ð�ť void
	 */
	public static void disableButton() {
		startRenameButton.setEnabled(false);
		// startRenameButton.removeActionListener(null);
	}

	/**
	 * ���ť
	 */
	public static void enableButton() {
		startRenameButton.setEnabled(true);
		// startRenameButton.addActionListener(null);
	}

	/**
	 * ������filenameΪ�ļ������ļ�
	 * 
	 * @param filename
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean creatTxtFile(File filename) throws IOException {
		boolean flag = false;
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return flag;
	}
}
