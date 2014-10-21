package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.dao.EmprestimoJpaController;
import com.seven.mavenbiblioteca.dao.LivroJpaController;
import com.seven.mavenbiblioteca.dao.UsuarioJpaController;
import com.seven.mavenbiblioteca.modelo.Emprestimo;
import com.seven.mavenbiblioteca.dao.util.Emf;
import com.seven.mavenbiblioteca.modelo.Livro;
import com.seven.mavenbiblioteca.modelo.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class LogicaEmprestimo {

    private final EmprestimoJpaController daoEmprestimo;
    private final UsuarioJpaController daoUsuario;
    private final LivroJpaController daoLivro;
    private FacesContext context = FacesContext.getCurrentInstance();
    

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
    
    public Boolean veirficarLivroEntregueHoje(Usuario u, Livro l){
        Date hoje = new Date();
        List<Emprestimo> list = daoEmprestimo.emprestimosFechadosUsuario(u, hoje);
        for (Emprestimo e : list) {
            Livro livro2 = daoLivro.pesquisarLivroID(e.getLivro().getId());
            if (l.getId().equals(livro2.getId())) {
                return false;
            }
        }
        return true;
    }
    
    public Boolean verificarAtraso(Emprestimo e){
        return e.getData_devolucao().before(e.getData_presvista_devolucao());
    }
    
    /*public int DiferencaEntreDatas(String data1, String data2) throws ParseException {
        GregorianCalendar ini = new GregorianCalendar();
        GregorianCalendar fim = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ini.setTime(sdf.parse(data1));
        fim.setTime(sdf.parse(data2));
        long dt1 = ini.getTimeInMillis();
        long dt2 = fim.getTimeInMillis();
        return (int) (((dt2 - dt1) / 86400000) + 1);
    }*/
    
    public int DiferencaEntreDatas(Date data1, Date data2) {
        int ini = (int) data1.getTime();
        int fim = (int) data2.getTime();
        int dias = (ini - fim)/86400000; //miliseconds 1000 * 60 * 60 * 24
        return dias; // retorna a quantidade de dias
    }
    
    public List<Emprestimo> emprestimosAbertos(){
        return daoEmprestimo.emprestimosAndamento();
    }
    
    public void realizarDevolucao(Emprestimo emp){
        
        try{
            daoEmprestimo.edit(emp);
        }catch (Exception ex) {
            Logger.getLogger(LogicaLivro.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
   
}
