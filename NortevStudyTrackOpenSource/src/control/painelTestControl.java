package control;

import gui.MainWindow;
import panels.TestPanel;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import logic.Test;
import logic.Subject;
import logic.No;
import other.Format;
import other.Messages;


public class painelTestControl implements ActionListener   {

    private Test atual = null;
    private Subject materia;
    private TestPanel jan;
    private DefaultMutableTreeNode noAdd;
    
    public painelTestControl(TestPanel jan){
        this.jan = jan;
    }
    
    public void setNoAdd(DefaultMutableTreeNode a){
        this.noAdd = a;
    }
    
    public void setMateria(Subject m){
        materia = m;
        preencherCamposMateria();
        atual = null;
        zerarCampos();
    }
    
    public void setProva(Test a){
        atual = a;
        preencherCamposProva();
    }
    
    private void preencherCamposProva(){
        jan.campoAssunto.setText(atual.getAssunto());
        jan.campoEstudar.setText(atual.getEstudar());
        jan.campoData.setDate(atual.getData());
        jan.campoHora.setText(atual.getHora());
    }
    private void preencherCamposMateria(){
        jan.campoProfessor.setText(materia.getProfessor());
        jan.campoMateria.setText(materia.getNome());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jan.bSalvar){
            
            if (verificarDados()) {
                if (atual == null) {
                    Test novo = new Test(jan.campoAssunto.getText(), jan.campoEstudar.getText(), jan.campoData.getDate(), jan.campoHora.getText());
                    materia.addProva(novo);
                    atual = novo;
                    No nova = new No("PRO");
                    nova.setProvaAtual(atual);
                    noAdd.add(new DefaultMutableTreeNode(nova));
                    ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noAdd);

                } else {
                    atual.setAssunto(jan.campoAssunto.getText());
                    atual.setEstudar(jan.campoEstudar.getText());
                    atual.setData(jan.campoData.getDate());
                    atual.setHora(jan.campoHora.getText());
                }
            MainWindow.banco.salvar();
                Messages.mensagemSucesso("Lembrete salvo com sucesso.", jan);
                
            }
        }else if(e.getSource() == jan.bExcluir){
            if(atual == null){
                Messages.mensagemErro("Este lembrete ainda não foi salvo!", jan);
            }else{
                materia.getProvas().remove(atual);
                zerarCampos();
                DefaultMutableTreeNode noExcluir = null;
                Enumeration aaa = noAdd.children();

                while (aaa.hasMoreElements()) {
                    noExcluir = (DefaultMutableTreeNode) aaa.nextElement();
                     if (noExcluir.toString().equals("Prova: "+atual.getAssunto()+" - "+Format.sdf.format(atual.getData()))) {
                       break;
                    }
                }

                noAdd.remove(noExcluir);
                ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noAdd);
                atual = null;
                Messages.mensagemSucesso("Lembrete excluído com sucesso", jan);
            }
        }
    }

    private boolean verificarDados() {
        if(jan.campoAssunto.getText() == null || jan.campoAssunto.getText().equals("")){
            Messages.mensagemErro("Digite o assunto da prova.", jan);
            jan.campoAssunto.grabFocus();
            return false;          
        }else if(jan.campoData.getDate() == null){
            Messages.mensagemErro("Selecione a data da prova.", jan);
            jan.campoData.grabFocus();
            return false;
        }else if(jan.campoHora.getText().equals("") || jan.campoHora.getText() == null){
            Messages.mensagemErro("Digite o horário da prova.", jan);
            jan.campoHora.grabFocus();
            return false;
        }else if(jan.campoEstudar.getText() == null || jan.campoEstudar.getText().equals("")){
            Messages.mensagemErro("Digite o que você deve estudar para a prova.", jan);
            jan.campoEstudar.grabFocus();
            return false;
        }        
        return true;
    }

    private void zerarCampos() {
        jan.campoAssunto.setText("");
        jan.campoEstudar.setText("");
        jan.campoData.setDate(new Date());
        jan.campoHora.setText("");

    }
    
}
