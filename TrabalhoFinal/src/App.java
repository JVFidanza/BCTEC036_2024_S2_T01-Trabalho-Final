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
        for(int indexExt = 0; indexExt < numeroDeTimes; indexExt++){
            for(int indexInt = indexExt + 1; indexInt < numeroDeTimes; indexInt++){
                Partida partida = new Partida();
                partida.Time1 = times[indexExt];
                partida.Time2 = times[indexInt];
                partidas[partidaIndex] = partida;
                partidaIndex++;
            }
        }

        System.out.println("Palpites para o jogador " + jogador1.Nome + ":");

        for(Partida partida : partidas){
            console.readLine("Palpite para a partida: 1 - " + partida.Time1.Nome + " x 2 - " + partida.Time2.Nome);
        }
    }
}
