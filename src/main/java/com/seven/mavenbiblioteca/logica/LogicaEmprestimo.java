package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.dao.EmprestimoJpaController;
import com.seven.mavenbiblioteca.dao.LivroJpaController;
import com.seven.mavenbiblioteca.dao.UsuarioJpaController;
import com.seven.mavenbiblioteca.modelo.Emprestimo;
import com.seven.mavenbiblioteca.dao.util.Emf;
import com.seven.mavenbiblioteca.modelo.Livro;
import com.seven.mavenbiblioteca.modelo.Usuario;
import java.util.Date;
import java.util.List;

public class LogicaEmprestimo {

    private final EmprestimoJpaController daoEmprestimo;
    private final UsuarioJpaController daoUsuario;
    private final LivroJpaController daoLivro;

    public LogicaEmprestimo() {
        daoEmprestimo = new EmprestimoJpaController(Emf.factory);
        daoUsuario = new UsuarioJpaController(Emf.factory);
        daoLivro = new LivroJpaController(Emf.factory);
    }

    public void cadastrarEmprestimo(Emprestimo e) {
        daoEmprestimo.create(e);
    }

    public Boolean verificarUsuario(Usuario u) {
        return daoUsuario.pesquisarUsuarioCPF(u) != null;
    }

    public Boolean verificarPunicao(Usuario u) {
        return u.getData_punicao().before(new Date());
    }

    public int qtdEmprestimosAbertos(Usuario u) {

        if (daoEmprestimo.emprestimosAbertosUsuario(u) != null) {
            return daoEmprestimo.emprestimosAbertosUsuario(u).size();
        }
        return 0;
    }

    public Boolean exemplarEmprest(Usuario u, Livro l) {
        List<Emprestimo> list = daoEmprestimo.emprestimosAbertosUsuario(u);
        Livro livro = daoLivro.pesquisarLivroID(l.getId());
        for (Emprestimo e : list) {
            Livro livro2 = daoLivro.pesquisarLivroID(e.getLivro().getId());
            if (livro.getIsbn().equals(livro2.getIsbn())) {
                return false;
            }
        }
        return true;
    }
}
