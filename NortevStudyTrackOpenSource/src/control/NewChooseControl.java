package control;

import gui.MainWindow;
import gui.NewChoose;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import logic.Course;
import logic.Institution;
import logic.Subject;
import logic.Semester;
import other.Messages;

public class NewChooseControl implements ActionListener, ItemListener {

    private NewChoose jan;
    private bd bd;
    private Institution current_Institution;
    private Course current_Course;
    private Semester current_Semester;
    private Subject current_Subject;

    public NewChooseControl(NewChoose jan, bd bd) {
        this.jan = jan;
        this.bd = bd;
    }

    public void preencheComboInst() {
        for (int i = 0; i < bd.instituicoes.size(); i++) {
            jan.comboInstituicao.addItem(bd.instituicoes.get(i).getNome());
        }
    }

    public void preencheComboCurso() {
        try {
            ArrayList<Course> cursos = current_Institution.getCursos();

            for (int i = 0; i < cursos.size(); i++) {
                jan.comboCurso.addItem(cursos.get(i).getNome());
            }

        } catch (Exception e) {
        }
    }

    public void preencheComboSemestre() {
        try {
            ArrayList<Semester> semestres = current_Course.getSemestres();


            for (int i = 0; i < semestres.size(); i++) {
                jan.comboSemestre.addItem(semestres.get(i).getNome());
            }



        } catch (Exception e) {
        }
    }

    public void preencheComboMateria() {
        try {
            ArrayList<Subject> materias = current_Semester.getMaterias();


            for (int i = 0; i < materias.size(); i++) {
                jan.comboMateria.addItem(materias.get(i).getNome());
            }

        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jan.bCriar) {

            if (current_Subject != null) {
                if (jan.opAnot.isSelected()) {
                    MainWindow.painelAnotacao.getControl().setMateria(current_Subject);
                    MainWindow.setarPainel(MainWindow.EDITOR);
                    jan.dispose();
                }

                if (jan.opProv.isSelected()) {
                    MainWindow.painelProva.getControl().setMateria(current_Subject);
                    MainWindow.setarPainel(MainWindow.LEMBRETE_PROVA);
                    jan.dispose();
                    
                }

                if (jan.opTrab.isSelected()) {
                    MainWindow.painelTrabalho.getControl().setMateria(current_Subject);
                    MainWindow.setarPainel(MainWindow.LEMBRETE_TRABALHO);
                    jan.dispose();
                }

                if (jan.opRef.isSelected()) {
                    MainWindow.painelBibliografia.getControl().setSubject(current_Subject);
                    MainWindow.setarPainel(MainWindow.REF);
                    jan.dispose();
                            
                }

                JOptionPane.showMessageDialog(jan, "\nInstituicao: " + current_Institution.getNome() + "\nCurso: " + current_Course.getNome() + "\nSemestre: " + current_Semester.getNome() + "\nMateria: " + current_Subject.getNome(), "Local para gravar", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(jan, Messages.erroCombos, "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == jan.bFechar) {
            jan.dispose();
        }



    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == jan.comboSemestre) {
            jan.comboMateria.removeAllItems();


            try {
                current_Semester = current_Course.getSemestres().get(jan.comboSemestre.getSelectedIndex());
            } catch (Exception ex) {
                current_Semester = null;
            }

            preencheComboMateria();


        } else if (e.getSource() == jan.comboCurso) {
            jan.comboSemestre.removeAllItems();
            try {
                current_Course = current_Institution.getCursos().get(jan.comboCurso.getSelectedIndex());
            } catch (Exception exx) {
                current_Course = null;
            }

            preencheComboSemestre();
        } else if (e.getSource() == jan.comboInstituicao) {

            try {
                current_Institution = bd.instituicoes.get(jan.comboInstituicao.getSelectedIndex());
            } catch (Exception exxx) {
                current_Institution = null;
            }

            jan.comboCurso.removeAllItems();
            preencheComboCurso();
        } else if (e.getSource() == jan.comboMateria) {

            try {
                current_Subject = current_Semester.getMaterias().get(jan.comboMateria.getSelectedIndex());
            } catch (Exception ex2) {
                current_Subject = null;
            }
        }

    }
}
