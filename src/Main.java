import model.*;
import model.SexoPet;
import model.TipoPet;
import service.PetService;
import util.Validador;

import java.nio.file.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final PetService petService = new PetService();

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get("data"));
            Files.createDirectories(Paths.get("petsCadastrados"));
            Path formPath = Paths.get("data/formulario.txt");
            if (!Files.exists(formPath)) {
                Files.write(formPath, List.of(
                        "1 - Qual o nome e sobrenome do pet?",
                        "2 - Qual o tipo do pet (Cachorro/Gato)?",
                        "3 - Qual o sexo do animal?",
                        "4 - Qual endereço e bairro que ele foi encontrado?",
                        "5 - Qual a idade aproximada do pet?",
                        "6 - Qual o peso aproximado do pet?",
                        "7 - Qual a raça do pet?"
                ));
            }
        } catch (Exception e) {
            System.out.println("Erro criando pastas: " + e.getMessage());
        }

        while (true) {
            System.out.println("""
                ===== MENU =====
                1 - Cadastrar novo pet
                2 - Alterar dados de um pet
                3 - Deletar um pet
                4 - Listar todos os pets
                5 - Buscar pets por critério
                6 - Sair
                Escolha:
            """);
            String op = sc.nextLine();
            if (!op.matches("\\d+")) {
                System.out.println("Digite apenas números!");
                continue;
            }
            int opcao = Integer.parseInt(op);
            switch (opcao) {
                case 1 -> cadastrarPet();
                case 2 -> petService.alterarPet();
                case 3 -> petService.deletarPet();
                case 4 -> petService.listarTodos();
                case 5 -> petService.buscarPets();
                case 6 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarPet() {
        try {
            List<String> perguntas = Files.readAllLines(Paths.get("data/formulario.txt"));
            Pet pet = new Pet();

            // Nome
            System.out.println(perguntas.get(0));
            String nomeCompleto = sc.nextLine();
            pet.setNomeCompleto(Validador.validarNome(nomeCompleto));

            // Tipo
            System.out.println(perguntas.get(1));
            pet.setTipo(TipoPet.valueOf(sc.nextLine().trim().toUpperCase()));

            // Sexo
            System.out.println(perguntas.get(2));
            pet.setSexo(SexoPet.valueOf(sc.nextLine().trim().toUpperCase()));

            // Endereço
            System.out.println("Rua:");
            String rua = sc.nextLine();
            System.out.println("Número:");
            String numero = sc.nextLine();
            System.out.println("Bairro:");
            String bairro = sc.nextLine();
            System.out.println("Cidade:");
            String cidade = sc.nextLine();
            pet.setEndereco(Validador.formatarEndereco(rua, numero, bairro, cidade));

            // Idade
            System.out.println(perguntas.get(4));
            pet.setIdade(Validador.validarIdade(sc.nextLine()));

            // Peso
            System.out.println(perguntas.get(5));
            pet.setPeso(Validador.validarPeso(sc.nextLine()));

            // Raça
            System.out.println(perguntas.get(6));
            pet.setRaca(Validador.validarRaca(sc.nextLine()));

            petService.salvarPet(pet);
        } catch (Exception e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }
}