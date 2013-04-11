package fileread.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtReader {
	public TxtReader() {
	}
	/** 
	* 读取txt文档的第一行 
	* @param filePath 文件的全路径
	* @return String  返回txt的第一行
	* @throws IOException    
	*/
	public static String getFirstLineText(String filePath) throws IOException {
		String result =null;
		//读取文件的一行
		FileReader file = new FileReader(filePath);
		BufferedReader in = new BufferedReader(file);
		String strALine;
		while ((strALine = in.readLine()) != null) {
			//对每一行进行统计
			result = strALine.trim();
			if (!result.equals("")) {
				file.close();
				return result;
			}
		}
		file.close();
        return result;
    }
}
