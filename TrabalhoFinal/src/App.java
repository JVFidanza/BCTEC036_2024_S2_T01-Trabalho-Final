import java.io.Console;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Console console = System.console();
        Scanner terminalInput = new Scanner(System.in);

        int numeroDeTimes;
        Jogador jogador1 = new Jogador();
        Jogador jogador2 = new Jogador();
        
        jogador1.Nome = console.readLine("Insira o nome do Jogador 1: ");
        jogador2.Nome = console.readLine("Insira o nome do Jogador 2: ");
        
        System.out.println("Seja bem vindos " + jogador1.Nome + " e " + jogador2.Nome+ ", vocês têm 50 fichas para palpitar nos seus times escolhidos.");
        System.out.println("Escolha o número de times para compor o campeonato (4 ou 8)");
        numeroDeTimes = terminalInput.nextInt();
        Time[] times = new Time[numeroDeTimes];

        for(int i = 0; i < numeroDeTimes; i++){
            // System.out.println("Informe o nome do time " +( i + 1));
            Time time = new Time();
            time.Id = i;
            time.Nome = console.readLine("Informe o nome do time " + ( i + 1) + ": ");
            times[i] = time;
        }
        
        int numeroDePartidas = 0;
        for(int i = numeroDeTimes - 1;i >= 0; i--){
            numeroDePartidas += i;
        }

        Partida[] partidas = new Partida[numeroDePartidas];
        int partidaIndex = 0;

        //criação das partidas
        int indexGeralPartidas = 0;
        for(int indexExt = 0; indexExt < numeroDeTimes; indexExt++){
            for(int indexInt = indexExt + 1; indexInt < numeroDeTimes; indexInt++){
                Partida partida = new Partida();
                partida.Time1 = times[indexExt];
                partida.Time2 = times[indexInt];
                partida.Id = indexGeralPartidas;
                partidas[partidaIndex] = partida;
                partidaIndex++;
                indexGeralPartidas++;
            }
        }

        InserirPalpites(jogador1, partidas, console);
        InserirPalpites(jogador2, partidas, console);

        

    }
    public static void InserirPalpites(Jogador jogador, Partida[] partidas, Console console) throws Exception{
        System.out.println("Palpites para o jogador " + jogador.Nome + ":");

        for(Partida partida : partidas){
            System.out.println("Palpite para a partida: 1 - " + partida.Time1.Nome + " x 2 - " + partida.Time2.Nome);
            String timeVencedorString = console.readLine("Insira o número do time vencedor: ");
            int timeVencedor = Integer.valueOf(timeVencedorString);

            if(timeVencedor != 1 && timeVencedor != 2){
                throw new Exception("Número deve ser 1 ou 2");
            }
            Palpite palpite = new Palpite();
            palpite.TimeVencedor = Integer.valueOf(timeVencedor);
            boolean continueFichas = true;
            int fichas = 0;
            while(continueFichas){
                System.out.println("Ficas restantes: " + jogador.Fichas);
                String fichasString = console.readLine("Insira o número de fichas: ");
                fichas = Integer.valueOf(fichasString);
                if(fichas > jogador.Fichas){
                    System.out.println("Jogador não possui fichas suficientes!");
                }
                else
                    continueFichas = false;
            }
            palpite.Fichas = fichas;
            palpite.partidaId = partida.Id;
            jogador.Fichas = jogador.Fichas - fichas; 
            jogador.Palpites.add(palpite);
        }
    }
}
