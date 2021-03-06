package com.seven.mavenbiblioteca.beans;

import com.seven.mavenbiblioteca.logica.LogicaEmprestimo;
import com.seven.mavenbiblioteca.logica.LogicaLivro;
import com.seven.mavenbiblioteca.logica.LogicaUsusario;
import com.seven.mavenbiblioteca.modelo.Emprestimo;
import com.seven.mavenbiblioteca.modelo.Livro;
import com.seven.mavenbiblioteca.modelo.Usuario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@RequestScoped
public class EmprestimoMB {

    private Emprestimo emprestimo;
    private final LogicaUsusario logicaUsuario;
    private final LogicaLivro logicaLivro;
    private final LogicaEmprestimo logicaEmprestimo;
    private Usuario usuario;
    private Livro livro;
    private List<Livro> livrosDiponiveis;
    private List<Emprestimo> emprestimosAbertos;
    private final FacesContext context;
    private String descricao = "nulo"; 

    public EmprestimoMB() {
        usuario = new Usuario();
        livro = new Livro();
        logicaLivro = new LogicaLivro();
        emprestimo = new Emprestimo();
        logicaUsuario = new LogicaUsusario();
        logicaEmprestimo = new LogicaEmprestimo();
        context = FacesContext.getCurrentInstance();
    }

    public void novoEmprestimo() {
        usuario.setCpf(logicaUsuario.removerMascara(getUsuario().getCpf()));
        if (logicaEmprestimo.verificarUsuario(usuario)) {
            usuario = logicaUsuario.pesquisarUsuarioCPF(usuario);
            if (logicaEmprestimo.verificarPunicao(usuario)) {
                if (logicaEmprestimo.qtdEmprestimosAbertos(usuario) < 3) {
                    if (logicaEmprestimo.exemplarEmprest(usuario, livro)) {
                        if (logicaEmprestimo.veirficarLivroEntregueHoje(usuario, livro)) {
                            emprestimo.setUsuario(usuario);
                            emprestimo.setLivro(logicaLivro.pesqLivroID(livro.getId()));
                            emprestimo.setData_devolucao(new Date(1969 - 12 - 31));
                            emprestimo.setData_emprestimo(new Date());
                            emprestimo.setAtraso(false);
                            emprestimo.setData_presvista_devolucao(somarData(10, emprestimo.getData_emprestimo()));

                            logicaEmprestimo.cadastrarEmprestimo(emprestimo);

                            emprestimo = new Emprestimo();
                            usuario = new Usuario();
                            livro = new Livro();

                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Emprestimo realizado!"));

//                    try {
//                        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastrarEmprestimo.xhtml");
//                    } catch (IOException ex) {
//                        Logger.getLogger(EmprestimoMB.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                        }else{
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "O livro não pode ser emprestado!"));
                        }
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Livro já emprestado!"));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Usuário atingiu o limite de emprestimos"));
                }
            } else {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");     
                String data = formato.format(usuario.getData_punicao());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Usuário está punido até " + data));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "CPF não cadastrado ou CPF Inválido!"));
        }

    }

    public static Date somarData(int dias, Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DATE, dias);

        return calendar.getTime();
    }

    public void onRowSelect(SelectEvent event) {
        this.livro = (Livro) event.getObject();
    }
    
    public void onRowSelectEmp(SelectEvent event) {
        this.emprestimo = (Emprestimo) event.getObject();
        this.livro = emprestimo.getLivro();
        this.usuario = emprestimo.getUsuario();
    }

    /**
     * @return the emprestimo
     */
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    /**
     * @param emprestimo the emprestimo to set
     */
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
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
     * @return the livrosDiponiveis
     */
    public List<Livro> getLivrosDiponiveis() {
        return logicaLivro.listarLivorsDisponivel();
    }

    /**
     * @param livrosDiponiveis the livrosDiponiveis to set
     */
    public void setLivrosDiponiveis(List<Livro> livrosDiponiveis) {
        this.livrosDiponiveis = livrosDiponiveis;
    }
    
    public void setEmprestimosAbertos(List<Emprestimo> emprestimosAbertos) {
        this.emprestimosAbertos = emprestimosAbertos;
    }
    
    public List<Emprestimo> getEmprestimosAbertos() {
        return logicaEmprestimo.emprestimosAbertos();
    }
    
    public void realizarDevolucao(Emprestimo emp){
        
           
           emp.setData_devolucao(new Date());
      
           //emp.setLivro(livro);
           //emp.setUsuario(usuario);  
     
           if(logicaEmprestimo.verificarAtraso(emp) == false){
                int aux = logicaEmprestimo.DiferencaEntreDatas(emp.getData_devolucao(), emp.getData_presvista_devolucao());
                aux = aux * 2;
                //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "At ", "Entroufuncao"));
                if(emp.getUsuario().getData_punicao().after(new Date())){
                     emp.getUsuario().setData_punicao(somarData(aux, emp.getUsuario().getData_punicao()));
                     //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "At ", "EntrouTeste"));
                }
                else{
                    emp.getUsuario().setData_punicao(somarData(aux, emp.getData_devolucao()));
                    //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "At ", "EntrouTeste2"));
                }
                logicaUsuario.alterarUsuario(emp.getUsuario());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção ", "Usuário "+emp.getUsuario().getNome()+ " foi punido em " +aux+ " dias."));       
           }
           logicaEmprestimo.realizarDevolucao(emp);
           context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso ", "Livro devolvido"));
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

       
}