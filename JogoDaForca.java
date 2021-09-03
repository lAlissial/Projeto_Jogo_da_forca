package projeto_jodo_da_forca;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStream;

/**
 * Projeto 1 de POO
 * Grupo de alunos: Adriana Albuquerque De Moura e Alíssia Deolinda Oliveira de Lima
 */

public class JogoDaForca {

    private int N;                                      // quantidade de palavras do arquivo (lido do arquivo)*
    private String[] palavras;                          // um array com as N palavras (lidas do arquivo)
    private String[] dicas;                             // um array com as N dicas (lidas do arquivo)
    private String palavra;                             // a palavra sorteada
    private int indice = 0;                             // posição (0 a N-1) da palavra sorteada no array indice da palavra sorteadado jogo
    private int acertos = 0;                            // total de acertos do jogo
    private int erros = 0;                              // total de erros do jogo
    private String[] penalidades = {"perna", "perna", "braço", "braço", "tronco", "cabeça"};
    private StringBuffer tracoPalavra;                  //guarda as letrinhas descobertas na posição certinha
    private String auxPalavra;                          //guarda as letras que AINDA NÂO foram descobertas da plavra sorteada
    private StringBuffer guardaletraserradas;           //guardas letras erradas



    public JogoDaForca(String nomearquivo) throws Exception{

        Scanner arquivo = null;
        String stringDoArq = "";
        String[] arrayComDicasJuntoDasPalavras;
        String[] juntinhos;

        try {
            //arquivo = new Scanner(new File(nomearquivo));
            InputStream fonte = this.getClass().getResourceAsStream("/fonte/palavras.txt");
            arquivo = new Scanner(fonte);
        } catch (Exception e){ //catch (FileNotFoundException e) {
            throw new Exception("Arquivo inexistente/não encontrado");
        }

        this.N = Integer.parseInt(arquivo.nextLine());

        while (arquivo.hasNextLine()) {
            stringDoArq += arquivo.nextLine() + "-";
        }

        this.N = Integer.parseInt(arquivo.nextLine());

        while (arquivo.hasNextLine()) {
            stringDoArq += arquivo.nextLine() + "-";
        }

        arrayComDicasJuntoDasPalavras = stringDoArq.split("-");
        this.palavras = new String[N];
        this.dicas = new String[N];

        for (int i = 0; i < N; i++) {
            juntinhos = arrayComDicasJuntoDasPalavras[i].split(";");
            this.palavras[i] = juntinhos[0];
            this.dicas[i] = juntinhos[1];
        }

        this.guardaletraserradas = new StringBuffer("");

        arquivo.close();
    }



    public void iniciar() {

        Random sortearPalavrita = new Random();
        this.indice = sortearPalavrita.nextInt(N);
        this.palavra = this.palavras[this.indice].toUpperCase();

        this.tracoPalavra = new StringBuffer("");

        for (int e = 0; e < this.palavra.length(); e++) {
            this.tracoPalavra.append("*");
        }

        this.auxPalavra = new String(this.palavra);
    }

    public boolean adivinhou(String letra)  throws Exception{

        Pattern padraozito = Pattern.compile("[a-zA-Z]");
        Matcher testando = padraozito.matcher(letra);

        letra = letra.toUpperCase();

        if(!testando.matches()){
            throw new Exception("Digite UMA LETRA!");
        }
        if (this.tracoPalavra.toString().contains(letra)) {
            throw new Exception("Digite uma letra ainda não informada");
        }
        if (this.terminou()) {
            throw new Exception("Jogo já terminou inicie novamente!");
        }

        if (this.guardaletraserradas.toString().contains(letra)) {
            throw new Exception("Letra já foi escrita anteriormente");
        }

        if (this.palavra.contains(letra)) {
            for (int k = 0; k < this.auxPalavra.length(); k++) {
                if (letra.equals(this.auxPalavra.substring(k, k + 1))) {
                    this.acertos ++;
                    this.tracoPalavra = this.tracoPalavra.replace(k, k + 1, letra);
                }
            }
            this.auxPalavra = this.auxPalavra.replace(letra, "~");

        } else {
            this.erros ++;
            this.guardaletraserradas.append(letra + "-");
        }
        return this.palavra.contains(letra);
    }


    /*public boolean adivinhou(String letra)  throws Exception{

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
                            if (!this.terminou()) {
                                this.acertos ++;
                                this.tracoPalavra = this.tracoPalavra.replace(k, k + 1, letra);
                            }else{
                                throw new Exception("Jogo já terminou inicie novamente!");
                            }
                        }
                    }
                    this.auxPalavra = this.auxPalavra.replace(letra, "~");

                } else {
                    if (this.guardaletraserradas.toString().contains(letra)) {
                        throw new Exception("Letra já foi escrita anteriormente");
                    } else {
                        if (!this.terminou()) {
                            this.erros ++;
                            this.guardaletraserradas.append(letra + "-");
                        } else{
                            throw new Exception("Jogo já terminou inicie novamente!");
                        }
                    }
                }
                return this.palavra.contains(letra);
            }
        } else {
            throw new Exception("Digite UMA LETRA!");
        }
    }*/



    public boolean terminou() {
        return (this.acertos == this.palavra.length() || this.erros == 6);
    }



    public String getPalavra() { return this.tracoPalavra.toString();}



    public String getDica() {
        return this.dicas[indice];
    }



    public String getPenalidade() {
        return this.penalidades[this.erros - 1];
    }



    public int getAcertos() {
        return this.acertos;
    }



    public int getErros() {
        return this.erros;
    }



    public String getResultado() {

        if (this.acertos==this.palavra.length()){
            return "GANHOU O JOGO!";
        } else {
            return "VOCÊ FOI ENFORCADO";
        }

    }



    public String getLetrasErradas(){
        return this.guardaletraserradas.toString();
    }

}
