package lab.imobiliaria.Entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "LOCACAO")
public @Data class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_IMOVEL")
    private Imoveis idImovel;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_INQUILINO")
    private Cliente idInquilino;
    @Column(name = "VALOR_ALUGUEL")
    private BigDecimal valorAluguel;
    @Column(name = "PECENTUAL_MULTA")
    private BigDecimal pecentualMulta;
    @Column(name = "DIA_VENCIMENTO")
    private int dataVencimento;
    @Column(name = "DATA_INICIO")
    private Date dataInicio;
    @Column(name = "DATA_FIM")
    private Date dataFim;
    @Column(name = "ATIVO")
    private boolean ativo;
    @Column(name = "OBS")
    private String obs;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locacao")
    private Set<Aluguel> alugueis;

    @Override
    public String toString() {
        return "Locacao{" +
                "id=" + id +
                ", idImovel=" + idImovel +
                ", idInquilino=" + idInquilino +
                ", valorAluguel=" + valorAluguel +
                ", pecentualMulta=" + pecentualMulta +
                ", dataVencimento=" + dataVencimento +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", ativo=" + ativo +
                ", obs='" + obs + '\'' +
                '}';
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void atualizarDisponibilidade() {
        this.ativo = idInquilino == null;
    }
}
