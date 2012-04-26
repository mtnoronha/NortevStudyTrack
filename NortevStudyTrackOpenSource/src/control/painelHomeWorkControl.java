package control;

import gui.MainWindow;
import panels.HomeWorkPanel;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import logic.HomeWork;
import logic.Subject;
import logic.No;
import other.Format;
import other.Messages;


public class painelHomeWorkControl implements ActionListener   {

    private HomeWork atual = null;
    private Subject materia;
    private HomeWorkPanel jan;
    private DefaultMutableTreeNode noAdd;
    
    public painelHomeWorkControl(HomeWorkPanel jan){
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
    
    public void setTrabalho(HomeWork a){
        atual = a;
        preencherCamposTrabalho();
    }
    
    private void preencherCamposTrabalho(){
        jan.campoTitulo.setText(atual.getTitulo());
        jan.campoEnunciado.setText(atual.getEnunciado());
        jan.campoData.setDate(atual.getData());
        jan.campoHora.setText(atual.getHora());
        jan.campoConcluido.setSelected(atual.isConcluido());
    }
    
    private void preencherCamposMateria(){
        jan.campoProfessor.setText(materia.getProfessor());
        jan.campoMateria.setText(materia.getNome());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jan.bSalvar) {
            if (verificarDados()) {
                if (atual == null) {
                    HomeWork novo = new HomeWork(jan.campoTitulo.getText(), jan.campoEnunciado.getText(), jan.campoData.getDate(), jan.campoHora.getText(),jan.campoConcluido.isSelected());
                    materia.addTrabalho(novo);
                    atual = novo;
                    No nova = new No("TRA");
                    nova.setTrabAtual(atual);
                    noAdd.add(new DefaultMutableTreeNode(nova));
                    ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noAdd);
                } else {
                    atual.setTitulo(jan.campoTitulo.getText());
                    atual.setEnunciado(jan.campoEnunciado.getText());
                    atual.setData(jan.campoData.getDate());
                    atual.setHora(jan.campoHora.getText());
                    atual.setConcluido(jan.campoConcluido.isSelected());
                }
            MainWindow.banco.salvar();
                Messages.mensagemSucesso("Lembrete salvo com sucesso.", jan);
            }
        }else if(e.getSource() == jan.bExcluir){
            if(atual == null){
                Messages.mensagemErro("Este lembrete ainda não foi salvo!", jan);
            }else{
                materia.getTrabalhos().remove(atual);
                zerarCampos();
                DefaultMutableTreeNode noExcluir = null;
                Enumeration aaa = noAdd.children();

                while (aaa.hasMoreElements()) {
                    noExcluir = (DefaultMutableTreeNode) aaa.nextElement();
                     if (noExcluir.toString().equals("Trabalho: "+atual.getTitulo()+ " - "+Format.sdf.format(atual.getData()))) {
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
        if(jan.campoEnunciado.getText() == null || jan.campoEnunciado.getText().equals("")){
            Messages.mensagemErro("Digite o enunciado do trabalho.", jan);
            jan.campoEnunciado.grabFocus();
            return false;          
        }else if(jan.campoData.getDate() == null){
            Messages.mensagemErro("Selecione a data do trabalho.", jan);
            jan.campoData.grabFocus();
            return false;
        }else if(jan.campoHora.getText().equals("") || jan.campoHora.getText() == null){
            Messages.mensagemErro("Digite o horário do trabalho.", jan);
            jan.campoHora.grabFocus();
            return false;
        }else if(jan.campoTitulo.getText() == null || jan.campoTitulo.getText().equals("")){
            Messages.mensagemErro("Digite o título do trabalho.", jan);
            jan.campoTitulo.grabFocus();
            return false;
        }        
        return true;
    }

    private void zerarCampos() {
        jan.campoTitulo.setText("");
        jan.campoEnunciado.setText("");
        jan.campoData.setDate(new Date());
        jan.campoHora.setText("");
    }
    
}
