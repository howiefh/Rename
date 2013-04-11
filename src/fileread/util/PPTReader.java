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
 	* 读取ppt后缀的PPT的内容
	* @param filePath 文件的全路径
	* @param all 是否读取全部内容
	* @return String  返回PPT的内容
    */
    public static String getTextFromPpt(String filePath,boolean all) {

        StringBuffer content = new StringBuffer("");
        try {

            SlideShow ss = new SlideShow(new HSLFSlideShow(filePath));// path为文件的全路径名称，建立SlideShow
            Slide[] slides = ss.getSlides();// 获得每一张幻灯片
            for (int i = 0; i < slides.length; i++) {
                TextRun[] t = slides[i].getTextRuns();// 为了取得幻灯片的文字内容，建立TextRun
                for (int j = 0; j < t.length; j++) {
                    content.append(t[j].getText());// 这里会将一个块的文字内容加到content中去
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
	* 读取pptx后缀的PPT的内容
	* @param filePath 文件的全路径
	* @param all 是否读取全部内容
	* @return String  返回PPT的内容
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
	* 读取ppt后缀的PPT的全部内容
	* @param filePath 文件的全路径
	* @return String  返回PPT的全部内容
    */
    public static String getTextFromPpt(String filePath) {
    	return getTextFromPpt(filePath,true);
    }
    /** 
  	* 读取pptx后缀的PPT的全部内容
	* @param filePath 文件的全路径
	* @return String  返回PPT的全部内容
    */
    public static String getTextFromPptx(String filePath) {
    	return getTextFromPptx(filePath,true);
    }
	
	/** 
	* 读取PPT的全部内容
	* @param filePath 文件的全路径
	* @return String  返回PPT的全部内容
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
	* 读取pptx后缀的PPT的第一行
	* @param filePath 文件的全路径
	* @return String  返回PPT的第一行
	*/
	public static String getFirstLineTextFromPptx(String filePath) {
		return getTextFromPptx(filePath,false);
	}
	
	/** 
	* 读取ppt后缀的PPT的第一行
	* @param filePath 文件的全路径
	* @return String  返回PPT的第一行
	*/
	public static String getFirstLineTextFromPpt(String filePath) {
		return getTextFromPpt(filePath,false);
	}
	
	
	/** 
	* 读取PPT的第一行
	* @param filePath 文件的全路径
	* @return String  返回PPT的第一行
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
