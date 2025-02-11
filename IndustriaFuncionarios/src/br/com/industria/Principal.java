package br.com.industria;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Principal {
    public static void main(String[] args) {
        // Formatação da data e do salário
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat formatoSalario = new DecimalFormat("#,##0.00", symbols);

        // Criação da lista de funcionários
        List<Funcionario> funcionarios = new ArrayList<>();

        // Inserindo os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remove o funcionário "João"
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // Aplica aumento de 10% no salário de todos os funcionários
        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10")); // Calcula 10% do salário
            BigDecimal novoSalario = funcionario.getSalario().add(aumento);                 // Soma o aumento ao salário atual
            funcionario.setSalario(novoSalario);                                            // Atualiza o salário do funcionário
        }
        
     // Agrupar os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            funcionariosPorFuncao.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario);
        }
        
     // Encontra o funcionário com a maior idade
        Funcionario maisVelho = null;
        int maiorIdade = -1;

        for (Funcionario funcionario : funcionarios) {
            // Calcula a idade do funcionário
            int idade = LocalDate.now().getYear() - funcionario.getDataNascimento().getYear();
            
            // Se a data de nascimento ainda não passou este ano, subtrai 1 da idade
            if (LocalDate.now().getDayOfYear() < funcionario.getDataNascimento().getDayOfYear()) {
                idade--;
            }

            // Verifica se a idade calculada é a maior
            if (idade > maiorIdade) {
                maiorIdade = idade;
                maisVelho = funcionario;
            }
        }
        
     // Ordena a lista de funcionários por nome em ordem alfabética
        Collections.sort(funcionarios, Comparator.comparing(Funcionario::getNome));
        
     // Calcula o total dos salários
        BigDecimal totalSalarios = BigDecimal.ZERO;

        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        
     // Defini o valor do salário mínimo
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        
        // Imprimir funcionários após o aumento de 10%
        System.out.println("Funcionários após aumento de 10% no salário:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Salário atualizado: R$ " + formatoSalario.format(funcionario.getSalario()));
            System.out.println("---------------------------");
        }

        // Imprimir funcionários com formatação (já formatada a data e salário)
        System.out.println("Lista de Funcionários Formatada:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatoData));
            System.out.println("Salário: R$ " + formatoSalario.format(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println("---------------------------");
        }
        
     // Imprimir os funcionários agrupados por função
        System.out.println("Funcionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario f : entry.getValue()) {
                // Formatar o salário para exibição
                System.out.println(" - " + f.getNome() + " | " + formatoSalario.format(f.getSalario()));
            }
            System.out.println("-----------------------------");
        }
        
     // Imprimir funcionários com aniversário em outubro (10) e dezembro (12)
        System.out.println("Funcionários com aniversário nos meses 10 e 12:");
        for (Funcionario funcionario : funcionarios) {
            int mesNascimento = funcionario.getDataNascimento().getMonthValue(); // Obtém o mês da data de nascimento
            if (mesNascimento == 10 || mesNascimento == 12) { // Verifica se é outubro (10) ou dezembro (12)
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatoData));
                System.out.println("---------------------------");
            }
        }
        
     // Imprimir o nome e a idade do funcionário mais velho
        if (maisVelho != null) {
            System.out.println("Funcionário mais velho:");
            System.out.println("Nome: " + maisVelho.getNome());
            System.out.println("Idade: " + maiorIdade);
            System.out.println("---------------------------");
        }
        
     // Imprimir a lista de funcionários ordenada
        System.out.println("Funcionários ordenados por nome:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println("Salário: R$ " + formatoSalario.format(funcionario.getSalario()));
            System.out.println("---------------------------");
        }
        
     // Imprimir o total dos salários
        System.out.println("Total dos salários dos funcionários: R$ " + formatoSalario.format(totalSalarios));
        System.out.println("---------------------------");
        
     // Imprimir quantos salários mínimos cada funcionário ganha
        System.out.println("Quantidade de salários mínimos que cada funcionário ganha:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Salário: R$ " + formatoSalario.format(funcionario.getSalario()));
            System.out.println("Salários Mínimos: " + quantidadeSalariosMinimos);
            System.out.println("---------------------------");
        }
    }
}
