package fileread.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.apache.poi.poifs.filesystem.POIFSFileSystem;  

public class ExcelReader extends FileInfoReader {

	/** 
	* ��ȡxls��׺��Excel������
	* @param filePath �ļ���ȫ·��
	* @param all �Ƿ��ȡȫ������
	* @return String  ����Excel������
	*/
	public static String getTextFromXls(String filePath,boolean all) {
		StringBuffer buff = new StringBuffer();
		try {
			// ������Excel�������ļ�������
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
			// �����Թ���������á�
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					HSSFSheet aSheet = wb.getSheetAt(numSheets);// ���һ��sheet
					for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
							.getLastRowNum(); rowNumOfSheet++) {
						if (null != aSheet.getRow(rowNumOfSheet)) {
							HSSFRow aRow = aSheet.getRow(rowNumOfSheet); // ���һ����
							for (int cellNumOfRow = 0; cellNumOfRow <= aRow
									.getLastCellNum(); cellNumOfRow++) {
								if (null != aRow.getCell(cellNumOfRow)) {
									HSSFCell aCell = aRow.getCell(cellNumOfRow);// �����ֵ
									switch (aCell.getCellType()) {
									case HSSFCell.CELL_TYPE_FORMULA:
										break;
									case HSSFCell.CELL_TYPE_NUMERIC:
										buff.append(aCell.getNumericCellValue())
												.append('\t');
										break;
									case HSSFCell.CELL_TYPE_STRING:
										buff.append(aCell.getStringCellValue())
												.append('\t');
										break;
									}
								}
							}
							buff.append('\n');
							if (!all) {
								String result = buff.toString().trim();
								if (!result.equals("")) {
									return result;
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff.toString();
	}

	/** 
	* ��ȡxlsx��׺��Excel������
	* @param filePath �ļ���ȫ·��
	* @param all �Ƿ��ȡȫ������
	* @return String  ����Excel������
	*/
	public static String getTextFromXlsx(String filePath, boolean all) {
		StringBuffer content = new StringBuffer();

		InputStream is;
		// ���� XSSFWorkbook ����strPath �����ļ�·��
		XSSFWorkbook xwb;
		try {
			is = new FileInputStream(new File(filePath));
			xwb = new XSSFWorkbook(is);
			// ѭ��������Sheet
			for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
				XSSFSheet xSheet = xwb.getSheetAt(numSheet);
				if (xSheet == null) {
					continue;
				}

				// ѭ����Row
				for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
					XSSFRow xRow = xSheet.getRow(rowNum);
					if (xRow == null) {
						continue;
					}

					// ѭ����Cell
					for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
						XSSFCell xCell = xRow.getCell(cellNum);
						if (xCell == null) {
							continue;
						}

						if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
							content.append(xCell.getBooleanCellValue());
						} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
							content.append(xCell.getNumericCellValue());
						} else {
							content.append(xCell.getStringCellValue());
						}
					}
					content.append("\n");
					if (!all) {
						String result = content.toString().trim();
						if (!result.equals("")) {
							return result;
						}
					}
				}
			}
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content.toString();
	}
	
	/** 
	* ��ȡxlsx��׺��Excel��ȫ������
	* @param filePath �ļ�ȫ·��
	* @return String  ����Excel��ȫ������ 
	*/
	public static String getTextFromXlsx(String filePath) {
		return getTextFromXlsx(filePath, true);
	}
	
	/** 
	* ��ȡxls��׺��Excel��ȫ������
	* @param filePath �ļ�ȫ·��
	* @return String  ����Excel��ȫ������ 
	*/
	public static String getTextFromXls(String filePath) {
		return getTextFromXls(filePath, true);
	}
	
	/** 
	* ��ȡExcel��ȫ������
	* @param filePath �ļ���ȫ·��
	* @return String  ����Excel��ȫ������ 
	*/
	public static String getTextFromExcel(String filePath) {
		String result = null;
		if (getExtension(filePath).equalsIgnoreCase("xls")) {
			result = getTextFromXls(filePath);
		} else if (getExtension(filePath).equalsIgnoreCase("xlsx")) {
			result = getTextFromXlsx(filePath);
		}
		return result;
	}
	
	/** 
	* ��ȡxlsx��׺��Excel�ĵ�һ�� 
	* @param filePath �ļ���ȫ·��
	* @return String  ����Excel�ĵ�һ��
	*/
	public static String getFirstLineTextFromXlsx(String filePath) {
		return getTextFromXlsx(filePath,false);
	}
	
	/** 
	* ��ȡxls��׺��Excel�ĵ�һ�� 
	* @param filePath �ļ���ȫ·��
	* @return String  ����Excel�ĵ�һ��
	*/
	public static String getFirstLineTextFromXls(String filePath) {
		return getTextFromXls(filePath,false);
	}
	
	/** 
	* ��ȡExcel�ĵ�һ�� 
	* @param filePath �ļ���ȫ·��
	* @return String  ����Excel�ĵ�һ��
	*/
	public static String getFirstLineText(String filePath) {
		String result = null;
		if (getExtension(filePath).equalsIgnoreCase("xls")) {
			result = getFirstLineTextFromXls(filePath);
		} else if (getExtension(filePath).equalsIgnoreCase("xlsx")) {
			result = getFirstLineTextFromXlsx(filePath);
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println(getTextFromXlsx("files/2007.xlsx"));
		System.out.println(getTextFromExcel("files/2003.xls"));
	}

}
