package scl.pdi.billpaid.holders;

import lombok.Getter;
import lombok.Setter;
import scl.pdi.billpaid.modelo.Grupo;

public class GrupoHolder {
    private static final GrupoHolder INSTANCE = new GrupoHolder();
    @Setter
    @Getter
    private Grupo grupo;

    public static GrupoHolder getInstance() {
        return INSTANCE;
    }
}
