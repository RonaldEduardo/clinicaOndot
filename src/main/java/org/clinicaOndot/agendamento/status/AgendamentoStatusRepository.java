package org.clinicaOndot.agendamento.status;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface AgendamentoStatusRepository extends PanacheRepository<AgendamentoStatus> {
}
