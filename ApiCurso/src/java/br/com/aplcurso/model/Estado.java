package br.com.aplcurso.model;

import java.util.Objects;

public class Estado {

    private int id;
    private String nomeEstado;
    private String siglaEstado;

    public Estado() {
        this.id = 0;
        this.nomeEstado = "";
        this.siglaEstado = "";
    }

    public Estado(int id, String nomeEstado, String siglaEstado) {
        this.id = id;
        this.nomeEstado = nomeEstado;
        this.siglaEstado = siglaEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.siglaEstado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Estado other = (Estado) obj;

        if (this.id != other.id) {
            return false;
        }

        return Objects.equals(
                this.siglaEstado,
                other.siglaEstado
        );
    }
}