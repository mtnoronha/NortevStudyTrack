package control;

import gui.NewChoose;
import gui.NewSubject;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import logic.Course;
import logic.Institution;
import logic.Subject;
import logic.Semester;
import other.Messages;

public class NewSubjectControl implements ActionListener, ItemListener {

    private NewSubject jan;
    private bd bd;
    private Institution current_Institution;
    private Course current_Course;
    private Semester current_Semester;

    public NewSubjectControl(NewSubject jan, bd bd) {
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
                jan.comboSemestre.addItem(semestres.get(i).getNome() + " - Ano: " + semestres.get(i).getAno());
            }



        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jan.bCadastrar) {
            try {
                if (!jan.campoMateria.getText().equals("")) {


                    try {
                        //a posição do curso da combo, é a mesma posição em que esta o curso esta na ArrayList de Cursos
                        if (bd.existeMateria(current_Semester, jan.campoMateria.getText()) == -1) {

                            current_Semester.addMateria(new Subject(jan.campoMateria.getText(), jan.campoProfessor.getText()));
                            Messages.mensagemSucesso(Messages.sucessoCadastro, jan);


                            bd.salvar();

                            NewChoose nCC = new NewChoose(bd);
                            nCC.setVisible(true);
                            jan.dispose();

                        } else {
                            Messages.mensagemErro("Esta Matéria já existe.", jan);
                        }
                    } catch (NullPointerException ex) {
                        Messages.mensagemErro(Messages.erroCombos, jan);
                    }


                } else {
                    Messages.mensagemErro("Digite o nome da matéria.", jan);
                }

            } catch (Exception exxx) {
                Messages.mensagemErro("Nenhuma instituição, curso ou semestre cadastrado(a).", jan);
            }
        } else if (e.getSource() == jan.bFechar) {
            jan.dispose();
        }


    }

    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == jan.comboCurso) {
            jan.comboSemestre.removeAllItems();
            try {
                current_Course = current_Institution.getCursos().get(jan.comboCurso.getSelectedIndex());
            } catch (Exception exx) {
                current_Course = null;
            }

            preencheComboSemestre();

        } else if (e.getSource() == jan.comboSemestre) {

            try {
                current_Semester = current_Course.getSemestres().get(jan.comboSemestre.getSelectedIndex());
            } catch (Exception exxx) {
                current_Semester = null;
            }

        } else if (e.getSource() == jan.comboInstituicao) {

            try {
                current_Institution = bd.instituicoes.get(jan.comboInstituicao.getSelectedIndex());
            } catch (Exception exxx) {
            }

            jan.comboCurso.removeAllItems();
            preencheComboCurso();

        }

    }
}
