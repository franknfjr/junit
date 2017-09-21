/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufra.domain;

import java.io.Serializable;

/**
 *
 * @author Frank
 */
public class Medalha implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tipo;
    private String desc;
    private String peso;
    private String fabricacao;
    private String sede;
    private String urlFoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(String fabricacao) {
        this.fabricacao = fabricacao;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public String toString() {
        return "Medalha{" + "id=" + id + ", tipo=" + tipo + ", desc=" + desc + ", peso=" + peso + ", fabricacao=" + fabricacao + ", sede=" + sede + ", urlFoto=" + urlFoto + '}';
    }
        
}
