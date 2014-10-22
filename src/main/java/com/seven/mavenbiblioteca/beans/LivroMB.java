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
    
    private boolean mostrarPainelMaterial = false;
    private boolean materialSucesso = false;
    private boolean materialAletrado = false;
    private boolean materialExcluido = false;
    private boolean terEditoraCadastrada = false;

    public LivroMB() {
        editora = new Editora();
        logicaLivro = new LogicaLivro();
        livro = new Livro();
        livros = logicaLivro.listarLivros();
        context = FacesContext.getCurrentInstance();
    }

    public void novoLivro() {
        if (getEditora()!=null) {
            getLivro().setEditora(getEditora());
            for (int i = 0; i < numero; i++) {
                getLivro().setId(null);
                logicaLivro.novoLivro(getLivro());
            }
            setLivro(new Livro());
            setEditora(new Editora());
            livros = logicaLivro.listarLivros();
            
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro cadastrado!"));
            setMaterialSucesso(true);
        }else{
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Cadastrar primeiro uma Editora"));
            setTerEditoraCadastrada(true);
        }
        mostrarPainelMaterial();
    }

    public void pesquisarLivro() {
        setLivro(logicaLivro.pesqLivroID(id));

        if (getLivro() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LivroMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            setEditora(getLivro().getEditora());
            NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "mostrarLivro");
        }
    }

    public void alterarLivro() {
        getLivro().setEditora(getEditora());
        logicaLivro.alterarLivro(getLivro());
        /*try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrarLivro.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LivroMB.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        setMaterialAletrado(true);
        mostrarPainelMaterial();
    }

    public void excluirLivro() {
        getLivro().setEditora(getEditora());
        logicaLivro.excluirLivor(getLivro());
        /*try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrarLivro.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LivroMB.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        setMaterialExcluido(true);
        mostrarPainelMaterial();
    }

    public List<Editora> getEditoras() {
        return logicaLivro.listEditoras();
    }

    public void onRowSelect(SelectEvent event) {
        this.setLivro((Livro) event.getObject());
        this.setEditora(getLivro().getEditora());
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

    public boolean isMostrarPainelMaterial() {
        return mostrarPainelMaterial;
    }

    public void setMostrarPainelMaterial(boolean mostrarPainelMaterial) {
        this.mostrarPainelMaterial = mostrarPainelMaterial;
    }

    public boolean isMaterialSucesso() {
        return materialSucesso;
    }

    public void setMaterialSucesso(boolean materialSucesso) {
        this.materialSucesso = materialSucesso;
    }

    public boolean isMaterialAletrado() {
        return materialAletrado;
    }

    public void setMaterialAletrado(boolean materialAletrado) {
        this.materialAletrado = materialAletrado;
    }

    public boolean isMaterialExcluido() {
        return materialExcluido;
    }

    public void setMaterialExcluido(boolean materialExcluido) {
        this.materialExcluido = materialExcluido;
    }

    public boolean isTerEditoraCadastrada() {
        return terEditoraCadastrada;
    }

    public void setTerEditoraCadastrada(boolean terEditoraCadastrada) {
        this.terEditoraCadastrada = terEditoraCadastrada;
    }
    
    public void mostrarPainelMaterial()
    {
        setMostrarPainelMaterial(true);
    }
    
    public void esconderPainelMaterial()
    {
        setMostrarPainelMaterial(false);
    }
    
    public void limparCampos()
    {
        setLivro(new Livro());
        setEditora(new Editora());
    }
}
