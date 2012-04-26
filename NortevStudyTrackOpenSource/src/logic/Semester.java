package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Semester implements Serializable {
    
    private String nome;
    private int ano;
    private ArrayList<Subject> materias;

    public Semester(String nome,int ano) {
        this.nome = nome;
        materias = new ArrayList<Subject>();
        this.ano = ano;
    }

    public Semester(ArrayList<Subject> materias,String nome, int ano) {
        this.nome = nome;
        this.materias = materias;
        this.ano = ano;
    }

     public void addMateria(Subject add){
        materias.add(add);
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public ArrayList<Subject> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Subject> materias) {
        this.materias = materias;
    }





}
