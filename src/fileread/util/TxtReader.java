package fileread.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtReader {
	public TxtReader() {
	}
	/** 
	* ��ȡtxt�ĵ��ĵ�һ�� 
	* @param filePath �ļ���ȫ·��
	* @return String  ����txt�ĵ�һ��
	* @throws IOException    
	*/
	public static String getFirstLineText(String filePath) throws IOException {
		String result =null;
		//��ȡ�ļ���һ��
		FileReader file = new FileReader(filePath);
		BufferedReader in = new BufferedReader(file);
		String strALine;
		while ((strALine = in.readLine()) != null) {
			//��ÿһ�н���ͳ��
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
