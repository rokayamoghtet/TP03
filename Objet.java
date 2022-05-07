package fr.main.sacADos;

import java.util.Comparator;

/**
 * La classe Objet représente un objet
 * Celui-ci est composé d'un libellé, d'un poids et d'une valeur
 *
 * @author  Jules Doumèche, Gwénolé Martin
 * @version 1.0
 * @since   2020-10
 */
public class Objet {
    /**
     * Taille maximale du libellé
     */
    private static final int MAX_LIB_SIZE = 30;
    /**
     * Le libellé
     */
    private final String libelle;
    /**
     * Poids, valeur et rapport poids/valeur
     */
    private final double poids, valeur, rapport;

    /**
     * Constructeur pour instancier un Objet
     *
     * @param libelle le libellé
     * @param poids le poids
     * @param valeur le poids
     */
    public Objet(String libelle, double poids, double valeur){
        //Si le libellé dépasse la taille maximum, on ne prend que les premiers caractères
        if (libelle.length() <= 30)
            this.libelle = libelle;
        else
            this.libelle = libelle.substring(0,MAX_LIB_SIZE);
        this.valeur = valeur;
        this.poids = poids;
        this.rapport = valeur/ poids;
    }

    /**
     * Renvoie le libellé de l'objet
     *
     * @return String libelle
     */
    public String getLibelle(){
        return this.libelle;
    }

    /**
     * Renvoie le poids de l'objet
     *
     * @return double poids
     */
    public double getPoids(){
        return this.poids;
    }

    /**
     * Renvoie la valeur de l'objet
     *
     * @return double valeur
     */
    public double getValeur(){
        return this.valeur;
    }

    /**
     * Renvoie le rapport valeur/poids de l'objet
     *
     * @return double rapport
     */
    public double getRapport(){
        return this.rapport;
    }

    /**
     * Représentation en chaîne du caractère de l'objet
     * "Libellé [poids= ?, prix= ?]"
     *
     * @return String objet
     */
    @Override
    public String toString() {
        return libelle + " [" +
                "poids= " + poids +
                ", prix= " + valeur +
                ']';
    }

    /**
     * Permet d'obtenir le comparator d'objet qui permet de trier par ordre décroissant en fonction du rapport d'une liste d'objets
     *
     * @return Comparator comparator
     */
    public static Comparator<Objet> parRapport() {
        return (o1, o2) -> Double.compare(o2.getRapport(), o1.getRapport());
    }
}
