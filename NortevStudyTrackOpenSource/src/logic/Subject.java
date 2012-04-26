package logic;

import java.io.Serializable;
import java.util.ArrayList;


public class Subject implements Serializable {
    private String nome,professor;    
    private ArrayList<Note> anotacoes;
    private ArrayList<Test> provas;
    private ArrayList<HomeWork> trabalhos;
    private ArrayList<Biography> bibliografias;

   
    public Subject(String nome,String professor) {
        this.nome = nome;
        this.professor = professor;
        anotacoes = new ArrayList<Note>();
        provas = new ArrayList<Test>();
        trabalhos = new ArrayList<HomeWork>();
        bibliografias = new ArrayList<Biography>();
    }

    
    public Subject(String nome, String professor, ArrayList<Note> anotacoes, ArrayList<Test> provas, ArrayList<HomeWork> trabalhos, ArrayList<Biography> referencias) {
        this.nome = nome;
        this.professor = professor;
        this.anotacoes = anotacoes;
        this.provas = provas;
        this.trabalhos = trabalhos;
        this.bibliografias = referencias;
    }

    public void addAnotacao(Note add){
        anotacoes.add(add);
    }
    
    public void addProva(Test add){
        provas.add(add);
    }
    
    public void addTrabalho(HomeWork add){
        trabalhos.add(add);
    }
    
    public void addBibliografia(Biography add){
        bibliografias.add(add);
    }
    
    public ArrayList<Note> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(ArrayList<Note> anotacoes) {
        this.anotacoes = anotacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public ArrayList<Test> getProvas() {
        return provas;
    }

    public void setProvas(ArrayList<Test> provas) {
        this.provas = provas;
    }

    public ArrayList<Biography> getBibliografias() {
        return bibliografias;
    }

    public void setBibliografias(ArrayList<Biography> referencias) {
        this.bibliografias = referencias;
    }

    public ArrayList<HomeWork> getTrabalhos() {
        return trabalhos;
    }

    public void setTrabalhos(ArrayList<HomeWork> trabalhos) {
        this.trabalhos = trabalhos;
    }
    
    
    
    
   
}