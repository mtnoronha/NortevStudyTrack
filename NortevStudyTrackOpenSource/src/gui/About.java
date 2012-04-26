package gui;

public class About extends javax.swing.JFrame {


    public About() {
        super("Sobre...");
        initComponents();
        setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("<html> <head>   \t </head> <body>  \t <div>   <span><font size=\"14pt\"><b>Nortev Study Track</b></font></span><br>&#160;&#160;    Software desenvolvido com o intuito de ajudar nossos clientes com v&#225;rias    atividadades<br>durante sua rotina escolar. Este programa possui    cadastros de Institui&#231;&#245;es, Cursos, </div> <div>   Semestres, Materias, Anota&#231;&#245;es, Refer&#234;ncias Bibliogr&#225;fica e Lembretes de    Provas e </div> <div>   Trabalhos. <br><br><span><font size=\"14pt\"><b>Desenvolvimento</b></font></span><br>&#160;&#160;&#160;    Software desenvolvido por <span><i>Marco T&#250;lio Jacovine Noronha</i></span>    &lt;<span><font color=\"rgb(0, 0, 205)\">mtnoronha7@hotmail.com</font></span>&gt;,<br>como    ferramenta auxiliar da <span><i>Nortev Solutions</i></span> &lt;<a href=\"http://www.nortev.net63.net/\"><u><span><font color=\"rgb(0, 0, 205)\">http://www.nortev.net63.net/</font></span></u></a>&gt;.<br><br><span><font size=\"14pt\"><b>Licen&#231;a</b></font></span><br>&#160;&#160;&#160;    Este software &#233; freeware e est&#225; livre para&#160; distribu&#237;&#231;&#227;o. Este programa    n&#227;o cont&#233;m nenhum tipo<br>de garantia. O risco e os problemas    causados pelo seu uso s&#227;o por conta do usu&#225;rio.<br><br> </div>   </body> </html>");

        jButton1.setText("Fechar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
