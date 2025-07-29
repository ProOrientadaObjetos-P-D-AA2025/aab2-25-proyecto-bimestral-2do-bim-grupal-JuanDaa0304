package modelo;

public class PlanPostPagoMegas extends Plan {

    private double megasEnGB;
    private double costoPorGB;

    public PlanPostPagoMegas(double megasEnGB, double costoPorGB, double tarifaBase) {
        super("PostPago Megas", tarifaBase);
        this.megasEnGB = megasEnGB;
        this.costoPorGB = costoPorGB;
    }

    @Override
    public double calcularCosto() {
        return megasEnGB * costoPorGB + tarifaBase;
    }

    public double getMegasEnGB() {
        return megasEnGB;
    }

    public double getCostoPorGB() {
        return costoPorGB;
    }
}
