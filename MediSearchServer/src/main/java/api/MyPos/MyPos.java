package api.MyPos;

import org.locationtech.proj4j.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;


    //  시작 url : URL IS DELETED

    /*  입력 파라미터
        Parameter   /   Type        /   필수 여부    /   내용
        confmkey    /   String      /   Y   /   인증키
        admCd       /   String      /   Y   /   행정구역코드
        rnMgtSn     /   String      /   Y   /   도로명코드
        udrtYn      /   String      /   Y   /   지하여부 0:지상, 1:지하
        buldMnnm    /   Number      /   Y   /   건물본번
        buldSlno    /   Number      /   Y   /   건물부번
        resultType  /   String      /   N   /   xml(default) | json : !!xml 사용!!
    */
    /*  출력 데이터
    *   데이터        /   Type        /   내용
    *   entX        /   double      /   변환전 X좌표
    *   entY        /   double      /   변환전 Y좌표
    *   transX      /   double      /   변환후 X좌표
    *   transY      /   double      /   변환후 Y좌표
    * */

//  리턴 값 : -1 (api 오류), 0 (정상), 1 (빈 데이터) ab


public class MyPos {
    private final String confmKey = "CONFMKEY IS DELETED";
    private final String baseUrl = "URL IS DELETED";
    private String totalCnt = "0";
    private String errCode = "-1";
    private String errMsg = "null";
    private double transX = 0.0;
    private double transY = 0.0;
    private double entX = 0.0;
    private double entY = 0.0;

    public int findMyXY(long admCd, long rnMgtSn, int udrtYn, int buldMnnm, int buldSlno){
        try {
            URL url = new URL(baseUrl+"?admCd="+admCd+"&rnMgtSn="+rnMgtSn+"&udrtYn="+udrtYn+"&buldMnnm="+buldMnnm+"&buldSlno="+buldSlno+"&confmKey="+confmKey);
            InputStream stream = url.openStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            doc.getDocumentElement().normalize();

            NodeList nList1 = doc.getElementsByTagName("common");
            NodeList nList2 = doc.getElementsByTagName("juso");

            for (int temp = 0; temp < nList1.getLength(); temp++) {
                Node nNode = nList1.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    totalCnt=getTagValue("totalCount", eElement);
                    errCode=getTagValue("errorCode", eElement);
                    errMsg=getTagValue("errorMessage", eElement);
                }
            }

            if (!errCode.equals("0")){
                System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                return -1;
            }else if(totalCnt.equals("0")){
                System.out.println("MyPos : No data");
                return 1;
            }else {
                for (int temp = 0; temp < nList2.getLength(); temp++) {
                    Node nNode = nList2.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        entX = Double.parseDouble(getTagValue("entX", eElement));
                        entY = Double.parseDouble(getTagValue("entY", eElement));
                        transGrsToWgs(entX, entY);
                    }
                }
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    private static String getTagValue(String tag, Element eElement) {
        try{
            NodeList nList =eElement.getElementsByTagName(tag).item(0).getChildNodes();
            Node nValue = (Node) nList.item(0);
            if(nValue==null)
                return "";
            return nValue.getNodeValue();
        }catch (NullPointerException e){
            return "";
        }
    }
    private void transGrsToWgs(double x, double y){
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem WGS84 = crsFactory.createFromParameters("WGS84",
                "+proj=longlat +datum=WGS84 +no_defs");
        CoordinateReferenceSystem GRS80 =crsFactory.createFromParameters("GRS80","+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs ");

        CoordinateTransformFactory ctFactory =new CoordinateTransformFactory();
        CoordinateTransform grsToWgs = ctFactory.createTransform(GRS80, WGS84);
        ProjCoordinate result =new ProjCoordinate();
        grsToWgs.transform(new ProjCoordinate(x,y), result);
        transX = result.x;
        transY = result.y;
    }

    public double getTransX(){
        return transX;
    }

    public double getTransY() {
        return transY;
    }

    public double getEntX() {
        return entX;
    }

    public double getEntY() {
        return entY;
    }

    public int getTotalCnt() {
        return Integer.parseInt(totalCnt);
    }

    public int getErrCode() {
        return Integer.parseInt(errCode);
    }

    public String getErrMsg() {
        return errMsg;
    }
}
