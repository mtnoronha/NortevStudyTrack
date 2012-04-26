package logic;

import gui.MainWindow;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CustomIconRenderer extends DefaultTreeCellRenderer {

    ImageIcon instIcon;
    ImageIcon curIcon;
    ImageIcon semIcon;
    ImageIcon matIcon;
    ImageIcon anoIcon;
    ImageIcon lemIcon;
    ImageIcon refIcon;
    ImageIcon lembretesIcon;
    ImageIcon anotacoesIcon;
    ImageIcon bibliografiaIcon;

    public CustomIconRenderer() {
        instIcon = MainWindow.criarImagem("/imagens/instituicao.png", "1");
        curIcon = MainWindow.criarImagem("/imagens/curso.png","1");
        semIcon = MainWindow.criarImagem("/imagens/semestre.png","1");
        matIcon = MainWindow.criarImagem("/imagens/materia.png","1");
        anoIcon = MainWindow.criarImagem("/imagens/newP.png","1");
        lemIcon = MainWindow.criarImagem("/imagens/lembrete.png","1");
        refIcon = MainWindow.criarImagem("/imagens/reference.png","1");
        lembretesIcon = MainWindow.criarImagem("/imagens/lembretes.png","1");
        anotacoesIcon = MainWindow.criarImagem("/imagens/anotacoes.png","1");
        bibliografiaIcon =  MainWindow.criarImagem("/imagens/bibliografia.png","1");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
            Object value, boolean sel, boolean expanded, boolean leaf,
            int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel,
                expanded, leaf, row, hasFocus);

        No noAtual = null;
        try {
            noAtual = (No) ((DefaultMutableTreeNode) value).getUserObject();
        } catch (Exception e) {
            if(((DefaultMutableTreeNode) value).toString().equals("Lembretes")){
                setIcon(lembretesIcon);
            }else if(((DefaultMutableTreeNode) value).toString().equals("Anotações")){
                setIcon(anotacoesIcon);
            }else if(((DefaultMutableTreeNode) value).toString().equals("Bibliografia")){
                setIcon(bibliografiaIcon);
            }
                
        }

        if (noAtual != null) {
            String tipo = noAtual.getTipo();
            // check whatever you need to on the node user object
            if (tipo.equals("INS")) {
                setIcon(instIcon);
            } else if (tipo.equals("CUR")) {
                setIcon(curIcon);
            } else if (tipo.equals("SEM")) {
                setIcon(semIcon);
            } else if (tipo.equals("MAT")) {
                setIcon(matIcon);
            } else if (tipo.equals("ANO")) {
                setIcon(anoIcon);
            } else if (tipo.equals("PRO")) {
                setIcon(lemIcon);
            } else if (tipo.equals("TRA")) {
                setIcon(lemIcon);                
                if(noAtual.getTrabAtual().isConcluido()){
                    setForeground(Color.GREEN);
                }else{
                    setForeground(Color.RED);
                }
            } else {
                setIcon(refIcon);
            }

        }

        return this;
    }
}