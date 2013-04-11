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
	* 读取xls后缀的Excel的内容
	* @param filePath 文件的全路径
	* @param all 是否读取全部内容
	* @return String  返回Excel的内容
	*/
	public static String getTextFromXls(String filePath,boolean all) {
		StringBuffer buff = new StringBuffer();
		try {
			// 创建对Excel工作簿文件的引用
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
			// 创建对工作表的引用。
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					HSSFSheet aSheet = wb.getSheetAt(numSheets);// 获得一个sheet
					for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
							.getLastRowNum(); rowNumOfSheet++) {
						if (null != aSheet.getRow(rowNumOfSheet)) {
							HSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一个行
							for (int cellNumOfRow = 0; cellNumOfRow <= aRow
									.getLastCellNum(); cellNumOfRow++) {
								if (null != aRow.getCell(cellNumOfRow)) {
									HSSFCell aCell = aRow.getCell(cellNumOfRow);// 获得列值
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
	* 读取xlsx后缀的Excel的内容
	* @param filePath 文件的全路径
	* @param all 是否读取全部内容
	* @return String  返回Excel的内容
	*/
	public static String getTextFromXlsx(String filePath, boolean all) {
		StringBuffer content = new StringBuffer();

		InputStream is;
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb;
		try {
			is = new FileInputStream(new File(filePath));
			xwb = new XSSFWorkbook(is);
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
				XSSFSheet xSheet = xwb.getSheetAt(numSheet);
				if (xSheet == null) {
					continue;
				}

				// 循环行Row
				for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
					XSSFRow xRow = xSheet.getRow(rowNum);
					if (xRow == null) {
						continue;
					}

					// 循环列Cell
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
	* 读取xlsx后缀的Excel的全部内容
	* @param filePath 文件全路径
	* @return String  返回Excel的全部内容 
	*/
	public static String getTextFromXlsx(String filePath) {
		return getTextFromXlsx(filePath, true);
	}
	
	/** 
	* 读取xls后缀的Excel的全部内容
	* @param filePath 文件全路径
	* @return String  返回Excel的全部内容 
	*/
	public static String getTextFromXls(String filePath) {
		return getTextFromXls(filePath, true);
	}
	
	/** 
	* 读取Excel的全部内容
	* @param filePath 文件的全路径
	* @return String  返回Excel的全部内容 
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
	* 读取xlsx后缀的Excel的第一行 
	* @param filePath 文件的全路径
	* @return String  返回Excel的第一行
	*/
	public static String getFirstLineTextFromXlsx(String filePath) {
		return getTextFromXlsx(filePath,false);
	}
	
	/** 
	* 读取xls后缀的Excel的第一行 
	* @param filePath 文件的全路径
	* @return String  返回Excel的第一行
	*/
	public static String getFirstLineTextFromXls(String filePath) {
		return getTextFromXls(filePath,false);
	}
	
	/** 
	* 读取Excel的第一行 
	* @param filePath 文件的全路径
	* @return String  返回Excel的第一行
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
