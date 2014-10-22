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
import javax.faces.application.FacesMessage;
import org.primefaces.event.SelectEvent;

@ManagedBean
@RequestScoped
public class EditoraMB {

    private Editora editora;
    private Endereco endereco;
    private final LogicaEditora logicaEditora;
    private List<Editora> editoras;
    private final FacesContext context;
    
    private boolean mostrarPainelEditora = false;
    private boolean editoraSucesso = false;
    private boolean editoraAlterada = false;
    private boolean editoraExcluida = false;
    private boolean editoraCadastrada = false;

    public EditoraMB() {
        editora = new Editora();
        endereco = new Endereco();
        logicaEditora = new LogicaEditora();
        editoras = logicaEditora.listEditoras();
        context = FacesContext.getCurrentInstance();
    }

    public void novaEditora() {
        if (logicaEditora.pesquisarEditora(getEditora()) == null) {
            getEndereco().setCep(logicaEditora.removerMascara(getEndereco().getCep()));
            getEditora().setEndereco(getEndereco());
            logicaEditora.novoEditora(getEditora());
            editoras = logicaEditora.listEditoras();

            setEditora(new Editora());
            setEndereco(new Endereco());

            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Editora cadastrada!"));
            setEditoraSucesso(true);
        } else {
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Editora já foi cadastrada"));
            setEditoraCadastrada(true);
        }
        mostrarPainelEditora();
    }

    public void alterarEditora() {
        getEndereco().setCep(logicaEditora.removerMascara(getEndereco().getCep()));
        getEditora().setEndereco(getEndereco());
        logicaEditora.alterarEditora(getEditora());
        setEditora(new Editora());
        setEndereco(new Endereco());
        setEditoraAlterada(true);
        mostrarPainelEditora();

    }

    public void excluirEditora() {
        getEditora().setEndereco(getEndereco());
        logicaEditora.excluirEditora(getEditora());
        setEditora(new Editora());
        setEndereco(new Endereco());

        /*try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrarEditora.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EditoraMB.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        setEditoraExcluida(true);
        mostrarPainelEditora();
    }

    public void pesquisarEditora() {
        setEditora(logicaEditora.pesquisarEditora(getEditora()));

        if ((getEditora() == null)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(EditoraMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            setEndereco(getEditora().getEndereco());

            NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "mostrarEditora");
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.setEditora((Editora) event.getObject());
        this.setEndereco(getEditora().getEndereco());
    }
    
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

    public boolean isMostrarPainelEditora() {
        return mostrarPainelEditora;
    }

    public void setMostrarPainelEditora(boolean mostrarPainelEditora) {
        this.mostrarPainelEditora = mostrarPainelEditora;
    }

    public boolean isEditoraSucesso() {
        return editoraSucesso;
    }

    public void setEditoraSucesso(boolean editoraSucesso) {
        this.editoraSucesso = editoraSucesso;
    }

    public boolean isEditoraAlterada() {
        return editoraAlterada;
    }

    public void setEditoraAlterada(boolean editoraAlterada) {
        this.editoraAlterada = editoraAlterada;
    }

    public boolean isEditoraExcluida() {
        return editoraExcluida;
    }

    public void setEditoraExcluida(boolean editoraExcluida) {
        this.editoraExcluida = editoraExcluida;
    }

    public boolean isEditoraCadastrada() {
        return editoraCadastrada;
    }

    public void setEditoraCadastrada(boolean editoraCadastrada) {
        this.editoraCadastrada = editoraCadastrada;
    }
    
    public void limparCampos()
    {
        setEditora(new Editora());
        setEndereco(new Endereco());
    }
    
    public void mostrarPainelEditora()
    {
        setMostrarPainelEditora(true);
    }
    
    public void esconderPainelEditora()
    {
        setMostrarPainelEditora(false);
    }

}