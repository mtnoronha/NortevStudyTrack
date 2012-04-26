package control;

import gui.MainWindow;
import panels.BiographyPanel;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import logic.Biography;
import logic.Subject;
import logic.No;
import other.Messages;


public class painelBibliografiaControl implements ActionListener   {

    private Biography current_Biography = null;
    private Subject subject;
    private BiographyPanel jan;
    private DefaultMutableTreeNode noAdd;
    
    public void setNoAdd(DefaultMutableTreeNode a){
        this.noAdd = a;
    }
    
    public painelBibliografiaControl(BiographyPanel jan){
        this.jan = jan;
    }
    
    
    public void setSubject(Subject m){
        subject = m;
        preencherCampossubject();
        current_Biography = null;
        zerarCampos();
    }
    
    public void setBibliografia(Biography a){
        current_Biography = a;
        preencherCamposBibliografia();
    }
    
    private void preencherCamposBibliografia(){
        jan.campoAutor.setText(current_Biography.getAutor());
        jan.campoTipo.setSelectedItem(current_Biography.getTipo());
        jan.campoTitulo.setText(current_Biography.getTitulo());
        jan.campoEncontrar.setText(current_Biography.getOndeEncontrar());
        jan.campoAno.setText(current_Biography.getAno()+"");
    }
    
    private void preencherCampossubject(){
        jan.campoProfessor.setText(subject.getProfessor());
        jan.campoMateria.setText(subject.getNome());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jan.bSalvar){   
                        
            if(current_Biography == null){               
                Biography novo = new Biography(jan.campoAutor.getText(),jan.campoTitulo.getText(),jan.campoTipo.getSelectedItem().toString(),jan.campoEncontrar.getText(),jan.campoAno.getText());
                subject.addBibliografia(novo);
                current_Biography = novo;
                No nova = new No("BIB");
                nova.setRefAtual(current_Biography);
                noAdd.add(new DefaultMutableTreeNode(nova));
                                        ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noAdd);
            }else{
                current_Biography.setAutor(jan.campoAutor.getText());
                current_Biography.setTitulo(jan.campoTitulo.getText());
                current_Biography.setTipo(jan.campoTipo.getSelectedItem().toString());
                current_Biography.setAno(jan.campoAno.getText());
                current_Biography.setOndeEncontrar(jan.campoEncontrar.getText());
            }
            
            
            
            MainWindow.banco.salvar();
           
            
            Messages.mensagemSucesso("Bibliografia salva com sucesso.", jan);

        }else if(e.getSource() == jan.bExcluir){
            if(current_Biography == null){
                Messages.mensagemErro("Esta bibliografia ainda não foi salva!", jan);
            }else{
                subject.getBibliografias().remove(current_Biography);
                zerarCampos();
                DefaultMutableTreeNode noExcluir = null;
                Enumeration aaa = noAdd.children();

                while (aaa.hasMoreElements()) {
                    noExcluir = (DefaultMutableTreeNode) aaa.nextElement();
                     if (noExcluir.toString().equals("Titulo: "+current_Biography.getTitulo()+" - Autor: "+current_Biography.getAutor())) {
                       break;
                    }
                }

                noAdd.remove(noExcluir);
                ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noAdd);
                current_Biography = null;
                Messages.mensagemSucesso("Bibliografia excluída com sucesso", jan);
            }
        }
    }

    private void zerarCampos() {
           jan.campoAutor.setText("");
           jan.campoTitulo.setText("");
           jan.campoTipo.setSelectedIndex(0);
           jan.campoAno.setText("");
           jan.campoEncontrar.setText("");        
    }
    
}
