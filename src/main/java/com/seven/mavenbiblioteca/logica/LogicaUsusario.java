package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.dao.EnderecoJpaController;
import com.seven.mavenbiblioteca.dao.UsuarioJpaController;
import com.seven.mavenbiblioteca.dao.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.seven.mavenbiblioteca.modelo.Usuario;
import com.seven.mavenbiblioteca.dao.util.Emf;

public class LogicaUsusario {

    private final UsuarioJpaController daoUsuario;
    private final EnderecoJpaController daoEndereco;

    public LogicaUsusario() {
        daoUsuario = new UsuarioJpaController(Emf.factory);
        daoEndereco = new EnderecoJpaController(Emf.factory);
    }

    public void novoUsuario(Usuario u) {
        daoUsuario.create(u);
    }

    public void alterarUsuario(Usuario u) {

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

    public void excluirUsuario(Usuario u) {
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

    public List<Usuario> listarUsuario() {
        return daoUsuario.findUsuarioEntities();
    }

    public Usuario pesquisarUsuarioCPF(Usuario u) {
        return daoUsuario.pesquisarUsuarioCPF(u);
    }

    public int convertPrioridade(String p) {
        switch (p) {
            case "Funcionario":
                return 2;
            case "Administrador":
                return 3;
            default:
                return 1;
        }
    }

    public String desconverterPrioridade(int p) {
        switch (p) {
            case 2:
                return "Funcionario";
            case 3:
                return "Administrador";
            default:
                return "Cliente";
        }
    }

    public String removerMascara(String text) {
        return text.replaceAll("[.-]", "");
    }
}
