package scl.pdi.billpaid.modelo;

public class Sesion {
    private static String userId;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Sesion.userId = userId;
    }
}
