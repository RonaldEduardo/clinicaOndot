package org.clinicaOndot.agendamento.status;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgendamentoStatusRepository implements PanacheRepository<AgendamentoStatus> {
}
