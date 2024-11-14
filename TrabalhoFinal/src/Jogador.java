import java.util.ArrayList;

public class Jogador {
    public int Id;
    public String Nome;
    public int Fichas;
    public ArrayList<Palpite> Palpites;

    Jogador(){
        Fichas = 50;
        Palpites = new ArrayList<Palpite>();
    }
}
