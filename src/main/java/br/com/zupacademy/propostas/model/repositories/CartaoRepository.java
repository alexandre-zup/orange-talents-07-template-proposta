package br.com.zupacademy.propostas.model.repositories;

import br.com.zupacademy.propostas.model.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
