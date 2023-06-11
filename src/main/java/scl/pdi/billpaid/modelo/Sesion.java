package scl.pdi.billpaid.modelo;


import lombok.Getter;
import lombok.Setter;

public class Sesion {

    private static String userId;

    public static String getLatestIdGroup() {
        return latestIdGroup;
    }

    public static void setLatestIdGroup(String latestIdGroup) {
        Sesion.latestIdGroup = latestIdGroup;
    }

    private static String latestIdGroup;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Sesion.userId = userId;
    }
}
