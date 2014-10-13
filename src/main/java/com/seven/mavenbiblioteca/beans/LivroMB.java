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
import com.seven.mavenbiblioteca.logica.LogicaLivro;
import com.seven.mavenbiblioteca.modelo.Editora;
import com.seven.mavenbiblioteca.modelo.Livro;
import javax.faces.application.FacesMessage;
import org.primefaces.event.SelectEvent;

@ManagedBean
@RequestScoped
public class LivroMB {

    private Livro livro;
    private List livros;
    private final LogicaLivro logicaLivro;
    private Editora editora;
    private int numero;
    private Long id;
    private final FacesContext context;

    public LivroMB() {
        editora = new Editora();
        logicaLivro = new LogicaLivro();
        livro = new Livro();
        livros = logicaLivro.listarLivros();
        context = FacesContext.getCurrentInstance();
    }

    public void novoLivro() {
        if (editora!=null) {
            livro.setEditora(editora);
            for (int i = 0; i < numero; i++) {
                livro.setId(null);
                logicaLivro.novoLivro(livro);
            }
            livro = new Livro();
            editora = new Editora();
            livros = logicaLivro.listarLivros();
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro cadastrado!"));
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Cadastrar primeiro uma Editora"));
        }
    }

    public void pesquisarLivro() {
        livro = logicaLivro.pesqLivroID(id);

        if (livro == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LivroMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            editora = livro.getEditora();
            NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "mostrarLivro");
        }
    }

    public void alterarLivro() {
        livro.setEditora(editora);
        logicaLivro.alterarLivro(livro);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrarLivro.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LivroMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirLivro() {
        livro.setEditora(editora);
        logicaLivro.excluirLivor(livro);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrarLivro.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LivroMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Editora> getEditoras() {
        return logicaLivro.listEditoras();
    }

    public void onRowSelect(SelectEvent event) {
        this.livro = (Livro) event.getObject();
        this.editora = livro.getEditora();
    }

    /**
     * @return the livro
     */
    public Livro getLivro() {
        return livro;
    }

    /**
     * @param livro the livro to set
     */
    public void setLivro(Livro livro) {
        this.livro = livro;
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
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }
}
