package lab.imobiliaria.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "CLIENTES")
public @Data class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOME", nullable = false)
    private String nome;
    @Column(name = "CPF", nullable = false)
    private String cpf;
    @Column(name = "TELEFONE",  nullable = false)
    private String telefone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DT_NACIMENTO")
    private LocalDate dtNacimento;

    @OneToMany(mappedBy = "idProprietario")
    private List<Imoveis> imoveis;

    @OneToMany(mappedBy = "idInquilino" )
    private List<Locacao> locacoes;

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
