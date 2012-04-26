
package gui;

import logic.Subject;
import logic.Course;
import logic.Institution;
import logic.SearchKeywordsNotesTableModel;
import logic.Semester;

public class SearchKeywordsNotes extends javax.swing.JFrame {

    private static SearchKeywordsNotesTableModel model;
    private static SearchKeywordsNotes single;
            
    public static SearchKeywordsNotes getInstance(){
        if(single == null){
            single = new SearchKeywordsNotes();
        }
        
        model.find("");
        return single;
    }
    private SearchKeywordsNotes() {
        super("..:: Procurar por palavras chaves ::..");
        initComponents();
        setLocationRelativeTo(null);
        
        model = new SearchKeywordsNotesTableModel();
        tableResult.setModel(model);
        
        for (Institution inst : MainWindow.banco.getInstituicoes()) {
            for (Course curso : inst.getCursos()) {
                for (Semester semester : curso.getSemestres()) {
                    for (Subject subj : semester.getMaterias()) {
                        model.addListaDeNote(subj.getAnotacoes());
                    }
                }
            }
        }

        model.find("");
               
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        fieldKeywords = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResult = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Palavras chaves");

        fieldKeywords.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                fieldKeywordsCaretUpdate(evt);
            }
        });

        tableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(fieldKeywords, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldKeywords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableResultMouseClicked
        // TODO add your handling code here:
        NoteViewer nv = new NoteViewer(model.getNote(tableResult.getSelectedRow()), fieldKeywords.getText());
        nv.setVisible(true);
    }//GEN-LAST:event_tableResultMouseClicked

    private void fieldKeywordsCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_fieldKeywordsCaretUpdate
        // TODO add your handling code here:
        model.find(fieldKeywords.getText());        
    }//GEN-LAST:event_fieldKeywordsCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField fieldKeywords;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tableResult;
    // End of variables declaration//GEN-END:variables
}
