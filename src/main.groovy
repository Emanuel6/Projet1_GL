import groovy.json.JsonSlurper
import java.nio.file.Paths

final String jsonPath = "ressources/pretTest.json"
def pretInfosJSON
JsonSlurper slurper = new JsonSlurper()

Paths.get(jsonPath).withReader { reader ->
    pretInfosJSON = slurper.parse(reader)
}

Pret pret = new Pret(pretInfosJSON.scenario,pretInfosJSON.datePret,pretInfosJSON.montantInitial,
        pretInfosJSON.nombrePeriodes, pretInfosJSON.tauxPeriodique)

// calcul versement mensuel
pret.versementMensuel = pret.getVersementMensuel()

for (i = 0; i < pret.nbPeriod; i++){

    pret.listSoldeDebut.add(pret.solde)

    // calcul interet et maj de la liste
    pret.interetMois =  pret.getInteretMois()
    pret.listInteret.add(pret.interetMois)
    // calcul capital et maj de la liste
    pret.listCapital.add(pret.getCapital())
    // calcul du nouveau solde et maj de la liste
    pret.solde = pret.getNouveauSolde()
    pret.listSoldeFin.add(pret.solde)

}






