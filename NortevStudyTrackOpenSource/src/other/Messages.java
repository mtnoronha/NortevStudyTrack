package other;

import gui.MainWindow;
import java.awt.Component;
import javax.swing.JOptionPane;

public class Messages {

    public static String erroCombos = "Opps.. Alguma informação está faltando!";
    public static String sucessoCadastro = "Cadastro realizado com sucesso!";

    public static void mensagemErro(String msg, Component e){
        JOptionPane.showMessageDialog(e, msg,"Erro",JOptionPane.ERROR_MESSAGE);
    }
    
    public static void mensagemSucesso(String msg, Component e){
        JOptionPane.showMessageDialog(e, msg,"Sucesso",JOptionPane.INFORMATION_MESSAGE);
    }

     public static String mensagemInput(String msg, String titulo, Component e,String pathImagem){
         Object resp = null;
         
         resp = JOptionPane.showInputDialog(e, msg, titulo, JOptionPane.OK_OPTION, MainWindow.criarImagem(pathImagem, "1"), null,null);
         //resp = JOptionPane.showInputDialog(e, msg, titulo, JOptionPane.OK_OPTION);
                 
         return resp.toString();
     }
}
