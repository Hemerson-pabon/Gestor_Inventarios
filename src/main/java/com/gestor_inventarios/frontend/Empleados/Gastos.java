package com.gestor_inventarios.frontend.Empleados;

public class Gastos {
    String motivo;
    Double valorGasto;

    public Gastos(String motivo, Double valorGasto) {
        this.motivo = motivo;
        this.valorGasto = valorGasto;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public Double getValorGasto() {
        return valorGasto;
    }
    public void setValorGasto(Double valorGasto) {
        this.valorGasto = valorGasto;
    }
}
