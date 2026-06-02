package model;

import java.time.LocalDate;

public class Atleta extends User{
    private static final long serialVersionUID = 1L;

    private int id;
    private String nickname;
    private LocalDate dataNascimento;
    private String nacionalidade;
    private String endereco;

    public Atleta(String nome, String email, String senha,int id, String nickname, LocalDate dataNascimento, String nacionalidade, String endereco) {
        super(nome, email, senha);
        this.id = id;
        this.nickname = nickname;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.endereco = endereco;
    }

    @Override
    public void mostrarDados() {
        super.mostrarDados();
        System.out.println("Id do atleta: " + this.id);
        System.out.println("Apelido do atleta: " + this.nickname);
        System.out.println("data de Nascimento do atleta: " + this.dataNascimento);
        System.out.println("Nacionalidade do atleta: " + this.nacionalidade);
        System.out.println("Endereço do atleta: " + this.endereco);
        System.out.println("------------------------");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }


    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
