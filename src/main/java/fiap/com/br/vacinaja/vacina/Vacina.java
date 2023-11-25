package fiap.com.br.vacinaja.vacina;

import fiap.com.br.vacinaja.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long IdRegistro;

    @Size(max = 50, message = "{vacina.nome.size}")
    String nome;

    @Min(1)
    @Max(9999)
    Integer lote;

    @Min(0)
    @Max(100)
    Integer status = 0;

    @ManyToOne
    User user;

}
