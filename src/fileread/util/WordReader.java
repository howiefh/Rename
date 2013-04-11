package fileread.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLPropertiesTextExtractor;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

public class WordReader extends FileInfoReader {
	public WordReader() {
	}
	
	/** 
	* 获取doc的word文件的内容
	* @param filePath 文件的全路径
	* @return  
	* String   返回文件的内容  
	*/
	public static String getTextFromDoc(String filePath) {
		String result = null;
		File file = new File(filePath);
		try {
			FileInputStream fis = new FileInputStream(file);
			WordExtractor wordExtractor = new WordExtractor(fis);
			result = wordExtractor.getText();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/** 
	* 获取docx的word文件的内容
	* @param filePath 文件的全路径
	* @return    
	* String  返回文件的内容 
	*/
	public static  String getTextFromDocx(String filePath) {
		String result = null;
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
			POIXMLTextExtractor extractor = new XWPFWordExtractor(
					opcPackage);
			result = extractor.getText();
			opcPackage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/** 
	* 读出的Word(docx/doc)的内容
	* @param filePath 文件的全路径
	* @return    
	* String  返回文件的内容 
	*/
	public static  String getTextFromWord(String filePath) {
		String result = null;
		if (getExtension(filePath).equalsIgnoreCase("doc")) {
			result = getTextFromDoc(filePath);
		} else if (getExtension(filePath).equalsIgnoreCase("docx")) {
			result = getTextFromDocx(filePath);
		}
		return result;
	}

	/** 
	* 获取word文档的第一行内容,效率较低，需要先整个读文件的全部内容，然后再调用getLine方法获取第一行内容
	* @param filePath 文件的全路经
	* @return    
	* String  返回第一行内容 
	*/
	public String getFirstLine(String filePath) {
		String result;
		result = getTextFromWord(filePath);
		result = getLine(result);
		return result;

	}
	
	/** 
	* 获取doc后缀的word文档的第一行内容
	* @param filePath 文件的全路经
	* @return
	* String  返回第一行内容 
	* @throws IOException    
	*/
	public static String getFirstLineTextFromDoc(String filePath) throws IOException {
    	InputStream is = new FileInputStream(new File(filePath));
        HWPFDocument doc=new HWPFDocument(is);
        Range r=doc.getRange();
        for(int x=0;x<r.numSections();x++){
            Section s=r.getSection(x);
            for(int y=0;y<s.numParagraphs();y++){
                Paragraph p=s.getParagraph(y);
                for(int z=0;z<p.numCharacterRuns();z++){
                    CharacterRun run=p.getCharacterRun(z);
                    String text=run.text().trim();
                    if (!text.isEmpty()) {
						return text;
					}
//                    System.out.print(text);
                }
            }
        }
        return null;
    }
	/** 
	* 获取docx后缀的word文档的第一行内容,效率较低，需要先整个读文件的全部内容，然后再调用getLine方法获取第一行内容
	* @param filePath 文件的全路径
	* @return    
	* String  返回第一行内容  
	*/
	public static  String getFirstLineTextFromDocx(String filePath) {
		String result = null;
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
			POIXMLTextExtractor extractor = new XWPFWordExtractor(
					opcPackage);
			result = extractor.getText();
			result = getLine(result);
			//以下可以获得文件的title属性
//			POIXMLPropertiesTextExtractor extractor1 = new POIXMLPropertiesTextExtractor(extractor);
//			System.out.println(extractor1.getCorePropertiesText());
//			String temp = extractor1.getCorePropertiesText();
//			int index = temp.lastIndexOf("Title =");
//			result = temp.substring(index + 7).trim();
//			System.out.println(result);
			
			/*
			System.out.println(extractor1.getCorePropertiesText());
			System.out.println(extractor1.getExtendedPropertiesText());
			System.out.println(extractor1.getCustomPropertiesText());
			java.util.ArrayList<PackagePart> packageParts = opcPackage.getParts();
			for (PackagePart packagePart : packageParts) {
				System.out.println(packagePart);
				System.out.println(packagePart.getPartName());
				System.out.println(packagePart.getPackage());
			}*/
			opcPackage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) {
		getFirstLineTextFromDocx("D:\\User\\Documents\\Program\\Java\\Rename\\files\\2007.docx");
	}
}


