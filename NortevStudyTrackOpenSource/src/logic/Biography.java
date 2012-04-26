package logic;

import java.io.Serializable;

public class Biography implements Serializable { 
    //Autor
    //Título
    //Tipo
    //
    //Onde encontrar
    private String autor,titulo,tipo,ondeEncontrar,ano;


    public Biography(String autor, String titulo, String tipo, String ondeEncontrar, String ano) {
        this.autor = autor;
        this.titulo = titulo;
        this.tipo = tipo;
        this.ondeEncontrar = ondeEncontrar;
        this.ano = ano;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getOndeEncontrar() {
        return ondeEncontrar;
    }

    public void setOndeEncontrar(String ondeEncontrar) {
        this.ondeEncontrar = ondeEncontrar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    

}
