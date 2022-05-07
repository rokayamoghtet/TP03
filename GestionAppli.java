ackage fr.main.util;

import fr.main.Appli;
import fr.main.sacADos.SacADos;
import fr.main.sacADos.methode.Methodes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * La classe GestionAppli permet de gérer les I/O de l'utilisateur, les paramètres et les arguments ainsi que le menu
 *
 * @author  Jules Doumèche, Gwénolé Martin
 * @version 1.0
 * @since   2020-10
 */
public class GestionAppli extends Appli {

    /**
     * Constructeur pour instancier l'application
     */
    public GestionAppli() {}

    /**
     * Chemin vers le dossier contenant les sources
     */
    public static final String DATA_PATH = "data/";

    /**
     * Permet d'afficher l'état du sac
     *
     * @param sac le sac à résoudre
     */
    public void afficher(SacADos sac) {
        affSucces(sac.toString());
    }

    /**
     * Permet d'afficher l'état du sac
     *
     * @param sac le sac à résoudre
     * @param temps durée d'exécution
     */
    public void afficher(SacADos sac, long temps) {
        affSucces(sac.toString() + "\n Durée d'exécution: " + String.valueOf(temps) + " ms");
    }

    /**
     * Permet de lancer le programme et de gérer les erreurs potentielles
     *
     * @param args les arguments fournis par l'utilisateur
     */
    public void start(String[] args){
        try {
            validerArgs(args);
            affSucces("Arguments:" + "\nChemin: " + CHEMIN +
                    "\nPoids max: " + POIDS_MAX + "\nMéthode: " + METHODE);
        }
        catch(IllegalArgumentException e) {
            affErreur(e);
            chargerMenu();
        }
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     * Permet d'afficher le message d'erreur d'une exception
     *
     * @param e Exception à gérer
     */
    public void affErreur(Exception e){
        affErreur(e.getMessage());
    }

    /**
     * Permet d'afficher un message d'erreur
     *
     * @param msg le message à afficher
     */
    public static void affErreur(String msg){
        System.out.println(ANSI_RED + "Erreur: " + msg + ANSI_RESET + "\n");
    }

    /**
     * Affiche un message de succès
     *
     * @param msg le message à afficher
     */
    private void affSucces(String msg){
        System.out.println(ANSI_GREEN + msg + ANSI_RESET + "\n");
    }

    /**
     * Valide les arguments,
     * Si aucun argument n'est fourni le menu est chargé
     *
     * @param args les arguments
     */
    private static void validerArgs(String[] args) throws IllegalArgumentException {
        if (args.length < 3) {
            String msg = "Arguments Manquants : ";
            switch(args.length){
                case 0:
                    msg += "chemin ";
                case 1:
                    msg += "poids-maximal ";
                case 2:
                    msg += "methode\nSyntaxe: ./resoudre-sac-a-dos chemin poids-maximal methode;";
                    break;
                default:
                    break;
            }
            throw new IllegalArgumentException(msg);
        }
        else {
            for (int i = 0; i < args.length; i++) {
                switch (i) {
                    case 0:
                        File cheminValide = new File(DATA_PATH + args[i]);
                        if (!cheminValide.exists()) {
                            cheminValide = new File(args[i]);
                            if (!cheminValide.exists()) {
                                throw new IllegalArgumentException("Chemin invalide, inexistant ou inaccessible: ("
                                        + DATA_PATH + ")" + cheminValide.getPath());
                            }
                            CHEMIN = args[i];
                        }
                        CHEMIN = DATA_PATH + args[i];
                        break;
                    case 1:
                        try {
                            POIDS_MAX = Double.parseDouble(args[i]);
                        } catch (NumberFormatException PoidsNonNumerique) {
                            throw new IllegalArgumentException("Valeur du paramètre du poids maximal invalide: " + args[i]);
                        }
                        break;
                    case 2:
                        switch (args[i].toLowerCase()) {
                            case "g", "glouton", "gloutonne" -> METHODE = Methodes.GLOUTONNE;
                            case "d", "dyn", "dynamique" -> METHODE = Methodes.DYNAMIQUE;
                            case "p", "ps", "pse" -> METHODE = Methodes.PSE;
                            default -> throw new IllegalArgumentException("Méthode incconue ou non implémentée: " + args[i]);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * charge le menu d'initialisation
     */
    private void chargerMenu() {
        affSucces("MENU PRINCIPAL");
        Scanner scanner = new Scanner(System.in);

        //DATA SOURCE
        System.out.println("[0] - Quitter\n");
        System.out.println("Choisir la source des données:");

        File directory = new File(DATA_PATH);
        if (! directory.exists())
            directory.mkdir();

        List<String> sources = null;
        try (Stream<Path> walk = Files.walk(Paths.get(DATA_PATH))) {

            sources = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".txt")).collect(Collectors.toList());

            for(int i = 0; i < sources.size(); ++i) {
                System.out.println("[" + (i+1) + "] - " + sources.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sourceId;
        do {
            System.out.println(ANSI_BLUE);
            sourceId = scanner.nextInt();
            System.out.println(ANSI_RESET);
        } while( !sourceValide(sourceId, Objects.requireNonNull(sources)) );
        CHEMIN = sources.get(sourceId - 1);

        //POIDS MAX
        double poidMax;
        do {
            System.out.println("Poids maximal du sac:");
            System.out.println(ANSI_BLUE);
            poidMax = scanner.nextInt();
            System.out.println(ANSI_RESET);
        } while( !poidValide(poidMax) );
        POIDS_MAX = poidMax;

        //CHOIX ALGO
        int algoId;
        System.out.println("[1] - Glouton");
        System.out.println("[2] - Dynamique");
        System.out.println("[3] - PSE");
        do {
            System.out.println(ANSI_BLUE);
            algoId = scanner.nextInt();
            System.out.println(ANSI_RESET);
        } while( !algoValide(algoId) );

        METHODE = (algoId == 1 ? Methodes.GLOUTONNE : (algoId == 2 ? Methodes.DYNAMIQUE : Methodes.PSE));
    }

    /**
     * validation des entrées
     */
    private boolean sourceValide(int sourceId, List<String> sources) {
        if(sourceId == 0) {
            System.exit(1);
        }
        if(sourceId > sources.size() || sourceId  < 1){
            affErreur("Vérifiez votre saisie");
            return false;
        }  else return true;
    }
    /**
     * validation des entrées
     */
    private boolean poidValide(double poidMax) {
        if(poidMax < 0 ){
            affErreur("Le poids maximal est forcément négatif");
            return false;
        }  else return true;
    }
    /**
     * validation des entrées
     */
    private boolean algoValide(int algoId) {
        if(algoId < 1 | algoId > 3){
            affErreur("Vérifiez votre saisie");
            return false;
        }  else return true;
    }
}
