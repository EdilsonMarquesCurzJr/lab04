package lab.imobiliaria.Entity;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PROFISSIONAIS")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "TELEFONE1")
    private String telefone1;
    @Column(name = "TELEFONE2")
    private String telefone2;
    @Column(name = "VALOR_HORA")
    private BigDecimal valorHora;
    @Column(name = "OBS")
    private String obs;

}
