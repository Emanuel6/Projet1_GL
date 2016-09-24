class Pret {
    String scenario
    Integer nbPeriod
    Date date
    BigDecimal montantInit
    BigDecimal taux
    BigDecimal versementMensuel
    BigDecimal interetMois
    BigDecimal solde

    Pret (String scenario, String date, String montantInit, String nbPeriod, String taux){
        this.scenario = scenario
        this.montantInit = validerNombre(new BigDecimal(montantInit))
        this.nbPeriod = validerNombre(nbPeriod.toInteger())
        this.taux = validerNombre(new BigDecimal(taux))
        this.date = Date.parse("yyyy-MM-dd", date)
        this.solde = this.montantInit
    }

    def validerNombre (montant){
        assert montant >= 0 : "Les données ne peuvent pas être négatives!"
        return montant
    }

    def getVersementMensuel (){
        versementMensuel = (montantInit *taux ) / (1-((1 + taux)**(-nbPeriod)));
    }

    def getInteretMois (){
        interetMois = (solde * taux)
    }

    def getNouveauSolde () {
        solde - (versementMensuel - interetMois)
    }

    def arrondiDixieme (BigDecimal nb) {
        nb.setScale(2,BigDecimal.ROUND_HALF_EVEN)
    }

}
