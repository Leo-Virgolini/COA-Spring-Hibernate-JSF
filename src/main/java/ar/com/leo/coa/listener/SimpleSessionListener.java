/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.leo.coa.listener;

import ar.com.leo.coa.mb.SessionManagedBean;
import ar.com.leo.coa.model.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedProperty;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Leo
 */
public class SimpleSessionListener implements HttpSessionListener, Serializable {

    @ManagedProperty("#{sessionManagedBean}")
    private SessionManagedBean sessionManagedBean; // Setter required.

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session created : " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();

        if (sessionManagedBean != null) {

            Usuario usuario = sessionManagedBean.getUsuario();

            System.out.println("Usuario: " + usuario);

            if (usuario != null) {
                if (usuario.getRol() == 1) {
                    System.out.println("Alumno :" + sessionManagedBean.getAlumno().getNombre());
                } else if (usuario.getRol() == 2) {
                    System.out.println("Profesor :" + sessionManagedBean.getProfesor().getNombre());
                } else {
                    System.out.println("Admin :" + sessionManagedBean.getUsuario().getEmail());
                }
            }
        }

//        SessionManagedBean sessionManagedBean = (SessionManagedBean) session.getAttribute("sessionManagedBean");
//        Usuario usuario = sessionManagedBean.getUsuario();
        
        /*Do some bussiness logic such as save audit logs to db, 
         save logout history, and so on.*/
        System.out.println("session destroyed :"
                + session.getId() + " Logging out user...");
    }

    public void setSessionManagedBean(SessionManagedBean sessionManagedBean) {
        this.sessionManagedBean = sessionManagedBean;
    }

}
