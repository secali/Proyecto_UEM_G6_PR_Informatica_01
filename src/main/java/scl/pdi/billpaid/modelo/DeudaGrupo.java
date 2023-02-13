package scl.pdi.billpaid.modelo;

public class DeudaGrupo {
    private int ID_Grupo;
    private double saldoCalculado;
    private int ID_Persona;

    public DeudaGrupo(int ID_Grupo, double saldoCalculado, int ID_Persona) {
        this.ID_Grupo = ID_Grupo;
        this.saldoCalculado = saldoCalculado;
        this.ID_Persona = ID_Persona;
    }

    //mostrarDeudaGrupo()
    public String toString() {
        return "Deuda{" +
                " ID del grupo ='" + ID_Grupo + '\'' +
                ", El saldo calculado ='" + saldoCalculado + '\'' +
                ", ID de la Persona ='" + ID_Persona + '\'' +
                '}';
    }
}

