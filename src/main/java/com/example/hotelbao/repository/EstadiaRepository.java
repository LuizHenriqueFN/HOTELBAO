package com.example.hotelbao.repository;

import com.example.hotelbao.model.Estadia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadiaRepository extends JpaRepository<Estadia, Long> {
    List<Estadia> findByClienteId(Long clienteId);

    Optional<Estadia> findTopByClienteIdOrderByQuartoValorDesc(Long clienteId);

    Optional<Estadia> findTopByClienteIdOrderByQuartoValorAsc(Long clienteId);

    @Query("SELECT SUM(e.quarto.valor) FROM Estadia e WHERE e.cliente.id = :clienteId")
    Optional<BigDecimal> sumValorEstadiasByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT EXISTS(" +
            "SELECT 1 FROM Estadia e WHERE e.quarto.id = :quartoId " +
            "AND e.dataEstadia = :dataEstadia " +
            "AND (:id IS NULL OR e.id <> :id)" +
            ")")
    boolean existsConflict(@Param("quartoId") Long quartoId,
            @Param("dataEstadia") LocalDate dataEstadia,
            @Param("id") Long id);
}