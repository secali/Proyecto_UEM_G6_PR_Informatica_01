package scl.pdi.billpaid.holders;

import lombok.Getter;
import lombok.Setter;
import scl.pdi.billpaid.modelo.Grupo;

import java.util.ArrayList;
import java.util.List;

public class ListGruposHolder {
    private static final ListGruposHolder INSTANCE = new ListGruposHolder();
    @Setter
    @Getter

    private ArrayList<Grupo> lista_grupos;

    public static ListGruposHolder getInstance() {
        return INSTANCE;
    }
}
