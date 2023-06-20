package api.HospDetailInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;

public class HospDetail {

    private String ServiceKey = "SERVICEKEY IS DELETED";
    private String baseUrl = "URL IS DELETED";
    private String totalCnt = "0";
    private String errCode = "-1";
    private String errMsg = "null";
    private String address = "null";
    private String clCdNm = "null";
    private String estbDd = "null";
    private String telNum = "null";
    private String yadmNm = "null";
    private String[] deptlist;
    private String hospurl = "null";
    private String trmtMonStart = "null";
    private String trmtMonEnd = "null";
    private String trmtTueStart = "null";
    private String trmtTueEnd = "null";
    private String trmtWedStart = "null";
    private String trmtWedEnd = "null";
    private String trmtThuStart = "null";
    private String trmtThuEnd = "null";
    private String trmtFriStart = "null";
    private String trmtFriEnd = "null";
    private String trmtSatStart = "null";
    private String trmtSatEnd = "null";
    private String trmtSunStart = "null";
    private String trmtSunEnd = "null";
    private String lunchSat = "null";
    private String lunchWeek = "null";
    private String noTrmtSun = "null";
    private String noTrmtHoli = "null";

    int currentPage = 0;
    int countPerPage = 1000;
    int maxPage = 0;
    public int searchDepts(String ykiho){
        System.out.println("In searchDepts");
        String deptUrl = baseUrl+"URL IS DELETED";
        try {
            currentPage=0;
            do{
                currentPage++;
                StringBuilder makeUrl = new StringBuilder();
                makeUrl.append(deptUrl);
                makeUrl.append("?serviceKey=");
                makeUrl.append(ServiceKey);
                makeUrl.append("&ykiho=");
                makeUrl.append(ykiho);
                makeUrl.append("&pageNo=");
                makeUrl.append(currentPage);
                makeUrl.append("&numOfRows=");
                makeUrl.append(countPerPage);
                makeUrl.append("&_type=xml");

                URL url = new URL(makeUrl.toString());
                System.out.println(makeUrl.toString());

                InputStream stream = url.openStream();

                System.out.println("end Input Stream ");
                System.out.println("start dbfactory");

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(stream);

                doc.getDocumentElement().normalize();

                System.out.println("start get Element by tagname");

                NodeList nList1 = doc.getElementsByTagName("header");

                NodeList nList2 = doc.getElementsByTagName("item");

                NodeList nList3 = doc.getElementsByTagName("body");

                System.out.println("end get Elementb y tagname");

                for (int temp = 0; temp < nList3.getLength(); temp++) {
                    Node nNode = nList3.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        totalCnt = getTagValue("totalCount", eElement);
                    }
                }
                if(currentPage==1){
                    deptlist = new String[Integer.parseInt(totalCnt)];
                    maxPage=(Integer.parseInt(totalCnt)/countPerPage)+1;
                }
                for (int temp = 0; temp < nList1.getLength(); temp++) {
                    Node nNode = nList1.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        errCode = getTagValue("resultCode", eElement);
                        errMsg = getTagValue("resultMsg", eElement);
                    }
                }
                if (!errCode.equals("00")){
                    System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                    return -1;
                }else if(totalCnt.equals("0")){
                    System.out.println("검색결과 없음");
                    return 1;
                }else {
                    for (int temp = 0; temp < nList2.getLength(); temp++) {
                        Node nNode = nList2.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            int currRow = temp+(currentPage-1)*countPerPage;
                            deptlist[currRow]=getTagValue("dgsbjtCdNm", eElement);
                        }
                    }
                }
                System.out.println("HospInfo 검색 페이지 : "+currentPage);
                System.out.println("maxPage : "+maxPage);
            }while((maxPage > currentPage)&&(currentPage<90));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }
    public int searchHolidayInfo(String ykiho){
        System.out.println("In searchHolidayInfo");
        String holiUrl = baseUrl+"URL IS DELETED";
        try {
            currentPage=0;
            do{
                currentPage++;
                StringBuilder makeUrl = new StringBuilder();
                makeUrl.append(holiUrl);
                makeUrl.append("?serviceKey=");
                makeUrl.append(ServiceKey);
                makeUrl.append("&ykiho=");
                makeUrl.append(ykiho);
                makeUrl.append("&pageNo=");
                makeUrl.append(currentPage);
                makeUrl.append("&numOfRows=");
                makeUrl.append(countPerPage);
                makeUrl.append("&_type=xml");
                URL url = new URL(makeUrl.toString());
                System.out.println(makeUrl.toString());
                InputStream stream = url.openStream();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(stream);

                doc.getDocumentElement().normalize();

                NodeList nList1 = doc.getElementsByTagName("header");

                NodeList nList2 = doc.getElementsByTagName("item");

                NodeList nList3 = doc.getElementsByTagName("body");

                for (int temp = 0; temp < nList3.getLength(); temp++) {
                    Node nNode = nList3.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        errCode = getTagValue("resultCode", eElement);
                        errMsg = getTagValue("resultMsg", eElement);
                    }
                }
                for (int temp = 0; temp < nList1.getLength(); temp++) {
                    Node nNode = nList1.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        errCode = getTagValue("resultCode", eElement);
                        errMsg = getTagValue("resultMsg", eElement);
                    }
                }
                if(currentPage==1){
                    maxPage=(Integer.parseInt(totalCnt)/countPerPage)+1;
                }
                for (int temp = 0; temp < nList1.getLength(); temp++) {
                    Node nNode = nList1.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        errCode = getTagValue("resultCode", eElement);
                        errMsg = getTagValue("resultMsg", eElement);
                    }
                }
                if (totalCnt.equals("0")){
                    System.out.println("검색결과 없음");
                    return 1;
                }else if(!errCode.equals("00")){
                    System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                    return -1;
                }else {
                    for (int temp = 0; temp < nList2.getLength(); temp++) {
                        Node nNode = nList2.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            trmtMonStart =getTagValue("trmtMonStart", eElement);
                            trmtMonEnd =getTagValue("trmtMonEnd", eElement);
                            trmtTueStart =getTagValue("trmtTueStart", eElement);
                            trmtTueEnd =getTagValue("trmtTueEnd", eElement);
                            trmtWedStart =getTagValue("trmtWedStart", eElement);
                            trmtWedEnd =getTagValue("trmtWedEnd", eElement);
                            trmtThuStart =getTagValue("trmtThuStart", eElement);
                            trmtThuEnd =getTagValue("trmtThuEnd", eElement);
                            trmtFriStart =getTagValue("trmtFriStart", eElement);
                            trmtFriEnd =getTagValue("trmtFriEnd", eElement);
                            trmtSatStart =getTagValue("trmtSatStart", eElement);
                            trmtSatEnd =getTagValue("trmtSatEnd", eElement);
                            trmtSunStart =getTagValue("trmtSunStart", eElement);
                            trmtSunEnd =getTagValue("trmtSunEnd", eElement);
                            lunchSat = getTagValue("lunchSat",eElement);
                            lunchWeek = getTagValue("lunchWeek",eElement);
                            noTrmtHoli = getTagValue("noTrmtHli",eElement);
                            noTrmtSun = getTagValue("noTrmtSun",eElement);
                        }
                    }
                }
            }while((maxPage > currentPage)&&(currentPage<90));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int searchHospDetail(String ykiho){
        System.out.println("In SearchHospDetail");
        String infoUrl = baseUrl+"URL IS DELETED";
        try {
            currentPage=0;
            currentPage++;
            StringBuilder makeUrl = new StringBuilder();
            makeUrl.append(infoUrl);
            makeUrl.append("?serviceKey=");
            makeUrl.append(ServiceKey);
            makeUrl.append("&ykiho=");
            makeUrl.append(ykiho);
            makeUrl.append("&pageNo=");
            makeUrl.append(currentPage);
            makeUrl.append("&numOfRows=");
            makeUrl.append(countPerPage);
            makeUrl.append("&_type=xml");
            URL url = new URL(makeUrl.toString());
            System.out.println(makeUrl.toString());
            InputStream stream = url.openStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            doc.getDocumentElement().normalize();

            NodeList nList1 = doc.getElementsByTagName("header");

            NodeList nList2 = doc.getElementsByTagName("item");

            NodeList nList3 = doc.getElementsByTagName("body");

            for (int temp = 0; temp < nList3.getLength(); temp++) {
                Node nNode = nList3.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    totalCnt = getTagValue("totalCount", eElement);
                }
            }
            for (int temp = 0; temp < nList1.getLength(); temp++) {
                Node nNode = nList1.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    errCode = getTagValue("resultCode", eElement);
                    errMsg = getTagValue("resultMsg", eElement);
                }
            }
            if (!errCode.equals("00")){
                System.out.println("errCode : "+errCode+"\nerrMsg : "+errMsg);
                return -1;
            }else if(totalCnt.equals("0")){
                System.out.println("검색결과 없음");
                return 1;
            }else {
                for (int temp = 0; temp < nList2.getLength(); temp++) {
                    Node nNode = nList2.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        address=getTagValue("addr", eElement);
                        clCdNm=getTagValue("clCdNm", eElement);
                        estbDd=getTagValue("estbDd", eElement);
                        telNum=getTagValue("telno", eElement);
                        yadmNm=getTagValue("yadmNm", eElement);
                        hospurl=getTagValue("hospUrl", eElement);
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public String[] getDeptlist() {
        return deptlist;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getAddress() {
        return address;
    }

    public String getClCdNm() {
        return clCdNm;
    }

    public String getHospurl() { return hospurl;}

    public String getLunchSat() {
        return lunchSat;
    }

    public String getLunchWeek() {
        return lunchWeek;
    }

    public String getTrmtFriEnd() {
        return trmtFriEnd;
    }

    public String getTrmtFriStart() {
        return trmtFriStart;
    }

    public String getTrmtMonEnd() {
        return trmtMonEnd;
    }

    public String getTrmtMonStart() {
        return trmtMonStart;
    }

    public String getTrmtSatEnd() {
        return trmtSatEnd;
    }

    public String getTrmtSunEnd() {
        return trmtSunEnd;
    }

    public String getTrmtSunStart() {
        return trmtSunStart;
    }

    public String getTrmtSatStart() {
        return trmtSatStart;
    }

    public String getTrmtThuEnd() {
        return trmtThuEnd;
    }

    public String getTrmtThuStart() {
        return trmtThuStart;
    }

    public String getTrmtTueEnd() {
        return trmtTueEnd;
    }

    public String getTrmtTueStart() {
        return trmtTueStart;
    }

    public String getTrmtWedEnd() {
        return trmtWedEnd;
    }

    public String getTrmtWedStart() {
        return trmtWedStart;
    }

    public int getErrCode() {
        return Integer.parseInt(errCode);
    }

    public int getEstbDd() {
        return Integer.parseInt(estbDd);
    }

    public String getTelNum() {
        return telNum;
    }

    public int getTotalCnt() {
        return Integer.parseInt(totalCnt);
    }

    public String getYadmNm() {
        return yadmNm;
    }

    public String getNoTrmtHoli() {
        return noTrmtHoli;
    }

    public String getNoTrmtSun() {
        return noTrmtSun;
    }

    private static String getTagValue(String tag, Element eElement) {
        try{
            NodeList nList =eElement.getElementsByTagName(tag).item(0).getChildNodes();
            Node nValue = (Node) nList.item(0);
            if(nValue==null)
                return "null";
            return nValue.getNodeValue();
        }catch (NullPointerException e){
            return "null";
        }
    }
}
