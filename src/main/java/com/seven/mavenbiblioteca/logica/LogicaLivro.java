package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.dao.EditoraJpaController;
import com.seven.mavenbiblioteca.dao.LivroJpaController;
import com.seven.mavenbiblioteca.dao.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.seven.mavenbiblioteca.modelo.Editora;
import com.seven.mavenbiblioteca.modelo.Livro;
import com.seven.mavenbiblioteca.dao.util.Emf;

public class LogicaLivro {
    
    private final LivroJpaController daoLivro;
    private final EditoraJpaController daoEditora;
    
    public LogicaLivro(){
        daoLivro = new LivroJpaController(Emf.factory);
        daoEditora = new EditoraJpaController(Emf.factory);
    }
    
    public void novoLivro(Livro livro){
        daoLivro.create(livro);
    }
    
    public void alterarLivro(Livro livro){
        try {
            daoLivro.edit(livro);
        } catch (Exception ex) {
            Logger.getLogger(LogicaLivro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluirLivor (Livro livro){
        try {
            daoLivro.destroy(livro.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LogicaLivro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Livro> listarLivros(){
        return daoLivro.findLivroEntities();
    }
    
    public Livro pesqLivroID(Long i){
        return daoLivro.pesquisarLivroID(i);
    }
    
    public List<Editora> listEditoras(){
        return daoEditora.findEditoraEntities();
    }
    
    public List<Livro> listarLivorsDisponivel(){
        return daoLivro.listarLivrosDisponiveis();
    }
}
