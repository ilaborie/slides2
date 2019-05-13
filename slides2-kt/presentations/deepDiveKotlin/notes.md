# [1] Deep Dive Kotlin : du Hello World au ByteCode [EV-IL]
Pas de notes
---
### [2] Speakers [EV-IL]
Pas de notes
---
### [3] Roadmap [EV-IL]
Pas de notes
---
## [4] ByteCode Java ? [EV-IL]
Pas de notes
---
### [5] javac [IL]
Pas de notes
---
### [6] HelloWorld.java [IL]
* `javac HelloWorld.java`
* `java HelloWorld` 
---
### [7] Java ByteCode binary [IL]
* `hexdump -C HelloWorld.class`
---
### [8] Explorons le ByteCode [IL]
* `javap -c -v HelloWorld.class`
---
### [9] À propos du ByteCode [IL]
Pas de notes
---
### [10] Jouons un peu [EV-IL]
Pas de notes
---
### [11] Liens [IL]
Pas de notes
---
## [12] Introduction Kotlin [EV-IL]
Pas de notes
---
### [13] Historique [EV]
Pas de notes
---
### [14] Cibles [EV]
Pas de notes
---
### [15] HelloWorld.kt [EV]
* transform to kotlin (tooling IntelliJ)
* nettoyage (pas d'object, pas d'annotation, pas d'args)
* `kotlinc HelloWorld.kt`
* `java HelloWorldKt`
---
### [16] kotlinc [EV]
Pas de notes
---
### [17] hexdump [EV]
Pas de notes
---
### [18] Java ByteCode [EV]
Pas de notes
---
### [19] HelloWorld-java [EV]
Pas de notes
---
### [20] Bilan HelloWorld.kt [EV]
Pas de notes
---
## [21] Les bases [EV-IL]
Pas de notes
---
### [22] val-var.kt [IL]
Pas de notes
---
### [23] string-template.kt [IL]
Pas de notes
---
### [24] string-template.java [IL]
Pas de notes
---
### [25] ByteCode de string-template [IL]
Pas de notes
---
### [26] numeric.kt [IL]
Pas de notes
---
### [27] numeric.java [IL]
Pas de notes
---
### [28] ByteCode de numeric [IL]
Pas de notes
---
### [29] C'est simple [IL]
Pas de notes
---
## [30] null-safety [EV-IL]
* `somethingNotNull` mettre `null`, ça plante car `val`
* passage à `var`, ça plante car type pas nullable
* `length = something.length` ça plante 
* on met le `?.`, ça plante car type `Int?`
* on met le `?:0` ça marche
* `length = createSomething()?.length?:0` pareil avec la fonction
* `length = createSomething()!!.length` ça plante au runtime
* `length = createSomething()?.length?:throw IllegalStateException("WTF")` c'est moins mauvais
* `something = "aString"` et `length = something.length`, pas besoin de `?.` car smart Cast
* Profiter pour montrer le tooling 'Show Kotlin Bycode' et 'Decompile' 
---
### [31] billion-dollar mistake [EV]
Pas de notes
---
### [32] null-safety.kt [EV]
Pas de notes
---
### [33] null-safety.java [EV]
Pas de notes
---
### [34] Bilan null-safety [EV]
Pas de notes
---
## [35] Les types [EV-IL]
Pas de notes
---
### [36] Types basiques [IL]
Pas de notes
---
### [37] Types basiques nullable [IL]
Pas de notes
---
### [38] todo.kt [IL]
Pas de notes
---
### [39] Hiérarchie des types [IL]
Pas de notes
---
## [40] Les fonctions [EV-IL]
Pas de notes
---
### [41] named-params.kt [IL]
Pas de notes
---
### [42] named-params.java [IL]
Pas de notes
---
### [43] default-value.kt [IL]
Pas de notes
---
### [44] Comment ça marche [IL]
Pas de notes
---
### [45] default-value.java [IL]
Pas de notes
---
### [46] ByteCode de default-value [IL]
Pas de notes
---
### [47] Kotlin c'est fun ! [IL]
Pas de notes
---
## [48] Les lambdas [EV-IL]
Pas de notes
---
### [49] function.kt [EV]
Pas de notes
---
### [50] function.java [EV]
Pas de notes
---
### [51] lambda.kt [EV]
* `val suml: (Int, Int) -> Int = { a, b -> a + b }` notation Lambda
* montrer l'ajout des types dans la lambda
* `val sum4 = compute(1, 2, { a, b -> a + b })`, puis sortie de la lambda
* `val sum4 = compute(1, 2) { a, b -> a + b }`
---
### [52] lambda.java [EV]
Pas de notes
---
### [53] let.kt [EV]
Pas de notes
---
### [54] let.java [EV]
Pas de notes
---
### [55] Les lambdas [EV]
Pas de notes
---
## [56] Les classes [EV-IL]
Pas de notes
---
### [57] astronomy.kt [EV]
--Planet--
* dans `AstronomicalBody` ajouter un `val name: String`
* `Planet(plantName: String, moons: List<Moon> = emptyList())` ajout constructeur avec 2 params
* `: AstronomicalBody`, et `val override name = plantName`
* déplacement du `val override` dans le constructeur
* ajout ` init { require(name.isNotEmpty()) }` pour controller le name
* ajout de `data`
--Moon--
* `data class Moon(override val name: String) : AstronomicalBody`
--SolarSystem--
* utilisation du `object` pour les singletons
* `val earth = Planet(name = "Earth") + moon` => ajout de l'opérateur avec le `copy`
--PlanetKind--
* création de l'enum
* ajout du companion object avec `fun fromName(name: String): PlanetKind = PlanetKind.valueOf(name)`
--getMoons--
* `val (_, moons) = planet` deconstruction puis retour des lunes
* utiliser `_`
---
### [58] astronomy.java [EV]
Pas de notes
---
### [59] Héritage en Kotlin [IL]
* `: SmallBody()`,  utiliser `open` sur la class
* `: SmallBody()` et `override sizeRange()`,  utiliser `open` sur la `fun`
* montrer type inférence sur le `bodies`
---
### [60] Génériques [IL]
Pas de notes
---
### [61] Sealed [IL]
Pas de notes
---
### [62] Alias en Kotlin [IL]
Pas de notes
---
### [63] ByteCode d'alias [IL]
Pas de notes
---
### [64] Classe, le bilan [EV-IL]
Pas de notes
---
## [65] Pause [EV-IL]
Pas de notes
---
## [66] ByteCode Android [EV-IL]
Pas de notes
---
### [67] Compilation pour Android [EV]
Pas de notes
---
### [68] Dalvik EXecutable format [EV]
Pas de notes
---
### [69] dexdump [EV]
Pas de notes
---
### [70] smali [EV]
Pas de notes
---
## [71] Autres structures [EV-IL]
Pas de notes
---
### [72] if-then-else.kt [IL]
Pas de notes
---
### [73] for.kt [IL]
Pas de notes
---
### [74] when.kt [IL]
Pas de notes
---
### [75] for-factorial.kt [IL]
Pas de notes
---
### [76] ByteCode factoriel avec for [IL]
Pas de notes
---
### [77] rec-factorial.kt [IL]
Pas de notes
---
### [78] ByteCode factoriel avec récursivité [IL]
Pas de notes
---
### [79] Récursion non terminale [IL]
Pas de notes
---
### [80] Récursion terminale [IL]
Pas de notes
---
### [81] tailrec-factorial.kt [IL]
Pas de notes
---
### [82] ByteCode factoriel avec recursivité terminale 1/2 [IL]
Pas de notes
---
### [83] ByteCode factoriel avec recursivité terminale 2/2 [IL]
Pas de notes
---
### [84] Performances sur 10! [IL]
Pas de notes
---
### [85] Bilan structures [IL]
Pas de notes
---
## [86] Extensions de fonctions [EV-IL]
Pas de notes
---
### [87] extension.kt [EV]
* ajouter `val AstronomicalBody.size: Int` avec `name.length`
* ajouter `fun AstronomicalBody.fullName(): String` en utilisant le `name, et la size`
---
### [88] extension.java [EV]
Pas de notes
---
### [89] Extension [EV]
Pas de notes
---
## [90] Les collections [EV-IL]
Pas de notes
---
### [91] Collections [IL]
Pas de notes
---
### [92] Les Maps [IL]
Pas de notes
---
### [93] api.kt [IL]
* on démarre par `SolarSystem.bodies`, une liste de corps celeste
* on filtre sur les `Planet`
* on `flatMap` sur les `moons`, on utilise `it`
* on `filterNot` pour retirer les "S/"
* on `sortBy` name
* on `joinToString(",\n")`
---
### [94] break-immutable.kt [IL]
Pas de notes
---
### [95] sequence.kt [EV]
* ajout du `asSequence()`, y compris dans le `flatMap`
---
### [96] Performance des séquences 1/2 [EV]
Pas de notes
---
### [97] sequence2.kt [EV]
Pas de notes
---
### [98] Performance des séquences 2/2 [EV]
Pas de notes
---
### [99] Bilan collection [EV-IL]
Pas de notes
---
## [100] Les delegates [EV-IL]
Pas de notes
---
### [101] delegate.kt [EV]
Pas de notes
---
### [102] lazy.kt [EV]
* test sans lazy
* ajout du `by lazy`
---
### [103] observable.kt [EV]
Pas de notes
---
### [104] lateinit.kt [EV]
Pas de notes
---
### [105] Delegate [EV]
Pas de notes
---
## [106] inline [EV-IL]
Pas de notes
---
### [107] inline.kt [IL]
Pas de notes
---
### [108] Logger.java [IL]
Pas de notes
---
### [109] reified.kt [IL]
Pas de notes
---
### [110] reified.java [IL]
Pas de notes
---
### [111] geoloc.kt [IL]
Pas de notes
---
### [112] geoloc bytecode [IL]
Pas de notes
---
### [113] Bilan inline [IL]
Pas de notes
---
## [114] Coroutines [EV-IL]
Pas de notes
---
### [115] Callback hell problem [EV]
Pour expliquer un des intérets des coroutines, on va partir d'un problème que vous avez surement tous eu, le callback hell.
On a une function assez simple, main, qui prend un paramètres et qui contient à l'intérieur deux étapes de calcul, la deuxieme étape nécessitant le résultat de la seconde.
Au niveau écriture, on ne se préoccupe pas vraiment du temps que peut prendre step1 pour répondre, on est bloquant.
---
### [116] Utilisons un callback [EV]

Avec kotlin, on peut s'implement l'écrire avec des lambdas, ce qui donne ceci à droite. On vient de créer une callback.
---
### [117] Ajoutons une étape [EV]
Maintenant si on rajoute une autre étape, on se rend vite compte qu'autant le code écrit de facon directe à gauche continue d'etre lisible, autant le code à droite l'est moins.
Donc pour une besoin de lisibilité, j'aurai tendance à préférer écrire le code de gauche. Mais imaginons maintenant que mes étapes soient non blocantes, comment faire en sorte de pouvoir écrire ce code ?
La réponse c'est les coroutines.
---
### [118] suspend [EV]
Pas de notes
---
### [119] Continuation [EV]
Pas de notes
---
### [120] Plus sur les coroutines [EV]
On va pas aller beaucoup plus loin sur les coroutines parce que d'une 3H ca serait pas assez, et de deux on est pas mal limité par la quantité de bytecode généré et par pas mal de bugs sur le décompilateur qui pour des cas assez simples génèrent des fichiers énormes.

---
## [121] Conclusion [EV-IL]
Pas de notes
---
### [122] Android [EV]
Pas de notes
---
### [123] Serveur [IL]
Pas de notes
---
### [124] Web et Natif [EV-IL]
Pas de notes
---
### [125] Bilan [EV-IL]
Pas de notes
---
### [126] Tendance [EV-IL]
Pas de notes
---
### [127] Kotlin vs Java [EV-IL]
Pas de notes
---
### [128] Merci [EV-IL]
Pas de notes
---