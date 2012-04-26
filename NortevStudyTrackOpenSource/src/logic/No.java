package logic;

import java.text.SimpleDateFormat;
import other.Format;

public class No {
    private String tipo;
    private Institution instAtual;
    private Course curAtual;
    private Semester semAtual;
    private Subject matAtual;
    private Note anotAtual;
    private Test provaAtual;
    private HomeWork trabAtual;
    private Biography bibAtual;


    public No(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        String nome = null;
        
        if(tipo.equals("INS")){
            nome = instAtual.getNome();
        }else if(tipo.equals("CUR")){
            nome = curAtual.getNome();
        }else if(tipo.equals("SEM")){
            nome = semAtual.getNome()+" - Ano: "+semAtual.getAno();
        }else if(tipo.equals("MAT")){
            nome = matAtual.getNome();
        }else if(tipo.equals("ANO")){
            nome = Format.sdf.format(anotAtual.getData())+" - "+anotAtual.getTitulo(); 
        }else if(tipo.equals("PRO")){
            nome = "Prova: "+provaAtual.getAssunto()+" - "+Format.sdf.format(provaAtual.getData());
        }else if(tipo.equals("TRA")){
            nome = "Trabalho: "+trabAtual.getTitulo()+ " - "+Format.sdf.format(trabAtual.getData());
        }else if(tipo.equals("BIB")){
            nome = "Titulo: "+bibAtual.getTitulo()+" - Autor: "+bibAtual.getAutor();
        }
        
        return nome;
    }

    public Course getCurAtual() {
        return curAtual;
    }

    public void setCurAtual(Course curAtual) {
        this.curAtual = curAtual;
    }

    public Institution getInstAtual() {
        return instAtual;
    }

    public void setInstAtual(Institution instAtual) {
        this.instAtual = instAtual;
    }

    public Subject getMatAtual() {
        return matAtual;
    }

    public void setMatAtual(Subject matAtual) {
        this.matAtual = matAtual;
    }

    public Semester getSemAtual() {
        return semAtual;
    }

    public void setSemAtual(Semester semAtual) {
        this.semAtual = semAtual;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Note getAnotAtual() {
        return anotAtual;
    }

    public void setAnotAtual(Note anotAtual) {
        this.anotAtual = anotAtual;
    }

    public Test getProvaAtual() {
        return provaAtual;
    }

    public void setProvaAtual(Test provaAtual) {
        this.provaAtual = provaAtual;
    }

    public Biography getBibAtual() {
        return bibAtual;
    }

    public void setRefAtual(Biography refAtual) {
        this.bibAtual = refAtual;
    }

    public HomeWork getTrabAtual() {
        return trabAtual;
    }

    public void setTrabAtual(HomeWork trabAtual) {
        this.trabAtual = trabAtual;
    }
    
    
}
