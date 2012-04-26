package logic;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable, Comparable<Note> {  
    private String titulo, conteudo;
    private Date data;

    public Note(String titulo, String conteudo,Date data) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    
    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int compareTo(Note o) {
       int valor = data.compareTo(o.data);  
        return (valor != 0 ? valor : 1);
    }
    
    
    
}
