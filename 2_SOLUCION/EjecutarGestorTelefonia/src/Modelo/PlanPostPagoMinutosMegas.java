package modelo;

public class PlanPostPagoMinutosMegas extends Plan {

    private int minutos;
    private double costoMinuto;
    private double megasEnGB;
    private double costoPorGB;

    public PlanPostPagoMinutosMegas(int minutos, double costoMinuto,
            double megasEnGB, double costoPorGB) {
        super("PostPago Minutos+Megas", 14.0);
        this.minutos = minutos;
        this.costoMinuto = costoMinuto;
        this.megasEnGB = megasEnGB;
        this.costoPorGB = costoPorGB;
    }

    @Override
    public double calcularCosto() {
        return minutos * costoMinuto
                + megasEnGB * costoPorGB
                + tarifaBase;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public double getCostoMinuto() {
        return costoMinuto;
    }

    public void setCostoMinuto(double costoMinuto) {
        this.costoMinuto = costoMinuto;
    }

    public double getMegasEnGB() {
        return megasEnGB;
    }

    public void setMegasEnGB(double megasEnGB) {
        this.megasEnGB = megasEnGB;
    }

    public double getCostoPorGB() {
        return costoPorGB;
    }

    public void setCostoPorGB(double costoPorGB) {
        this.costoPorGB = costoPorGB;
    }
    
    
}
