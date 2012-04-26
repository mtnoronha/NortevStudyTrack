package dataBase;

import java.io.Serializable;
import java.util.ArrayList;
import logic.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import javax.swing.JOptionPane;
import other.Messages;


public class bd implements Serializable {

    public ArrayList<Institution> instituicoes;

    //para serializar
    public static File arqBD = new File("lib\\banco.nst");
    public static ObjectOutputStream writer;
    public static ObjectInputStream reader;

    public bd(){
        instituicoes = new ArrayList<Institution>();
    }

    //zerar todos os dados
    public void novo(){
       this.instituicoes = new ArrayList<Institution>();

       arqBD = new File("lib\\banco.nst");
    }

    
    //inicialmente isto ficaria na tela principal la do programa.. vamos ver
    //o que acontece se ele ficar aqui.
    //AKA Serializar
     public void salvar(){
        try {
            writer = new ObjectOutputStream(new FileOutputStream(arqBD));

            writer.writeObject(this);

            writer.close();



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar arquivo para escrita.\nPrograma encerrando!", "Erro fatal", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

     //o mesmo que salvar, porém desta vez ele vai poder escolher o diretório
    public void backup(File arq){
         try {
            writer = new ObjectOutputStream(new FileOutputStream(arq.getAbsolutePath() + "\\bancoBCK.nst"));

            writer.writeObject(this);

            writer.close();

             JOptionPane.showMessageDialog(null, "Backup salvo com sucesso.\nLocal do backup: "+arq.getAbsolutePath() + "\\bancoBCK.nst", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar arquivo para escrita.\nPrograma encerrando!", "Erro fatal", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    //idem AKA desSerializar
    public void abrir(File arquivo) throws Exception {
            reader = new ObjectInputStream(new FileInputStream(arquivo));
            bd este;
            este = (bd) reader.readObject();
            this.setInstituicao(este.instituicoes);
            reader.close();
            arqBD = arquivo;
    }

    //agora o bixo pega
    public void importar(File arquivo){
          try {
            reader = new ObjectInputStream(new FileInputStream(arquivo));
            bd este;
            este = (bd) reader.readObject();

            //Agora o bixo pega...
            //nao posso simplesmente fazer: this.setInstituicao(este.instituicoes);
            //tenho que desmembrar e deixar o usuario escolher o que ele quer importar
            //para o seu proprio arquivo de BD.
           Messages.mensagemErro("Oops, ainda não foi implementado!", null);

            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar arquivo para leitura.\nO arquivo selecionado é um arquivo próprio do Nortev Study Track?!", "Erro fatal", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Isto pode ser o inicio
    //paga o método "IMPORTAR bd"... onde a pessoa vai escolher
    //qual/quais elementos do banco exterior ela quer incorporar no seu proprio
    //banco
    public void debug() {
        for (Institution instituicao : instituicoes) {
            System.out.println("Instituicao " + instituicao.getNome());
            for (Course curso : instituicao.getCursos()) {
                System.out.println("   Curso: " + curso.getNome());
                for (Semester semestre : curso.getSemestres()) {
                    System.out.println("      Semestre: " + semestre.getNome());
                    for (Subject materia : semestre.getMaterias()) {
                        System.out.println("         Materia: " + materia.getNome());
                    }
                }
            }
        }
    }

    //setar a instituicao [equivalente a ABRIR projeto]
    public void setInstituicao(ArrayList<Institution> instituicao) {
        this.instituicoes = instituicao;
    }

    public ArrayList<Institution> getInstituicoes(){
        return instituicoes;
    }

   //ver se a instituicao ja existe...
    public int existeInstituicao(String nome){
        int resul = -1;

        //percorrer a array inteira até encontrar, ou ela acabar...
        for(int i=0;i<instituicoes.size() && resul == -1;i++){
            if(nome.toLowerCase().equals(instituicoes.get(i).getNome().toLowerCase())){
                resul = i;
            }
        }

        return resul;
    }

    //ver se existe curso dentro de uma instituicao
    public int existeCurso(Institution inst, String nome){
        int resul = -1;

        ArrayList<Course> test = inst.getCursos();

        for(int i=0;i<test.size() && resul == -1;i++){
            if(nome.toLowerCase().equals(test.get(i).getNome().toLowerCase())){
                resul = i;
            }
        }
        return resul;
    }

    //ver se existe um Semestre dentro de um Curso
    public int existeSemestre(Course cur, String nome, int ano){
        int resul = -1;

        ArrayList<Semester> test = cur.getSemestres();

        for (int i = 0; i < test.size() && resul == -1; i++) {
            if (nome.toLowerCase().equals(test.get(i).getNome().toLowerCase()) && test.get(i).getAno() == ano) {
                resul = i;
            }
        }

        return resul;
    }

    //ver se existe uma Materia dentro de um Semestre
    public int existeMateria(Semester sem, String nome){
         int resul = -1;

        ArrayList<Subject> test = sem.getMaterias();

        for (int i = 0; i < test.size() && resul == -1; i++) {
            if (nome.toLowerCase().equals(test.get(i).getNome().toLowerCase())) {
                resul = i;
            }
        }
              
        return resul;

    }

     /* Por enquanto não estou precisando destes métodos

    public ArrayList<Curso> getCursos(int local){
        return instituicao.get(local).getCursos();
    }

    public ArrayList<Semestre> getSemestres(Curso curso){
        return curso.getSemestres();
    }

    public ArrayList<Materia> getMaterias(Semestre sem){
        return sem.getMaterias();
    }
     *
     */
    
}
