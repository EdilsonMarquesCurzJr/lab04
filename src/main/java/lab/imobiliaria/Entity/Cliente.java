package lab.imobiliaria.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CLIENTES")
public @Data class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "TELEFONE")
    private String telefone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DT_NACIMENTO")
    private Date dtNacimento;

    @OneToMany(mappedBy = "idProprietario")
    private List<Imoveis> imoveis;

    @OneToMany(mappedBy = "idInquilino" )
    private List<Locacao> locacaos;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", dtNacimento=" + dtNacimento +
                '}';
    }
}
