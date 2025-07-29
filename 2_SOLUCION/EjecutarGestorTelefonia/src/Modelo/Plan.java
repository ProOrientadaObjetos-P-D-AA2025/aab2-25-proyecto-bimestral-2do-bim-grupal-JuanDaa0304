package modelo;

public abstract class Plan {

    protected String tipo;
    protected double tarifaBase;

    public Plan(String tipo, double tarifaBase) {
        this.tipo = tipo;
        this.tarifaBase = tarifaBase;
    }

    public abstract double calcularCosto();

    public String getTipo() {
        return tipo;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }
}
