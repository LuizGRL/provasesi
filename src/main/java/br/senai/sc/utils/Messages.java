package br.senai.sc.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messages implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private void addMessage(String msg, FacesMessage.Severity severity) {
		FacesMessage fm = new FacesMessage(severity,msg,null);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	public void info(String msg) {
		addMessage(msg,FacesMessage.SEVERITY_INFO);
	}

}
