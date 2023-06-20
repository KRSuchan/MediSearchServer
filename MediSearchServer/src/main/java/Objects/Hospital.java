package Objects;

import java.io.Serializable;

public class Hospital implements Serializable {
    String ykiho = "null"; // 병원 id
    String yadmNm = "null"; // 병원 이름
    String clcdNm = "null"; // 병원 종류
    String yAddress = "null"; // 병원 주소
    Double distance = 0.0; // 거리

    public String getYadmNm() {
        return yadmNm;
    }

    public Double getDistance() {
        return distance;
    }

    public String getYkiho() {
        return ykiho;
    }

    public String getClcdNm() {
        return clcdNm;
    }

    public String getYAddress() {
        return yAddress;
    }

    public void setClcdNm(String clcdNm) {
        this.clcdNm = clcdNm;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setYAddress(String yAddress) {
        this.yAddress = yAddress;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public void setYkiho(String ykiho) {
        this.ykiho = ykiho;
    }

    public String getDistanceStr() {
        return String.format("%.1f", distance / 1000) + " km";
    }
}
