package projeto_jodo_da_forca;

/*
 * IFPB-POO-PROJETO1
 * Aplicação console para testar a classe JogoDaForca
 *
 */
import java.util.Locale;
import java.util.Scanner;

public class JogoDaForcaConsole {
    /* ---------------------------- FIRULAS ---------------------------- */
    public static final String ANSI_LIGHT_BLUE_BACKGROUND = "\u001B[104m";
    /* ----------------------------------------------------------------- */


    public static void main(String[] args) {

        /*System.out.println(ANSI_GREEN_BACKGROUND + "This text has a green background but default text!" + ANSI_RESET);
        System.out.println(ANSI_RED + "This text has red text but a default background!" + ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND + ANSI_RED + "This text has a green background and red text!" + ANSI_RESET);
        */

        Scanner teclado = new Scanner (System.in);
        JogoDaForca jogo = null;
        try {
            jogo = new JogoDaForca("palavras.txt");

        } catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        jogo.iniciar();
        String letra;

        System.out.println("==================================");
        System.out.println(ANSI_LIGHT_BLUE_BACKGROUND+"           JOGO DA FORCA          "+ANSI_RESET);
        System.out.println("==================================");

        do {
            System.out.println("\nPalavra = " + jogo.getPalavra());
            System.out.println("Dica = " + jogo.getDica());

            System.out.print("Digite uma letra da palavra: ");
            letra = teclado.nextLine();
            try {
                System.out.println();
                if (jogo.adivinhou(letra)) {
                    System.out.println("Voce acertou a letra " + letra.toUpperCase());
                    System.out.println("Total de acertos = " + jogo.getAcertos());
                } else {
                    System.out.println("Voce errou a letra " + letra);
                    System.out.println("Total de erros = " + jogo.getErros());
                    System.out.println("Erros já digitados = " + jogo.getLetrasErradas());
                    System.out.println("Penalidade: " + jogo.getPenalidade());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while(!jogo.terminou());

        System.out.println("\nPalavra = " + jogo.getPalavra());

        teclado.close();
        System.out.println("----------------------------------");
        System.out.println("Resultado final = "+jogo.getResultado() );
        System.out.println("==================================");

    }
}
