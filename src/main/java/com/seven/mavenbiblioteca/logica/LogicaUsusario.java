package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.beans.util.ValidarCPF;
import com.seven.mavenbiblioteca.beans.util.ValidarCPF1;
import com.seven.mavenbiblioteca.dao.EnderecoJpaController;
import com.seven.mavenbiblioteca.dao.UsuarioJpaController;
import com.seven.mavenbiblioteca.dao.exceptions.NonexistentEntityException;
import com.seven.mavenbiblioteca.dao.util.Emf;
import com.seven.mavenbiblioteca.modelo.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicaUsusario {
    private final UsuarioJpaController daoUsuario;
    private final EnderecoJpaController daoEndereco;
    private final ValidarCPF1 validarCPF;
    
    public LogicaUsusario(){
        daoUsuario = new UsuarioJpaController(Emf.factory);
        daoEndereco = new EnderecoJpaController(Emf.factory);
        validarCPF = new ValidarCPF1();
    }
    
    public void novoUsuario(Usuario u){
        if(getValidarCPF().isCPF(u.getCpf()))
        {
            daoUsuario.create(u);
        }
        
    }
    
    public void alterarUsuario(Usuario u){
        
        try {
            daoUsuario.edit(u);
        } catch (Exception ex) {
            Logger.getLogger(LogicaUsusario.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            daoEndereco.edit(u.getEndereco());
        } catch (Exception ex) {
            Logger.getLogger(LogicaUsusario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluirUsuario(Usuario u){
        try {
            daoUsuario.destroy(u.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LogicaUsusario.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            daoEndereco.destroy(u.getEndereco().getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LogicaUsusario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Usuario> listarUsuario(){
        return daoUsuario.findUsuarioEntities();
    }
    
    public Usuario pesquisarUsuarioCPF(Usuario u){
        return daoUsuario.pesquisarUsuarioCPF(u);
    }
    
    public int convertPrioridade(String p){
        switch (p) {
            case "Funcionario":
                return 2;
            case "Administrador":
                return 3;
            default:
                return 1;
        }
    }
    
    public String desconverterPrioridade(int p){
        switch (p) {
            case 2:
                return "Funcionario";
            case 3:
                return "Administrador";
            default:
                return "Cliente";
        }
    }
    
    public Boolean validarCPF(String cpf){
        removerMascara(cpf);
        return getValidarCPF().isCPF(cpf);
    }
    
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    /**
     * @return the validarCPF
     */
    public ValidarCPF1 getValidarCPF() {
        return validarCPF;
    }
}
