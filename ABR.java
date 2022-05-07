package fr.main.sacADos.methode.util;

import fr.main.sacADos.Objet;
import fr.main.util.GestionAppli;

/**
 * La classe ABR permet la construction d'arbre binaire de recherche
 *
 * @author  Jules Doumèche, Gwénolé Martin
 * @version 1.0
 * @since   2020-10
 * @see GestionAppli
 */
public class ABR {
    /**
     * Le fils gauche
     */
    private ABR filsGauche;
    /**
     * Le fils droit
     */
    private ABR filsDroit;
    /**
     * Le parent
     */
    private final ABR parent;
    /**
     * La profondeur
     */
    private final int profondeur;
    /**
     * La valeur actuel
     */
    private final double valeur;
    /**
     * Le fils gauche
     */
    private final double poids;
    private int indexObjet;

    /**
     * Constructeur pour instancier la racine de l'arbre
     */
    public ABR(){
        this.parent = this; //le parent est lui même --> racine
        this.profondeur = 0;
        this.poids = this.valeur = 0.0;
    }

    /**
     * Constructeur pour instancier un nouveau noeud/branche à l'abre
     *
     * @param parent le noeud parent
     * @param valeur la valeur du noeud
     * @param poids le poid du noeud
     * @param index l'index de l'objet
     */
    public ABR(ABR parent, double valeur, double poids, int index){
        this.parent = parent;
        this.profondeur = parent.profondeur + 1;
        this.poids = poids;
        this.valeur = valeur;
        this.indexObjet = index;
    }

    /**
     * Permet de construire le fils gauche en ajoutant un objet supplémentaire
     *
     * @param o l'objet à ajouter
     * @param index l'index de l'objet
     */
    public void setFilsGauche(Objet o, int index) {
        this.filsGauche = new ABR(this, this.valeur + o.getValeur(), this.poids + o.getPoids(), index);
    }

    /**
     * Permet de construire le fils droit
     * Celui-ci n'est qu'une copie de son père, mais il n'a pas d'objet (indiqué par l'index)
     */
    public void setFilsDroit() {
        this.filsDroit = new ABR(this, this.valeur, this.poids, -1);
    }

    /**
     * Permet d'obtenir le fils gauche
     *
     * @return ABR filsGauche
     */
    public ABR getFilsGauche() {
        return this.filsGauche;
    }

    /**
     * Permet d'obtenir le fils droit
     *
     * @return ABR filsDroit
     */
    public ABR getFilsDroit() {
        return this.filsDroit;
    }

    /**
     * Permet d'obtenir la valeur du noeud
     *
     * @return double valeur
     */
    public double getValeur() {
        return this.valeur;
    }

    /**
     * Permet d'obtenir le poid du noeud
     *
     * @return double poid
     */
    public double getPoids() {
        return this.poids;
    }

    /**
     * Permet d'obtenir l'index de l'objet
     *
     * @return int index
     */
    public int getIndexObjet() {
        return this.indexObjet;
    }

    /**
     * Vérifie si le noeud est le noeud racine
     *
     * @return boolean
     */
    public boolean estRacine() {
        return this.profondeur == 0;
    }

    /**
     * Permet d'obtenir le parent
     *
     * @return ABR parent
     */
    public ABR getParent() {
        return this.parent;
    }
}
