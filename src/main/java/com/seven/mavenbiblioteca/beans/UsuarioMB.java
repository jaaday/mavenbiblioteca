package com.seven.mavenbiblioteca.beans;

import com.seven.mavenbiblioteca.logica.LogicaUsusario;
import com.seven.mavenbiblioteca.modelo.Endereco;
import com.seven.mavenbiblioteca.modelo.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@RequestScoped
public class UsuarioMB {

    private Usuario usuario;
    private Endereco endereco;
    private String prioridade;
    private List usuarios;
    private Boolean but;
    private final LogicaUsusario logicaU;
    private final FacesContext context;

    public UsuarioMB() {
        usuario = new Usuario();
        endereco = new Endereco();
        logicaU = new LogicaUsusario();
        usuarios = logicaU.listarUsuario();
        but = false;
        context = FacesContext.getCurrentInstance();
    }

    public void cadastrarUsuario() {
        usuario.setCpf(logicaU.removerMascara(usuario.getCpf()));
        if (logicaU.pesquisarUsuarioCPF(usuario) == null) {
            endereco.setCep(logicaU.removerMascara(endereco.getCep()));
            usuario.setEndereco(endereco);
            usuario.setData_punicao(new Date(1969 - 12 - 31));
            usuario.setPrioridade(logicaU.convertPrioridade(prioridade));
            logicaU.novoUsuario(usuario);
            if(logicaU.validarCPF(usuario.getCpf()))
            {
                usuario = new Usuario();
                endereco = new Endereco();
            }
            usuarios = logicaU.listarUsuario();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário cadastrado!"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "CPF cadastrado ou CPF Inválido!"));
        }
    }

    public void pesquisarUsuario() {
        usuario.setCpf(logicaU.removerMascara(usuario.getCpf()));
        usuario = logicaU.pesquisarUsuarioCPF(usuario);

        if (usuario == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            endereco = usuario.getEndereco();
            prioridade = logicaU.desconverterPrioridade(usuario.getPrioridade());

            NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "mostrarUsuario");
        }
    }

    public void alterarUsuario() {
        usuario.setCpf(logicaU.removerMascara(usuario.getCpf()));
        endereco.setCep(logicaU.removerMascara(endereco.getCep()));
        usuario.setEndereco(endereco);
        usuario.setPrioridade(logicaU.convertPrioridade(prioridade));
        logicaU.alterarUsuario(usuario);

        usuario = new Usuario();
        endereco = new Endereco();
        but = false;

        
        try {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário alterado!"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrarUsuario.xhtml"); 
        } catch (IOException ex) {
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void excluirUsuario() {
        usuario.setEndereco(endereco);
        logicaU.excluirUsuario(usuario);
        but = false;
        usuario = new Usuario();
        endereco = new Endereco();

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário excluido!"));
    }

    public void onRowSelect(SelectEvent event) {
        this.usuario = (Usuario) event.getObject();
        this.endereco = usuario.getEndereco();
        but = true;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
     * @return the logicaU
     */
    public LogicaUsusario getLogicaU() {
        return logicaU;
    }

    /**
     * @return the prioridade
     */
    public String getPrioridade() {
        return prioridade;
    }

    /**
     * @param prioridade the prioridade to set
     */
    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the but
     */
    public Boolean getBut() {
        return but;
    }

    /**
     * @param but the but to set
     */
    public void setBut(Boolean but) {
        this.but = but;
    }

}
