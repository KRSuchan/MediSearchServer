package org.example;

import Objects.Address;
import Objects.Hospital;
import Objects.HospitalDetail;
import api.HospDetailInfo.HospDetail;
import api.HospInfo.HospInfo;
import api.MyPos.MyPos;
import api.SearchAddr.SearchAddr;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

public class ClientHandler extends Thread{
    private Socket commSocket;
    private ClientsList cliList;

    public ClientHandler(Socket commSocket, ClientsList cliList){
        this.commSocket = commSocket;
        this.cliList = cliList;
    }

    public void run(){
        InputStream is;
        OutputStream os;
        BufferedReader br;
        BufferedWriter bw;
        String readBuf;
        StringTokenizer st;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try{
            os = commSocket.getOutputStream();
            oos = new ObjectOutputStream(os);
            System.out.println("under of new oos");
            is = commSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            ois = new ObjectInputStream(is);
            System.out.println("under of new ois");

            doJoin(ois, oos);

            System.out.println("Data out of while");

            while ((ois) != null) {
                System.out.println("Data in while");
                readBuf = ois.readObject().toString();
                System.out.println("received readBuf : "+readBuf);
                StringBuffer sendData = new StringBuffer();
                st = new StringTokenizer(readBuf, "/=/");
                String token = st.nextToken();

                if(token.equals("01")){
                    String keyword = st.nextToken();
                    System.out.println(keyword);
                    SearchAddr searchAddr = new SearchAddr();
                    searchAddr.searchAddress(keyword);
                    int addrTotalCnt = searchAddr.getTotalCount();
                    Address[] address = new Address[addrTotalCnt];
                    System.out.println("address Search Clear \n address total : "+addrTotalCnt);
                    for (int i = 0; i <addrTotalCnt; i++) {
                        address[i] = new Address();
                        address[i].setRoadAddress(searchAddr.getRoadAddr(i));
                        address[i].setJibunAddress(searchAddr.getJibunAddr(i));
                    }
                    doSend(oos, address);
                    System.out.println("under of doSend in code 01");
                    ois = new ObjectInputStream(is);
                }
                else if (token.equals("02")) {
                    String keyword = st.nextToken();
                    SearchAddr searchAddr = new SearchAddr();
                    searchAddr.searchAddress(keyword);

                    long admcd = searchAddr.getAdmCd(0);        // 행정구역 코드
                    long rnMgtSn = searchAddr.getRnMgtSn(0);    // 도로명 코드
                    int udrtYn = searchAddr.getUdrtYn(0);       // 지하 여부
                    int buldMnnm = searchAddr.getBuldMnnm(0);   // 건물 본번
                    int buldSlno = searchAddr.getBuldSlno(0);   // 건물 부번

                    MyPos myPos = new MyPos();
                    myPos.findMyXY(admcd,rnMgtSn,udrtYn,buldMnnm,buldSlno);

                    double myX = myPos.getTransX();
                    double myY = myPos.getTransY();

                    System.out.println("Transform Coordination complete\nX : "+myX+"\nY : "+myY);
                    HospInfo hospInfo = new HospInfo();
                    int deptCnt = Integer.parseInt(st.nextToken());
                    String[] deptNums = new String[deptCnt];
                    for (int i = 0; i < deptCnt; i++) {
                        deptNums[i] = st.nextToken();
                    }
                    int radius = Integer.parseInt(st.nextToken());
                    hospInfo.searchHospByDepts(myX, myY, radius, deptCnt, deptNums);
                    int hospTotalCnt = hospInfo.getTotalCnt();
                    Hospital[] hospitals = new Hospital[hospTotalCnt];
                    System.out.println("hospital Search Clear \n hospital total : "+hospTotalCnt);
                    for (int i = 0; i < hospTotalCnt; i++) {
                        hospitals[i] = new Hospital();
                        hospitals[i].setYkiho(hospInfo.getYkiho(i)); // 요양기호
                        hospitals[i].setYadmNm(hospInfo.getYadmNm(i)); // 병원이름
                        hospitals[i].setClcdNm(hospInfo.getClCdNm(i)); // 종목이름
                        hospitals[i].setYAddress(hospInfo.getAddr(i)); // 주소
                        hospitals[i].setDistance(hospInfo.getDistance(i)); // 거리(m)
                    }
                    doSend(oos,hospitals);
                    System.out.println("under of doSend in code 02");
                    ois = new ObjectInputStream(is);
                } else if (token.equals("03")) {
                    String keyword = st.nextToken();
                    SearchAddr searchAddr = new SearchAddr();
                    searchAddr.searchAddress(keyword);

                    long admcd = searchAddr.getAdmCd(0);
                    long rnMgtSn = searchAddr.getRnMgtSn(0);
                    int udrtYn = searchAddr.getUdrtYn(0);
                    int buldMnnm = searchAddr.getBuldMnnm(0);
                    int buldSlno = searchAddr.getBuldSlno(0);

                    MyPos myPos = new MyPos();
                    myPos.findMyXY(admcd,rnMgtSn,udrtYn,buldMnnm,buldSlno);

                    double myX = myPos.getTransX();
                    double myY = myPos.getTransY();

                    System.out.println("Transform Coordination complete\nX : "+myX+"\nY : "+myY);
                    HospInfo hospInfo = new HospInfo();
                    int deptCnt = Integer.parseInt(st.nextToken());
                    String[] deptNums = new String[deptCnt];
                    for (int i = 0; i < deptCnt; i++) {
                        deptNums[i] = st.nextToken();
                        System.out.println(deptNums[i]);
                    }
                    String clcdCode = st.nextToken();
                    int radius = Integer.parseInt(st.nextToken());
                    hospInfo.searchHospByDeptsClcd(myX, myY, radius, deptCnt, deptNums, clcdCode);
                    int hospTotalCnt = hospInfo.getTotalCnt();
                    Hospital[] hospitals = new Hospital[hospTotalCnt];
                    System.out.println("hospital Search Clear \n hospital total : "+hospTotalCnt);
                    for (int i = 0; i < hospTotalCnt; i++) {
                        hospitals[i] = new Hospital();
                        hospitals[i].setYkiho(hospInfo.getYkiho(i)); // 요양기호
                        hospitals[i].setYadmNm(hospInfo.getYadmNm(i)); // 병원이름
                        hospitals[i].setClcdNm(hospInfo.getClCdNm(i)); // 종목이름
                        hospitals[i].setYAddress(hospInfo.getAddr(i)); // 주소
                        hospitals[i].setDistance(hospInfo.getDistance(i)); // 거리(m)
                    }
                    doSend(oos,hospitals);
                    System.out.println("under of doSend in code 03");
                    ois = new ObjectInputStream(is);
                } else if (token.equals("04")) {
                    String keyword = st.nextToken();
                    SearchAddr searchAddr = new SearchAddr();
                    searchAddr.searchAddress(keyword);

                    long admcd = searchAddr.getAdmCd(0);
                    long rnMgtSn = searchAddr.getRnMgtSn(0);
                    int udrtYn = searchAddr.getUdrtYn(0);
                    int buldMnnm = searchAddr.getBuldMnnm(0);
                    int buldSlno = searchAddr.getBuldSlno(0);

                    MyPos myPos = new MyPos();
                    myPos.findMyXY(admcd,rnMgtSn,udrtYn,buldMnnm,buldSlno);

                    double myX = myPos.getTransX();
                    double myY = myPos.getTransY();

                    System.out.println("Transform Coordination complete\nX : "+myX+"\nY : "+myY);
                    HospInfo hospInfo = new HospInfo();
                    String clcdCode = st.nextToken();
                    int radius = Integer.parseInt(st.nextToken());
                    hospInfo.searchHospByClcd(myX, myY, radius, clcdCode);
                    int hospTotalCnt = hospInfo.getTotalCnt();
                    Hospital[] hospitals = new Hospital[hospTotalCnt];
                    System.out.println("hospital Search Clear \n hospital total : "+hospTotalCnt);
                    for (int i = 0; i < hospTotalCnt; i++) {
                        hospitals[i] = new Hospital();
                        hospitals[i].setYkiho(hospInfo.getYkiho(i)); // 요양기호
                        hospitals[i].setYadmNm(hospInfo.getYadmNm(i)); // 병원이름
                        hospitals[i].setClcdNm(hospInfo.getClCdNm(i)); // 종목이름
                        hospitals[i].setYAddress(hospInfo.getAddr(i)); // 주소
                        hospitals[i].setDistance(hospInfo.getDistance(i)); // 거리(m)
                    }
                    doSend(oos,hospitals);
                    System.out.println("under of doSend in code 04");
                    ois = new ObjectInputStream(is);

                } else if (token.equals("05")) {
                    String keyword = st.nextToken();
                    SearchAddr searchAddr = new SearchAddr();
                    searchAddr.searchAddress(keyword);

                    long admcd = searchAddr.getAdmCd(0);
                    long rnMgtSn = searchAddr.getRnMgtSn(0);
                    int udrtYn = searchAddr.getUdrtYn(0);
                    int buldMnnm = searchAddr.getBuldMnnm(0);
                    int buldSlno = searchAddr.getBuldSlno(0);

                    MyPos myPos = new MyPos();
                    myPos.findMyXY(admcd,rnMgtSn,udrtYn,buldMnnm,buldSlno);

                    double myX = myPos.getTransX();
                    double myY = myPos.getTransY();

                    System.out.println("Transform Coordination complete\nX : "+myX+"\nY : "+myY);
                    HospInfo hospInfo = new HospInfo();
                    int radius = Integer.parseInt(st.nextToken());
                    hospInfo.searchHospBybasic(myX, myY, radius);
                    int hospTotalCnt = hospInfo.getTotalCnt();
                    Hospital[] hospitals = new Hospital[hospTotalCnt];
                    System.out.println("hospital Search Clear \n hospital total : "+hospTotalCnt);
                    for (int i = 0; i < hospTotalCnt; i++) {
                        hospitals[i] = new Hospital();
                        hospitals[i].setYkiho(hospInfo.getYkiho(i)); // 요양기호
                        hospitals[i].setYadmNm(hospInfo.getYadmNm(i)); // 병원이름
                        hospitals[i].setClcdNm(hospInfo.getClCdNm(i)); // 종목이름
                        hospitals[i].setYAddress(hospInfo.getAddr(i)); // 주소
                        hospitals[i].setDistance(hospInfo.getDistance(i)); // 거리(m)
                    }
                    doSend(oos,hospitals);
                    System.out.println("under of doSend in code 05");
                    ois = new ObjectInputStream(is);
                } else if (token.equals("06")) {
                    // 요양기호 획득
                    String hospYkiho = st.nextToken();

                    // 병원 상세 검색 api 객체 생성
                    HospDetail hospDetail = new HospDetail();

                    hospDetail.searchHospDetail(hospYkiho);
                    hospDetail.searchDepts(hospYkiho);
                    hospDetail.searchHolidayInfo(hospYkiho);

                    // 병원 상세 객체 생성
                    HospitalDetail hospitalDetail = new HospitalDetail();

                    hospitalDetail.setYadmNm(hospDetail.getYadmNm());   // 병원 이름
                    hospitalDetail.setClcdNm(hospDetail.getClCdNm());   // 병원 규모
                    hospitalDetail.setYAddress(hospDetail.getAddress());// 병원 주소
                    hospitalDetail.setDepts(hospDetail.getDeptlist());  // 진료 과목 (배열)
                    hospitalDetail.setHospUrl(hospDetail.getHospurl()); // 병원 홈페이지
                    hospitalDetail.setTelNo(hospDetail.getTelNum());    // 병원 전화번호

                    // 병원 주간 진료 시작 시간
                    hospitalDetail.setTrmtMonStart(hospDetail.getTrmtMonStart());
                    hospitalDetail.setTrmtTueStart(hospDetail.getTrmtTueStart());
                    hospitalDetail.setTrmtWedStart(hospDetail.getTrmtWedStart());
                    hospitalDetail.setTrmtThuStart(hospDetail.getTrmtThuStart());
                    hospitalDetail.setTrmtFriStart(hospDetail.getTrmtFriStart());
                    hospitalDetail.setTrmtSatStart(hospDetail.getTrmtSatStart());
                    hospitalDetail.setTrmtSunStart(hospDetail.getTrmtSunStart());

                    // 병원 주간 진료 종료 시간
                    hospitalDetail.setTrmtMonEnd(hospDetail.getTrmtMonEnd());
                    hospitalDetail.setTrmtTueEnd(hospDetail.getTrmtTueEnd());
                    hospitalDetail.setTrmtWedEnd(hospDetail.getTrmtWedEnd());
                    hospitalDetail.setTrmtThuEnd(hospDetail.getTrmtThuEnd());
                    hospitalDetail.setTrmtFriEnd(hospDetail.getTrmtFriEnd());
                    hospitalDetail.setTrmtSatEnd(hospDetail.getTrmtSatEnd());
                    hospitalDetail.setTrmtSunEnd(hospDetail.getTrmtSunEnd());

                    //병원 주간, 토요일 점심시간
                    hospitalDetail.setLunchSat(hospDetail.getLunchSat());
                    hospitalDetail.setLunchWeek(hospDetail.getLunchWeek());

                    // 일요일, 공휴일 휴진 안내
                    hospitalDetail.setNoTrmtSun(hospDetail.getNoTrmtSun());
                    hospitalDetail.setNoTrmtHoli(hospDetail.getNoTrmtHoli());
                    // 병원 상세 객체 전송
                    doSend(oos,hospitalDetail);
                    System.out.println("under of doSend in code 05");
                    // ObjectInputStream 재생성
                    ois = new ObjectInputStream(is);
                }else {
                    doSend(oos,"error");
                    System.out.println("under of doSend in else error");
                    ois = new ObjectInputStream(is);
                }
            }
            System.out.println("commSocket close");
            commSocket.close();
        }catch (SocketException e){
            System.out.println("catch SocketException in ClientHandler");
            System.out.println("commSocket close");
        }
        catch(IOException e){
            System.out.println("catch IOException in ClientHandler");
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("catch ClassNotFoundException in ClientHandler");
            System.err.println(e);
        }
    }

    private void doJoin(ObjectInputStream ois, ObjectOutputStream oos){
        String msg = commSocket.getPort() + " entered the server";
        System.out.println(msg);
        cliList.addClient(oos);
    }

    private void doSend(ObjectOutputStream oos, Object data){
        System.out.println(commSocket.getPort() + ": " + data);
        cliList.sendDataToTheClient(oos, data);
    }
}