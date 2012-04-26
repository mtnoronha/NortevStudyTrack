package logic;

import java.io.Serializable;
import java.util.Date;

public class HomeWork implements Serializable{

    private String titulo, enunciado;
    private Date data;
    private String hora;
    private boolean concluido;

    public HomeWork(String titulo, String enunciado, Date data, String hora,boolean concluido) {
        this.titulo = titulo;
        this.enunciado = enunciado;
        this.data = data;
        this.hora = hora;
        this.concluido = concluido;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    
}
