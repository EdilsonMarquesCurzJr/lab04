package lab.imobiliaria.Entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SERVIÇOS_IMOVEL")
public @Data class ServicoImovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PROFISSIONAL")
    private Profissional idProfissional;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_IMOVEL")
    private Imoveis idImovel;
    @Column(name = "DATA_SERVICO")
    private Date dataServico;
    @Column(name = "VALOR_TOTAL")
    private BigDecimal valorTotal;
    @Column(name = "OBS")
    private String obs;

    @Override
    public String toString() {
        return "ServicoImovel{" +
                "id=" + id +
                ", dataServico=" + dataServico +
                ", valorTotal=" + valorTotal +

                '}';
    }
}
