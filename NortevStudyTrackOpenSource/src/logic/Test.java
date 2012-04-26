package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Test implements Serializable {

    private String assunto,estudar;
    private Date data;
    private String hora;

    public Test(String assunto, String estudar, Date dia, String hora) {
        this.assunto = assunto;
        this.estudar = estudar;
        this.data = dia;
        this.hora = hora;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date dia) {
        this.data = dia;
    }

    public String getEstudar() {
        return estudar;
    }

    public void setEstudar(String estudar) {
        this.estudar = estudar;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    
}
