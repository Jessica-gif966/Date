package com.example.nicedate;

public class ListElemet_mensare_recivido {
    public String mensajre_recivido;

    public ListElemet_mensare_recivido(String mensajre_recivido) {
        this.mensajre_recivido = mensajre_recivido;
    }

    public String getMensajre_recivido() {
        return mensajre_recivido;
    }

    public void setMensajre_recivido(String mensajre_recivido) {
        this.mensajre_recivido = mensajre_recivido;
    }

    @Override
    public String toString() {
        return "ListElemet_mensare_recivido{" +
                "mensajre_recivido='" + mensajre_recivido + '\'' +
                '}';
    }
}
