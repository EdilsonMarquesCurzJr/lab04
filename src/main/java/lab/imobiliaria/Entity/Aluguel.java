package lab.imobiliaria.Entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ALUGUEIS")
public @Data class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_LOCACAO")
    private Locacao idLocacao;
    @Column(name = "DATA_VENCIMENTO")
    private Date dataVencimento;
    @Column(name = "VALOR_PAGO")
    private BigDecimal valorPago;
    @Column(name = "DATA_PAGAMENTO")
    private Date dataPagamento;
    @Column(name = "OBS")
    private String obs;



    @Override
    public String toString() {
        return "Aluguel{" +
                "id=" + id +
                ", idLocacao=" + idLocacao +
                ", dataVencimento=" + dataVencimento +
                ", valorPago=" + valorPago +
                ", dataPagamento=" + dataPagamento +
                ", obs='" + obs + '\'' +
                '}';
    }
}
