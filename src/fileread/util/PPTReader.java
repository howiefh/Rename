package fileread.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;


public class PPTReader extends FileInfoReader{

    /** 
 	* ��ȡppt��׺��PPT������
	* @param filePath �ļ���ȫ·��
	* @param all �Ƿ��ȡȫ������
	* @return String  ����PPT������
    */
    public static String getTextFromPpt(String filePath,boolean all) {

        StringBuffer content = new StringBuffer("");
        try {

            SlideShow ss = new SlideShow(new HSLFSlideShow(filePath));// pathΪ�ļ���ȫ·�����ƣ�����SlideShow
            Slide[] slides = ss.getSlides();// ���ÿһ�Żõ�Ƭ
            for (int i = 0; i < slides.length; i++) {
                TextRun[] t = slides[i].getTextRuns();// Ϊ��ȡ�ûõ�Ƭ���������ݣ�����TextRun
                for (int j = 0; j < t.length; j++) {
                    content.append(t[j].getText());// ����Ὣһ������������ݼӵ�content��ȥ
                    if (!all) {
						String result = content.toString().trim();
						if (!result.equals("")) {
							return result;
						}
					}
                }
                content.append(slides[i].getTitle());
//                String title;
//				if ((title = slides[i].getTitle())!=null) {
//	                content.append(slides[i].getTitle());
//				}
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return content.toString();

    }

    /** 
	* ��ȡpptx��׺��PPT������
	* @param filePath �ļ���ȫ·��
	* @param all �Ƿ��ȡȫ������
	* @return String  ����PPT������
    */
    public static String getTextFromPptx(String filePath,boolean all) {
//        XSLFSlideShow slideShow;
        String reusltString=null;
        try {
//            slideShow = new XSLFSlideShow(path);
            InputStream is = new FileInputStream(new File(filePath));
//            XMLSlideShow xmlSlideShow = new XMLSlideShow(slideShow);
            XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
            XSLFSlide[] slides = xmlSlideShow.getSlides();
            StringBuilder sbuilder = new StringBuilder();
//            for (XSLFSlide slide : slides) {
////                CTSlide rawSlide = slide._getCTSlide();
//                CTSlide rawSlide = slide.getXmlObject();
//                CTGroupShape gs = rawSlide.getCSld().getSpTree();
//                CTShape[] shapes = gs.getSpArray();
//                for (CTShape shape : shapes) {
//                    CTTextBody tb = shape.getTxBody();
//                    if (null == tb)
//                        continue;
//                    CTTextParagraph[] paras = tb.getPArray();
//                    for (CTTextParagraph textParagraph : paras) {
//                        CTRegularTextRun[] textRuns = textParagraph.getRArray();
//                        for (CTRegularTextRun textRun : textRuns) {
//                            sb.append(textRun.getT());
//                        }
//                    }
//                }
//            }
            
//            Dimension pageSize = xmlSlideShow.getPageSize();  // size of the canvas in points
            for(XSLFSlide slide : slides) {
                for(XSLFShape shape : slide){
//                    Rectangle2D anchor = shape.getAnchor();  // position on the canvas
                    if(shape instanceof XSLFTextShape) {
                        XSLFTextShape txShape = (XSLFTextShape)shape;
//                        System.out.println(txShape.getText());
                        sbuilder.append(txShape.getText());
                    } //else if (shape instanceof XSLFPictureShape){
                    if (!all) {
						String result = sbuilder.toString().trim();
						if (!result.equals("")) {
							return result;
						}
					}
//                        XSLFPictureShape pShape = (XSLFPictureShape)shape;
//                        XSLFPictureData pData = pShape.getPictureData();
//                        System.out.println(pData.getFileName());
//                    } else {
//                        System.out.println("Process me: " + shape.getClass());
//                    }
                }
            }
	        is.close();
	        reusltString=sbuilder.toString();
        }  catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return reusltString;
    }
    
    /** 
	* ��ȡppt��׺��PPT��ȫ������
	* @param filePath �ļ���ȫ·��
	* @return String  ����PPT��ȫ������
    */
    public static String getTextFromPpt(String filePath) {
    	return getTextFromPpt(filePath,true);
    }
    /** 
  	* ��ȡpptx��׺��PPT��ȫ������
	* @param filePath �ļ���ȫ·��
	* @return String  ����PPT��ȫ������
    */
    public static String getTextFromPptx(String filePath) {
    	return getTextFromPptx(filePath,true);
    }
	
	/** 
	* ��ȡPPT��ȫ������
	* @param filePath �ļ���ȫ·��
	* @return String  ����PPT��ȫ������
	*/
	public static String getTextFromPPT(String filePath) {
		String result = null;
		if (getExtension(filePath).equalsIgnoreCase("ppt")) {
			result = getTextFromPpt(filePath);
		} else if (getExtension(filePath).equalsIgnoreCase("pptx")) {
			result = getTextFromPpt(filePath);
		}
		return result;
	}
	
	/** 
	* ��ȡpptx��׺��PPT�ĵ�һ��
	* @param filePath �ļ���ȫ·��
	* @return String  ����PPT�ĵ�һ��
	*/
	public static String getFirstLineTextFromPptx(String filePath) {
		return getTextFromPptx(filePath,false);
	}
	
	/** 
	* ��ȡppt��׺��PPT�ĵ�һ��
	* @param filePath �ļ���ȫ·��
	* @return String  ����PPT�ĵ�һ��
	*/
	public static String getFirstLineTextFromPpt(String filePath) {
		return getTextFromPpt(filePath,false);
	}
	
	
	/** 
	* ��ȡPPT�ĵ�һ��
	* @param filePath �ļ���ȫ·��
	* @return String  ����PPT�ĵ�һ��
	*/
	public static String getFirstLineText(String filePath) {
		String result = null;
		if (getExtension(filePath).equalsIgnoreCase("ppt")) {
			result = getFirstLineTextFromPpt(filePath);
		} else if (getExtension(filePath).equalsIgnoreCase("pptx")) {
			result = getFirstLineTextFromPptx(filePath);
		}
		return result;
	}
//    public static void main(String[] args) {
//        System.out.println(PPTReader.getTextFromPpt("files/2003.ppt"));
//        System.out.println(PPTReader.getTextFromPptx("files/2007.pptx"));
//    }
}
