package org.clinicaOndot.agendamento;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public interface AgendamentoRepository extends PanacheRepository<Agendamento> {
}
