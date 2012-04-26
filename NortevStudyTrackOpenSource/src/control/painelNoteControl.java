package control;

import gui.MainWindow;
import panels.NotePanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import logic.Note;
import logic.Subject;
import logic.No;
import other.Format;
import other.Messages;


public class painelNoteControl implements ActionListener   {

    private Note atual = null;
    private Subject materia;
    private NotePanel jan;
    private DefaultMutableTreeNode noAdd;
    
    
    public painelNoteControl(NotePanel jan){
        this.jan = jan;
    }
    
    public void setNoAdd(DefaultMutableTreeNode a){
        noAdd = a;     
    }
    
    public void setMateria(Subject m){
        materia = m;
        preencherCamposMateria();
        atual = null;
        zerarCampos();
    }
    
    public void setAnotacao(Note a){
        atual = a;
        preencherCamposAnotacao();
    }
    
    private void preencherCamposAnotacao(){
        jan.editor.setText(atual.getConteudo());
        jan.campoTitulo.setText(atual.getTitulo());
        jan.campoData.setDate(atual.getData());
        jan.editor.setCaretPosition(0);
        
    }
    private void preencherCamposMateria(){
        jan.campoProfessor2.setText(materia.getProfessor());
        jan.campoMateria2.setText(materia.getNome());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jan.bSalvar) {
            if (verificarDados()) {
                if (atual == null) {
                    Note nova = new Note(jan.campoTitulo.getText(), jan.editor.getText(), jan.campoData.getDate());
                    materia.addAnotacao(nova);
                    atual = nova;
                    No novo = new No("ANO");
                    novo.setAnotAtual(atual);
                    noAdd.add(new DefaultMutableTreeNode(novo));
                    ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noAdd);

                } else {
                    atual.setTitulo(jan.campoTitulo.getText());
                    atual.setConteudo(jan.editor.getText());
                    atual.setData(jan.campoData.getDate());
                }
            MainWindow.banco.salvar();
                Messages.mensagemSucesso("Anotação salva com sucesso.", jan);
            }
        }else if(e.getSource() == jan.bExcluir){
            if(atual == null){
                Messages.mensagemErro("Esta anotação ainda não foi salva!", jan);
            }else{
                materia.getAnotacoes().remove(atual);
                zerarCampos();
                DefaultMutableTreeNode noExcluir = null;
                Enumeration aaa = noAdd.children();

                while (aaa.hasMoreElements()) {
                    noExcluir = (DefaultMutableTreeNode) aaa.nextElement();
                     if (noExcluir.toString().equals(Format.sdf.format(atual.getData()) + " - " + atual.getTitulo())) {
                       break;
                    }
                }

                noAdd.remove(noExcluir);
                ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noAdd);
                atual = null;
                Messages.mensagemSucesso("Anotação excluída com sucesso", jan);
            }
        }
    }
    
        private boolean verificarDados() {
        if(jan.campoTitulo.getText() == null || jan.campoTitulo.getText().equals("")){
            Messages.mensagemErro("Digite o título da anotação.", jan);
            jan.campoTitulo.grabFocus();
            return false;          
        }else if(jan.campoData.getDate() == null){
            Messages.mensagemErro("Selecione a data da anotação.", jan);
            jan.campoData.grabFocus();
            return false;
        }else if(jan.editor.getText().equals("") || jan.editor.getText() == null){
            Messages.mensagemErro("Digite o conteúdo da anotação.", jan);
            jan.editor.grabFocus();
            return false;
        }
        
        return true;
    }


    private void zerarCampos() {
        jan.editor.setText("");
        jan.campoTitulo.setText("");
        jan.campoData.setDate(new Date());
    }
    
}
