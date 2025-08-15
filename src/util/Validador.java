package util;

import java.text.Normalizer;

public class Validador {
    public static final String NAO_INFORMADO = "NÃO INFORMADO";

    public static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) return NAO_INFORMADO;
        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$"))
            throw new IllegalArgumentException("Nome inválido!");
        if (!nome.trim().contains(" "))
            throw new IllegalArgumentException("Informe nome e sobrenome!");
        return nome.trim();
    }

    public static String validarRaca(String raca) {
        if (raca == null || raca.isBlank()) return NAO_INFORMADO;
        if (!raca.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$"))
            throw new IllegalArgumentException("Raça inválida!");
        return raca.trim();
    }

    public static double validarPeso(String pesoStr) {
        if (pesoStr == null || pesoStr.isBlank()) return -1;
        pesoStr = pesoStr.replace(",", ".");
        double peso = Double.parseDouble(pesoStr);
        if (peso < 0.5 || peso > 60)
            throw new IllegalArgumentException("Peso fora do limite!");
        return peso;
    }

    public static double validarIdade(String idadeStr) {
        if (idadeStr == null || idadeStr.isBlank()) return -1;
        idadeStr = idadeStr.replace(",", ".");
        double idade = Double.parseDouble(idadeStr);
        if (idade > 20)
            throw new IllegalArgumentException("Idade fora do limite!");
        if (idade < 1) idade = idade / 12;
        return idade;
    }

    public static String formatarEndereco(String rua, String numero, String bairro, String cidade) {
        if (rua.isBlank()) rua = NAO_INFORMADO;
        if (numero.isBlank()) numero = NAO_INFORMADO;
        if (bairro.isBlank()) bairro = NAO_INFORMADO;
        if (cidade.isBlank()) cidade = NAO_INFORMADO;
        return rua + ", " + numero + ", " + bairro + " - " + cidade;
    }

    public static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}
