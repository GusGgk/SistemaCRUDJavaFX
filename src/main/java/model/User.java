package model;

public abstract class User {
    private String nome;
    private String email;
    private String senha;

    public User(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void mostrarDados(){
        System.out.println("Nome do Usuário: " + this.nome);
        System.out.println("Email cadastrado: " + this.email);
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return "";
    }
}
