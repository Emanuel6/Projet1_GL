Pret pret = new Pret("test","1992-06-27","10000.00", "36", "0.02")


pret.versementMensuel = pret.getVersementMensuel()
println (pret.versementMensuel.toString())

for (i = 0; i < pret.nbPeriod; i++){

    pret.listSoldeDebut.add(pret.solde)
    pret.interetMois =  pret.getInteretMois()
    pret.listInteret.add(pret.interetMois)
    pret.listCapital.add(pret.getCapital())
    pret.solde = pret.getNouveauSolde()
    pret.listSoldeFin.add(pret.solde)


}

// FIXME : test
println("test")





