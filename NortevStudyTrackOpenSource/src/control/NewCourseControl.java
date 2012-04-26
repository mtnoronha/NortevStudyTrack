package control;

import gui.NewCourse;
import gui.NewSemester;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import logic.Course;
import logic.Institution;
import other.Messages;

public class NewCourseControl implements ActionListener, ItemListener {

    private NewCourse jan;
    private bd bd;
    private Institution current_Institution;

    public NewCourseControl(NewCourse jan, bd bd) {
        this.jan = jan;
        this.bd = bd;

    }

    public void preencheComboInst() {
        for (int i = 0; i < bd.instituicoes.size(); i++) {
            jan.comboInstituicao.addItem(bd.instituicoes.get(i).getNome());
        }
    }

    public void itemStateChanged(ItemEvent e) {
        // TODO add your handling code here:
        try {
            current_Institution = bd.instituicoes.get(jan.comboInstituicao.getSelectedIndex());
        } catch (Exception ex) {
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jan.bCadastrar) {
            try {
                if (!jan.campoCurso.getText().equals("")) {

                    //a posição da instituicao da combo, é a mesma posição em que esta instituicao esta na ArrayList de Instituicoes
                    if (bd.existeCurso(current_Institution, jan.campoCurso.getText()) == -1) {

                        current_Institution.addCurso(new Course(jan.campoCurso.getText()));
                        Messages.mensagemSucesso(Messages.sucessoCadastro, jan);

                        bd.salvar();
                        
                        NewSemester nS = new NewSemester(bd);
                        nS.setVisible(true);
                        jan.dispose();
                    } else {
                        Messages.mensagemErro("Este curso já existe.", jan);
                    }

                } else {
                    Messages.mensagemErro("Digite o nome do Curso.", jan);
                }
            } catch (Exception exx) {
                Messages.mensagemErro("Nenhuma instituição cadastrada.", jan);
            }

        } else if (e.getSource() == jan.bFechar) {


            jan.dispose();
        }
    }
}
