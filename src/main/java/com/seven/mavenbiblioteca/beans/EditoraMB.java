package com.seven.mavenbiblioteca.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import com.seven.mavenbiblioteca.logica.LogicaEditora;
import com.seven.mavenbiblioteca.modelo.Editora;
import com.seven.mavenbiblioteca.modelo.Endereco;

@ManagedBean
@RequestScoped
public class EditoraMB {

    private Editora editora;
    private Endereco endereco;
    private final LogicaEditora logicaEditora;
    private List<Editora> editoras;

    public EditoraMB() {
        editora = new Editora();
        endereco = new Endereco();
        logicaEditora = new LogicaEditora();
        editoras = logicaEditora.listEditoras();
    }

    public void novaEditora() {
        endereco.setCep(logicaEditora.removerMascara(endereco.getCep()));
        editora.setEndereco(endereco);
        logicaEditora.novoEditora(editora);
        editoras = logicaEditora.listEditoras();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EditoraMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alterarEditora() {
        endereco.setCep(logicaEditora.removerMascara(endereco.getCep()));
        editora.setEndereco(endereco);
        logicaEditora.alterarEditora(editora);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EditoraMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirEditora() {
        editora.setEndereco(endereco);
        logicaEditora.excluirEditora(editora);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EditoraMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarEditora() {
        editora = logicaEditora.pesquisarEditora(editora);

        if ((editora == null)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(EditoraMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            endereco = editora.getEndereco();

            NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "mostrarEditora");
        }
    }

    /*public void pesquisarEditoras(){
     editoras = logicaEditora.listarEditora(editora);
     }*/
    /**
     * @return the editora
     */
    public Editora getEditora() {
        return editora;
    }

    /**
     * @param editora the editora to set
     */
    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the logicaEditora
     */
    public LogicaEditora getLogicaEditora() {
        return logicaEditora;
    }

    /**
     * @return the editoras
     */
    public List<Editora> getEditoras() {
        return editoras;
    }

    /**
     * @param editoras the editoras to set
     */
    public void setEditoras(ArrayList<Editora> editoras) {
        this.editoras = editoras;
    }

}
