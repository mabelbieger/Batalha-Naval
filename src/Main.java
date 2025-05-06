import java.util.Random;
import java.util.Scanner;

public class Main {

            static final int tamanhoTabuleiro = 10;
            static final int agua = 0;
            static final int navio = 1;
            static final int tiroAgua = 2;
            static final int tiroNavio = 3;


            static final int[] tamanhosNavios = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
            static final int[] quantidadesNavios = {1, 2, 3, 4};


            static Scanner scanner = new Scanner(System.in);
            static Random random = new Random();


            public static void main(String[] args) {
                System.out.println("BATALHA NAVAL!");
                System.out.println("Bem-vindo(a) a Batalha Naval!");


                int modo = escolherModoJogo();


                String jogador1 = obterNomeJogador(1);
                String jogador2;
                if (modo == 1) {
                    jogador2 = "Computador";
                } else {
                    jogador2 = obterNomeJogador(2);
                }


                int[][] tabuleiroJogador1 = new int[tamanhoTabuleiro][tamanhoTabuleiro];
                int[][] tabuleiroJogador2 = new int[tamanhoTabuleiro][tamanhoTabuleiro];


                System.out.println("\n" + jogador1 + ", posicione seus navios!");
                posicionarNavios(tabuleiroJogador1, jogador1);


                System.out.println("\nAgora é a vez de " + jogador2 + " posicionar os navios!");
                if (modo == 1) {
                    posicionarNaviosAutomaticamente(tabuleiroJogador2);
                    System.out.println("O computador posicionou os navios automaticamente.");
                } else {
                    posicionarNavios(tabuleiroJogador2, jogador2);
                }


                boolean jogadorAtual = true;
                boolean fimDeJogo = false;


                while (!fimDeJogo) {
                    String nomeJogadorAtual;
                    String nomeJogadorOponente;
                    int[][] tabuleiroAtacante;
                    int[][] tabuleiroDefensor;


                    if (jogadorAtual) {
                        nomeJogadorAtual = jogador1;
                        nomeJogadorOponente = jogador2;
                        tabuleiroAtacante = tabuleiroJogador1;
                        tabuleiroDefensor = tabuleiroJogador2;
                    } else {
                        nomeJogadorAtual = jogador2;
                        nomeJogadorOponente = jogador1;
                        tabuleiroAtacante = tabuleiroJogador2;
                        tabuleiroDefensor = tabuleiroJogador1;
                    }


                    System.out.println("\nTurno de " + nomeJogadorAtual);


                    System.out.println("\nTabuleiro do(a) " + nomeJogadorOponente);
                    exibirTabuleiroOponente(tabuleiroDefensor);


                    boolean continuarJogando = realizarAtaque(tabuleiroDefensor, nomeJogadorAtual, modo, !jogadorAtual);


                    if (verificarFimDeJogo(tabuleiroDefensor)) {
                        System.out.println("\n" + nomeJogadorAtual + " VENCEU! Todos os navios de " + nomeJogadorOponente + " foram destruídos!");
                        fimDeJogo = true;
                    }


                    if (!continuarJogando && !fimDeJogo) {
                        jogadorAtual = !jogadorAtual;
                    }
                }


                scanner.close();
            }


            public static int escolherModoJogo() {
                int modo = 0;
                boolean entradaValida = false;


                while (!entradaValida) {
                    System.out.println("\nEscolha o modo de jogo:");
                    System.out.println("1 - Jogar contra o computador");
                    System.out.println("2 - Jogar contra outro jogador");
                    System.out.print("Opção: ");


                    if (scanner.hasNextInt()) {
                        modo = scanner.nextInt();
                        if (modo == 1 || modo == 2) {
                            entradaValida = true;
                        } else {
                            System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                        }
                    } else {
                        System.out.println("Inválido! Por favor, digite um número.");
                        scanner.next();
                    }
                }
                scanner.nextLine();
                return modo;
            }


            public static String obterNomeJogador(int numeroJogador) {
                System.out.print("Digite o nome do Jogador " + numeroJogador + ": ");
                String nome = scanner.nextLine().trim();


                if (nome.isEmpty()) {
                    nome = "Jogador " + numeroJogador;
                }


                return nome;
            }


            public static void posicionarNavios(int[][] tabuleiro, String nomeJogador) {
                int opcaoPosicionamento = escolherFormaPosicionamento();


                if (opcaoPosicionamento == 1) {
                    posicionarNaviosAutomaticamente(tabuleiro);
                    System.out.println("Navios posicionados automaticamente para " + nomeJogador);
                } else {
                    posicionarNaviosManualmente(tabuleiro, nomeJogador);
                }
            }


            public static int escolherFormaPosicionamento() {
                int opcao = 0;
                boolean entradaValida = false;


                while (!entradaValida) {
                    System.out.println("\nComo você quer posicionar seus navios?");
                    System.out.println("1 - Automático (posicionamento aleatório)");
                    System.out.println("2 - Manual (você escolhe as posições)");
                    System.out.print("Opção: ");


                    if (scanner.hasNextInt()) {
                        opcao = scanner.nextInt();
                        if (opcao == 1 || opcao == 2) {
                            entradaValida = true;
                        } else {
                            System.out.println("Opção inválida! Por favor, escolha 1 ou 2.");
                        }
                    } else {
                        System.out.println("Inválido! Por favor, digite um número.");
                        scanner.next();
                    }
                }
                scanner.nextLine();
                return opcao;
            }


            public static void posicionarNaviosAutomaticamente(int[][] tabuleiro) {
                for (int i = 0; i < tamanhosNavios.length; i++) {
                    int tamanhoNavio = tamanhosNavios[i];
                    boolean posicionado = false;


                    while (!posicionado) {
                        int orientacao = random.nextInt(2);


                        int linha, coluna;
                        if (orientacao == 0) { // Horizontal
                            linha = random.nextInt(tamanhoTabuleiro);
                            coluna = random.nextInt(tamanhoTabuleiro - tamanhoNavio + 1);
                        } else { // Vertical
                            linha = random.nextInt(tamanhoTabuleiro - tamanhoNavio + 1);
                            coluna = random.nextInt(tamanhoTabuleiro);
                        }


                        if (validarPosicaoNavio(tabuleiro, linha, coluna, tamanhoNavio, orientacao)) {
                            posicionarNavio(tabuleiro, linha, coluna, tamanhoNavio, orientacao);
                            posicionado = true;
                        }
                    }
                }
            }


            public static void posicionarNaviosManualmente(int[][] tabuleiro, String nomeJogador) {
                System.out.println("\n" + nomeJogador + ", posicione seus navios:");


                int navioAtual = 0;
                while (navioAtual < tamanhosNavios.length) {
                    int tamanhoNavio = tamanhosNavios[navioAtual];


                    System.out.println("\nPosicionando navio de tamanho " + tamanhoNavio);
                    System.out.println("Seu tabuleiro atual:");
                    exibirTabuleiroJogador(tabuleiro);


                    int orientacao = escolherOrientacaoNavio();


                    System.out.println("Digite a posição inicial do navio:");
                    int linha = lerCoordenada("Linha (0-9): ");
                    char colunaChar = lerCoordenadaColuna("Coluna (A-J): ");
                    int coluna = colunaCharParaIndice(colunaChar);


                    if (validarPosicaoNavio(tabuleiro, linha, coluna, tamanhoNavio, orientacao)) {
                        posicionarNavio(tabuleiro, linha, coluna, tamanhoNavio, orientacao);
                        navioAtual++;
                    } else {
                        System.out.println("Posição inválida! O navio ficaria fora do tabuleiro ou sobre outro navio.");
                    }
                }


                System.out.println("\nTodos os navios foram posicionados!");
                System.out.println("Seu tabuleiro final:");
                exibirTabuleiroJogador(tabuleiro);
            }


            public static int escolherOrientacaoNavio() {
                int orientacao = -1;
                boolean entradaValida = false;


                while (!entradaValida) {
                    System.out.println("Escolha a orientação do navio:");
                    System.out.println("0 - Horizontal");
                    System.out.println("1 - Vertical");
                    System.out.print("Orientação: ");


                    if (scanner.hasNextInt()) {
                        orientacao = scanner.nextInt();
                        if (orientacao == 0 || orientacao == 1) {
                            entradaValida = true;
                        } else {
                            System.out.println("Orientação inválida! Escolha 0 para horizontal ou 1 para vertical.");
                        }
                    } else {
                        System.out.println("Inválido! Digite um número (0 ou 1).");
                        scanner.next();
                    }
                }


                return orientacao;
            }


            public static int lerCoordenada(String mensagem) {
                int coordenada = -1;
                boolean entradaValida = false;


                while (!entradaValida) {
                    System.out.print(mensagem);


                    if (scanner.hasNextInt()) {
                        coordenada = scanner.nextInt();
                        if (coordenada >= 0 && coordenada < tamanhoTabuleiro) {
                            entradaValida = true;
                        } else {
                            System.out.println("Coordenada inválida! Digite um valor entre 0 e " + (tamanhoTabuleiro - 1) + ".");
                        }
                    } else {
                        System.out.println("Inválido! Digite um número.");
                        scanner.next();
                    }
                }


                return coordenada;
            }


            public static char lerCoordenadaColuna(String mensagem) {
                char coluna = ' ';
                boolean entradaValida = false;


                while (!entradaValida) {
                    System.out.print(mensagem);
                    String entrada = scanner.next().toUpperCase();


                    if (entrada.length() == 1) {
                        coluna = entrada.charAt(0);
                        if (coluna >= 'A' && coluna < 'A' + tamanhoTabuleiro) {
                            entradaValida = true;
                        } else {
                            System.out.println("Coordenada inválida! Digite uma letra entre A e " + (char)('A' + tamanhoTabuleiro - 1) + ".");
                        }
                    } else {
                        System.out.println("Inválido! Digite uma única letra.");
                    }
                }


                return coluna;
            }


            public static int colunaCharParaIndice(char colunaChar) {
                return colunaChar - 'A';
            }


            public static char indiceParaColunaChar(int indice) {
                return (char)('A' + indice);
            }


            public static boolean validarPosicaoNavio(int[][] tabuleiro, int linha, int coluna, int tamanhoNavio, int orientacao) {


                if (orientacao == 0) { // Horizontal
                    if (coluna + tamanhoNavio > tamanhoTabuleiro) {
                        return false;
                    }


                    for (int i = Math.max(0, linha - 1); i <= Math.min(tamanhoTabuleiro - 1, linha + 1); i++) {
                        for (int j = Math.max(0, coluna - 1); j <= Math.min(tamanhoTabuleiro - 1, coluna + tamanhoNavio); j++) {
                            if (tabuleiro[i][j] == navio) {
                                return false;
                            }
                        }
                    }
                } else {
                    if (linha + tamanhoNavio > tamanhoTabuleiro) {
                        return false;
                    }


                    for (int i = Math.max(0, linha - 1); i <= Math.min(tamanhoTabuleiro - 1, linha + tamanhoNavio); i++) {
                        for (int j = Math.max(0, coluna - 1); j <= Math.min(tamanhoTabuleiro - 1, coluna + 1); j++) {
                            if (tabuleiro[i][j] == navio) {
                                return false;
                            }
                        }
                    }
                }


                return true;
            }


            public static void posicionarNavio(int[][] tabuleiro, int linha, int coluna, int tamanhoNavio, int orientacao) {
                if (orientacao == 0) { // Horizontal
                    for (int j = 0; j < tamanhoNavio; j++) {
                        tabuleiro[linha][coluna + j] = navio;
                    }
                } else { // Vertical
                    for (int i = 0; i < tamanhoNavio; i++) {
                        tabuleiro[linha + i][coluna] = navio;
                    }
                }
            }


            public static void exibirTabuleiroJogador(int[][] tabuleiro) {
                System.out.print("  ");
                for (int j = 0; j < tamanhoTabuleiro; j++) {
                    System.out.print(indiceParaColunaChar(j) + " ");
                }
                System.out.println();


                for (int i = 0; i < tamanhoTabuleiro; i++) {
                    System.out.print(i + " ");
                    for (int j = 0; j < tamanhoTabuleiro; j++) {
                        if (tabuleiro[i][j] == agua) {
                            System.out.print("~ ");
                        } else if (tabuleiro[i][j] == navio) {
                            System.out.print("N ");
                        } else if (tabuleiro[i][j] == tiroAgua) {
                            System.out.print("O ");
                        } else if (tabuleiro[i][j] == tiroNavio) {
                            System.out.print("X ");
                        } else {
                            System.out.print("? ");
                        }
                    }
                    System.out.println();
                }
            }


            public static void exibirTabuleiroOponente(int[][] tabuleiro) {
                System.out.print("  ");
                for (int j = 0; j < tamanhoTabuleiro; j++) {
                    System.out.print(indiceParaColunaChar(j) + " ");
                }
                System.out.println();


                for (int i = 0; i < tamanhoTabuleiro; i++) {
                    System.out.print(i + " ");
                    for (int j = 0; j < tamanhoTabuleiro; j++) {
                        if (tabuleiro[i][j] == agua || tabuleiro[i][j] == navio) {
                            System.out.print("~ ");
                        } else if (tabuleiro[i][j] == tiroAgua) {
                            System.out.print("O ");
                        } else if (tabuleiro[i][j] == tiroNavio) {
                            System.out.print("X ");
                        } else {
                            System.out.print("? ");
                        }
                    }
                    System.out.println();
                }
            }


            public static boolean realizarAtaque(int[][] tabuleiroDefensor, String nomeJogadorAtacante, int modoJogo, boolean jogadorEhPC) {
                int linha, coluna;
                boolean posicaoValida = false;


                while (!posicaoValida) {
                    if (jogadorEhPC && modoJogo == 1) {
                        linha = random.nextInt(tamanhoTabuleiro);
                        coluna = random.nextInt(tamanhoTabuleiro);
                        System.out.println("\nO computador atacou a posição: Linha " + linha + ", Coluna " + indiceParaColunaChar(coluna));
                    } else {
                        System.out.println("\n" + nomeJogadorAtacante + ", escolha uma posição para atacar:");
                        linha = lerCoordenada("Linha (0-9): ");
                        char colunaChar = lerCoordenadaColuna("Coluna (A-J): ");
                        coluna = colunaCharParaIndice(colunaChar);
                    }


                    if (tabuleiroDefensor[linha][coluna] == tiroAgua || tabuleiroDefensor[linha][coluna] == tiroNavio) {
                        if (!(jogadorEhPC && modoJogo == 1)) {
                            System.out.println("Esta posição já foi atacada! Escolha outra.");
                        }
                    } else {
                        posicaoValida = true;


                        if (tabuleiroDefensor[linha][coluna] == navio) {
                            tabuleiroDefensor[linha][coluna] = tiroNavio;
                            System.out.println("\nAcertou um navio! " + nomeJogadorAtacante + " joga novamente.");
                            return true;
                        } else {
                            tabuleiroDefensor[linha][coluna] = tiroAgua;
                            System.out.println("\nAcertou na água!");
                            return false;
                        }
                    }
                }


                return false;
            }


            public static boolean verificarFimDeJogo(int[][] tabuleiro) {
                for (int i = 0; i < tamanhoTabuleiro; i++) {
                    for (int j = 0; j < tamanhoTabuleiro; j++) {
                        if (tabuleiro[i][j] == navio) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
