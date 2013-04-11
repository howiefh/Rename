package fileread.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

//http://daning.iteye.com/blog/165284
public class PdfReader extends FileInfoReader {
	private PDDocument document = null;
	private FileInputStream is = null;
	// pdf�ļ���ҳ��
	private int pageNums = 0;

	/**
	 * ���ļ�����������ȡpdfģ��
	 * 
	 * @param filePath
	 */
	public PdfReader(String filePath) {
		try {
			is = new FileInputStream(filePath);
			PDFParser parser = new PDFParser(is);
			parser.parse();
			document = parser.getPDDocument();
			pageNums = document.getNumberOfPages();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡpdf�ļ�����
	 * 
	 * @return String ����pdf�ļ�����
	 */
	public String getTextFromPdf() {
		String result = null;
		result = getTextFromPdfPages(0, pageNums - 1);
		return result;
	}

	/**
	 * ��ȡ��startҳ��endҳ������
	 * 
	 * @param start
	 *            ��ʼҳ��
	 * @param end
	 *            ��ֹҳ��
	 * @return String ���ش�startҳ��endҳ������
	 */
	public String getTextFromPdfPages(int start, int end) {
		String result = null;
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setStartPage(start);
			stripper.setEndPage(end);
			result = stripper.getText(document);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * ��ȡpdf�ļ��ĵ�һ������
	 * 
	 * @return String ����pdf�ļ��ĵ�һ������
	 */
	public String getFirstLineText() {
		String result = null;
		for (int i = 0; i < pageNums; i++) {
			result = getTextFromPdfPages(i, i + 1);
			result = getLine(result);
			if (!result.isEmpty()) {
				break;
			}
		}
		return result;
	}

	/**
	 * �ر���
	 */
	public void closeAll() {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (document != null) {
			try {
				document.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		PdfReader pdfReader = new PdfReader("files/1.pdf");
		System.out.println(pdfReader.getFirstLineText());
		pdfReader.closeAll();
	}
}


