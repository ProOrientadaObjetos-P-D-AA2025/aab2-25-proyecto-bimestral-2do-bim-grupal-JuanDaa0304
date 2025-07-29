package modelo;

public class PlanPostPagoMinutosMegasEconomico extends Plan {

    private int minutos;
    private double costoMinuto;
    private double megasEnGB;
    private double costoPorGB;
    private double porcentajeDescuento;

    public PlanPostPagoMinutosMegasEconomico(int minutos, double costoMinuto,
            double megasEnGB, double costoPorGB,
            double porcentajeDescuento) {
        super("PostPago Minutos+Megas Económico", 16.0);
        this.minutos = minutos;
        this.costoMinuto = costoMinuto;
        this.megasEnGB = megasEnGB; 
        this.costoPorGB = costoPorGB;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularCosto() {
        double subtotal = minutos * costoMinuto
                + megasEnGB * costoPorGB
                + tarifaBase;
        return subtotal * (1 - porcentajeDescuento / 100);
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

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

}
