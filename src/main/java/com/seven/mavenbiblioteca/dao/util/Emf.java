package com.seven.mavenbiblioteca.dao.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Emf {

    public static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("BibliotecaPU");
}
