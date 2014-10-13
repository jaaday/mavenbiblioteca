package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.dao.EmprestimoJpaController;
import com.seven.mavenbiblioteca.dao.UsuarioJpaController;
import com.seven.mavenbiblioteca.modelo.Emprestimo;
import com.seven.mavenbiblioteca.dao.util.Emf;
import com.seven.mavenbiblioteca.modelo.Usuario;
import java.util.Date;

public class LogicaEmprestimo {

    private final EmprestimoJpaController daoEmprestimo;
    private final UsuarioJpaController daoUsuario;

    public LogicaEmprestimo() {
        daoEmprestimo = new EmprestimoJpaController(Emf.factory);
        daoUsuario = new UsuarioJpaController(Emf.factory);
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
}
