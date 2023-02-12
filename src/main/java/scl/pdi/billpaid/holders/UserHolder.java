package scl.pdi.billpaid.holders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scl.pdi.billpaid.modelo.User;


public final class UserHolder {

    @Setter
    @Getter
    private User usuario;

    private static final UserHolder INSTANCE = new UserHolder();

    public static UserHolder getInstance() {
        return INSTANCE;
    }

}
