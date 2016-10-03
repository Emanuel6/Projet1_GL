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
println("date pret recue "+ pretInfosJSON.datePret)
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

String message = pret.toString()
File file = new File('resultat.txt')
/*file.withWriter('utf-8') {
    writer -> writer.writeLine "message\n"
}*/
file << message +"\n"
file <<"Calendrier de rembroussement\n"
file <<"-------------------------------------------------------------------------\n"
file << "No   Date   Solde début   Versement   Intérêt   Capital   Solde fin\n"
file <<"-------------------------------------------------------------------------\n"
for (i = 0; i < pret.nbPeriod; i++){
    file << i +"   "
    file << pret.date.toString()+"   "
    file << pret.listSoldeDebut.get(i) +" \$"+"   "

    file << pret.versementMensuel +" \$   "

    file <<  pret.getInteretMois()  +" \$"+"   "

    file << pret.listInteret.get(i) +" \$"+"   "

    file << pret.listCapital.get(i) +" \$"+"   "

    file << pret.listSoldeFin.get(i)+" \$" +"   \n"
   }


String fichierPdf = "pandoc -s -o resultat.pdf resultat.txt"
fichierPdf.execute()
file.delete()

