import java.util.Random;
import java.util.Scanner;

public class SorteadorGrupos {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static String[] alunos = new String[100];
    static String[] assuntos = new String[100];
    static String[][] grupos = new String[50][20];
    static String[] assuntosSorteados = new String[50];

    static int totalAlunos = 0;
    static int totalAssuntos = 0;
    static int alunosPorGrupo = 0;
    static int totalGrupos = 0;

    static boolean gruposFormados = false;
    static boolean assuntosDefinidos = false;

    public static void main(String[] args) {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInt();

            switch (opcao) {
                case 1:
                    cadastrarAlunos();
                    break;
                case 2:
                    cadastrarAssuntos();
                    break;
                case 3:
                    definirAlunosPorGrupo();
                    break;
                case 4:
                    sortearGrupos();
                    break;
                case 5:
                    sortearAssuntosParaGrupos();
                    break;
                case 6:
                    mostrarResultadoFinal();
                    break;
                case 7:
                    refazerSorteio();
                    break;
                case 8:
                    mostrarAlunos();
                    break;
                case 9:
                    mostrarAssuntos();
                    break;
                case 0:
                    System.out.println("\nEncerrando o programa...");
                    break;
                default:
                    System.out.println("\nOpcao invalida.");
            }

            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }

        } while (opcao != 0);
    }

    public static void mostrarMenu() {
        System.out.println("\n==============================================================");
        System.out.println("         SISTEMA DE FORMACAO DE GRUPOS E SORTEIO");
        System.out.println("==============================================================");
        System.out.println("1. Cadastrar alunos");
        System.out.println("2. Cadastrar assuntos");
        System.out.println("3. Definir quantidade de alunos por grupo");
        System.out.println("4. Sortear grupos");
        System.out.println("5. Sortear assuntos para os grupos");
        System.out.println("6. Mostrar resultado final");
        System.out.println("7. Refazer sorteio");
        System.out.println("8. Mostrar alunos cadastrados");
        System.out.println("9. Mostrar assuntos cadastrados");
        System.out.println("0. Sair");
        System.out.println("==============================================================");
        System.out.print("Escolha uma opcao: ");
    }

    public static void cadastrarAlunos() {
        String nome;

        System.out.println("\n=== CADASTRO DE ALUNOS ===");
        System.out.println("Digite os nomes dos alunos.");
        System.out.println("Quando quiser parar, digite FIM.\n");

        while (true) {
            if (totalAlunos >= alunos.length) {
                System.out.println("Limite maximo de alunos atingido.");
                break;
            }

            System.out.print("Nome do aluno: ");
            nome = scanner.nextLine();

            if (nome.equalsIgnoreCase("fim")) {
                break;
            }

            if (nome.trim().isEmpty()) {
                System.out.println("Nome invalido.");
            } else {
                alunos[totalAlunos] = nome;
                totalAlunos++;
                gruposFormados = false;
                assuntosDefinidos = false;
            }
        }

        System.out.println("\nTotal de alunos cadastrados: " + totalAlunos);
    }

    public static void cadastrarAssuntos() {
        String assunto;

        System.out.println("\n=== CADASTRO DE ASSUNTOS ===");
        System.out.println("Digite os assuntos.");
        System.out.println("Quando quiser parar, digite FIM.\n");

        while (true) {
            if (totalAssuntos >= assuntos.length) {
                System.out.println("Limite maximo de assuntos atingido.");
                break;
            }

            System.out.print("Assunto: ");
            assunto = scanner.nextLine();

            if (assunto.equalsIgnoreCase("fim")) {
                break;
            }

            if (assunto.trim().isEmpty()) {
                System.out.println("Assunto invalido.");
            } else {
                assuntos[totalAssuntos] = assunto;
                totalAssuntos++;
                assuntosDefinidos = false;
            }
        }

        System.out.println("\nTotal de assuntos cadastrados: " + totalAssuntos);
    }

    public static void definirAlunosPorGrupo() {
        if (totalAlunos == 0) {
            System.out.println("\nCadastre os alunos primeiro.");
            return;
        }

        System.out.print("\nDigite a quantidade de alunos por grupo: ");
        alunosPorGrupo = lerInt();

        if (alunosPorGrupo <= 0) {
            System.out.println("Quantidade invalida.");
            alunosPorGrupo = 0;
            return;
        }

        totalGrupos = totalAlunos / alunosPorGrupo;

        if (totalAlunos % alunosPorGrupo != 0) {
            totalGrupos++;
        }

        gruposFormados = false;
        assuntosDefinidos = false;

        System.out.println("Quantidade definida com sucesso.");
        System.out.println("Serao formados " + totalGrupos + " grupo(s).");
    }

    public static void sortearGrupos() {
        if (totalAlunos == 0) {
            System.out.println("\nCadastre os alunos primeiro.");
            return;
        }

        if (alunosPorGrupo == 0) {
            System.out.println("\nDefina a quantidade de alunos por grupo primeiro.");
            return;
        }

        limparGrupos();

        String[] copiaAlunos = new String[totalAlunos];

        for (int i = 0; i < totalAlunos; i++) {
            copiaAlunos[i] = alunos[i];
        }

        embaralharVetor(copiaAlunos, totalAlunos);

        int indice = 0;

        for (int i = 0; i < totalGrupos; i++) {
            for (int j = 0; j < alunosPorGrupo; j++) {
                if (indice < totalAlunos) {
                    grupos[i][j] = copiaAlunos[indice];
                    indice++;
                }
            }
        }

        gruposFormados = true;
        assuntosDefinidos = false;

        System.out.println("\nGrupos sorteados com sucesso.");
    }

    public static void sortearAssuntosParaGrupos() {
        if (!gruposFormados) {
            System.out.println("\nFaça primeiro o sorteio dos grupos.");
            return;
        }

        if (totalAssuntos == 0) {
            System.out.println("\nCadastre os assuntos primeiro.");
            return;
        }

        if (totalAssuntos < totalGrupos) {
            System.out.println("\nNao ha assuntos suficientes para todos os grupos.");
            System.out.println("Cadastre pelo menos " + totalGrupos + " assuntos.");
            return;
        }

        String[] copiaAssuntos = new String[totalAssuntos];

        for (int i = 0; i < totalAssuntos; i++) {
            copiaAssuntos[i] = assuntos[i];
        }

        embaralharVetor(copiaAssuntos, totalAssuntos);

        for (int i = 0; i < totalGrupos; i++) {
            assuntosSorteados[i] = copiaAssuntos[i];
        }

        assuntosDefinidos = true;

        System.out.println("\nAssuntos sorteados com sucesso.");
    }

    public static void mostrarResultadoFinal() {
        if (!gruposFormados) {
            System.out.println("\nOs grupos ainda nao foram sorteados.");
            return;
        }

        System.out.println("\n================ RESULTADO FINAL ================\n");

        for (int i = 0; i < totalGrupos; i++) {
            System.out.println("Grupo " + (i + 1) + ":");

            for (int j = 0; j < alunosPorGrupo; j++) {
                if (grupos[i][j] != null) {
                    System.out.println("- " + grupos[i][j]);
                }
            }

            if (assuntosDefinidos) {
                System.out.println("Assunto: " + assuntosSorteados[i]);
            } else {
                System.out.println("Assunto: ainda nao sorteado");
            }

            System.out.println();
        }
    }

    public static void refazerSorteio() {
        if (totalAlunos == 0 || alunosPorGrupo == 0) {
            System.out.println("\nCadastre os alunos e defina os grupos primeiro.");
            return;
        }

        sortearGrupos();

        if (totalAssuntos >= totalGrupos) {
            sortearAssuntosParaGrupos();
        } else {
            System.out.println("\nOs grupos foram refeitos.");
            System.out.println("Os assuntos nao foram sorteados porque faltam assuntos suficientes.");
        }
    }

    public static void mostrarAlunos() {
        if (totalAlunos == 0) {
            System.out.println("\nNenhum aluno cadastrado.");
            return;
        }

        System.out.println("\n=== ALUNOS CADASTRADOS ===");
        for (int i = 0; i < totalAlunos; i++) {
            System.out.println((i + 1) + ". " + alunos[i]);
        }
    }

    public static void mostrarAssuntos() {
        if (totalAssuntos == 0) {
            System.out.println("\nNenhum assunto cadastrado.");
            return;
        }

        System.out.println("\n=== ASSUNTOS CADASTRADOS ===");
        for (int i = 0; i < totalAssuntos; i++) {
            System.out.println((i + 1) + ". " + assuntos[i]);
        }
    }

    public static void limparGrupos() {
        for (int i = 0; i < grupos.length; i++) {
            for (int j = 0; j < grupos[i].length; j++) {
                grupos[i][j] = null;
            }
        }

        for (int i = 0; i < assuntosSorteados.length; i++) {
            assuntosSorteados[i] = null;
        }
    }

    public static void embaralharVetor(String[] vetor, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            int posicaoAleatoria = random.nextInt(tamanho);

            String temp = vetor[i];
            vetor[i] = vetor[posicaoAleatoria];
            vetor[posicaoAleatoria] = temp;
        }
    }

    public static int lerInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um numero inteiro valido: ");
            scanner.nextLine();
        }

        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }
}