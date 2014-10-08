package com.seven.mavenbiblioteca.beans.util;

import com.seven.mavenbiblioteca.dao.EditoraJpaController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.seven.mavenbiblioteca.modelo.Editora;
import com.seven.mavenbiblioteca.dao.util.Emf;

@FacesConverter(value = "editora", forClass = Editora.class)
public class EditoraConverte implements Converter {

    private Editora editora = new Editora();

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
        EditoraJpaController dao = new EditoraJpaController(Emf.factory);
        return dao.findEditora(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        editora = (Editora) value;
        return String.valueOf(editora.getId());
    }
}
