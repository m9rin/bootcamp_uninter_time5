package br.uninter.medalerta.app;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.model.ConfirmacaoConsumo;
import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.QuantidadeTipo;
import br.uninter.medalerta.model.StatusAlerta;
import br.uninter.medalerta.model.Tratamento;
import br.uninter.medalerta.model.TratamentoMedicamento;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.service.AlertaService;
import br.uninter.medalerta.service.MedicamentoService;
import br.uninter.medalerta.service.TratamentoService;
import br.uninter.medalerta.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class App implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final MedicamentoService medicamentoService;
    private final TratamentoService tratamentoService;
    private final AlertaService alertaService;

    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final DateTimeFormatter fmtDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void run(String... args) {
        System.out.println("==============================");
        System.out.println("         MedAlerta");
        System.out.println("  Seu lembrete de medicamentos");
        System.out.println("==============================");

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Usuarios");
            System.out.println("2 - Medicamentos");
            System.out.println("3 - Tratamentos");
            System.out.println("4 - Alertas");
            System.out.println("5 - Medicamentos de um Tratamento");
            System.out.println("0 - Sair");

            switch (lerInteiro("Escolha: ")) {
                case 1 -> menuUsuario();
                case 2 -> menuMedicamento();
                case 3 -> menuTratamento();
                case 4 -> menuAlerta();
                case 5 -> menuTratamentoMedicamento();
                case 0 -> rodando = false;
                default -> aviso("Opcao invalida. Tente novamente.");
            }
        }
        System.out.println("\nAte logo! Nao esqueca de tomar seus medicamentos.");
    }

    private void menuUsuario() {
        System.out.println("\n--- Usuarios ---");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar todos");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Atualizar");
        System.out.println("5 - Remover");
        System.out.println("0 - Voltar");

        switch (lerInteiro("Escolha: ")) {
            case 1 -> cadastrarUsuario();
            case 2 -> listarUsuarios();
            case 3 -> buscarUsuario();
            case 4 -> atualizarUsuario();
            case 5 -> removerUsuario();
            case 0 -> {}
            default -> aviso("Opcao invalida.");
        }
    }

    private void cadastrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome(lerTexto("Nome: "));
        usuario.setTelefone(lerTexto("Telefone: "));
        usuario.setEmail(lerTexto("E-mail: "));
        usuarioService.salvar(usuario);
        ok("Usuario cadastrado com sucesso!");
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        if (usuarios.isEmpty()) {
            aviso("Nenhum usuario cadastrado.");
            return;
        }
        usuarios.forEach(u -> System.out.printf("  [%d] %s | %s | %s%n",
                u.getIdUsuario(), u.getNome(), u.getTelefone(), u.getEmail()));
    }

    private void buscarUsuario() {
        int id = lerInteiro("ID do usuario: ");
        usuarioService.buscarPorId(id).ifPresentOrElse(
                u -> System.out.printf("[%d] %s | %s | %s%n", u.getIdUsuario(), u.getNome(), u.getTelefone(), u.getEmail()),
                () -> aviso("Usuario nao encontrado.")
        );
    }

    private void atualizarUsuario() {
        int id = lerInteiro("ID do usuario a atualizar: ");
        usuarioService.buscarPorId(id).ifPresentOrElse(usuario -> {
            System.out.println("Deixe em branco para manter o valor atual.");
            String nome = lerTextoOpcional("Nome [" + usuario.getNome() + "]: ");
            String telefone = lerTextoOpcional("Telefone [" + usuario.getTelefone() + "]: ");
            String email = lerTextoOpcional("E-mail [" + usuario.getEmail() + "]: ");
            if (!nome.isBlank()) usuario.setNome(nome);
            if (!telefone.isBlank()) usuario.setTelefone(telefone);
            if (!email.isBlank()) usuario.setEmail(email);
            usuarioService.salvar(usuario);
            ok("Usuario atualizado!");
        }, () -> aviso("Usuario nao encontrado."));
    }

    private void removerUsuario() {
        int id = lerInteiro("ID do usuario a remover: ");
        usuarioService.buscarPorId(id).ifPresentOrElse(usuario -> {
            usuarioService.deletar(id);
            ok("Usuario removido.");
        }, () -> aviso("Usuario nao encontrado."));
    }

    private void menuMedicamento() {
        System.out.println("\n--- Medicamentos ---");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar todos");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Atualizar");
        System.out.println("5 - Remover");
        System.out.println("0 - Voltar");

        switch (lerInteiro("Escolha: ")) {
            case 1 -> cadastrarMedicamento();
            case 2 -> listarMedicamentos();
            case 3 -> buscarMedicamento();
            case 4 -> atualizarMedicamento();
            case 5 -> removerMedicamento();
            case 0 -> {}
            default -> aviso("Opcao invalida.");
        }
    }

    private void cadastrarMedicamento() {
        Medicamento medicamento = new Medicamento();
        medicamento.setNomeComercial(lerTexto("Nome comercial: "));
        medicamento.setNomeGenerico(lerTextoOpcional("Nome generico: "));
        medicamento.setFormaUso(lerTexto("Forma de uso (ex: oral, injetavel): "));
        medicamento.setObservacao(lerTextoOpcional("Observacao (opcional): "));
        medicamento.setUnidadeMedida(lerUnidadeMedida());
        medicamentoService.salvar(medicamento);
        ok("Medicamento cadastrado!");
    }

    private void listarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoService.listarTodos();
        if (medicamentos.isEmpty()) {
            aviso("Nenhum medicamento cadastrado.");
            return;
        }
        medicamentos.forEach(m -> System.out.printf("  [%d] %s (%s) | %s | %s%n",
                m.getIdMedicamento(), m.getNomeComercial(), valorOuTraco(m.getNomeGenerico()),
                m.getUnidadeMedida(), valorOuTraco(m.getFormaUso())));
    }

    private void buscarMedicamento() {
        int id = lerInteiro("ID do medicamento: ");
        medicamentoService.buscarPorId(id).ifPresentOrElse(
                m -> System.out.printf("[%d] %s (%s) | %s | Obs: %s%n",
                        m.getIdMedicamento(), m.getNomeComercial(), valorOuTraco(m.getNomeGenerico()),
                        valorOuTraco(m.getFormaUso()), valorOuTraco(m.getObservacao())),
                () -> aviso("Medicamento nao encontrado.")
        );
    }

    private void atualizarMedicamento() {
        int id = lerInteiro("ID do medicamento a atualizar: ");
        medicamentoService.buscarPorId(id).ifPresentOrElse(medicamento -> {
            System.out.println("Deixe em branco para manter o valor atual.");
            String nomeComercial = lerTextoOpcional("Nome comercial [" + medicamento.getNomeComercial() + "]: ");
            String nomeGenerico = lerTextoOpcional("Nome generico [" + medicamento.getNomeGenerico() + "]: ");
            String formaUso = lerTextoOpcional("Forma de uso [" + medicamento.getFormaUso() + "]: ");
            String observacao = lerTextoOpcional("Observacao [" + medicamento.getObservacao() + "]: ");
            if (!nomeComercial.isBlank()) medicamento.setNomeComercial(nomeComercial);
            if (!nomeGenerico.isBlank()) medicamento.setNomeGenerico(nomeGenerico);
            if (!formaUso.isBlank()) medicamento.setFormaUso(formaUso);
            if (!observacao.isBlank()) medicamento.setObservacao(observacao);
            medicamentoService.salvar(medicamento);
            ok("Medicamento atualizado!");
        }, () -> aviso("Medicamento nao encontrado."));
    }

    private void removerMedicamento() {
        int id = lerInteiro("ID do medicamento a remover: ");
        medicamentoService.buscarPorId(id).ifPresentOrElse(medicamento -> {
            medicamentoService.deletar(id);
            ok("Medicamento removido.");
        }, () -> aviso("Medicamento nao encontrado."));
    }

    private void menuTratamento() {
        System.out.println("\n--- Tratamentos ---");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar todos");
        System.out.println("3 - Listar por usuario");
        System.out.println("4 - Buscar por ID");
        System.out.println("5 - Atualizar");
        System.out.println("6 - Remover");
        System.out.println("0 - Voltar");

        switch (lerInteiro("Escolha: ")) {
            case 1 -> cadastrarTratamento();
            case 2 -> listarTratamentos();
            case 3 -> listarTratamentosPorUsuario();
            case 4 -> buscarTratamento();
            case 5 -> atualizarTratamento();
            case 6 -> removerTratamento();
            case 0 -> {}
            default -> aviso("Opcao invalida.");
        }
    }

    private void cadastrarTratamento() {
        listarUsuarios();
        int idUsuario = lerInteiro("ID do usuario: ");
        Tratamento tratamento = new Tratamento();
        tratamento.setDescricao(lerTextoOpcional("Descricao do tratamento: "));
        tratamento.setDataInicio(lerDataOpcional("Data de inicio (dd/MM/yyyy, opcional): "));
        tratamento.setDataFim(lerDataOpcional("Data de fim (dd/MM/yyyy, opcional): "));
        tratamento.setStatus(lerTextoOpcional("Status (opcional): "));
        tratamentoService.salvar(idUsuario, tratamento);
        ok("Tratamento cadastrado!");
    }

    private void listarTratamentos() {
        List<Tratamento> tratamentos = tratamentoService.listarTodos();
        if (tratamentos.isEmpty()) {
            aviso("Nenhum tratamento cadastrado.");
            return;
        }
        tratamentos.forEach(this::imprimirTratamento);
    }

    private void listarTratamentosPorUsuario() {
        int idUsuario = lerInteiro("ID do usuario: ");
        List<Tratamento> tratamentos = tratamentoService.listarPorUsuario(idUsuario);
        if (tratamentos.isEmpty()) {
            aviso("Nenhum tratamento encontrado para este usuario.");
            return;
        }
        tratamentos.forEach(this::imprimirTratamento);
    }

    private void buscarTratamento() {
        int id = lerInteiro("ID do tratamento: ");
        tratamentoService.buscarPorId(id).ifPresentOrElse(this::imprimirTratamento, () -> aviso("Tratamento nao encontrado."));
    }

    private void atualizarTratamento() {
        int id = lerInteiro("ID do tratamento a atualizar: ");
        tratamentoService.buscarPorId(id).ifPresentOrElse(tratamento -> {
            System.out.println("Deixe em branco para manter o valor atual.");
            String descricao = lerTextoOpcional("Descricao [" + tratamento.getDescricao() + "]: ");
            String status = lerTextoOpcional("Status [" + tratamento.getStatus() + "]: ");
            if (!descricao.isBlank()) tratamento.setDescricao(descricao);
            if (!status.isBlank()) tratamento.setStatus(status);
            tratamentoService.salvar(tratamento.getUsuario().getIdUsuario(), tratamento);
            ok("Tratamento atualizado!");
        }, () -> aviso("Tratamento nao encontrado."));
    }

    private void removerTratamento() {
        int id = lerInteiro("ID do tratamento a remover: ");
        tratamentoService.buscarPorId(id).ifPresentOrElse(tratamento -> {
            tratamentoService.deletar(id);
            ok("Tratamento removido.");
        }, () -> aviso("Tratamento nao encontrado."));
    }

    private void menuAlerta() {
        System.out.println("\n--- Alertas ---");
        System.out.println("1 - Criar alerta");
        System.out.println("2 - Listar por tratamento");
        System.out.println("3 - Listar nao emitidos");
        System.out.println("4 - Registrar consumo");
        System.out.println("5 - Remover");
        System.out.println("0 - Voltar");

        switch (lerInteiro("Escolha: ")) {
            case 1 -> criarAlerta();
            case 2 -> listarAlertasPorTratamento();
            case 3 -> listarAlertasNaoEmitidos();
            case 4 -> registrarConsumo();
            case 5 -> removerAlerta();
            case 0 -> {}
            default -> aviso("Opcao invalida.");
        }
    }

    private void criarAlerta() {
        listarTratamentos();
        int idTratamento = lerInteiro("ID do tratamento: ");
        Alerta alerta = new Alerta();
        alerta.setDataHorarioAlerta(lerDataHora("Data/hora do alerta (dd/MM/yyyy HH:mm): "));
        alerta.setStatusAlerta(StatusAlerta.NAO_EMITIDO);
        alerta.setConfirmacaoConsumo(ConfirmacaoConsumo.NAO);
        alertaService.salvar(idTratamento, alerta);
        ok("Alerta criado!");
    }

    private void listarAlertasPorTratamento() {
        int idTratamento = lerInteiro("ID do tratamento: ");
        List<Alerta> alertas = alertaService.listarPorTratamento(idTratamento);
        if (alertas.isEmpty()) {
            aviso("Nenhum alerta encontrado.");
            return;
        }
        alertas.forEach(this::imprimirAlerta);
    }

    private void listarAlertasNaoEmitidos() {
        List<Alerta> alertas = alertaService.listarPorStatus(StatusAlerta.NAO_EMITIDO);
        if (alertas.isEmpty()) {
            ok("Nenhum alerta pendente de emissao.");
            return;
        }
        alertas.forEach(this::imprimirAlerta);
    }

    private void registrarConsumo() {
        int id = lerInteiro("ID do alerta: ");
        System.out.println("O medicamento foi tomado?");
        System.out.println("1 - Sim");
        System.out.println("2 - Nao");
        int opcao = lerInteiro("Escolha: ");
        ConfirmacaoConsumo confirmacao = switch (opcao) {
            case 1 -> ConfirmacaoConsumo.SIM;
            case 2 -> ConfirmacaoConsumo.NAO;
            default -> null;
        };
        if (confirmacao == null) {
            aviso("Opcao invalida.");
            return;
        }
        alertaService.registrarConsumo(id, confirmacao);
        ok("Consumo registrado!");
    }

    private void removerAlerta() {
        int id = lerInteiro("ID do alerta a remover: ");
        alertaService.buscarPorId(id).ifPresentOrElse(alerta -> {
            alertaService.deletar(id);
            ok("Alerta removido.");
        }, () -> aviso("Alerta nao encontrado."));
    }

    private void menuTratamentoMedicamento() {
        System.out.println("\n--- Medicamentos do Tratamento ---");
        System.out.println("1 - Associar medicamento a tratamento");
        System.out.println("2 - Listar medicamentos de um tratamento");
        System.out.println("3 - Desassociar medicamento");
        System.out.println("0 - Voltar");

        switch (lerInteiro("Escolha: ")) {
            case 1 -> associarMedicamento();
            case 2 -> listarMedicamentosDeTratamento();
            case 3 -> desassociarMedicamento();
            case 0 -> {}
            default -> aviso("Opcao invalida.");
        }
    }

    private void associarMedicamento() {
        listarTratamentos();
        int idTratamento = lerInteiro("ID do tratamento: ");
        listarMedicamentos();
        int idMedicamento = lerInteiro("ID do medicamento: ");
        String quantidade = lerTexto("Quantidade/dose (ex: 1 comprimido, 10 ml): ");
        String observacao = lerTextoOpcional("Observacao (opcional): ");
        LocalTime horarioUso = lerHorario("Horario de uso (HH:mm): ");
        String frequenciaUso = lerTexto("Frequencia de uso (ex: 8h, diario): ");
        tratamentoService.associarMedicamento(idTratamento, idMedicamento, quantidade, observacao, horarioUso, frequenciaUso);
        ok("Medicamento associado ao tratamento!");
    }

    private void listarMedicamentosDeTratamento() {
        int idTratamento = lerInteiro("ID do tratamento: ");
        List<TratamentoMedicamento> itens = tratamentoService.listarMedicamentos(idTratamento);
        if (itens.isEmpty()) {
            aviso("Nenhum medicamento associado.");
            return;
        }
        itens.forEach(tm -> System.out.printf("  [%d] %s (%s) | Dose: %s | Horario: %s | Frequencia: %s%n",
                tm.getMedicamento().getIdMedicamento(),
                tm.getMedicamento().getNomeComercial(),
                valorOuTraco(tm.getMedicamento().getNomeGenerico()),
                valorOuTraco(tm.getQuantidade()),
                tm.getHorarioUso() != null ? tm.getHorarioUso().format(fmtTime) : "-",
                valorOuTraco(tm.getFrequenciaUso())));
    }

    private void desassociarMedicamento() {
        int idTratamento = lerInteiro("ID do tratamento: ");
        int idMedicamento = lerInteiro("ID do medicamento: ");
        tratamentoService.desassociarMedicamento(idTratamento, idMedicamento);
        ok("Medicamento desassociado.");
    }

    private void imprimirTratamento(Tratamento tratamento) {
        System.out.printf("  [%d] %s | Usuario: %s | Inicio: %s | Fim: %s | Status: %s%n",
                tratamento.getIdTratamento(),
                valorOuTraco(tratamento.getDescricao()),
                tratamento.getUsuario().getNome(),
                tratamento.getDataInicio() != null ? tratamento.getDataInicio().format(fmtDate) : "-",
                tratamento.getDataFim() != null ? tratamento.getDataFim().format(fmtDate) : "-",
                valorOuTraco(tratamento.getStatus()));
    }

    private void imprimirAlerta(Alerta alerta) {
        System.out.printf("  [%d] %s | Tratamento: %d | Status: %s | Consumo: %s%n",
                alerta.getIdAlerta(),
                alerta.getDataHorarioAlerta().format(fmtDateTime),
                alerta.getTratamento().getIdTratamento(),
                alerta.getStatusAlerta(),
                alerta.getConfirmacaoConsumo());
    }

    private QuantidadeTipo lerUnidadeMedida() {
        System.out.println("Unidade de medida:");
        QuantidadeTipo[] tipos = QuantidadeTipo.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("  %d - %s%n", i + 1, tipos[i].name());
        }
        int idx = lerInteiro("Escolha: ") - 1;
        if (idx < 0 || idx >= tipos.length) {
            aviso("Opcao invalida. Usando UNIDADE.");
            return QuantidadeTipo.UNIDADE;
        }
        return tipos[idx];
    }

    private String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String lerTextoOpcional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                aviso("Digite apenas numeros.");
            }
        }
    }

    private LocalDateTime lerDataHora(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDateTime.parse(scanner.nextLine().trim(), fmtDateTime);
            } catch (DateTimeParseException e) {
                aviso("Formato invalido. Use: dd/MM/yyyy HH:mm");
            }
        }
    }

    private LocalDate lerDataOpcional(String prompt) {
        while (true) {
            String valor = lerTextoOpcional(prompt);
            if (valor.isBlank()) {
                return null;
            }
            try {
                return LocalDate.parse(valor, fmtDate);
            } catch (DateTimeParseException e) {
                aviso("Formato invalido. Use: dd/MM/yyyy");
            }
        }
    }

    private LocalTime lerHorario(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalTime.parse(scanner.nextLine().trim(), fmtTime);
            } catch (DateTimeParseException e) {
                aviso("Formato invalido. Use: HH:mm");
            }
        }
    }

    private String valorOuTraco(String valor) {
        return valor == null || valor.isBlank() ? "-" : valor;
    }

    private void ok(String msg) {
        System.out.println("OK: " + msg);
    }

    private void aviso(String msg) {
        System.out.println("AVISO: " + msg);
    }
}
