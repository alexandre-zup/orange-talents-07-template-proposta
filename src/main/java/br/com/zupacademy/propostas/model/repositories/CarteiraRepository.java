package br.com.zupacademy.propostas.model.repositories;

import br.com.zupacademy.propostas.model.entities.Carteira;
import br.com.zupacademy.propostas.model.enums.ServicoCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    boolean existsByNomeAndCartaoId(ServicoCarteira nome, Long id);
}
