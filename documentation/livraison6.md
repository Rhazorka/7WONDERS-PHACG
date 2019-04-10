# Livraison 6

## Fonctionnalités à implémenter

La gestion des tours qui inclue la diminution des mains des joueurs lorsqu'ils jouent ainsi que la rotation des paquets de cartes entre joueurs à la fin d'un tour. Ce qui inclue aussi la gestion des âges qui actuellement donne une limite de tour à jouer durant l'âge

## Tâches réalisées 

1. Affichage plus lisible 

2. réorganisaton des méthodes contenu dans client, serveur et commun afin que le découpage soit correcte (Hugo et Céline)

3. - mise en place de tests pour vérifier plusieur scénario où le joueur essaye d'acheté un carte (AcheterCartes() )(Celine)
   - Pour les tests ServeurTest et ClientTest, ce qu'on a voulu mettre en place c'est une méthode qui retourne un string afin de prouver que la communication entre Serveur/Client marche bien. Du coup pour les interactions entre classes test et main, ça serait entre ServeurTest et Client puis entre Serveur et ClientTest. 
Pour le moment, pour vérifier que la méthode Test() (qui retourne un String) marche correctement, les sorties systèmes sont mises en places dans Client et Serveur (du coup on voit les tests en lançant le jeu).
Le problème est qu'on ne sait pas comment récupérer la chaîne de caractères de Test() dans une variable global à la classe test, car par exemple pour la variable phrase_base dans Client, toute mise à jour effectuée de cette variable reste en interne sous une connexion.on... ce qui fait qu'à la sortie d'une connexion.on phrase_base reste une chaîne vide. Ce qui nous empêche du coup de faire des assert pour les tests.
    
4. Gestion de la distribution des mains (de 3 cartes actuellement) à partir du deck de l'Age 1, par joueur.

5. Instanciation du maximum de cartes possibles selon les classes de cartes mise en place

6. mise en place de premier tour de jeu pour le première âge (version 1 à amélioré) (Hugo)

## reporté à l'itération suivante

1. l'appelle de la méthode AcheterCartes() qui vérifie si le joueur peut acheter une carte, dans le déroulement d'une partie (Gautier + Alexandre)
2. amélioration des tours de jeu car ils ne sont pas tout à fait au point

3. Pour les tests ServeurTest et ClientTest, ce qu'on a voulu mettre en place c'est une méthode qui retourne un string afin de prouver que la communication entre Serveur/Client marche bien. Du coup pour les interactions entre classes test et main, ça serait entre ServeurTest et Client puis entre Serveur et ClientTest. 
Pour le moment, pour vérifier que la méthode Test() (qui retourne un String) marche correctement, les sorties systèmes sont mises en places dans Client et Serveur (du coup on voit les tests en lançant le jeu).
Le problème est qu'on ne sait pas comment récupérer la chaîne de caractères de Test() dans une variable global à la classe test, car par exemple pour la variable phrase_base dans Client, toute mise à jour effectuée de cette variable reste en interne sous une connexion.on... ce qui fait qu'à la sortie d'une connexion.on phrase_base reste une chaîne vide. Ce qui nous empêche du coup de faire des assert pour les tests.
