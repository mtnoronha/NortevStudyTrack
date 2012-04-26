
package gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import logic.Note;
import net.atlanticbb.tantlinger.shef.HTMLEditorPane;
import net.miginfocom.swing.MigLayout;

public class NoteViewer extends javax.swing.JFrame {

    private HTMLEditorPane editor;
    private Note current;
    private String[] searchingKeyWords;
    
    public NoteViewer(Note note, String searching) {
        super("..:: Visualizar anotação ::..");
        initComponents();
        setLocationRelativeTo(null);
        current = note;
        
        if(!searching.contains("\"")){
            searchingKeyWords = searching.split(" ");
        }else{
            searchingKeyWords = new String[1];
            searchingKeyWords[0] = searching;
        }
        editor = new HTMLEditorPane();
        this.setLayout(new MigLayout("fill"));
        this.add(editor,"grow");        
        setNote();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 732, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 502, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void setNote() {

        StringBuilder processedText = new StringBuilder();
        String text = current.getConteudo();

        for (int i = 0; i < searchingKeyWords.length; i++) {           
            
            searchingKeyWords[i] = searchingKeyWords[i].replaceAll("\"", "");
                            
            Pattern p = Pattern.compile(searchingKeyWords[i], Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(text);
            StringBuffer sb = new StringBuffer();

            if (!searchingKeyWords[i].equals("")) {
                while (m.find()) {
                    m.appendReplacement(sb, "<b><font color=\"#ff0000\">" + searchingKeyWords[i] + "</font></b>");
                }
            }

            m.appendTail(sb);
            if (!sb.toString().isEmpty()) {
                text = sb.toString();
            }

            if (i == searchingKeyWords.length-1) {
                processedText.append(sb.toString());
            }
        }

        editor.setText(processedText.toString());
        editor.setCaretPosition(0);
    }
}
