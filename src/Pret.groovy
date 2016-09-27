class Pret {
    String scenario
    Integer nbPeriod
    Date date
    BigDecimal montantInit
    BigDecimal taux
    BigDecimal versementMensuel
    BigDecimal interetMois
    BigDecimal solde
    ArrayList<BigDecimal> listSoldeDebut
    ArrayList<BigDecimal> listInteret
    ArrayList<BigDecimal> listCapital
    ArrayList<BigDecimal> listSoldeFin


    Pret (String scenario, String date, String montantInit, String nbPeriod, String taux){
        this.scenario = scenario
        this.montantInit = validerNombre(new BigDecimal(montantInit))
        this.nbPeriod = validerNombre(nbPeriod.toInteger())
        this.taux = validerNombre(new BigDecimal(taux))
        this.date = Date.parse("yyyy-MM-dd", date)
        this.solde = this.montantInit
        this.listSoldeDebut = new ArrayList<>()
        this.listInteret = new ArrayList<>()
        this.listCapital = new ArrayList<>()
        this.listSoldeFin = new ArrayList<>()
    }

    def validerNombre (montant){
        assert montant >= 0 : "Les données ne peuvent pas être négatives!"
        return montant
    }

    def getVersementMensuel (){
        arrondiDixieme((montantInit *taux ) / (1-((1 + taux)**(-nbPeriod))))
    }

    def getInteretMois (){
        arrondiDixieme(solde * taux)
    }

    def getNouveauSolde () {
        arrondiDixieme(solde - (versementMensuel - interetMois))
    }

    def getCapital () {
        arrondiDixieme(versementMensuel - interetMois)
    }

    def arrondiDixieme (BigDecimal nb) {
        nb.setScale(2,BigDecimal.ROUND_HALF_EVEN)
    }

}
