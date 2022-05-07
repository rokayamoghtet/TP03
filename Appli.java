package fr.main;

import java.io.IOException;

import fr.main.sacADos.methode.*;
import fr.main.sacADos.*;
import fr.main.util.*;

public class Appli {
    /**
     * Précision des décimales, utilisée par l'algorithme PSE
     */
    public static final int PRECISION = 0;
    /**
     * Le Poids Maximal du sac
     */
    public static Double POIDS_MAX;
    /**
     * Le chemin vers le fichier texte contenant la liste d'objets
     */
    public static String CHEMIN;
    /**
     * La méthode de résolution
     * @see Methodes
     */
    public static Methodes METHODE;

    /**
     * Entrée du programme
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        GestionAppli app = new GestionAppli();

        //Validation des Arguments
        app.start(args);

        //Création d'un sac à dos
        SacADos sac = null;
        try {
            sac = new SacADos(CHEMIN, POIDS_MAX);
        } catch (IOException e) {
            app.affErreur(e);
            System.exit(1);
        }

        //Résolution
        long temps = sac.resoudre(METHODE);

        //Résultat
        app.afficher(sac, temps);
    }
}
