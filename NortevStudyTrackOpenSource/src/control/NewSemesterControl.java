package control;

import gui.NewSubject;
import gui.NewSemester;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import logic.Course;
import logic.Institution;
import logic.Semester;
import other.Messages;

public class NewSemesterControl implements ActionListener, ItemListener {

    private NewSemester jan;
    private bd bd;
    private Institution current_Institution;
    private Course current_Course;

    public NewSemesterControl(NewSemester jan, bd bd) {
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jan.bCadastrar) {
            try {
                if (!jan.campoSemestre.getText().equals("")) {

                    try {
                        //a posição do curso da combo, é a mesma posição em que esta o curso esta na ArrayList de Cursos
                        int ano = Integer.parseInt(jan.campoAno.getText());
                        if (bd.existeSemestre(current_Course, jan.campoSemestre.getText(), ano) == -1) {

                            current_Course.addSemestre(new Semester(jan.campoSemestre.getText(), ano));
                            Messages.mensagemSucesso(Messages.sucessoCadastro, jan);

                            bd.salvar();

                            NewSubject nM = new NewSubject(bd);
                            nM.setVisible(true);
                            jan.dispose();

                        } else {
                            Messages.mensagemErro("Este semestre já existe.", jan);
                        }
                    } catch (NullPointerException ex) {
                        Messages.mensagemErro(Messages.erroCombos, jan);
                    }

                } else {
                    Messages.mensagemErro("Digite o nome do Semestre.", jan);
                }
            } catch (IndexOutOfBoundsException exxx) {
                Messages.mensagemErro("Nenhuma Instituição ou Curso cadastrada(o).", jan);
            } catch (NumberFormatException exxxx) {
                Messages.mensagemErro("Digite o ano correto.", jan);

            }

        } else if (e.getSource() == jan.bFechar) {

            jan.dispose();
        }


    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == jan.comboInstituicao) {
            jan.comboCurso.removeAllItems();

            try {
                current_Institution = bd.instituicoes.get(jan.comboInstituicao.getSelectedIndex());
            } catch (Exception ex) {
            }

            preencheComboCurso();
        } else if (e.getSource() == jan.comboCurso) {

            try {
                current_Course = current_Institution.getCursos().get(jan.comboCurso.getSelectedIndex());
            } catch (Exception ex) {
                current_Course = null;
            }

        }

    }
}
