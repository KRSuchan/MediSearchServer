package api.HospInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;

public class HospInfo {
    private final String ServiceKey = "SERVICEKEY IS DELETED";
    private final String baseUrl ="URL IS DELETED";
    private String totalCnt = "0";
    private String errCode = "-1";
    private String errMsg = "null";
    private String[][] hospInfoList;
    int currentPage = 0;
    int countPerPage = 1000;
    int maxPage = 0;
    public int searchHospBybasic(double xPos, double yPos, int radius){
        try {
            do{
                currentPage++;
                // url 생성
                StringBuilder makeUrl = new StringBuilder();
                makeUrl.append(baseUrl);
                makeUrl.append("?serviceKey=");
                makeUrl.append(ServiceKey);
                makeUrl.append("&pageNo=");
                makeUrl.append(currentPage);
                makeUrl.append("&numOfRows=");
                makeUrl.append(countPerPage);
                makeUrl.append("&xPos=");
                makeUrl.append(xPos);
                makeUrl.append("&yPos=");
                makeUrl.append(yPos);
                makeUrl.append("&radius=");
                makeUrl.append(radius);

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

                // xml에서 body의 totalCount 태그의 값 읽음
                for (int temp = 0; temp < nList3.getLength(); temp++) {
                    Node nNode = nList3.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        totalCnt = getTagValue("totalCount", eElement);
                        System.out.println("totalCnt : "+totalCnt);
                    }
                }
                
                // 첫 페이지일 때 (함수 처음 시작할 때)  hospital 개수만큼 hospitalList String 배열 생성
                // 검색할 최대 페이지 수 설정
                if(currentPage==1){
                    hospInfoList = new String[Integer.parseInt(totalCnt)][7];
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

                            hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
                            hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
                            hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
                            hospInfoList[currRow][3]=getTagValue("addr", eElement);
                            hospInfoList[currRow][4]=getTagValue("distance", eElement);
                            hospInfoList[currRow][5]=getTagValue("telno", eElement);
                            hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);
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
        return 0;
    }
    public int searchHospByClcd(double xPos, double yPos, double radius,String clcdCode){
        try {
            do{
                currentPage++;
                StringBuilder makeUrl = new StringBuilder();
                makeUrl.append(baseUrl);
                makeUrl.append("?serviceKey=");
                makeUrl.append(ServiceKey);
                makeUrl.append("&pageNo=");
                makeUrl.append(currentPage);
                makeUrl.append("&numOfRows=");
                makeUrl.append(countPerPage);
                makeUrl.append("&clCd=");
                makeUrl.append(clcdCode);
                makeUrl.append("&xPos=");
                makeUrl.append(xPos);
                makeUrl.append("&yPos=");
                makeUrl.append(yPos);
                makeUrl.append("&radius=");
                makeUrl.append(radius);

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
                if(currentPage==1){
                    hospInfoList = new String[Integer.parseInt(totalCnt)][7];
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
                            hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
                            hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
                            hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
                            hospInfoList[currRow][3]=getTagValue("addr", eElement);
                            hospInfoList[currRow][4]=getTagValue("distance", eElement);
                            hospInfoList[currRow][5]=getTagValue("telno", eElement);
                            hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);
                        }
                    }
                }
                System.out.println("HospInfo 검색 페이지 : "+currentPage);
            }while((maxPage > currentPage)&&(currentPage<90));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    public int searchHospByDepts(double xPos, double yPos, double radius,int totalCdsCnt, String[] dgsbjtCds){
        try {
            do{
                currentPage++;
                StringBuilder makeUrl = new StringBuilder();
                makeUrl.append(baseUrl);
                makeUrl.append("?serviceKey=");
                makeUrl.append(ServiceKey);
                for (int i = 0; i < totalCdsCnt; i++) {
                    makeUrl.append("&dgsbjtCd=");
                    makeUrl.append(dgsbjtCds[i]);
                }
                makeUrl.append("&pageNo=");
                makeUrl.append(currentPage);
                makeUrl.append("&numOfRows=");
                makeUrl.append(countPerPage);
                makeUrl.append("&xPos=");
                makeUrl.append(xPos);
                makeUrl.append("&yPos=");
                makeUrl.append(yPos);
                makeUrl.append("&radius=");
                makeUrl.append(radius);

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
                if(currentPage==1){
                    hospInfoList = new String[Integer.parseInt(totalCnt)][7];
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
                            hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
                            hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
                            hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
                            hospInfoList[currRow][3]=getTagValue("addr", eElement);
                            hospInfoList[currRow][4]=getTagValue("distance", eElement);
                            hospInfoList[currRow][5]=getTagValue("telno", eElement);
                            hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);
                        }
                    }
                }
                System.out.println("HospInfo 검색 페이지 : "+currentPage);
            }while((maxPage > currentPage)&&(currentPage<90));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int searchHospByDeptsClcd(double xPos, double yPos, double radius,int totalCdsCnt, String[] dgsbjtCds, String clcdCd){
        try {
            do{
                currentPage++;
                StringBuilder makeUrl = new StringBuilder();
                makeUrl.append(baseUrl);
                makeUrl.append("?serviceKey=");
                makeUrl.append(ServiceKey);
                for (int i = 0; i < totalCdsCnt; i++) {
                    makeUrl.append("&dgsbjtCd=");
                    makeUrl.append(dgsbjtCds[i]);
                }
                makeUrl.append("&pageNo=");
                makeUrl.append(currentPage);
                makeUrl.append("&numOfRows=");
                makeUrl.append(countPerPage);
                makeUrl.append("&xPos=");
                makeUrl.append(xPos);
                makeUrl.append("&yPos=");
                makeUrl.append(yPos);
                makeUrl.append("&clCd=");
                makeUrl.append(clcdCd);
                makeUrl.append("&radius=");
                makeUrl.append(radius);
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
                if(currentPage==1){
                    hospInfoList = new String[Integer.parseInt(totalCnt)][7];
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
                            hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
                            hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
                            hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
                            hospInfoList[currRow][3]=getTagValue("addr", eElement);
                            hospInfoList[currRow][4]=getTagValue("distance", eElement);
                            hospInfoList[currRow][5]=getTagValue("telno", eElement);
                            hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);
                        }
                    }
                }
                System.out.println("HospInfo 검색 페이지 : "+currentPage);
            }while((maxPage > currentPage)&&(currentPage<90));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    public String[][] getHospInfoList() { return hospInfoList;}

    public int getErrCode() {
        return Integer.parseInt(errCode);
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getTotalCnt() {
        return Integer.parseInt(totalCnt);
    }
/*
    hospInfoList[currRow][0]=getTagValue("ykiho", eElement);
    hospInfoList[currRow][1]=getTagValue("yadmNm", eElement);
    hospInfoList[currRow][2]=getTagValue("clCdNm", eElement);
    hospInfoList[currRow][3]=getTagValue("addr", eElement);
    hospInfoList[currRow][4]=getTagValue("distance", eElement);
    hospInfoList[currRow][5]=getTagValue("telno", eElement);
    hospInfoList[currRow][6]=getTagValue("hospUrl", eElement);  */
    public String getYkiho(int numOfRow) {
        return hospInfoList[numOfRow][0];}
    public String getYadmNm(int numOfRow){
        return hospInfoList[numOfRow][1];
    }
    public String getClCdNm(int numOfRow){
        return hospInfoList[numOfRow][2];
    }
    public String getAddr(int numOfRow){
        return hospInfoList[numOfRow][3];
    }
    public double getDistance(int numOfRow){
        if (hospInfoList[numOfRow][4].equals("")){
            return 0.0;
        }
        else {
            return Double.parseDouble(hospInfoList[numOfRow][4]);
        }
    }
    public String getTelno(int numOfRow){
        return hospInfoList[numOfRow][5];
    }
    public String getHospUrl(int numOfRow){
        return hospInfoList[numOfRow][6];
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
