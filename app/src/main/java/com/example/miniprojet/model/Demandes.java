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
                    int ribBanque, String etat, String identiteUri, String contratEndroitUri, String fiscaleUri, String ribUri) {
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
        this.identiteUri = identiteUri;
        this.contratEndroitUri = contratEndroitUri;
        this.fiscaleUri = fiscaleUri;
        this.ribUri = ribUri;
    }

    public Demandes(int id, String typeIdentite, int numIdentite, String nomEntreprise, String adresse, String activity, int numFiscale, int ribBanque,
                    String etat, String identiteUri, String contratEndroitUri, String fiscaleUri, String ribUri) {
        this.id = id;
        this.typeIdentite = typeIdentite;
        this.numIdentite = numIdentite;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.activity = activity;
        this.numFiscale = numFiscale;
        this.ribBanque = ribBanque;
        this.etat = etat;
        this.identiteUri = identiteUri;
        this.contratEndroitUri = contratEndroitUri;
        this.fiscaleUri = fiscaleUri;
        this.ribUri = ribUri;
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
    String identiteUri;
    String contratEndroitUri;
    String fiscaleUri;
    String ribUri;

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

    public String getIdentiteUri() {
        return identiteUri;
    }

    public void setIdentiteUri(String identiteUri) {
        this.identiteUri = identiteUri;
    }

    public String getContratEndroitUri() {
        return contratEndroitUri;
    }

    public void setContratEndroitUri(String contratEndroitUri) {
        this.contratEndroitUri = contratEndroitUri;
    }

    public String getFiscaleUri() {
        return fiscaleUri;
    }

    public void setFiscaleUri(String fiscaleUri) {
        this.fiscaleUri = fiscaleUri;
    }

    public String getRibUri() {
        return ribUri;
    }

    public void setRibUri(String ribUri) {
        this.ribUri = ribUri;
    }
}
