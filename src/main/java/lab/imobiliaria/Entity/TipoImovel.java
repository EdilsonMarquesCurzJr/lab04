package lab.imobiliaria.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TIPO_IMOVEL")
public @Data class TipoImovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRICAO")
    private String descricao;

    @Override
    public String toString() {
        return "TipoImovel{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
