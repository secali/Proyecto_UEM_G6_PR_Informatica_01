package scl.pdi.billpaid.holders;

import lombok.Getter;
import lombok.Setter;
import scl.pdi.billpaid.modelo.User;


public final class UserHolder {

    private static final UserHolder INSTANCE = new UserHolder();
    @Setter
    @Getter
    private User usuario;

    public static UserHolder getInstance() {
        return INSTANCE;
    }

}
