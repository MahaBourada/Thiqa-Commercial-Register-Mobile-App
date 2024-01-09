package com.example.miniprojet.model;

public class Demandes {

    public Demandes() {
    }

    public Demandes(int id, String typeIdentite, int numIdentite, String nomEntreprise, String adresse, String activity,
                    int numFiscale, int ribBanque, String etat) {
        this.id = id;
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
    }

    public Demandes(String typeIdentite, int numIdentite, String nomEntreprise, String adresse, String activity, int numFiscale,
                    int ribBanque, String etat, String identitePath, String contratEndroitPath, String fiscalePath, String ribPath) {
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
        this.identitePath = identitePath;
        this.contratEndroitPath = contratEndroitPath;
        this.fiscalePath = fiscalePath;
        this.ribPath = ribPath;
    }

    public Demandes(int id, String typeIdentite, int numIdentite, String nomEntreprise, String adresse, String activity, int numFiscale, int ribBanque,
                    String etat, String identitePath, String contratEndroitPath, String fiscalePath, String ribPath) {
        this.id = id;
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
        this.identitePath = identitePath;
        this.contratEndroitPath = contratEndroitPath;
        this.fiscalePath = fiscalePath;
        this.ribPath = ribPath;
    }

    int id;
    String typeIdentite;
    int numIdentite;
    String nomEntreprise;
    String adresse;
    String activity;
    int numFiscale;
    int ribBanque;
    String etat;
    String identitePath;
    String contratEndroitPath;
    String fiscalePath;
    String ribPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeIdentite() {
        return typeIdentite;
    }

    public void setTypeIdentite(String typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    public int getNumIdentite() {
        return numIdentite;
    }

    public void setNumIdentite(int numIdentite) {
        this.numIdentite = numIdentite;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getNumFiscale() {
        return numFiscale;
    }

    public void setNumFiscale(int numFiscale) {
        this.numFiscale = numFiscale;
    }

    public int getRibBanque() {
        return ribBanque;
    }

    public void setRibBanque(int ribBanque) {
        this.ribBanque = ribBanque;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getIdentitePath() {
        return identitePath;
    }

    public void setIdentitePath(String identitePath) {
        this.identitePath = identitePath;
    }

    public String getContratEndroitPath() {
        return contratEndroitPath;
    }

    public void setContratEndroitPath(String contratEndroitPath) {
        this.contratEndroitPath = contratEndroitPath;
    }

    public String getFiscalePath() {
        return fiscalePath;
    }

    public void setFiscalePath(String fiscalePath) {
        this.fiscalePath = fiscalePath;
    }

    public String getRibPath() {
        return ribPath;
    }

    public void setRibPath(String ribPath) {
        this.ribPath = ribPath;
    }
}
