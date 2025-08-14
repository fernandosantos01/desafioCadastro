package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int opcao = 0;
        do{
            menu();
            if (in.hasNextInt()){
                opcao = in.nextInt();
            }else {
                in.next();
            }

            switch (opcao) {
                case 1:
                    cadastrarPet();
                    break;
                case 2:
                    alterarPet();
                    break;
                case 3:
                    deletarPet();
                    break;
                case 4:
                    listarTodosOsPets();
                    break;
                case 5:
                    listarPetsComCriterio();
                    break;
                case 6:
                    System.out.println("ATÉ LOGO... E OBRIGADO POR USAR O NOSSO SISTEMA!!");
                    break;
                default:
                    System.out.println("CHARACTER INVALIDO DIGITE UM NUMERO DE (1 - 6)!!!");
            }
        }while(opcao != 6);
    }
    public static void menu(){
        System.out.println("""
                1. Cadastrar um novo pet
                2. Alterar os dados do pet cadastrado
                3. Deletar um pet cadastrado
                4. Listar todos os pets cadastrados
                5. Listar pets por algum critério (idade, nome, raça)
                6. Sair""");
    }
    public static void cadastrarPet(){
        String caminhoArquivo = "src\\domain\\resources\\formulario.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine())!=null){
                System.out.println(linha);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
    public static void alterarPet(){

    }
    public static void deletarPet(){

    }
    public static void listarTodosOsPets(){

    }
    public static void listarPetsComCriterio(){

    }
}
