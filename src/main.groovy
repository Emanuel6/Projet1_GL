variable1 = "Homme"
variable2 = 12

if (variable1 == "Homme" && variable2 > 6 )
    println "Hello, tu es rentré dans le test!"

helloWorld = {String nom, String profession -> println "Hello $nom, laisse moi regarde ma boule de cristal...tu es $profession"}
helloWorld("eric","informaticien")

affiche = {value -> println(value);}
affiche(['a','b','c'])

def c = { arg1, arg2-> println "${arg1} ${arg2}" }
def d = c.curry("Ici")
d("Zebra 3")

trouvepetit = {
    liste->
        liste.findAll { it.size() <= 4 }.each { println it }
}
trouvepetit(["Eric", "Jean-Claude", "Paul-Edouard","Luc"])
