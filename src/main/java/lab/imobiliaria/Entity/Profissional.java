package lab.imobiliaria.Entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROFISSIONAIS")
public @Data class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PROFISSAO",  nullable = false)
    private String profissao;
    @Column(name = "NOME",  nullable = false)
    private String nome;
    @Column(name = "TELEFONE1",  nullable = false)
    private String telefone1;
    @Column(name = "TELEFONE2")
    private String telefone2;
    @Column(name = "VALOR_HORA")
    private BigDecimal valorHora;
    @Column(name = "OBS")
    private String obs;


    @OneToMany(mappedBy = "idProfissional", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ServicoImovel> servicos = new ArrayList<>();

    public void addServico(ServicoImovel servico) {
        servicos.add(servico);
        servico.setIdProfissional(this);
    }

    @Override
    public String toString() {
        return "Profissional{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone1='" + telefone1 + '\'' +
                ", telefone2='" + telefone2 + '\'' +
                ", valorHora=" + valorHora +
                ", obs='" + obs + '\'' +
                ", servicos=" + servicos +
                '}';
    }
}
