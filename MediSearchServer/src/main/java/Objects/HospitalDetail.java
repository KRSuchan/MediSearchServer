package Objects;

import java.io.Serializable;

public class HospitalDetail implements Serializable {
    private String yadmNm= "null";
    private String clcdNm = "null";
    private String yAddress = "null";
    private String[] depts = {"null"};
    private String hospUrl = "null";
    private String telNo = "null";

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
    private String trmtSunStart= "null";
    private String trmtSunEnd= "null";
    private String lunchSat = "null";
    private String lunchWeek = "null";
    private String noTrmtHoli = "null";
    private String noTrmtSun = "null";


    public String getClcdNm() {
        return clcdNm;
    }

    public String getYadmNm() {
        return yadmNm;
    }

    public String getYAddress() {
        return yAddress;
    }

    public String[] getDepts() {
        return depts;
    }

    public String getHospUrl() {
        return hospUrl;
    }

    public String getTrmtTueStart() {
        return trmtTueStart;
    }

    public String getTrmtWedStart() {
        return trmtWedStart;
    }

    public String getTrmtThuStart() {
        return trmtThuStart;
    }

    public String getTrmtTueEnd() {
        return trmtTueEnd;
    }

    public String getTrmtThuEnd() {
        return trmtThuEnd;
    }

    public String getTrmtSatStart() {
        return trmtSatStart;
    }

    public String getTrmtSatEnd() {
        return trmtSatEnd;
    }

    public String getTrmtMonStart() {
        return trmtMonStart;
    }

    public String getTrmtMonEnd() {
        return trmtMonEnd;
    }

    public String getTrmtFriStart() {
        return trmtFriStart;
    }

    public String getTrmtFriEnd() {
        return trmtFriEnd;
    }

    public String getTrmtWedEnd() {
        return trmtWedEnd;
    }

    public String getTrmtSunEnd() {
        return trmtSunEnd;
    }

    public String getTrmtSunStart() {
        return trmtSunStart;
    }

    public String getLunchSat() {
        return lunchSat;
    }

    public String getLunchWeek() {
        return lunchWeek;
    }
    public String getTelNo(){
        return telNo;
    }

    public String getNoTrmtHoli() {
        return noTrmtHoli;
    }

    public String getNoTrmtSun() {
        return noTrmtSun;
    }

    public void setHospUrl(String hospUrl) {
        this.hospUrl = hospUrl;
    }

    public void setYAddress(String yAddress) {
        this.yAddress = yAddress;
    }

    public void setClcdNm(String clcdNm) {
        this.clcdNm = clcdNm;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public void setDepts(String[] depts) {
        this.depts = depts;
    }

    public void setLunchSat(String lunchSat) {
        this.lunchSat = lunchSat;
    }

    public void setLunchWeek(String lunchWeek) {
        this.lunchWeek = lunchWeek;
    }

    public void setTrmtFriEnd(String trmtFriEnd) {
        this.trmtFriEnd = trmtFriEnd;
    }

    public void setTrmtFriStart(String trmtFriStart) {
        this.trmtFriStart = trmtFriStart;
    }

    public void setTrmtMonEnd(String trmtMonEnd) {
        this.trmtMonEnd = trmtMonEnd;
    }

    public void setTrmtMonStart(String trmtMonStart) {
        this.trmtMonStart = trmtMonStart;
    }

    public void setTrmtSatEnd(String trmtSatEnd) {
        this.trmtSatEnd = trmtSatEnd;
    }

    public void setTrmtSatStart(String trmtSatStart) {
        this.trmtSatStart = trmtSatStart;
    }

    public void setTrmtThuEnd(String trmtThuEnd) {
        this.trmtThuEnd = trmtThuEnd;
    }

    public void setTrmtThuStart(String trmtThuStart) {
        this.trmtThuStart = trmtThuStart;
    }

    public void setTrmtTueEnd(String trmtTueEnd) {
        this.trmtTueEnd = trmtTueEnd;
    }

    public void setTrmtTueStart(String trmtTueStart) {
        this.trmtTueStart = trmtTueStart;
    }

    public void setTrmtWedEnd(String trmtWedEnd) {
        this.trmtWedEnd = trmtWedEnd;
    }

    public void setTrmtWedStart(String trmtWedStart) {
        this.trmtWedStart = trmtWedStart;
    }

    public void setTrmtSunEnd(String trmtSunEnd) {
        this.trmtSunEnd = trmtSunEnd;
    }

    public void setTrmtSunStart(String trmtSunStart) {
        this.trmtSunStart = trmtSunStart;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public void setNoTrmtHoli(String noTrmtHoli) {
        this.noTrmtHoli = noTrmtHoli;
    }

    public void setNoTrmtSun(String noTrmtSun) {
        this.noTrmtSun = noTrmtSun;
    }
}
