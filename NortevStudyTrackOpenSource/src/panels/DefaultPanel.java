package panels;

import java.io.File;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;


public class DefaultPanel extends javax.swing.JPanel {

    //mostrar a pagina default do programa...
    JEditorPane mostrarDefault;
    
    //ScrollPane que vai guardar o mostrarDefault acima
    JScrollPane htmlView;

    public DefaultPanel() {
        initComponents();

        mostrarDefault = new JEditorPane();
        mostrarDefault.setEditable(false);
        htmlView = new JScrollPane(mostrarDefault);

        setLayout(new MigLayout("fill"));
        add(htmlView, "grow");
        setarPagina();

    }

      public void setarPagina() {

        try {
            File pag = new File("lib\\main.html");
//            System.out.println("file:///"+pag.getAbsolutePath());
            mostrarDefault.setPage("file:///"+pag.getAbsolutePath());
            
            //mostrarDefault.setPage("http://dl.dropbox.com/u/15625458/Nortev%20Study%20Track%20Project/teste.html");
            
        } catch (IOException ex) {
            System.out.println("pqp!! \n" + ex);
            ex.printStackTrace();
            //System.exit(1);
        }


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
