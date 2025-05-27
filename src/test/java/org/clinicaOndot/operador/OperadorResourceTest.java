package org.clinicaOndot.operador;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@QuarkusTest
public class OperadorResourceTest {

    private final String NOME_TESTE = "Nome Completo";
    private final String DOC_TESTE = "098.765.432-11";

    private final Long LONG_TESTE = 5L;

    @Inject
    OperadorResource operadorResource;

    @InjectMock
    OperadorRepository operadorRepositoryMock;

    @Test
    void testOperadorRepositoryMock_deveSerInjetado() {
        // Verifica e o repositorio esta sendo injetado
        assertNotNull(operadorRepositoryMock, "Falha: OperadorRepositoryMock está nulo. @InjectMock não funcionou como esperado.");

        // Se o assertNotNull acima passar, então o mock foi injetado.
        // Aqui estou mockando, falando que o retorno do metodo vai ser LONG_TEST
        Mockito.when(operadorRepositoryMock.count()).thenReturn(LONG_TESTE);
        assertEquals(LONG_TESTE, operadorRepositoryMock.count(), "A contagem mockada não é a esperada.");
        Mockito.verify(operadorRepositoryMock).count(); // Verifica se o método count() foi chamado no mock
    }

    @Test
    void testCriarOperador_sucesso(){

        //Arrange
        OperadorRequestDto request = new OperadorRequestDto();
        request.setNomeCompleto(NOME_TESTE);
        request.setDocumento(DOC_TESTE);

        doNothing().when(operadorRepositoryMock).persist(any(Operador.class));
        //Act
        Operador operadorCriado = operadorResource.criar(request);

        // 3. Assert (Verificar)
        assertNotNull(operadorCriado, "O operador criado não deve ser nulo.");
        assertEquals(NOME_TESTE, operadorCriado.getNomeCompleto(), "O nome do operador não é o esperado.");
        assertEquals(DOC_TESTE, operadorCriado.getDocumento(), "O documento do operador não é o esperado.");
        assertTrue(operadorCriado.isAtivo(), "O operador deveria ser criado como ativo."); // O método 'criar' define 'ativo' como true

    }

    @Test
    @DisplayName("Testando-listarPorId")
    void testListarPorId_quandoIdExistente_deveRetornarOperador(){
        //Arrange
        Operador operadorMock = new Operador();
        operadorMock.setId(LONG_TESTE);
        operadorMock.setNomeCompleto(NOME_TESTE);
        operadorMock.setDocumento(DOC_TESTE);
        operadorMock.setAtivo(true);

        Mockito.when(operadorRepositoryMock.findByIdOptional(LONG_TESTE)).thenReturn(Optional.of(operadorMock));
        //Act
        Optional<Operador> operadorOpt = operadorResource.listarPorId(LONG_TESTE);

        //Assert
        assertTrue(operadorOpt.isPresent(), "O optional de Operador nao deveria estar vazio");

        Operador operadorReturn = operadorOpt.get();

        //Conferindo se os dados sao de retorno sao os mesmo
        assertNotNull(operadorReturn, "Operador return nao deveria ser null");
        assertEquals(LONG_TESTE, operadorReturn.getId(), "Id operador nao pode ser null");
        assertEquals(NOME_TESTE, operadorReturn.getNomeCompleto(), "Nome operador nao pode ser null");
        assertEquals(DOC_TESTE, operadorReturn.getDocumento(), "Doc operador nao pode ser null");

        Mockito.verify(operadorRepositoryMock, Mockito.times(1)).findByIdOptional(LONG_TESTE);
    }


    /*@Test
    @DisplayName("test_getHistoricoInformacoes")
    void test_getHistoricoInformacoes() throws Exception {
        //Arrange
        HistoricoSaudeAnimalPesquisarRequest request = new HistoricoSaudeAnimalPesquisarRequest();
        request.setAnimalId(1L);
        request.setTutorId(1L);
        request.setDataInicial(LocalDate.now().minusMonths(1));
        request.setDataFinal(LocalDate.now());

        //Act
        HistoricoInformacoesDTO informacoesDTO = new HistoricoInformacoesDTO();

        //Assert
        when(repository.buscarQuantidadesAtendimentosTotais(request)).thenReturn(informacoesDTO);
        when(repository.buscarDataProximoAgendamento(request.getAnimalId())).thenReturn(LocalDateTime.now());
        assertNotEquals(resource.getHistoricoInformacoes(request), null);
    }*/

}
