package projeto_jodo_da_forca;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;



/**
 * Projeto 1 de POO
 * Grupo de alunos: ºººººººººººººººººººººººººººººººººººººººº
 *
 */

public class JogoDaForca {
    private int N;                              // quantidade de palavras do arquivo (lido do arquivo)*
    private String[] palavras;                  // um array com as N palavras (lidas do arquivo)
    private String[] dicas;                     // um array com as N dicas (lidas do arquivo)
    private String palavra;                     // a palavra sorteada
    private int indice = 0;                   // posição (0 a N-1) da palavra sorteada no array indice da palavra sorteadado jogo
    private int acertos = 0;                   // total de acertos do jogo
    private int erros = 0;                    // total de erros do jogo
    private String[] penalidades = {"perna", "perna", "braço", "braço", "tronco", "cabeça"};
    //private String[] arquivos = {"pes.jpg","pernas.jpg","maos.jpg","bracos.jpg","tronco.jpg","cabeca.jpg"};
    //private String[] arquivos = {"0.png","1.png","2.png","3.png","4.png","5.png","6.png"};
    private StringBuffer tracoPalavra;      //guarda as letrinhas descobertas na posição certinha
    private String auxPalavra;              //guarda as letras que AINDA NÂO foram descobertas da plavra sorteada
    private StringBuffer guardaletraserradas;   //Guarda letras erradas


    //construtor que lê o arquivo com as n palavras e dicas e as coloca nos respectivos arrays.
    public JogoDaForca(String nomearquivo) {

        Scanner arquivo = null;
        String stringDoArq = "";
        String[] arrayComDicasJuntoDasPalavras;
        String[] juntinhos;
        
        //abrindo arquivo
        try {
            arquivo = new Scanner(new File(nomearquivo));
            
            //juntando em uma string todo o conteudo do arquivo com "-" separando cd linha
            while (arquivo.hasNextLine()) {
                stringDoArq += arquivo.nextLine() + "-";
            }
            // guardando no array a palavras e a dicas como uma coisa só, cd palavra com sua dica será um elemento desse array
            arrayComDicasJuntoDasPalavras = stringDoArq.split("-");
        
            // declarando o tamanho dos arrays
            palavras = new String[arrayComDicasJuntoDasPalavras.length];
            dicas = new String[arrayComDicasJuntoDasPalavras.length];

            // o array juntinho a cada iteração receberá um elemento do arrayComDicasJuntoDasPalavras mas esse será quebrado a partir do ";", que acaba por separar a dica da palavra
            //lembra que no arquivo cada linha tá assim:
            //PALAVRA;diquinha tal
            // aí a palavra ficará com indice 0 e a dica com indice 1
            for (int i = 0; i < arrayComDicasJuntoDasPalavras.length; i++) {
                juntinhos = arrayComDicasJuntoDasPalavras[i].split(";");
                palavras[i] = juntinhos[0];
                dicas[i] = juntinhos[1];
            }
        
            N = palavras.length;

            guardaletraserradas = new StringBuffer("");

            
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo inexistente/não encontrado");
        }
        

    }


    // inicia o jogo com o sorteio de uma das n palavras existentes.
    public void iniciar() {
        Random sortearPalavrita = new Random();
        indice = sortearPalavrita.nextInt(N);
        palavra = palavras[indice].toUpperCase();

        tracoPalavra = new StringBuffer("");

        for (int e = 0; e < palavra.length(); e++) {
            tracoPalavra.append("_");
        }

        auxPalavra = new String(palavra);
    }


    // retorna true, caso a letra exista dentro da palavra sorteada e
    //retorna false, caso contrário. Além disso, o método marca as posições encontradas e contabiliza X
    //acertos para as X ocorrências da letra encontrada dentro da palavra ou contabiliza 1 erro para a
    //inexistência da letra na palavra
    public boolean adivinhou(String letra)  throws Exception{
        // tratando o que vai receber
        // eu vou tentar parsear o que vai ser recebido, se for um número(q não é o certo[p um jogo da forca]) ele seguirá o try normalmente, por isso logo abaixo ele lançará 
        // uma exceção.
        // E se ele não conseguir parsear que é o esperado, já que queremos letras, entrará no catch
        
        try {
            int testandoLetra = Integer.parseInt(letra);
            throw new Exception("Informe uma LETRA, não um número!");
        } catch (NumberFormatException e){
            if (letra.length()>1){
                throw new Exception("Informe UMA LETRA válida!");
            } else {
                letra = letra.toUpperCase();

                if (tracoPalavra.toString().contains(letra)){
                    throw new Exception("Digite uma letra ainda não informada");
                } else {
                    if (palavra.contains(letra)) {
                        for (int k = 0; k < auxPalavra.length(); k++) {
                            if (letra.equals(auxPalavra.substring(k, k + 1))) {
                                acertos += 1;
                                tracoPalavra = tracoPalavra.replace(k, k + 1, letra);
                            }
                        }
                        auxPalavra = auxPalavra.replace(letra, "~");
                    } else {
                        if (guardaletraserradas.toString().contains(letra)){
                            throw new Exception("Digite uma letra ainda não informada");
                        }else {
                            erros += 1;
                            guardaletraserradas.append(letra + " - ");
                        }
                    }

                    return palavra.contains(letra);
                }
            }
        }
    // -----------------------------------------
    }





    //retorna true, se o total de acertos atingir o total de letras da palavra sorteada ou
    //se o total de erros atingir seis.
    public boolean terminou() {
        return (acertos == palavra.length() || erros == 6);
    }




    //retorna a palavra sorteada com as letras adivinhadas reveladas e com as letras
    //não adivinhadas escondidas com “*”.
    public String getPalavra() {
        String palavrita = tracoPalavra.toString().replace("_", "*");
        return palavrita;
    }





    //retorna a dica da palavra sorteada.
    public String getDica() {
        return dicas[indice];
    }





    //retorna o nome da penalidade de acordo com o total de erros.
    public String getPenalidade() {
        String guardando = "";
        if (erros == 1 || erros == 2){
            guardando = penalidades[0];
        } else{
            if (erros == 3 || erros == 4){
                guardando = penalidades[2];
            }
            if (erros == 5){
                guardando = penalidades[4];
            }
            if (erros == 6){
                guardando = penalidades[5];
            }
        }
        return guardando;
    }




    //retorna a imagem da penalidade lida de arquivo de acordo
    //com o total de erros (usada em aplicação gráfica).
    /*public BufferedReader getImgPenalidade() {

    }*/




    // retorna o total de acertos
    public int getAcertos() {
        return acertos;
    }




    //retorna o total de erros
    public int getErros() {
        return erros;
    }




    //retorna “ganhou o jogo” ou “você foi enforcado”
    public String getResultado() {
        String guardaresultado = "";
        if (acertos==palavra.length()){
            guardaresultado = "GANHOU O JOGO!";
        }
        if(erros==6){
            guardaresultado = "VOCÊ FOI ENFORCADO";
        }
        return guardaresultado;
    }




    // retorna as letras erradas já ditas
    public String getLetrasErradas(){
        return guardaletraserradas.toString();
    }
}
