package gui;

import panels.*;
import dataBase.bd;
import control.TreeControl;
import java.awt.CardLayout;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import logic.Note;
import logic.Course;
import logic.Institution;
import logic.Subject;
import logic.No;
import logic.Test;
import logic.Biography;
import logic.CustomIconRenderer;
import logic.Semester;
import logic.HomeWork;
import other.Messages;

public class MainWindow extends javax.swing.JFrame {

    //usados para identificar cada painel no cardlayout
    public static final String LEMBRETE_PROVA = "Lembrete Prova";
    public static final String LEMBRETE_TRABALHO = "Lembrete Trabalho";
    public static final String DEFAULT = "Bem vindo";
    public static final String EDITOR = "Visualizar / Criar HTML";
    public static final String REF = "Referencia Bibliografica";
    //paineis
    public static NotePanel painelAnotacao;
    public static HomeWorkPanel painelTrabalho;
    public static TestPanel painelProva;
    public static BiographyPanel painelBibliografia;
    
    
    //Arquivo do bd principal
    public static bd banco;

    //raiz da Arvore
    private DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("NST");
    
    //Formatação de datas
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         
    public MainWindow() {
        super("Nortev Study Track - Plataforma de Ensino");

        //look and feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        banco = new bd();

            
        //tem que ser  executado antes de iniciar os componentes ;)
        try {
            banco.abrir(banco.arqBD);
        } catch (Exception e) {
            //  JOptionPane.showMessageDialog(null, "Não foi possível recuperar arquivo para leitura.\nO arquivo selecionado é um arquivo próprio do Nortev Study Track?!\n\nErro tipo: " + e, "Erro fatal", JOptionPane.ERROR_MESSAGE);            
        }

        painelAnotacao = new NotePanel();
        painelTrabalho = new HomeWorkPanel();
        painelProva = new TestPanel();
        painelBibliografia = new BiographyPanel();
        
        //montar a arvore de acordo com o arquivo carregado
        carregarJTree();
        
        
        //initComponents do netbeans
        initComponents();

        //icone da aplicação
        this.setIconImage(criarImagem("/imagens/studyTrack.png", "Nortev Study Track").getImage());

        //fica no meio da tela
        setLocationRelativeTo(null);

        //aplicação em 'maximizada'
        setExtendedState(MAXIMIZED_BOTH);
        //setResizable(false);

        //cria paineis e configura
        criarPaineisConfigura();
        
        setarPainel(DEFAULT);
        
        //Controle da arvore (Eventos)
        TreeControl control = new TreeControl(this,banco);
        arvore.addMouseListener(control);

        //Modificar os icones de cada nó
        CustomIconRenderer treeRenderer = new CustomIconRenderer();
        arvore.setCellRenderer(treeRenderer);

    }
    
    //Adicionar um No novo que representa uma instituicao!
    public void addNoInstArvore(No no){
            DefaultMutableTreeNode i = new DefaultMutableTreeNode(no);
            raiz.add(i);
    }
    
    private void carregarJTree() {
        ArrayList<Institution> instituicoes = banco.getInstituicoes();

        for (Institution instituicao : instituicoes) {
            No inst = new No("INS");
            inst.setInstAtual(instituicao);
            
            
            DefaultMutableTreeNode i = new DefaultMutableTreeNode(inst);
            raiz.add(i);
            
            
            for (Course curso : instituicao.getCursos()) {
                No cur = new No("CUR");
                cur.setCurAtual(curso);
                DefaultMutableTreeNode c = new DefaultMutableTreeNode(cur);
                i.add(c);
                for (Semester semestre : curso.getSemestres()) {
                    No sem = new No("SEM");
                    sem.setSemAtual(semestre);
                    
                    DefaultMutableTreeNode s = new DefaultMutableTreeNode(sem);
                    c.add(s);
                    for (Subject materia : semestre.getMaterias()) {
                        No mat = new No("MAT");
                        mat.setMatAtual(materia);
                        
                        DefaultMutableTreeNode m = new DefaultMutableTreeNode(mat);
                        s.add(m);
                        DefaultMutableTreeNode anot = new DefaultMutableTreeNode("Anotações");
                        DefaultMutableTreeNode lem = new DefaultMutableTreeNode("Lembretes");
                        DefaultMutableTreeNode ref = new DefaultMutableTreeNode("Bibliografia");
                        m.add(anot);
                        m.add(lem);
                        m.add(ref);

                        ArrayList<Note> lista = materia.getAnotacoes();
                       Collections.sort(lista);
                       
                        for(Note anotacao : lista){
                            No anota = new No("ANO");
                            anota.setAnotAtual(anotacao);
                            anot.add(new DefaultMutableTreeNode(anota));
                        }
                        
                        for(Test prova : materia.getProvas()){
                            No prov = new No("PRO");
                            prov.setProvaAtual(prova);
                            lem.add(new DefaultMutableTreeNode(prov));                            
                        }
                        
                        for(HomeWork trab : materia.getTrabalhos()){
                            No traba = new No("TRA");
                            traba.setTrabAtual(trab);
                            lem.add(new DefaultMutableTreeNode(traba));
                        }
                        
                        for(Biography referencia : materia.getBibliografias()){
                            No refe = new No("BIB");
                            refe.setRefAtual(referencia);
                            ref.add(new DefaultMutableTreeNode(refe));
                        }

                    }
                }
            }
        }
    }

    
    //para outras janelas poderem modificar o painel a ser mostrado.
    public static void setarPainel(String painel) {
        CardLayout cl = (CardLayout) (painelCard.getLayout());
        cl.show(painelCard, painel);
    }

    //criar os Paineis do cardLayout
    public void criarPaineisConfigura() {
        //criando o cardlayout
        painelCard.setLayout(new CardLayout());



        //adicionar paineis ao cardLayout
        painelCard.add(painelAnotacao, EDITOR);
        painelCard.add(new DefaultPanel(), DEFAULT);
        painelCard.add(painelProva, LEMBRETE_PROVA);
        painelCard.add(painelTrabalho, LEMBRETE_TRABALHO);
        painelCard.add(painelBibliografia, REF);

    }

    //criarImagens
    public static ImageIcon criarImagem(String path, String description) {
        URL imageURL = MainWindow.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Imagem não encontrada: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelArvore = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arvore = new javax.swing.JTree(raiz);
        painelCard = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        subNovo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        subAbrir = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        subImportar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        subBackup = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        subFechar = new javax.swing.JMenuItem();
        menuNavegacao = new javax.swing.JMenu();
        subInst = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        subCurso = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        subSem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        subMat = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        subAnot = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        subLemb = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        subRef = new javax.swing.JMenuItem();
        menuUtil = new javax.swing.JMenu();
        subSearchNotes = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        subHelp = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        subLicença = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        subAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        painelArvore.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setViewportView(arvore);

        javax.swing.GroupLayout painelArvoreLayout = new javax.swing.GroupLayout(painelArvore);
        painelArvore.setLayout(painelArvoreLayout);
        painelArvoreLayout.setHorizontalGroup(
            painelArvoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelArvoreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelArvoreLayout.setVerticalGroup(
            painelArvoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelArvoreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelCard.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout painelCardLayout = new javax.swing.GroupLayout(painelCard);
        painelCard.setLayout(painelCardLayout);
        painelCardLayout.setHorizontalGroup(
            painelCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );
        painelCardLayout.setVerticalGroup(
            painelCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setText("Nortev Study Track");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel2.setText("-");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel3.setText("Plataforma de Estudo");

        menuArquivo.setText("Arquivo");

        subNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        subNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/newP.png"))); // NOI18N
        subNovo.setText("Novo");
        subNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subNovoActionPerformed(evt);
            }
        });
        menuArquivo.add(subNovo);
        menuArquivo.add(jSeparator1);

        subAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        subAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/abrir.png"))); // NOI18N
        subAbrir.setText("Abrir ...");
        subAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subAbrirActionPerformed(evt);
            }
        });
        menuArquivo.add(subAbrir);
        menuArquivo.add(jSeparator12);

        subImportar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        subImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/importar.png"))); // NOI18N
        subImportar.setText("importar...");
        subImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subImportarActionPerformed(evt);
            }
        });
        menuArquivo.add(subImportar);
        menuArquivo.add(jSeparator3);

        subBackup.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        subBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/backup.png"))); // NOI18N
        subBackup.setText("Backup");
        subBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subBackupActionPerformed(evt);
            }
        });
        menuArquivo.add(subBackup);
        menuArquivo.add(jSeparator13);

        subFechar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        subFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fechar.png"))); // NOI18N
        subFechar.setText("Fechar");
        subFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subFecharActionPerformed(evt);
            }
        });
        menuArquivo.add(subFechar);

        jMenuBar1.add(menuArquivo);

        menuNavegacao.setText("Navegação");

        subInst.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK));
        subInst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/instituicao.png"))); // NOI18N
        subInst.setText("Nova Instituição");
        subInst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subInstActionPerformed(evt);
            }
        });
        menuNavegacao.add(subInst);
        menuNavegacao.add(jSeparator4);

        subCurso.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        subCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/curso.png"))); // NOI18N
        subCurso.setText("Novo Curso");
        subCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCursoActionPerformed(evt);
            }
        });
        menuNavegacao.add(subCurso);
        menuNavegacao.add(jSeparator5);

        subSem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        subSem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/semestre.png"))); // NOI18N
        subSem.setText("Novo Semestre");
        subSem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subSemActionPerformed(evt);
            }
        });
        menuNavegacao.add(subSem);
        menuNavegacao.add(jSeparator6);

        subMat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
        subMat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/materia.png"))); // NOI18N
        subMat.setText("Nova Matéria");
        subMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMatActionPerformed(evt);
            }
        });
        menuNavegacao.add(subMat);
        menuNavegacao.add(jSeparator7);

        subAnot.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        subAnot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/addAnotacao.png"))); // NOI18N
        subAnot.setText("Nova Anotação");
        subAnot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subAnotActionPerformed(evt);
            }
        });
        menuNavegacao.add(subAnot);
        menuNavegacao.add(jSeparator8);

        subLemb.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        subLemb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/addLembrete.png"))); // NOI18N
        subLemb.setText("Novo Lembrete");
        subLemb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subLembActionPerformed(evt);
            }
        });
        menuNavegacao.add(subLemb);
        menuNavegacao.add(jSeparator9);

        subRef.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        subRef.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/reference.png"))); // NOI18N
        subRef.setText("Nova Referencia");
        subRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subRefActionPerformed(evt);
            }
        });
        menuNavegacao.add(subRef);

        jMenuBar1.add(menuNavegacao);

        menuUtil.setText("Utilitários");

        subSearchNotes.setText("Pesquisar anotações");
        subSearchNotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subSearchNotesActionPerformed(evt);
            }
        });
        menuUtil.add(subSearchNotes);

        jMenuBar1.add(menuUtil);

        menuAjuda.setText("Ajuda");

        subHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        subHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        subHelp.setText("Ajuda");
        subHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subHelpActionPerformed(evt);
            }
        });
        menuAjuda.add(subHelp);
        menuAjuda.add(jSeparator10);

        subLicença.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        subLicença.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/licenca.png"))); // NOI18N
        subLicença.setText("Licença");
        subLicença.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subLicençaActionPerformed(evt);
            }
        });
        menuAjuda.add(subLicença);
        menuAjuda.add(jSeparator11);

        subAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        subAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sobre.png"))); // NOI18N
        subAbout.setText("Sobre");
        subAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subAboutActionPerformed(evt);
            }
        });
        menuAjuda.add(subAbout);

        jMenuBar1.add(menuAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelArvore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelArvore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void subInstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subInstActionPerformed
        NewInstittution nI = new NewInstittution(banco);
        nI.setVisible(true);
    }//GEN-LAST:event_subInstActionPerformed

    private void subCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCursoActionPerformed
        // TODO add your handling code here:
        NewCourse nC = new NewCourse(banco);
        nC.setVisible(true);
    }//GEN-LAST:event_subCursoActionPerformed

    private void subSemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subSemActionPerformed
        // TODO add your handling code here:
        NewSemester nS = new NewSemester(banco);
        nS.setVisible(true);

    }//GEN-LAST:event_subSemActionPerformed

    private void subMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMatActionPerformed
        // TODO add your handling code here:
        NewSubject nM = new NewSubject(banco);
        nM.setVisible(true);
    }//GEN-LAST:event_subMatActionPerformed

    private void subAnotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subAnotActionPerformed
        // TODO add your handling code here:
        NewChoose nCc = new NewChoose(banco);
        nCc.setVisible(true);

    }//GEN-LAST:event_subAnotActionPerformed

    private void subLembActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subLembActionPerformed
        // TODO add your handling code here:
        NewChoose nCc = new NewChoose(banco);
        nCc.setVisible(true);
    }//GEN-LAST:event_subLembActionPerformed

    private void subRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subRefActionPerformed
        // TODO add your handling code here:
        NewChoose nCc = new NewChoose(banco);
        nCc.setVisible(true);
    }//GEN-LAST:event_subRefActionPerformed

    private void subAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subAbrirActionPerformed
        // TODO add your handling code here:
        JFileChooser arquivo = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Nortev Study Track", "nst");
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        arquivo.setFileFilter(filter);
        arquivo.setAcceptAllFileFilterUsed(false);
        arquivo.setMultiSelectionEnabled(false);
        File arq;


        if (arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION) {
            arq = new File(arquivo.getSelectedFile().getPath());
            try {
                banco.abrir(arq);
                carregarJTree();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível recuperar arquivo para leitura.\nO arquivo selecionado é um arquivo próprio do Nortev Study Track?!\n\nErro tipo: " + e, "Erro fatal", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Arquivo inválido!", "Erro", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_subAbrirActionPerformed

    private void subBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subBackupActionPerformed
        // TODO add your handling code here:
        JFileChooser arquivo = new JFileChooser();
        arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File arq;

        if (arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION) {
            arq = new File(arquivo.getSelectedFile().getPath());
            banco.backup(arq);
        } else {
            JOptionPane.showMessageDialog(null, "Escolha um diretório!!", "Erro", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_subBackupActionPerformed

    private void subNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subNovoActionPerformed
        // TODO add your handling code here:
        int resp;
        resp = JOptionPane.showConfirmDialog(this, "Todos os dados do banco atual serão perdidos. Tem certeza que quer continuar?", "Confirmação", JOptionPane.YES_NO_OPTION);

        //se for SIM - YES
        if (resp == 0) {
            banco.novo();
            raiz.removeAllChildren();
            ((DefaultTreeModel) MainWindow.arvore.getModel()).reload();
             
        }


    }//GEN-LAST:event_subNovoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void subFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subFecharActionPerformed
        // TODO add your handling code here:

        System.exit(0);

    }//GEN-LAST:event_subFecharActionPerformed

    private void subImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subImportarActionPerformed
        // TODO add your handling code here:
        JFileChooser arquivo = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Nortev Study Track", "nst");
        arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        arquivo.setFileFilter(filter);
        arquivo.setAcceptAllFileFilterUsed(false);
        arquivo.setMultiSelectionEnabled(false);
        File arq;


        if (arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION) {
            arq = new File(arquivo.getSelectedFile().getPath());
            banco.importar(arq);
        } else {
            JOptionPane.showMessageDialog(null, "Arquivo inválido!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_subImportarActionPerformed

    private void subAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subAboutActionPerformed
        // TODO add your handling code here:
        new About().setVisible(true);
    }//GEN-LAST:event_subAboutActionPerformed

    private void subSearchNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subSearchNotesActionPerformed
        // TODO add your handling code here:
        SearchKeywordsNotes.getInstance().setVisible(true);
    }//GEN-LAST:event_subSearchNotesActionPerformed

    private void subLicençaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subLicençaActionPerformed
        // TODO add your handling code here:
         new About().setVisible(true);
    }//GEN-LAST:event_subLicençaActionPerformed

    private void subHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subHelpActionPerformed
        // TODO add your handling code here:
        Messages.mensagemErro("Oops, ainda não foi implementado!", this);
    }//GEN-LAST:event_subHelpActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTree arvore;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenu menuNavegacao;
    private javax.swing.JMenu menuUtil;
    private javax.swing.JPanel painelArvore;
    private static javax.swing.JPanel painelCard;
    private javax.swing.JMenuItem subAbout;
    private javax.swing.JMenuItem subAbrir;
    private javax.swing.JMenuItem subAnot;
    private javax.swing.JMenuItem subBackup;
    private javax.swing.JMenuItem subCurso;
    private javax.swing.JMenuItem subFechar;
    private javax.swing.JMenuItem subHelp;
    private javax.swing.JMenuItem subImportar;
    private javax.swing.JMenuItem subInst;
    private javax.swing.JMenuItem subLemb;
    private javax.swing.JMenuItem subLicença;
    private javax.swing.JMenuItem subMat;
    private javax.swing.JMenuItem subNovo;
    private javax.swing.JMenuItem subRef;
    private javax.swing.JMenuItem subSearchNotes;
    private javax.swing.JMenuItem subSem;
    // End of variables declaration//GEN-END:variables
}
