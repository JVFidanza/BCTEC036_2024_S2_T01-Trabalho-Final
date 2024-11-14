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

        for(Partida partida : partidas){
            int golsTime1 = (int)(Math.random() * 11);
            int golsTime2 = (int)(Math.random() * 11);

            if(golsTime1 > golsTime2){
                partida.NumTimeVencedor = 1;
                partida.Time1.Pontos += 3;
                partida.GolsTimeVencedor = golsTime1;
                partida.GolsTimePerdedor = golsTime2;
            }
            else if(golsTime1 < golsTime2){
                partida.NumTimeVencedor = 2;
                partida.Time2.Pontos += 3;
                partida.GolsTimeVencedor = golsTime2;
                partida.GolsTimePerdedor = golsTime1;
            }
            else{
                partida.NumTimeVencedor = 0;
                partida.Time1.Pontos += 1;
                partida.Time2.Pontos += 1;
                partida.GolsTimeVencedor = golsTime2;
                partida.GolsTimePerdedor = golsTime1;
            }

            Palpite palpiteJog1 = GetPalpite(jogador1, partida);
            Palpite palpiteJog2 = GetPalpite(jogador2, partida);

            ProcessPalpite(palpiteJog1, partida, jogador1);
            ProcessPalpite(palpiteJog2, partida, jogador2); 
        }

        PrintLineSeparator();
        System.out.println("Número de partidas: " + numeroDePartidas);
        System.out.println("Pontos Jogador " + jogador1.Nome + ": " + jogador1.Pontos);
        System.out.println("Pontos Jogador " + jogador2.Nome + ": " + jogador2.Pontos);
        PrintLineSeparator();
        System.out.println("Pontuação dos times:");
        for(Time time : times){
            System.out.println("Time: " + time.Nome);
            System.out.println("Pontos: " + time.Pontos);
            System.out.println("*********");
        }
        PrintLineSeparator();
        System.out.println("Palpites do jogador " + jogador1.Nome + ":");
        for(Palpite palpite : jogador1.Palpites){
            System.out.println("Time: " + palpite.NomeTimeVencedor);
            System.out.println("Fichas: " + palpite.Fichas);
            System.out.println("*********");
        }

        PrintLineSeparator();
        System.out.println("Palpites do jogador " + jogador2.Nome + ":");
        for(Palpite palpite : jogador2.Palpites){
            System.out.println("Time: " + palpite.NomeTimeVencedor);
            System.out.println("Fichas: " + palpite.Fichas);
            System.out.println("*********");
        }

        PrintLineSeparator();
        System.out.println("Partidas:");
        for(Partida partida : partidas){
            if(partida.NumTimeVencedor == 1){
                System.out.println(partida.Time1.Nome + " - " + partida.GolsTimeVencedor + " X " + partida.Time2.Nome + " - " + partida.GolsTimePerdedor);
            }
            else if(partida.NumTimeVencedor == 2) {
                System.out.println(partida.Time2.Nome + " - " + partida.GolsTimeVencedor + " X " + partida.Time1.Nome + " - " + partida.GolsTimePerdedor);
            }
            else{
                System.out.println(partida.Time2.Nome + " - " + partida.GolsTimeVencedor + " X " + partida.Time1.Nome + " - " + partida.GolsTimePerdedor);
            }
        }

        terminalInput.close();
    }

    public static void InserirPalpites(Jogador jogador, Partida[] partidas, Console console) throws Exception{
        PrintLineSeparator();
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
            if(timeVencedor == 1)
                palpite.NomeTimeVencedor = partida.Time1.Nome;
            else
            palpite.NomeTimeVencedor = partida.Time2.Nome;
            boolean continueFichas = true;
            int fichas = 0;
            while(continueFichas){
                System.out.println("Fichas restantes: " + jogador.Fichas);
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

    public static Palpite GetPalpite(Jogador jogador, Partida partida){
        Palpite palpiteGet = jogador.Palpites.stream()
                .filter(palpite -> palpite.partidaId == partida.Id)
                .findFirst()
                .get();
        
        return palpiteGet;
    }

    public static void ProcessPalpite(Palpite palpite, Partida partida, Jogador jogador){
        if(partida.NumTimeVencedor == 0)
            return;
        
        if(palpite.TimeVencedor == partida.NumTimeVencedor){
            jogador.Pontos += partida.GolsTimeVencedor * palpite.Fichas;
        }
    }

    public static void PrintLineSeparator(){
        System.out.println("--------------------------------");
    }
}
