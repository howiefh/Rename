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
	private final JFileChooser chooser = new JFileChooser("."); // 在当前目录下，创建文件选择器
	private static ArrayList<File> files = new ArrayList<File>();
	private static ArrayList<String> newfilenames = new ArrayList<String>();
	// 已经被重命名或被转换的文件数
	private static int renamedListSize = 0;
	private int tab;

	/**
	 * 初始化右部面板
	 */
	public RightPanel() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		// 创建标题边框显示这是文件列表
		setBorder(BorderFactory.createTitledBorder("文件列表"));
		// 用来初始化table模型
		final String[] columnNames = { "原文件名", "预览", "结果" };
		final Object[][] data = {};
		// 创建table模型
		tableModel = new DefaultTableModel(data, columnNames);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(300, 300));
		// 将表格放在滚动面板中
		scrollPane = new JScrollPane(table);
		// 将滚动面板添加的右边的面板
		add(scrollPane, BorderLayout.CENTER);
		// 创建按钮面板，并创建四个按钮
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		addButton = new JButton("添加");
		deleteButton = new JButton("移除");
		clearButton = new JButton("清空");
		startRenameButton = new JButton("重命名");

		addButton.setToolTipText("添加文件");
		deleteButton.setToolTipText("删除列表中的选中项");
		clearButton.setToolTipText("清空列表");
		startRenameButton.setToolTipText("开始重命名文件");

		buttonPanel.add(startRenameButton);
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(clearButton);
		add("South", buttonPanel);
		// 为四个按钮注册监听器
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		startRenameButton.addActionListener(this);
		addButton.addActionListener(this);

		// convertButton = new JButton("转换");
		// convertButton.addActionListener(this);

		chooser.setMultiSelectionEnabled(true);// 多选
		// 创建文件过滤器
		FileFilter wordFilter = new FileNameExtensionFilter(
				"Word File (*.doc;*.docx)", "doc", "docx");
		FileFilter excelFilter = new FileNameExtensionFilter(
				"Excel File (*.xls;*.xlsx)", "xls", "xlsx");
		FileFilter pptFilter = new FileNameExtensionFilter(
				"PPT File (*.ppt;*.pptx)", "ppt", "pptx");
		FileFilter pdfFilter = new FileNameExtensionFilter("PDF File (*.pdf)",
				"pdf");
		chooser.addChoosableFileFilter(wordFilter); // 加载word文件过滤器
		chooser.addChoosableFileFilter(excelFilter); // 加载excel文件过滤器
		chooser.addChoosableFileFilter(pptFilter); // 加载ppt文件过滤器
		chooser.addChoosableFileFilter(pdfFilter); // 加载pdf文件过滤器

		// chooser.setFileFilter(wordFilter);
		// //设置默认的文件管理器。如果不设置,则最后添加的文件过滤器为默认过滤器
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
	 * (非 Javadoc) <p>Title drop</p> <p>Description 处理鼠标拖拽文件到窗口的事件</p>
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
				// tab不是第二个，或命名规则不为空
				if (tab != 1 || text != null && !text.equals("")) {
					while (iterator.hasNext()) {
						File f = (File) iterator.next();
						// f是文件而不是目录
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
							"请先输入命名规则!", "警告", JOptionPane.WARNING_MESSAGE);
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
	 * (非 Javadoc) <p>Title actionPerformed</p> <p>Description 处理按钮事件</p>
	 * 
	 * @param e
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startRenameButton) {// 开始重命名
			int size = files.size();
			if (size == 0) {
				JOptionPane.showMessageDialog(startRenameButton.getRootPane(),
						"当前文件列表没有文件，请先添加!", "警告", JOptionPane.WARNING_MESSAGE);
			} else {
				// for (int i = 0; i < renamedListSize; i++) {
				// tableModel.setValueAt(files.get(i).getName(), i, 0);
				// tableModel.setValueAt("未改变", i, 2);
				// }
				for (; renamedListSize < size; renamedListSize++) {// 只对新增加到列表的文件重命名
					boolean isChange = false;
					// String newfilefullname=
					// newfilenames.get(renamedListSize);
					File newfile = new File(newfilenames.get(renamedListSize));
					// String newfilename = newfile.getName();
					File oldFile = files.get(renamedListSize);
					// 新旧文件名不同，则重命名
					if (!newfile.equals(oldFile)) {
						if (oldFile.renameTo(newfile)) {
							isChange = true;
						}
					}

					files.set(renamedListSize, newfile);
					// 更新表中数据
					if (isChange) {
						tableModel.setValueAt(newfile.getName(),
								renamedListSize, 0);
						tableModel.setValueAt("成功", renamedListSize, 2);
					} else {
						tableModel.setValueAt("未改变", renamedListSize, 2);
					}
					LeftPanel.setEnabledTab(true);
				}
			}
		} else if (e.getSource() == clearButton) {// 清空列表
			int rowCount = table.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				tableModel.removeRow(0);
			}
			// 删除文件名数组中的对应项
			files.clear();
			newfilenames.clear();
			renamedListSize = 0;// 清空
			LeftPanel.setEnabledTab(true);
		} else if (e.getSource() == deleteButton) {// 移除选中的列表项
			try {
				// 取得所选行数组长度;
				int numrow = table.getSelectedRows().length;
				if (numrow == 0) {
					JOptionPane.showMessageDialog(deleteButton.getRootPane(),
							"请先选择文件!", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					int size = files.size();
					int count = size - renamedListSize;
					for (int i = 0; i < numrow; i++) {
						// 删除所选行;
						int selectedRow = table.getSelectedRow();
						tableModel.removeRow(selectedRow);
						files.remove(selectedRow);
						newfilenames.remove(selectedRow);
						if (selectedRow < renamedListSize) {// 如果删除的项是已经重命名的
							renamedListSize--;
						} else {
							count--;
						}
						// 删除文件名数组中的对应项
					}
					// 文件全部被清空或者新添加的并且还没重命名或转换的文件被移除
					if (files.size() == 0 || count == 0) {
						LeftPanel.setEnabledTab(true);
					}
				}
			} catch (ArrayIndexOutOfBoundsException e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(deleteButton.getRootPane(),
						"数组越界!", "警告", JOptionPane.WARNING_MESSAGE);
			}

		} else if (e.getSource() == addButton) {
			tab = LeftPanel.getSelectedTab();
			if (tab == 1
					&& (LeftPanel.getNewText() == null
							|| LeftPanel.getNewText().equals("") || LeftPanel
							.getNewText().equals("在此输入\"A_#\"则文件名为\"A_<序列号>\""))) {
				JOptionPane.showMessageDialog(deleteButton.getRootPane(),
						"请先输入命名规则!", "警告", JOptionPane.WARNING_MESSAGE);
			} else {
				chooseFiles();
				int size = files.size();
				//新添加的文件数不为0
				if (size - renamedListSize != 0) {
					LeftPanel.setEnabledTab(false);
				}
			}
		}
	}

	/**
	 * 选择文件对话框选择文件
	 */
	public void chooseFiles() {
		int returnVal = chooser.showOpenDialog(addButton.getRootPane()); // 显示窗口
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			for (int i = 0; i < renamedListSize; i++) {// 对之前添加的显示未改变
				tableModel.setValueAt(files.get(i).getName(), i, 0);
				tableModel.setValueAt("未改变", i, 2);
			}
			addFiles();
		}
	}

	/**
	 * 获取所有新添加的文件的新文件名，并将其添加到newfilenames列表中
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
	 * 将文件file的新文件名添加到newfilenames列表中
	 * 
	 * @param file
	 *            要添加的文件
	 * @param index
	 *            模式2，替换#的序号
	 * @param oldsize
	 *            DefaultTableModel模型中要插入的行的行索引
	 * @param text
	 *            模式2命名规则中填入的内容
	 * @return boolean
	 */
	public boolean addFile(File file, Integer index, int oldsize, String text) {
		if (!files.contains(file)) {// 已经添加的文件不再重复添加
			files.add(file);
			// file文件的全路径名
			String fileFullName = file.getName();
			// file文件所在目录
			String fileParent = file.getParent();
			// file文件后缀
			String fileExt = FileInfoReader.getExtension(file);
			String newFileName = null;
			// file的文件名(不包括后缀)
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
					// 有的文件会抛出异常
					newFileName = null;
				}
			} else if (tab == 1) {// 用命名规则重命名
				newFileName = text.replaceAll("\\*", filename);
				newFileName = newFileName.replaceAll("#", (index).toString());

			} else if (tab == 2) {// 转化后文件的格式为txt
				newFileName = filename + ".txt";
			}
			if (tab != 2) { // 对于重命名，处理由于一些不可预料的情况导致newFileName为空指针
				if (newFileName == null) {
					newFileName = fileFullName;
				} else {
					newFileName = newFileName + "." + fileExt;
				}
			}

			// 将新文件名的全路径添加到newfilenames列表中
			newfilenames.add(fileParent + "/" + newFileName);
			Vector<String> row = new Vector<String>();
			row.add(fileFullName);
			row.add(newFileName);
			tableModel.insertRow(oldsize, row);
			return true;// 添加文件成功返回真
		}
		int indexoffile = files.indexOf(file);
		tableModel.setValueAt("重复添加此文件", indexoffile, 2);
		return false;
	}

	/**
	 * 将从文档中读出的内容写入txt文件中
	 */
	public static void writeToTXT() {
		int size = files.size();
		if (size == 0) {
			// JOptionPane.showMessageDialog(this.getRootPane(),
			// "当前文件列表没有文件，请先添加!", "警告", JOptionPane.WARNING_MESSAGE);
			JOptionPane.showMessageDialog(null, "当前文件列表没有文件，请先添加!", "警告",
					JOptionPane.WARNING_MESSAGE);
		} else {
			for (; renamedListSize < size; renamedListSize++) {// 只对新增加到列表的文件转换
				File file = new File(newfilenames.get(renamedListSize));
				// 如果之前的重命名的操作未执行，则跳过这些,只对后添加的需要转换为txt的文件操作
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
						// JOptionPane.showMessageDialog(this, "txt文件不需转换!");
						JOptionPane.showMessageDialog(null, "txt文件不需转换!");
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
					// 有的文件会抛出异常
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
							tableModel.setValueAt("成功", renamedListSize, 2);
						}

					} catch (Exception e) {
						// JOptionPane.showMessageDialog(this,"文件不能转换!");
						JOptionPane.showMessageDialog(null, "文件不能转换!");
					}
				}
				LeftPanel.setEnabledTab(true);
			}
		}
	}

	/**
	 * 禁用按钮 void
	 */
	public static void disableButton() {
		startRenameButton.setEnabled(false);
		// startRenameButton.removeActionListener(null);
	}

	/**
	 * 激活按钮
	 */
	public static void enableButton() {
		startRenameButton.setEnabled(true);
		// startRenameButton.addActionListener(null);
	}

	/**
	 * 创建以filename为文件名的文件
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
