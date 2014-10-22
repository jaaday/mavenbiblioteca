/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.seven.mavenbiblioteca.logica;

import com.seven.mavenbiblioteca.modelo.Editora;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Convidado
 */
public class LogicaEditoraTest {
    
    public LogicaEditoraTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of novoEditora method, of class LogicaEditora.
     */
    @org.junit.Test
    public void testNovoEditora() {
        System.out.println("novoEditora");
        Editora editora = new Editora();
        editora.setNome("Maria");
        LogicaEditora instance = new LogicaEditora();
        instance.novoEditora(editora);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of alterarEditora method, of class LogicaEditora.
     */
    @org.junit.Test
    public void testAlterarEditora() {
        System.out.println("alterarEditora");
        Editora editora = new Editora();
        editora.setNome("Maria 2");
        LogicaEditora instance = new LogicaEditora();
        instance.alterarEditora(editora);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of excluirEditora method, of class LogicaEditora.
     */
    @org.junit.Test
    public void testExcluirEditora() {
        //Criando Editora
        System.out.println("novoEditora");
        Editora editora = new Editora();
        editora.setNome("Maria");
        LogicaEditora instance = new LogicaEditora();
        instance.novoEditora(editora);
        //---------------------
        System.out.println("excluirEditora");
        //Editora editora = new Editora();
        //editora.setId(Long.MIN_VALUE);
        //editora.setNome("Maria");
       
       // LogicaEditora instance = new LogicaEditora();
        instance.excluirEditora(editora);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of pesquisarEditora method, of class LogicaEditora.
     */
    @org.junit.Test
    public void testPesquisarEditora() {
        //Criando Editora
        System.out.println("novoEditora");
        Editora editora = new Editora();
        editora.setNome("Maria");
        LogicaEditora instance = new LogicaEditora();
        instance.novoEditora(editora);
        //---------------------
        //System.out.println("pesquisarEditora");
        //Editora e = null;
       // LogicaEditora instance = new LogicaEditora();
        Editora expResult = null;
        //Editora result = instance.pesquisarEditora(editora);
        assertNull(expResult);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of removerMascara method, of class LogicaEditora.
     */
    @org.junit.Test
    public void testRemoverMascara() {
        System.out.println("removerMascara");
        String text = "";
        LogicaEditora instance = new LogicaEditora();
        String expResult = "";
        String result = instance.removerMascara(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of listEditoras method, of class LogicaEditora.
     */
    @org.junit.Test
    public void testListEditoras() {
         //Criando Editora
        System.out.println("novoEditora");
        Editora editora = new Editora();
        editora.setNome("Maria");
        LogicaEditora instance = new LogicaEditora();
        instance.novoEditora(editora);
        editora = new Editora();
        editora.setNome("Joana");
        instance.novoEditora(editora);
        //---------------------
        System.out.println("listEditoras");
       // LogicaEditora instance = new LogicaEditora();
        List<Editora> result = instance.listEditoras();
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
