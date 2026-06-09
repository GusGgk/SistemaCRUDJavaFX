package model;

public class Agente extends User {
    private static final long serialVersionUID = 1L;

    private int id;
    private String registroCref;
    private String telefone;

    public Agente(String nome, String email, String senha, int id, String registroCref, String telefone) {
        super(nome, email, senha);
        this.id = id;
        this.registroCref = registroCref;
        this.telefone = telefone;
    }

    @Override
    public void mostrarDados() {
        super.mostrarDados();
        System.out.println("ID do Agente: " + this.id);
        System.out.println("Registro CREF: " + this.registroCref);
        System.out.println("Telefone: " + this.telefone);
        System.out.println("------------------------");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistroCref() {
        return registroCref;
    }

    public void setRegistroCref(String registroCref) {
        this.registroCref = registroCref;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
