package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.dao.EmprestimoJpaController;
import com.seven.mavenbiblioteca.modelo.Emprestimo;
import com.seven.mavenbiblioteca.dao.util.Emf;

public class LogicaEmprestimo {

    private final EmprestimoJpaController daoEmprestimo;

    public LogicaEmprestimo() {
        daoEmprestimo = new EmprestimoJpaController(Emf.factory);
    }

    public void cadastrarEmprestimo(Emprestimo e) {
        daoEmprestimo.create(e);
    }
}
