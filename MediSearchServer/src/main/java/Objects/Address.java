package Objects;

import java.io.Serializable;

public class Address implements Serializable {
    private String roadAddress = "null";
    private String jibunAddress = "null";

    public String getJibunAddress() {
        return jibunAddress;
    }
    public String getRoadAddress() {
        return roadAddress;
    }

    public void setJibunAddress(String jibunAddress) {
        this.jibunAddress = jibunAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }
}
