package br.uninter.medalerta.app;

import br.uninter.medalerta.model.*;
import br.uninter.medalerta.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class App implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private final MedicamentoService medicamentoService;
    private final TratamentoService tratamentoService;
    private final AlertaService alertaService;
    private final TratamentoMedicamentoService tratamentoMedicamentoService;

    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm");

    public App(UsuarioService usuarioService,
               EnderecoService enderecoService,
               MedicamentoService medicamentoService,
               TratamentoService tratamentoService,
               AlertaService alertaService,
               TratamentoMedicamentoService tratamentoMedicamentoService) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.medicamentoService = medicamentoService;
        this.tratamentoService = tratamentoService;
        this.alertaService = alertaService;
        this.tratamentoMedicamentoService = tratamentoMedicamentoService;
    }

    @Override
    public void run(String... args) {
        System.out.println("==============================");
        System.out.println("         MedAlerta            ");
        System.out.println("  Seu lembrete de medicamentos");
        System.out.println("==============================");

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Usuarios");
            System.out.println("2 - Enderecos");
            System.out.println("3 - Medicamentos");
            System.out.println("4 - Tratamentos");
            System.out.println("5 - Alertas");
            System.out.println("6 - Medicamentos de um Tratamento");
            System.out.println("0 - Sair");

            switch (lerInteiro("Escolha: ")) {
                case 1 -> menuUsuario();
                case 2 -> menuEndereco();
                case 3 -> menuMedicamento();
                case 4 -> menuTratamento();
                case 5 -> menuAlerta();
                case 6 -> menuTratamentoMedicamento();
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
        System.out.println("\nNovo Usuario");
        Usuario u = new Usuario();
        u.setNome(lerTexto("Nome: "));
        u.setTelefone(lerTexto("Telefone: "));
        u.setEmail(lerTexto("E-mail: "));
        usuarioService.salvar(u);
        ok("Usuario cadastrado com sucesso!");
    }

    private void listarUsuarios() {
        List<Usuario> lista = usuarioService.listarTodos();
        if (lista.isEmpty()) { 
            aviso("Nenhum usuario cadastrado."); 
            return; 
        }
        System.out.println("\nUsuarios:");
        lista.forEach(u -> System.out.printf("  [%d] %s | %s | %s%n",
                u.getIdUsuario(), u.getNome(), u.getTelefone(), u.getEmail()));
    }

    private void buscarUsuario() {
        int id = lerInteiro("ID do usuario: ");
        usuarioService.buscarPorId(id).ifPresentOrElse(
                u -> System.out.printf("\n[%d] %s | %s | %s%n",
                        u.getIdUsuario(), u.getNome(), u.getTelefone(), u.getEmail()),
                () -> aviso("Usuario nao encontrado.")
        );
    }

    private void atualizarUsuario() {
        int id = lerInteiro("ID do usuario a atualizar: ");
        usuarioService.buscarPorId(id).ifPresentOrElse(u -> {
            System.out.println("Deixe em branco para manter o valor atual.");
            String nome  = lerTextoOpcional("Nome [" + u.getNome() + "]: ");
            String tel   = lerTextoOpcional("Telefone [" + u.getTelefone() + "]: ");
            String email = lerTextoOpcional("E-mail [" + u.getEmail() + "]: ");
            if (!nome.isBlank())  u.setNome(nome);
            if (!tel.isBlank())   u.setTelefone(tel);
            if (!email.isBlank()) u.setEmail(email);
            usuarioService.salvar(u);
            ok("Usuario atualizado!");
        }, () -> aviso("Usuario nao encontrado."));
    }

    private void removerUsuario() {
        int id = lerInteiro("ID do usuario a remover: ");
        usuarioService.buscarPorId(id).ifPresentOrElse(u -> {
            usuarioService.deletar(id);
            ok("Usuario removido.");
        }, () -> aviso("Usuario nao encontrado."));
    }

    private void menuEndereco() {
        System.out.println("\n--- Enderecos ---");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar por usuario");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Atualizar");
        System.out.println("5 - Remover");
        System.out.println("0 - Voltar");

        switch (lerInteiro("Escolha: ")) {
            case 1 -> cadastrarEndereco();
            case 2 -> listarEnderecosPorUsuario();
            case 3 -> buscarEndereco();
            case 4 -> atualizarEndereco();
            case 5 -> removerEndereco();
            case 0 -> {}
            default -> aviso("Opcao invalida.");
        }
    }

    private void cadastrarEndereco() {
        System.out.println("\nNovo Endereco");
        listarUsuarios();
        int idUsuario = lerInteiro("ID do usuario: ");
        Endereco e = new Endereco();
        e.setRua(lerTexto("Rua: "));
        e.setNumero(lerInteiro("Numero: "));
        e.setComplemento(lerTextoOpcional("Complemento (opcional): "));
        e.setBairro(lerTexto("Bairro: "));
        e.setCep(lerTexto("CEP: "));
        e.setCidade(lerTexto("Cidade: "));
        e.setEstado(lerTexto("Estado (UF): "));
        enderecoService.salvar(idUsuario, e);
        ok("Endereco cadastrado!");
    }

    private void listarEnderecosPorUsuario() {
        int idUsuario = lerInteiro("ID do usuario: ");
        List<Endereco> lista = enderecoService.listarPorUsuario(idUsuario);
        if (lista.isEmpty()) { 
            aviso("Nenhum endereco encontrado para este usuario."); 
            return; 
        }
        System.out.println("\nEnderecos:");
        lista.forEach(e -> System.out.printf("  [%d] %s, %d %s - %s - %s/%s | CEP: %s%n",
                e.getIdEndereco(), e.getRua(), e.getNumero(),
                e.getComplemento() != null ? e.getComplemento() : "",
                e.getBairro(), e.getCidade(), e.getEstado(), e.getCep()));
    }

    private void buscarEndereco() {
        int id = lerInteiro("ID do endereco: ");
        enderecoService.buscarPorId(id).ifPresentOrElse(
                e -> System.out.printf("\n[%d] %s, %d %s - %s - %s/%s | CEP: %s%n",
                        e.getIdEndereco(), e.getRua(), e.getNumero(),
                        e.getComplemento() != null ? e.getComplemento() : "",
                        e.getBairro(), e.getCidade(), e.getEstado(), e.getCep()),
                () -> aviso("Endereco nao encontrado.")
        );
    }

    private void atualizarEndereco() {
        int id = lerInteiro("ID do endereco a atualizar: ");
        enderecoService.buscarPorId(id).ifPresentOrElse(e -> {
            System.out.println("Deixe em branco para manter o valor atual.");
            String rua   = lerTextoOpcional("Rua [" + e.getRua() + "]: ");
            String bairro = lerTextoOpcional("Bairro [" + e.getBairro() + "]: ");
            String cep   = lerTextoOpcional("CEP [" + e.getCep() + "]: ");
            String cidade = lerTextoOpcional("Cidade [" + e.getCidade() + "]: ");
            String estado = lerTextoOpcional("Estado [" + e.getEstado() + "]: ");
            String comp  = lerTextoOpcional("Complemento [" + e.getComplemento() + "]: ");
            if (!rua.isBlank())    e.setRua(rua);
            if (!bairro.isBlank()) e.setBairro(bairro);
            if (!cep.isBlank())    e.setCep(cep);
            if (!cidade.isBlank()) e.setCidade(cidade);
            if (!estado.isBlank()) e.setEstado(estado);
            if (!comp.isBlank())   e.setComplemento(comp);
            enderecoService.atualizar(id, e);
            ok("Endereco atualizado!");
        }, () -> aviso("Endereco nao encontrado."));
    }

    private void removerEndereco() {
        int id = lerInteiro("ID do endereco a remover: ");
        enderecoService.buscarPorId(id).ifPresentOrElse(e -> {
            enderecoService.deletar(id);
            ok("Endereco removido.");
        }, () -> aviso("Endereco nao encontrado."));
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
        System.out.println("\nNovo Medicamento");
        Medicamento m = new Medicamento();
        m.setNomeComercial(lerTexto("Nome comercial: "));
        m.setNomeGenerico(lerTexto("Nome generico: "));
        m.setFormaUso(lerTexto("Forma de uso (ex: oral, injetavel): "));
        m.setObservacao(lerTextoOpcional("Observacao (opcional): "));

        System.out.println("Quantidade:");
        QuantidadeTipo[] tipos = QuantidadeTipo.values();
        for (int i = 0; i < tipos.length; i++)
            System.out.printf("  %d - %s%n", i + 1, tipos[i].name());
        int idx = lerInteiro("Escolha: ") - 1;
        if (idx < 0 || idx >= tipos.length) { 
            aviso("Opcao invalida."); 
            return; 
        }
        m.setQuantidade(tipos[idx]);

        medicamentoService.salvar(m);
        ok("Medicamento cadastrado!");
    }

    private void listarMedicamentos() {
        List<Medicamento> lista = medicamentoService.listarTodos();
        if (lista.isEmpty()) { aviso("Nenhum medicamento cadastrado."); return; }
        System.out.println("\nMedicamentos:");
        lista.forEach(m -> System.out.printf("  [%d] %s (%s) | %s | %s%n",
                m.getIdMedicamento(), m.getNomeComercial(), m.getNomeGenerico(),
                m.getQuantidade(), m.getFormaUso()));
    }

    private void buscarMedicamento() {
        int id = lerInteiro("ID do medicamento: ");
        medicamentoService.buscarPorId(id).ifPresentOrElse(
                m -> System.out.printf("\n[%d] %s (%s) | %s | Obs: %s%n",
                        m.getIdMedicamento(), m.getNomeComercial(), m.getNomeGenerico(),
                        m.getFormaUso(), m.getObservacao()),
                () -> aviso("Medicamento nao encontrado.")
        );
    }

    private void atualizarMedicamento() {
        int id = lerInteiro("ID do medicamento a atualizar: ");
        medicamentoService.buscarPorId(id).ifPresentOrElse(m -> {
            System.out.println("Deixe em branco para manter o valor atual.");
            String nc  = lerTextoOpcional("Nome comercial [" + m.getNomeComercial() + "]: ");
            String ng  = lerTextoOpcional("Nome generico [" + m.getNomeGenerico() + "]: ");
            String fu  = lerTextoOpcional("Forma de uso [" + m.getFormaUso() + "]: ");
            String obs = lerTextoOpcional("Observacao [" + m.getObservacao() + "]: ");
            if (!nc.isBlank())  m.setNomeComercial(nc);
            if (!ng.isBlank())  m.setNomeGenerico(ng);
            if (!fu.isBlank())  m.setFormaUso(fu);
            if (!obs.isBlank()) m.setObservacao(obs);
            medicamentoService.salvar(m);
            ok("Medicamento atualizado!");
        }, () -> aviso("Medicamento nao encontrado."));
    }

    private void removerMedicamento() {
        int id = lerInteiro("ID do medicamento a remover: ");
        medicamentoService.buscarPorId(id).ifPresentOrElse(m -> {
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
        System.out.println("\nNovo Tratamento");
        listarUsuarios();
        int idUsuario = lerInteiro("ID do usuario: ");
        Tratamento t = new Tratamento();
        t.setHorarioUso(lerHorario("Horario de uso (HH:mm): "));
        t.setFrequenciaUso(lerTexto("Frequencia de uso (ex: a cada 8 horas, diario): "));
        tratamentoService.salvar(idUsuario, t);
        ok("Tratamento cadastrado!");
    }

    private void listarTratamentos() {
        List<Tratamento> lista = tratamentoService.listarTodos();
        if (lista.isEmpty()) { 
            aviso("Nenhum tratamento cadastrado."); 
            return;
        }
        System.out.println("\nTratamentos:");
        lista.forEach(t -> System.out.printf("  [%d] Horario: %s | Frequencia: %s | Usuario: %s%n",
                t.getIdTratamento(),
                t.getHorarioUso() != null ? t.getHorarioUso().format(fmtTime) : "-",
                t.getFrequenciaUso(),
                t.getUsuario().getNome()));
    }

    private void listarTratamentosPorUsuario() {
        int idUsuario = lerInteiro("ID do usuario: ");
        List<Tratamento> lista = tratamentoService.listarPorUsuario(idUsuario);
        if (lista.isEmpty()) { 
            aviso("Nenhum tratamento encontrado para este usuario."); 
            return; 
        }
        System.out.println("\nTratamentos do usuario:");
        lista.forEach(t -> System.out.printf("  [%d] Horario: %s | Frequencia: %s%n",
                t.getIdTratamento(),
                t.getHorarioUso() != null ? t.getHorarioUso().format(fmtTime) : "-",
                t.getFrequenciaUso()));
    }

    private void buscarTratamento() {
        int id = lerInteiro("ID do tratamento: ");
        tratamentoService.buscarPorId(id).ifPresentOrElse(
                t -> System.out.printf("\n[%d] Horario: %s | Frequencia: %s | Usuario: %s%n",
                        t.getIdTratamento(),
                        t.getHorarioUso() != null ? t.getHorarioUso().format(fmtTime) : "-",
                        t.getFrequenciaUso(),
                        t.getUsuario().getNome()),
                () -> aviso("Tratamento nao encontrado.")
        );
    }

    private void atualizarTratamento() {
        int id = lerInteiro("ID do tratamento a atualizar: ");
        tratamentoService.buscarPorId(id).ifPresentOrElse(t -> {
            System.out.println("Deixe em branco para manter o valor atual.");
            String freq = lerTextoOpcional("Frequencia de uso [" + t.getFrequenciaUso() + "]: ");
            if (!freq.isBlank()) t.setFrequenciaUso(freq);
            tratamentoService.salvar(t.getUsuario().getIdUsuario(), t);
            ok("Tratamento atualizado!");
        }, () -> aviso("Tratamento nao encontrado."));
    }

    private void removerTratamento() {
        int id = lerInteiro("ID do tratamento a remover: ");
        tratamentoService.buscarPorId(id).ifPresentOrElse(t -> {
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
        System.out.println("\nNovo Alerta");
        listarTratamentos();
        int idTratamento = lerInteiro("ID do tratamento: ");
        Alerta a = new Alerta();
        a.setDataHorarioAlerta(lerDataHora("Data/hora do alerta (dd/MM/yyyy HH:mm): "));
        a.setStatusAlerta(StatusAlerta.NAO_EMITIDO);
        a.setConfirmacaoConsumo(ConfirmacaoConsumo.NAO);
        alertaService.salvar(idTratamento, a);
        ok("Alerta criado!");
    }

    private void listarAlertasPorTratamento() {
        int idTratamento = lerInteiro("ID do tratamento: ");
        List<Alerta> lista = alertaService.listarPorTratamento(idTratamento);
        if (lista.isEmpty()) { 
            aviso("Nenhum alerta encontrado."); 
            return; 
        }
        System.out.println("\nAlertas:");
        lista.forEach(a -> System.out.printf("  [%d] %s | Status: %s | Consumo: %s%n",
                a.getIdAlerta(),
                a.getDataHorarioAlerta().format(fmtDateTime),
                a.getStatusAlerta(),
                a.getConfirmacaoConsumo()));
    }

    private void listarAlertasNaoEmitidos() {
        List<Alerta> lista = alertaService.listarPorStatus(StatusAlerta.NAO_EMITIDO);
        if (lista.isEmpty()) { 
            ok("Nenhum alerta pendente de emissao."); 
            return; 
        }
        System.out.println("\nAlertas nao emitidos:");
        lista.forEach(a -> System.out.printf("  [%d] %s | Tratamento ID: %d%n",
                a.getIdAlerta(),
                a.getDataHorarioAlerta().format(fmtDateTime),
                a.getTratamento().getIdTratamento()));
    }

    private void registrarConsumo() {
        int id = lerInteiro("ID do alerta: ");
        alertaService.buscarPorId(id).ifPresentOrElse(a -> {
            System.out.println("O medicamento foi tomado?");
            System.out.println("1 - Sim");
            System.out.println("2 - Nao");
            int op = lerInteiro("Escolha: ");
            switch (op) {
                case 1 -> a.setConfirmacaoConsumo(ConfirmacaoConsumo.SIM);
                case 2 -> a.setConfirmacaoConsumo(ConfirmacaoConsumo.NAO);
                default -> { aviso("Opcao invalida."); return; }
            }
            a.setStatusAlerta(StatusAlerta.EMITIDO);
            a.setDataHorarioConsumo(LocalDateTime.now());
            alertaService.atualizar(id, a);
            ok("Consumo registrado!");
        }, () -> aviso("Alerta nao encontrado."));
    }

    private void removerAlerta() {
        int id = lerInteiro("ID do alerta a remover: ");
        alertaService.buscarPorId(id).ifPresentOrElse(a -> {
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
        tratamentoMedicamentoService.associar(idTratamento, idMedicamento);
        ok("Medicamento associado ao tratamento!");
    }

    private void listarMedicamentosDeTratamento() {
        int idTratamento = lerInteiro("ID do tratamento: ");
        List<TratamentoMedicamento> lista = tratamentoMedicamentoService.listarPorTratamento(idTratamento);
        if (lista.isEmpty()) { 
            aviso("Nenhum medicamento associado."); 
            return; 
        }
        System.out.println("\nMedicamentos do tratamento:");
        lista.forEach(tm -> System.out.printf("  [%d] %s (%s)%n",
                tm.getMedicamento().getIdMedicamento(),
                tm.getMedicamento().getNomeComercial(),
                tm.getMedicamento().getNomeGenerico()));
    }

    private void desassociarMedicamento() {
        int idTratamento = lerInteiro("ID do tratamento: ");
        int idMedicamento = lerInteiro("ID do medicamento: ");
        tratamentoMedicamentoService.desassociar(idTratamento, idMedicamento);
        ok("Medicamento desassociado.");
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

    private void ok(String msg) {
        System.out.println("OK: " + msg);
    }

    private void aviso(String msg) {
        System.out.println("AVISO: " + msg);
    }
}