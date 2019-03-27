# Livraison 5

## Fonctionnalités à implémenter

La gestion des threads serveur-clients a enfin été résolu grâce à l'aide que nous avons reçu. 
Nous n'avons pas réglé la gestion de l'instance d'un moteur qui se trouve en double dans Client car nous avons privilégié le renforcement des tests et le concept de l'achat d'une carte en fonction des ressources. 

## Tâches réalisées 

1. Mise en place d'une attente de connexion de tous les joueurs avant de distribuer les plateaux par Hugo 

2. Renforcement ou refonte des tests à partir de ce que nous avons développé sur les classes tests suivants :
    - Carte_ressourceTest par Alexandre
    - Carte_victoireTest par Alexandre
    - MoteurTest par Céline et Alexandre
En développant les tests du MoteurTest, notamment pour récupérer le toString() d'un joueur, nous avons remarqué une coquille sur la mise à jour des données. En effet alors que le Client disait bien qu'il a reçu son plateau, aucune méthode était mise en place pour ajouter le plateau dans leJoueur. 
    - Mise en place d'une méthode ajouterPlateau() dans Joueur

3. Gestion de la distribution des mains (de 3 cartes actuellement) à partir du deck de l'Age 1, par joueur.


