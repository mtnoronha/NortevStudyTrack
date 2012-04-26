package control;

import gui.MainWindow;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import logic.*;
import other.Messages;

public class TreeControl implements MouseListener {

    private bd bd;
    private MainWindow jan;
    private DefaultMutableTreeNode noSel;
    private No atual;

    public TreeControl(MainWindow jan, bd bd) {
        this.bd = bd;
        this.jan = jan;
    }

    public void mouseClicked(MouseEvent e) {
        TreePath tp = MainWindow.arvore.getPathForLocation(e.getX(), e.getY());
        if (tp == null) {
            return;
        }

        noSel = (DefaultMutableTreeNode) tp.getLastPathComponent();


        boolean raiz = false;
        MainWindow.arvore.setSelectionPath(tp);
        JPopupMenu popup = new JPopupMenu();

        try {
            atual = (No) noSel.getUserObject();
        } catch (ClassCastException exx) {
            //ou ele selecionou a RAIZ, ou a pasta "Anotações", "Lembretes", ou "Referências!!
            raiz = noSel.isRoot();
            atual = null;
            if (e.getButton() == MouseEvent.BUTTON3) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU PASTA LEMBRETES                
//------------------------------------------------------------------------------------------------
                if (noSel.toString().equals("Lembretes")) {
                    JMenuItem item2 = new JMenuItem("Adicionar lembrete de prova", MainWindow.criarImagem("/imagens/addLembrete.png", "1"));
                    JMenuItem item3 = new JMenuItem("Adicionar lembrete de trabalho", MainWindow.criarImagem("/imagens/addLembrete.png", "1"));


                    item2.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.setarPainel(MainWindow.LEMBRETE_PROVA);

                            DefaultMutableTreeNode pai = (DefaultMutableTreeNode) noSel.getParent();
                            No mate = (No) pai.getUserObject();
                            MainWindow.painelProva.getControl().setMateria(mate.getMatAtual());
                            MainWindow.painelProva.getControl().setNoAdd(noSel);
                        }
                    });

                    item3.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.setarPainel(MainWindow.LEMBRETE_TRABALHO);
                            DefaultMutableTreeNode pai = (DefaultMutableTreeNode) noSel.getParent();
                            No mate = (No) pai.getUserObject();
                            MainWindow.painelTrabalho.getControl().setMateria(mate.getMatAtual());

                            MainWindow.painelTrabalho.getControl().setNoAdd(noSel);
                        }
                    });

                    popup.add(item2);
                    popup.add(item3);

                } else if (noSel.toString().equals("Anotações")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU PASTA ANOTAÇÕES                
//------------------------------------------------------------------------------------------------
                    JMenuItem item = new JMenuItem("Adicionar anotação", MainWindow.criarImagem("/imagens/addAnotacao.png", "1"));


                    item.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.painelAnotacao.getControl().setNoAdd(noSel);
                            DefaultMutableTreeNode pai = (DefaultMutableTreeNode) noSel.getParent();
                            No mate = (No) pai.getUserObject();
                            MainWindow.painelAnotacao.getControl().setMateria(mate.getMatAtual());

                            MainWindow.setarPainel(MainWindow.EDITOR);

                        }
                    });

                    popup.add(item);

                } else if (noSel.toString().equals("Bibliografia")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU PASTA BIBLIOGRAFIA                
//------------------------------------------------------------------------------------------------
                    JMenuItem item4 = new JMenuItem("Adicionar bibliografia", MainWindow.criarImagem("/imagens/reference.png", "1"));
                    item4.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.setarPainel(MainWindow.REF);

                            MainWindow.painelBibliografia.getControl().setNoAdd(noSel);
                            DefaultMutableTreeNode pai = (DefaultMutableTreeNode) noSel.getParent();
                            No mate = (No) pai.getUserObject();
                            MainWindow.painelBibliografia.getControl().setSubject(mate.getMatAtual());


                        }
                    });

                    popup.add(item4);
                }
            }
        }


        if (e.getButton() == MouseEvent.BUTTON3) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU PASTA RAIZ              
//------------------------------------------------------------------------------------------------            
            if (raiz) {
                JMenuItem item = new JMenuItem("Adicionar instituição", MainWindow.criarImagem("/imagens/instituicao.png", "1"));

                item.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        boolean repetir = true;

                        while (repetir) {
                            String nome = Messages.mensagemInput("Digite o nome da Instituição", "Nova instituição", jan, "/imagens/instituicao.png");
                            if (nome != null) {
                                if (bd.existeInstituicao(nome) == -1) {

                                    Institution nova = new Institution(nome);
                                    bd.instituicoes.add(nova);

                                    No no = new No("INS");
                                    no.setInstAtual(nova);

                                    jan.addNoInstArvore(no);
                                    ((DefaultTreeModel) MainWindow.arvore.getModel()).reload();

                                    Messages.mensagemSucesso(Messages.sucessoCadastro, jan);


                                    bd.salvar();

                                    repetir = false;
                                } else {
                                    Messages.mensagemErro("Esta instituição já existe!", jan);
                                }
                            } else {
                                repetir = false;
                            }
                        }
                    }
                });


                popup.add(item);
            } else if (atual != null) {


                if (atual.getTipo().equals("INS")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UMA INSTITUICAO                
//------------------------------------------------------------------------------------------------                    
                    JMenuItem item = new JMenuItem("Adicionar curso", MainWindow.criarImagem("/imagens/curso.png", "1"));
                    JMenuItem item2 = new JMenuItem("Excluir Instituição", MainWindow.criarImagem("/imagens/delete.png", "1"));
                    
                    item2.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            int resp;
                            resp = JOptionPane.showConfirmDialog(jan, "Deseja mesmo excluir esta Instituição?\n.Todo o seu conteúdo didático também será excluído.", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);

                            //se for SIM - YES
                            if (resp == 0) {
                                bd.getInstituicoes().remove(atual.getInstAtual());
                                ((DefaultTreeModel) MainWindow.arvore.getModel()).removeNodeFromParent(noSel);
                                ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noSel.getParent());
                                bd.salvar();
                            }
                        }
                    });
                    
                    
                    item.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            boolean repetir = true;

                            while (repetir) {
                                String nome = Messages.mensagemInput("Digite o nome do Curso", "Novo curso", jan, "/imagens/curso.png");
                                if (nome != null) {
                                    if (bd.existeCurso(atual.getInstAtual(), nome) == -1) {

                                        Course novo = new Course(nome);
                                        atual.getInstAtual().addCurso(novo);

                                        No no = new No("CUR");
                                        no.setCurAtual(novo);

                                        DefaultMutableTreeNode i = new DefaultMutableTreeNode(no);
                                        noSel.add(i);
//                                        ((DefaultTreeModel) Jan_Principal.arvore.getModel()).reload();
                                        ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noSel);
                                        Messages.mensagemSucesso(Messages.sucessoCadastro, jan);


    
                                        bd.salvar();

                                        repetir = false;
                                    } else {
                                        Messages.mensagemErro("Este curso já existe!", jan);
                                    }
                                } else {
                                    repetir = false;
                                }
                            }
                        }
                    });
                    
                                        
                    
                    popup.add(item);
                    popup.add(new JSeparator());
                    popup.add(item2);
                    
                    
                } else if (atual.getTipo().equals("CUR")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UM CURSO                
//------------------------------------------------------------------------------------------------                    
                    JMenuItem item = new JMenuItem("Adicionar semestre", MainWindow.criarImagem("/imagens/semestre.png", "1"));
                    JMenuItem item2 = new JMenuItem("Excluir Curso", MainWindow.criarImagem("/imagens/delete.png", "1"));
                    
                    item2.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            int resp;
                            resp = JOptionPane.showConfirmDialog(jan, "Deseja mesmo excluir este Curso?\n.Todo o seu conteúdo didático também será excluído.", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);

                            //se for SIM - YES
                            if (resp == 0) {
                                DefaultMutableTreeNode pai = (DefaultMutableTreeNode) noSel.getParent();
                                pai.remove(noSel);
                                No noPai = (No) pai.getUserObject();
                                noPai.getInstAtual().getCursos().remove(atual.getCurAtual());
                                ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(pai);
                                bd.salvar();
                            }
                        }
                    });
                    
                    
                    item.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            boolean repetir = true;

                            while (repetir) {
                                String nome = Messages.mensagemInput("Digite o Semestre", "Novo semestre", jan, "/imagens/semestre.png");
                                int ano = -1;
                                try {
                                    ano = Integer.parseInt(Messages.mensagemInput("Digite o ano", "Ano do semestre", jan, "/imagens/semestre.png"));
                                } catch (NumberFormatException exx) {
                                    Messages.mensagemErro("Digite um ano válido.", jan);
                                }


                                if (nome != null && ano != -1) {
                                    if (bd.existeSemestre(atual.getCurAtual(), nome, ano) == -1) {

                                        Semester novo = new Semester(nome, ano);
                                        atual.getCurAtual().addSemestre(novo);

                                        No no = new No("SEM");
                                        no.setSemAtual(novo);

                                        DefaultMutableTreeNode i = new DefaultMutableTreeNode(no);
                                        noSel.add(i);


                                        ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noSel);


                                        Messages.mensagemSucesso(Messages.sucessoCadastro, jan);


    
                                        bd.salvar();

                                        repetir = false;
                                    } else {
                                        Messages.mensagemErro("Este semestre já existe!", jan);
                                    }
                                } else {
                                    repetir = false;
                                }
                            }
                        }
                    });

                    popup.add(item);
                    popup.add(new JSeparator());
                    popup.add(item2);
                    
                } else if (atual.getTipo().equals("SEM")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UM SEMESTRE               
//------------------------------------------------------------------------------------------------                    
                    JMenuItem item = new JMenuItem("Adicionar materia", MainWindow.criarImagem("/imagens/materia.png", "1"));
                    JMenuItem item2 = new JMenuItem("Excluir Semestre", MainWindow.criarImagem("/imagens/delete.png", "1"));
                    
                    item2.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            int resp;
                            resp = JOptionPane.showConfirmDialog(jan, "Deseja mesmo excluir este Semestre?\n.Todo o seu conteúdo didático também será excluído.", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);

                            //se for SIM - YES
                            if (resp == 0) {
                                DefaultMutableTreeNode pai = (DefaultMutableTreeNode) noSel.getParent();
                                pai.remove(noSel);
                                No noPai = (No) pai.getUserObject();
                                noPai.getCurAtual().getSemestres().remove(atual.getSemAtual());
                                ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(pai);
                                bd.salvar();
                            }
                        }
                    });

                    item.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            boolean repetir = true;

                            while (repetir) {
                                String nome = Messages.mensagemInput("Digite o nome da matéria", "Nova matéria", jan, "/imagens/materia.png");
                                String prof = Messages.mensagemInput("Digite o nome do professor", "Nova matéria", jan, "/imagens/materia.png");

                                if (nome != null && prof != null) {
                                    if (bd.existeMateria(atual.getSemAtual(), nome) == -1) {

                                        Subject novo = new Subject(nome, prof);
                                        atual.getSemAtual().addMateria(novo);

                                        No no = new No("MAT");
                                        no.setMatAtual(novo);

                                        DefaultMutableTreeNode i = new DefaultMutableTreeNode(no);
                                        DefaultMutableTreeNode anot = new DefaultMutableTreeNode("Anotações");
                                        DefaultMutableTreeNode lem = new DefaultMutableTreeNode("Lembretes");
                                        DefaultMutableTreeNode ref = new DefaultMutableTreeNode("Bibliografia");
                                        i.add(anot);
                                        i.add(lem);
                                        i.add(ref);
                                        noSel.add(i);

                                        ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(noSel);

                                        Messages.mensagemSucesso(Messages.sucessoCadastro, jan);


    
                                        bd.salvar();

                                        repetir = false;
                                    } else {
                                        Messages.mensagemErro("Esta matéria já existe!", jan);
                                    }
                                } else {
                                    repetir = false;
                                }
                            }
                        }
                    });



                    popup.add(item);
                    popup.add(new JSeparator());
                    popup.add(item2);
                    
                } else if (atual.getTipo().equals("MAT")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UMA MATERIA               
//------------------------------------------------------------------------------------------------                    
                    JMenuItem item = new JMenuItem("Adicionar anotação", MainWindow.criarImagem("/imagens/addAnotacao.png", "1"));
                    JMenuItem item2 = new JMenuItem("Adicionar lembrete de prova", MainWindow.criarImagem("/imagens/addLembrete.png", "1"));
                    JMenuItem item3 = new JMenuItem("Adicionar lembrete de trabalho", MainWindow.criarImagem("/imagens/addLembrete.png", "1"));
                    JMenuItem item4 = new JMenuItem("Adicionar bibliografia", MainWindow.criarImagem("/imagens/reference.png", "1"));
                    JMenuItem item5 = new JMenuItem("Excluir Materia", MainWindow.criarImagem("/imagens/delete.png", "1"));
                    
                    item5.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            int resp;
                            resp = JOptionPane.showConfirmDialog(jan, "Deseja mesmo excluir esta Materia?\n.Todo o seu conteúdo didático também será excluído.", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);

                            //se for SIM - YES
                            if (resp == 0) {
                                DefaultMutableTreeNode pai = (DefaultMutableTreeNode) noSel.getParent();
                                pai.remove(noSel);
                                No noPai = (No) pai.getUserObject();
                                noPai.getSemAtual().getMaterias().remove(atual.getMatAtual());
                                ((DefaultTreeModel) MainWindow.arvore.getModel()).reload(pai);
                                bd.salvar();
                            }
                        }
                    });

                    item.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.painelAnotacao.getControl().setMateria(atual.getMatAtual());
                            MainWindow.painelAnotacao.getControl().setNoAdd(noSel.getNextNode());
                            MainWindow.setarPainel(MainWindow.EDITOR);

                        }
                    });


                    item2.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.painelProva.getControl().setMateria(atual.getMatAtual());
                            MainWindow.setarPainel(MainWindow.LEMBRETE_PROVA);

                            DefaultMutableTreeNode a = null;
                            Enumeration aaa = noSel.children();

                            while (aaa.hasMoreElements()) {
                                a = (DefaultMutableTreeNode) aaa.nextElement();
                                if (a.toString().equals("Lembretes")) {
                                    break;
                                }
                            }
                            MainWindow.painelProva.getControl().setNoAdd(a);
                        }
                    });

                    item3.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.painelTrabalho.getControl().setMateria(atual.getMatAtual());
                            MainWindow.setarPainel(MainWindow.LEMBRETE_TRABALHO);

                            DefaultMutableTreeNode a = null;
                            Enumeration aaa = noSel.children();

                            while (aaa.hasMoreElements()) {
                                a = (DefaultMutableTreeNode) aaa.nextElement();
                                if (a.toString().equals("Lembretes")) {
                                    break;
                                }
                            }
                            MainWindow.painelTrabalho.getControl().setNoAdd(a);
                        }
                    });

                    item4.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            MainWindow.painelBibliografia.getControl().setSubject(atual.getMatAtual());
                            MainWindow.setarPainel(MainWindow.REF);

                            DefaultMutableTreeNode a = null;
                            Enumeration aaa = noSel.children();

                            while (aaa.hasMoreElements()) {
                                a = (DefaultMutableTreeNode) aaa.nextElement();
                                if (a.toString().equals("Bibliografia")) {
                                    break;
                                }
                            }

                            MainWindow.painelBibliografia.getControl().setNoAdd(a);


                        }
                    });

                    popup.add(item);
                    popup.add(item2);
                    popup.add(item3);
                    popup.add(item4);
                    popup.add(new JSeparator());
                    popup.add(item5);
                }
            }

            popup.show(e.getComponent(), e.getX(), e.getY());

        } else if (e.getButton() == MouseEvent.BUTTON1) {
            MainWindow.arvore.setSelectionPath(tp);

            if (atual != null) {
                if (atual.getTipo().equals("ANO")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UMA ANOTACAO               
//------------------------------------------------------------------------------------------------                    
                    DefaultMutableTreeNode noMat = (DefaultMutableTreeNode) tp.getParentPath().getParentPath().getLastPathComponent();

                    No noMateria = (No) noMat.getUserObject();


                    MainWindow.painelAnotacao.getControl().setMateria(noMateria.getMatAtual());
                    MainWindow.painelAnotacao.getControl().setAnotacao(atual.getAnotAtual());
                    MainWindow.setarPainel(MainWindow.EDITOR);

                } else if (atual.getTipo().equals("PRO")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UMA PROVA              
//------------------------------------------------------------------------------------------------                    
                    DefaultMutableTreeNode noMat = (DefaultMutableTreeNode) tp.getParentPath().getParentPath().getLastPathComponent();

                    No noMateria = (No) noMat.getUserObject();


                    MainWindow.painelProva.getControl().setMateria(noMateria.getMatAtual());
                    MainWindow.painelProva.getControl().setProva(atual.getProvaAtual());
                    MainWindow.setarPainel(MainWindow.LEMBRETE_PROVA);
                } else if (atual.getTipo().equals("TRA")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UM TRABALHO               
//------------------------------------------------------------------------------------------------
                    DefaultMutableTreeNode noMat = (DefaultMutableTreeNode) tp.getParentPath().getParentPath().getLastPathComponent();

                    No noMateria = (No) noMat.getUserObject();

                    MainWindow.painelTrabalho.getControl().setMateria(noMateria.getMatAtual());
                    MainWindow.painelTrabalho.getControl().setTrabalho(atual.getTrabAtual());
                    MainWindow.setarPainel(MainWindow.LEMBRETE_TRABALHO);


                } else if (atual.getTipo().equals("BIB")) {
//------------------------------------------------------------------------------------------------                
//--------------------------------------------------------------------SELECIONOU UMA BIBLIOGRAFIA              
//------------------------------------------------------------------------------------------------                    
                    DefaultMutableTreeNode noMat = (DefaultMutableTreeNode) tp.getParentPath().getParentPath().getLastPathComponent();

                    No noMateria = (No) noMat.getUserObject();
                    MainWindow.painelBibliografia.getControl().setSubject(noMateria.getMatAtual());
                    MainWindow.painelBibliografia.getControl().setBibliografia(atual.getBibAtual());


                    MainWindow.setarPainel(MainWindow.REF);
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
