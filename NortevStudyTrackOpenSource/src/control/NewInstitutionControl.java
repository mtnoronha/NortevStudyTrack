package control;

import gui.NewCourse;
import gui.NewInstittution;
import dataBase.bd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logic.Institution;
import other.Messages;

public class NewInstitutionControl implements ActionListener {

    private NewInstittution jan;
    private bd bd;

    public NewInstitutionControl(NewInstittution jan, bd bd) {
        this.jan = jan;
        this.bd = bd;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jan.bFechar) {
            jan.dispose();
        } else if (e.getSource() == jan.bCadastrar) {
            if (!jan.campoInstituicao.getText().equals("")) {
                if (bd.existeInstituicao(jan.campoInstituicao.getText()) == -1) {

                    bd.instituicoes.add(new Institution(jan.campoInstituicao.getText()));
                    Messages.mensagemSucesso(Messages.sucessoCadastro, jan);


                    bd.salvar();

                    NewCourse cN = new NewCourse(bd);
                    cN.setVisible(true);
                    jan.dispose();

                } else {
                    Messages.mensagemErro("Esta instituição já existe!", jan);
                }
            } else {
                Messages.mensagemErro("Digite o nome da Instituição!", jan);
            }
        }

    }
}
