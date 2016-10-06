import groovy.json.JsonSlurper
import java.nio.file.Paths
import java.text.SimpleDateFormat


final String jsonPath = "ressources/pretTest.json"
def pretInfosJSON
JsonSlurper slurper = new JsonSlurper()
BigDecimal totalPaiment =0
BigDecimal totalInteret = 0

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
SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd")
String sortie=""


File file = new File("resultat.html")
String entete =""
entete+= " <!DOCTYPE html>"
entete+=        "<html lang=\"fr\">"
entete+= "<head>"
entete+= "  <title> Calendrier de Rembourssemment de pret</title>"
entete+= "  <meta charset=\"utf-8\">"
entete+= "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
entete+= "  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
entete+= "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js\"></script>"
entete+= "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>"
entete+= "</head>"



file.write(entete)
file << "<body>"
sortie += '<div class = "row">'+
        '<div class = "col-sm-2">'+"Paramètre du Prêt : " +"</div>"  +"</div>"
sortie += '<div class = "row">'+
        '<div class = "col-sm-2">'+"Scenario : " +"</div>" + '<div class = "col-sm-2">'+ pret.getScenario() + "</div>" +"</div>"
sortie += '<div class = "row">'+
        '<div class = "col-sm-2">'+"DatePret : " +"</div>" + '<div class = "col-sm-2">'+ DATE_FORMAT.format(pret.getDate()) + "</div>" +"</div>"

sortie += '<div class = "row">'+
        '<div class = "col-sm-2">'+"MonatantInitial : " +"</div>" + '<div class = "col-sm-2">'+ pret.getMontantInit() + "</div>" +"</div>"

sortie += '<div class = "row">'+
        '<div class = "col-sm-2">'+"NombrePeriodes : " +"</div>" + '<div class = "col-sm-2">'+ pret.getNbPeriod() + "</div>" +"</div>"


sortie += '<div class = "row">'+
        '<div class = "col-sm-2">'+" tauxPeriodes : " +"</div>" + '<div class = "col-sm-2">'+ pret.getTaux() + "</div>" +"</div>"

sortie += '<div class = "row">'+
        '<div class = "col-sm-4">'+" Calendrier de rembroussement : " +"</div>"  +"</div>"


sortie += '<table class = table table-striped">'
sortie += "<thead>"
sortie +="<tr>" + "<th> No </th>"+"<th> Date </th>"+"<th>Solde debut</th>"+"<th>Intérêt</th>"+"<th>Versement</th>"+"<th>Capital</th>"
sortie +="<th>Solde fin</th>" +"</tr>"
sortie += "</thead>"


String [] dateString = DATE_FORMAT.format (pret.date).split("-")
int annee  = Integer.parseInt(dateString[0]);
int mois =Integer.parseInt(dateString[1]);
int jour =Integer.parseInt(dateString[2])
for (i = 0; i < pret.nbPeriod; i++){
    int j = i+1

    mois +=1
    if(mois > 12){
        annee +=1
        mois = 1
    }
    String moisString =""
    String jourString = ""
    if(mois<10){
        moisString = "0"+mois
    }else {
        moisString += mois
    }

    if (jour < 10 ){
        jourString +="0"+jour
    }else{
        jourString += jour
    }
    sortie += "<tr>"
    sortie += "<td>"+ j +"</td>"
    sortie += "<td>" + annee+"-"+moisString+"-"+jourString + "</td>"

    sortie += "<td>" + pret.listSoldeDebut.get(i) +" \$" +"</td>"

    sortie +="<td>" + pret.versementMensuel +" \$" + "</td>"

    sortie += "<td>" + pret.getListInteret().get(i)  +" \$" +"</td>"

    sortie += "<td>" +  pret.listCapital.get(i) +" \$"+"</td>"

    sortie += "<td>" + pret.listSoldeFin.get(i)+" \$" +"</td>"
    sortie += "</tr>"
    totalPaiment += pret.versementMensuel
    totalInteret += pret.getListInteret().get(i)
}

file <<(sortie)
String footer= ""
file<< "</table>"
file << '<div class = "row">'+
        '<div class = "col-sm-2">'+"Totaux : " +"</div>"+"</div>"

footer +='<div class = "row">'+
        '<div class = "col-sm-2">'+"Total des paiments : " +"</div>" + '<div class = "col-sm-2">'+ totalPaiment  +" \$" + "</div>" +"</div>"
footer +='<div class = "row">'+
        '<div class = "col-sm-2">'+"Total des intérêts : " +"</div>" + '<div class = "col-sm-2">'+ totalInteret +" \$" + "</div>" +"</div>"
footer +='<div class = "row">'+
        '<div class = "col-sm-2">'+"Total du capital remboursé : " +"</div>" + '<div class = "col-sm-2">'+
        (totalPaiment - totalInteret) +" \$" + "</div>" +"</div>"
file << footer
file<<"</body>"
file << "</html>"



