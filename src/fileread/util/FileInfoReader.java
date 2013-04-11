package fileread.util;

import java.io.File;


public class FileInfoReader {

	/** 
	* ��ȡ�ַ���source�ĵ�һ���Ӵ�,����ǰ���հ׺�β���հ�(��\nΪ��),�ο�trim()
	* @param source Ҫ������ַ��� 
	* @return String  ���ش����ĵ�һ���ַ��� 
	*/
	public static String getLine(String source) {
		int st = 0;
		int off = 0;
		int count = source.length();
		char[] val = source.toCharArray();

		while ((st < count) && (val[off + st] <= ' ')) {
			st++;
		}
		int end = source.indexOf('\n', st);
		while ((st < end) && (val[off + end - 1] <= ' ')) {
			end--;
		}
		return ((st > 0) || (end < count)) ? source.substring(st, end) : source;
	}

	/** 
	* ��ȡ�ļ�f��׺��
	* @param f �ļ�������� 
	* @return String   �����ļ�f��׺��
	*/
	public static String getExtension(File f) {
		return (f != null) ? getExtension(f.getName()) : "";
	}

	/** 
	* ��ȡ�ļ�filename��׺��
	* @param filename 
	* @return String   �����ļ�f��׺��
	*/
	public static String getExtension(String filename) {
		return getExtension(filename, "");
	}

	/** 
	* ��ȡ�ļ�filename��׺��
	* @param filename
	* @param defExt
	* @return String   
	*/
	public static String getExtension(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return defExt;
	}

//	public static void main(String[] args) throws XmlException,
//			OpenXML4JException, IOException {
//		WordReader wordReader = new WordReader();
////		String string2007 = wordReader.getTextFromWord("files\\2007.docx");
////		System.out.println(string2007);
////		String string2003 = wordReader.getTextFromWord("files\\2003.doc");
////		System.out.println(string2003);
////		
//		String first2003 = wordReader.getFirstLine("files\\2003.doc");
//		System.out.println(first2003);
//		String first2007 = wordReader.getFirstLine("files\\2007.docx");
//		System.out.println(first2007);
//		
//		ExcelReader excelReader = new ExcelReader();
//		
//		String excelString = excelReader.getTextFromExcel("files\\2003.xls");
//		System.out.println(excelString);
//		
//		String title2003 = wordReader.getFirstLineText("files\\2003.doc");
//		System.out.println(title2003);
//		// try {
//		// // word 2003�� ͼƬ���ᱻ��ȡ
//		// InputStream is = new FileInputStream(new File("files\\2003.doc"));
//		// WordExtractor ex = new WordExtractor(is);// is��WORD�ļ���InputStream
//		// String[] text2003 = ex.getParagraphText();
//		// int i = 0;
//		// while (true) {
//		// String string = text2003[i++].trim();
//		// if (!string.isEmpty()) {
//		// System.out.println(text2003[i - 1]);
//		// break;
//		// }
//		// }
//		//
//		// // word 2007 ͼƬ���ᱻ��ȡ�� ����е����ݻᱻ�����ַ��������
//		// OPCPackage opcPackage = POIXMLDocument
//		// .openPackage("files\\2007.docx");
//		// POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
//		// String text2007 = extractor.getText();
//		// System.out.println(text2007);
//		//
//		// } catch (Exception e) {
//		// e.printStackTrace();
//		// }
//	}
}



