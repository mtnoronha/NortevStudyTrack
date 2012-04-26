
package logic;

import java.io.Serializable;
import java.util.ArrayList;


public class Course implements Serializable {

    
    private String nome;
    private ArrayList<Semester> semestres;

    public Course(String nome) {
        this.nome = nome;
        semestres = new ArrayList<Semester>();
    }

    public Course( ArrayList<Semester> semestres,String nome) {
        this.nome = nome;
        this.semestres = semestres;
    }

        public void addSemestre(Semester add){
        semestres.add(add);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Semester> getSemestres() {
        return semestres;
    }

    public void setSemestres(ArrayList<Semester> semestres) {
        this.semestres = semestres;
    }




}
