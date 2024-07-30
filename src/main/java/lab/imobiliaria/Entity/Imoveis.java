package lab.imobiliaria.Entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "IMOVEIS")
public @Data class Imoveis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PROPRIETARIO")
    private Cliente idProprietario;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_TIPO_IMOVEL")
    private TipoImovel idTipoImovel;
    @Column(name = "LOGRADOURO")
    private String logradouro;
    @Column(name = "BAIRRO")
    private String bairro;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "METRAGEM")
    private Integer metragem;
    @Column(name = "DORMITORIOS")
    private int dormitorios;
    @Column(name = "BANHEIROS")
    private int banheiros;
    @Column(name = "SUITES")
    private int suites;
    @Column(name = "VAGAS_GARAGEM")
    private int vagasGaragem;
    @Column(name = "VALOR_ALUGUEL_SUGERIDO")
    private BigDecimal valorAluguelSugerido;
    @Column(name = "OBS")
    private String obs;

    @OneToMany(mappedBy = "imovel")
    private List<Locacao> idLocacoes = new ArrayList<>();


    @Override
    public String toString() {
        return "Imoveis{" +
                "id=" + id +
                ", idProprietario=" + idProprietario +
                ", idTipoImovel=" + idTipoImovel +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
                ", metragem=" + metragem +
                ", dormitorios=" + dormitorios +
                ", banheiros=" + banheiros +
                ", suites=" + suites +
                ", vagasGaragem=" + vagasGaragem +
                ", valorAluguelSugerido=" + valorAluguelSugerido +
                ", obs='" + obs + '\'' +
                '}';
    }
}
