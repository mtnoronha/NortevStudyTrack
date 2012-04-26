package logic;

import java.io.Serializable;
import java.util.ArrayList;


public class Institution implements Serializable{
    private ArrayList<Course> cursos;
    private String nome;


    public Institution(String nome) {
        this.nome = nome;
        cursos = new ArrayList<Course>();
    }

    public Institution(String nome, ArrayList<Course> cursos) {
        this.nome = nome;
        this.cursos = cursos;
    }

    public void addCurso(Course add){
        cursos.add(add);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public ArrayList<Course> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Course> cursos) {
        this.cursos = cursos;
    }

}
