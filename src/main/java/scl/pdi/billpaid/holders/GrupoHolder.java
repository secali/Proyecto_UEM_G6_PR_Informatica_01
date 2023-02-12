package scl.pdi.billpaid.holders;

import lombok.Getter;
import lombok.Setter;
import scl.pdi.billpaid.modelo.Grupo;
import scl.pdi.billpaid.modelo.User;

public class GrupoHolder {
    @Setter
    @Getter
    private Grupo grupo;


    private static final GrupoHolder INSTANCE = new GrupoHolder();

    public static GrupoHolder getInstance() {
        return INSTANCE;
    }
}
