package service;

import model.*;
import model.SexoPet;
import model.TipoPet;
import util.Validador;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PetService {
    private static final Scanner sc = new Scanner(System.in);

    public void salvarPet(Pet pet) throws IOException {
        String nomeArquivo = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm")
                .format(pet.getDataCadastro()) + "-" +
                pet.getNomeCompleto().replace(" ", "").toUpperCase() + ".txt";
        Path path = Paths.get("src/petsCadastrados", nomeArquivo);
        List<String> linhas = List.of(
                pet.getNomeCompleto(),
                pet.getTipo().toString(),
                pet.getSexo().toString(),
                pet.getEndereco(),
                String.valueOf(pet.getIdade()),
                String.valueOf(pet.getPeso()),
                pet.getRaca()
        );
        Files.write(path, linhas);
        System.out.println("Pet salvo com sucesso!");
    }

    public List<Path> listarTodosArquivos() throws IOException {
        return Files.list(Paths.get("src/petsCadastrados"))
                .filter(f -> f.toString().endsWith(".txt"))
                .toList();
    }

    public void listarTodos() {
        try {
            List<Path> arquivos = listarTodosArquivos();
            int i = 1;
            for (Path f : arquivos) {
                List<String> dados = Files.readAllLines(f);
                System.out.println(i++ + ". " + String.join(" - ", dados));
            }
        } catch (IOException e) {
            System.out.println("Erro listando pets: " + e.getMessage());
        }
    }

    public List<Path> buscarPetsRetornarArquivos() {
        System.out.println("Digite termo para buscar:");
        String termo = sc.nextLine();
        String termoNorm = Validador.normalizar(termo);
        List<Path> encontrados = new ArrayList<>();
        try {
            List<Path> arquivos = listarTodosArquivos();
            int i = 1;
            for (Path f : arquivos) {
                List<String> dados = Files.readAllLines(f);
                String todos = String.join(" ", dados);
                if (Validador.normalizar(todos).contains(termoNorm)) {
                    System.out.println(i + ". " + String.join(" - ", dados));
                    encontrados.add(f);
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("Erro na busca: " + e.getMessage());
        }
        return encontrados;
    }

    public void buscarPets() {
        buscarPetsRetornarArquivos();
    }

    public void alterarPet() {
        try {
            List<Path> encontrados = buscarPetsRetornarArquivos();
            if (encontrados.isEmpty()) {
                System.out.println("Nenhum pet encontrado.");
                return;
            }
            System.out.println("Escolha o número do pet para alterar:");
            String escolhaStr = sc.nextLine();
            if (!escolhaStr.matches("\\d+")) {
                System.out.println("Número inválido.");
                return;
            }
            int escolha = Integer.parseInt(escolhaStr);
            if (escolha < 1 || escolha > encontrados.size()) {
                System.out.println("Número inválido.");
                return;
            }
            Path arquivo = encontrados.get(escolha - 1);
            List<String> dados = Files.readAllLines(arquivo);

            Pet pet = new Pet();
            pet.setNomeCompleto(Validador.validarNome(alterarCampo("Nome completo", dados.get(0))));
            pet.setTipo(TipoPet.valueOf(dados.get(1))); // não altera tipo
            pet.setSexo(SexoPet.valueOf(dados.get(2))); // não altera sexo
            pet.setEndereco(Validador.formatarEndereco(
                    alterarCampo("Rua", dados.get(3).split(",")[0].trim()),
                    alterarCampo("Número", dados.get(3).split(",")[1].trim()),
                    alterarCampo("Bairro", dados.get(3).split(",")[2].trim().split("-")[0].trim()),
                    alterarCampo("Cidade", dados.get(3).split("-")[1].trim())
            ));
            pet.setIdade(Validador.validarIdade(alterarCampo("Idade", dados.get(4))));
            pet.setPeso(Validador.validarPeso(alterarCampo("Peso", dados.get(5))));
            pet.setRaca(Validador.validarRaca(alterarCampo("Raça", dados.get(6))));
            pet.setDataCadastro(LocalDateTime.now());

            // Sobrescreve
            salvarPetAlterado(arquivo, pet);
            System.out.println("Pet alterado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro alterando pet: " + e.getMessage());
        }
    }

    private String alterarCampo(String campo, String valorAtual) {
        System.out.println(campo + " [" + valorAtual + "]: (Enter para manter)");
        String novo = sc.nextLine();
        return novo.isBlank() ? valorAtual : novo;
    }

    private void salvarPetAlterado(Path arquivo, Pet pet) throws IOException {
        List<String> linhas = List.of(
                pet.getNomeCompleto(),
                pet.getTipo().toString(),
                pet.getSexo().toString(),
                pet.getEndereco(),
                String.valueOf(pet.getIdade()),
                String.valueOf(pet.getPeso()),
                pet.getRaca()
        );
        Files.write(arquivo, linhas, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void deletarPet() {
        try {
            List<Path> encontrados = buscarPetsRetornarArquivos();
            if (encontrados.isEmpty()) {
                System.out.println("Nenhum pet encontrado.");
                return;
            }
            System.out.println("Escolha o número do pet para deletar:");
            String escolhaStr = sc.nextLine();
            if (!escolhaStr.matches("\\d+")) {
                System.out.println("Número inválido.");
                return;
            }
            int escolha = Integer.parseInt(escolhaStr);
            if (escolha < 1 || escolha > encontrados.size()) {
                System.out.println("Número inválido.");
                return;
            }
            Path arquivo = encontrados.get(escolha - 1);
            System.out.println("Confirma exclusão? (SIM/NÃO)");
            String confirm = sc.nextLine().trim().toUpperCase();
            if (confirm.equals("SIM")) {
                Files.delete(arquivo);
                System.out.println("Pet deletado com sucesso!");
            } else {
                System.out.println("Exclusão cancelada.");
            }
        } catch (IOException e) {
            System.out.println("Erro deletando pet: " + e.getMessage());
        }
    }
}
