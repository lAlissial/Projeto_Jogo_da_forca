package projeto_jodo_da_forca;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Projeto 1 de POO
 * Grupo de alunos: ºººººººººººººººººººººººººººººººººººººººº
 *
 */

public class JogoDaForca {
    private int N;                                 // quantidade de palavras do arquivo (lido do arquivo)*
    private String[] palavras;                    // um array com as N palavras (lidas do arquivo)
    private String[] dicas;                       // um array com as N dicas (lidas do arquivo)
    private String palavra;                      // a palavra sorteada
    private int indice = 0;                      // posição (0 a N-1) da palavra sorteada no array indice da palavra sorteadado jogo
    private int acertos = 0;                     // total de acertos do jogo
    private int erros = 0;                       // total de erros do jogo
    private String[] penalidades = {"perna", "perna", "braço", "braço", "tronco", "cabeça"};
    private StringBuffer tracoPalavra;          //guarda as letrinhas descobertas na posição certinha
    private String auxPalavra;                  //guarda as letras que AINDA NÂO foram descobertas da plavra sorteada
    private StringBuffer guardaletraserradas;   //Guardasletraserradas


    //construtor que lê o arquivo com as n palavras e dicas e as coloca nos respectivos arrays.
    public JogoDaForca(String nomearquivo) throws Exception{

        Scanner arquivo = null;
        String stringDoArq = "";
        String[] arrayComDicasJuntoDasPalavras;
        String[] juntinhos;

        try {
            arquivo = new Scanner(new File(nomearquivo));
        } catch (FileNotFoundException e) {
            throw new Exception("Arquivo inexistente/não encontrado");
        }
        this.N = Integer.parseInt(arquivo.nextLine());

        while (arquivo.hasNextLine()) {
            stringDoArq += arquivo.nextLine() + "-";
        }

        arrayComDicasJuntoDasPalavras = stringDoArq.split("-");
        //N = arrayComDicasJuntoDasPalavras.length;
        this.palavras = new String[N];
        this.dicas = new String[N];

        //                         N
        for (int i = 0; i < N; i++) {
            juntinhos = arrayComDicasJuntoDasPalavras[i].split(";");
            this.palavras[i] = juntinhos[0];
            this.dicas[i] = juntinhos[1];
        }

        this.guardaletraserradas = new StringBuffer("");

        arquivo.close();            //fechar arquivo


    }


    // inicia o jogo com o sorteio de uma das n palavras existentes.
    public void iniciar() {
        Random sortearPalavrita = new Random();
        this.indice = sortearPalavrita.nextInt(N);
        this.palavra = this.palavras[this.indice].toUpperCase();

        this.tracoPalavra = new StringBuffer("");

        for (int e = 0; e < this.palavra.length(); e++) {
            //tracoPalavra.append("_");
            this.tracoPalavra.append("*");
        }

        this.auxPalavra = new String(this.palavra);
    }


    // retorna true, caso a letra exista dentro da palavra sorteada e
    //retorna false, caso contrário. Além disso, o método marca as posições encontradas e contabiliza X
    //acertos para as X ocorrências da letra encontrada dentro da palavra ou contabiliza 1 erro para a
    //inexistência da letra na palavra

    public boolean adivinhou(String letra)  throws Exception{
        // tratando o que vai receber
        Pattern padraozito = Pattern.compile("[a-zA-Z]");
        Matcher testando = padraozito.matcher(letra);
        if(testando.matches()) {
            letra = letra.toUpperCase();

            if (this.tracoPalavra.toString().contains(letra)){
                throw new Exception("Digite uma letra ainda não informada");
            } else {
                if (this.palavra.contains(letra)) {
                    for (int k = 0; k < this.auxPalavra.length(); k++) {
                        if (letra.equals(this.auxPalavra.substring(k, k + 1))) {
                            this.acertos += 1;
                            this.tracoPalavra = this.tracoPalavra.replace(k, k + 1, letra);
                        }
                    }
                    this.auxPalavra = this.auxPalavra.replace(letra, "~");
                } else {
                    if (this.guardaletraserradas.toString().contains(letra)) {
                        throw new Exception("Letra já foi escrita anteriormente");
                    } else {
                        this.erros += 1;
                        this.guardaletraserradas.append(letra + "-");
                    }
                }
                return this.palavra.contains(letra);
            }
        } else {
                throw new Exception("Digite UMA LETRA!");
        }
    }


    //retorna true, se o total de acertos atingir o total de letras da palavra sorteada ou
    //se o total de erros atingir seis.
    public boolean terminou() {
        return (this.acertos == this.palavra.length() || this.erros == 6);
    }



    //retorna a palavra sorteada com as letras adivinhadas reveladas e com as letras
    //não adivinhadas escondidas com “*”.
    public String getPalavra() {
        //String palavrita = tracoPalavra.toString().replace("_", "*");
        String palavrita = this.tracoPalavra.toString();
        return palavrita;
    }





    //retorna a dica da palavra sorteada.
    public String getDica() {
        return this.dicas[indice];
    }




    //retorna o nome da penalidade de acordo com o total de erros.
    public String getPenalidade() {
        return this.penalidades[this.erros - 1];





    // retorna o total de acertos
    public int getAcertos() {
        return this.acertos;
    }




    //retorna o total de erros
    public int getErros() {
        return this.erros;
    }




    //retorna “ganhou o jogo” ou “você foi enforcado”
    public String getResultado() {
        String guardaresultado = "";
        if (this.acertos==this.palavra.length()){
            guardaresultado = "GANHOU O JOGO!";
        }
        if(this.erros==6){
            guardaresultado = "VOCÊ FOI ENFORCADO";
        }
        return guardaresultado;
    }




    // retorna as letras erradas já ditas
    public String getLetrasErradas(){
        return this.guardaletraserradas.toString();
    }
}
