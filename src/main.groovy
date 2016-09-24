Pret pret = new Pret("test","1992-06-27","10000.00", "36", "0.02")


pret.versementMensuel = pret.getVersementMensuel()
println (pret.versementMensuel.toString())

for (i = 0; i <= pret.nbPeriod; i++){

    pret.interetMois = pret.getInteretMois()

    // FIXME : test pour voir les donner : a mettre dans un tableau
    println(pret.arrondiDixieme(pret.interetMois))

    pret.solde = pret.getNouveauSolde()
}






