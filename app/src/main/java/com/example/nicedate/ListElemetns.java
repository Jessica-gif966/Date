package com.example.nicedate;

public class ListElemetns {
    public String mensaje_eviado,mensaje_recivido;
    public int count;


    public ListElemetns(String mensaje_eviado, String mensaje_recivido, int count) {
        this.mensaje_eviado = mensaje_eviado;
        this.mensaje_recivido = mensaje_recivido;
        this.count = count;
    }

    public String getMensaje_eviado() {
        return mensaje_eviado;
    }

    public void setMensaje_eviado(String mensaje_eviado) {
        this.mensaje_eviado = mensaje_eviado;
    }

    public String getMensaje_recivido() {
        return mensaje_recivido;
    }

    public void setMensaje_recivido(String mensaje_recivido) {
        this.mensaje_recivido = mensaje_recivido;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ListElemetns{" +
                "mensaje_eviado='" + mensaje_eviado + '\'' +
                ", mensaje_recivido='" + mensaje_recivido + '\'' +
                ", count=" + count +
                '}';
    }
}
