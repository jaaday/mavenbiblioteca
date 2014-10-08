package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.dao.EditoraJpaController;
import com.seven.mavenbiblioteca.dao.EnderecoJpaController;
import com.seven.mavenbiblioteca.dao.exceptions.NonexistentEntityException;
import com.seven.mavenbiblioteca.modelo.Editora;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.seven.mavenbiblioteca.dao.util.Emf;

public class LogicaEditora {

    private final EditoraJpaController daoEditora;
    private final EnderecoJpaController daoEndereco;

    public LogicaEditora() {
        daoEditora = new EditoraJpaController(Emf.factory);
        daoEndereco = new EnderecoJpaController(Emf.factory);
    }

    public void novoEditora(Editora editora) {
        daoEditora.create(editora);
    }

    public void alterarEditora(Editora editora) {
        try {
            daoEditora.edit(editora);
        } catch (Exception ex) {
            Logger.getLogger(LogicaEditora.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            daoEndereco.edit(editora.getEndereco());
        } catch (Exception ex) {
            Logger.getLogger(LogicaEditora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirEditora(Editora editora) {
        try {
            daoEditora.destroy(editora.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LogicaEditora.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            daoEndereco.destroy(editora.getEndereco().getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LogicaEditora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Editora pesquisarEditora(Editora e) {
        return daoEditora.pesqEditoraNome(e);
    }

    /*public List<Editora> listarEditora(Editora e){
     if(e.getNome().equals("*")){
     return daoEditora.findEditoraEntities();
     }else{
     return daoEditora.pesqEditoraNome(e);
     }
     }*/
    public String removerMascara(String text) {
        return text.replaceAll("[.-]", "");
    }

    public List<Editora> listEditoras() {
        return daoEditora.findEditoraEntities();
    }
}
