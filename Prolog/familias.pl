% Grupo 2. Alfredo Hernández y Luis Alfonso González
dd(juan, maria, rosa, m).
dd(juan, maria, luis, h).
dd(jose, laura, pilar, m).
dd(luis, pilar, miguel, h).
dd(miguel,isabel,jaime,h).
dd(pedro, rosa, pablo, h).
dd(pedro, rosa, begoña, m).
padre(X, Y):- dd(X, _, Y, _).
madre(X, Y):- dd(_, X, Y, _).
hijo(X,Y):- dd(Y, _, X, h).
hijo(X,Y):- dd(_,Y, X,h).
hija(X,Y):- dd(Y, _, X, m).
hija(X,Y):- dd(_,Y, X,m).
abuelo(X,Y):- padre(X,Z),  padre(Z,Y).
abuelo(X,Y):- padre(X,Z),  madre(Z,Y).
abuela(X,Y):- madre(X,Z),  padre(Z,Y).
abuela(X,Y):- madre(X,Z),  madre(Z,Y).
hermano(X,Y):- hijo(X,Z),  padre(Z,Y).
hermano(X,Y):- hijo(X,Z),  madre(Z,Y).
hermana(X,Y):- hija(X,Z),  padre(Z,Y).
hermana(X,Y):- hija(X,Z),  madre(Z,Y).
primo(X,Y):- hijo(X,Z), hermano(Z,K), hijo(Y,K).
primo(X,Y):- hijo(X,Z), hermano(Z,K), hija(Y,K).
primo(X,Y):- hijo(X,Z), hermana(Z,K), hijo(Y,K).
primo(X,Y):- hijo(X,Z), hermana(Z,K), hija(Y,K).
prima(X,Y):- hija(X,Z), hermano(Z,K), hijo(Y,K).
prima(X,Y):- hija(X,Z), hermano(Z,K), hija(Y,K).
prima(X,Y):- hija(X,Z), hermana(Z,K), hijo(Y,K).
prima(X,Y):- hija(X,Z), hermana(Z,K), hija(Y,K).
ascendente(X,Y):- padre(X,Y).
ascendente(X,Y):- padre(X,Z), ascendente(Z,Y).
ascendente(X,Y):- madre(X,Y).
ascendente(X,Y):- madre(X,Z), ascendente(Z,Y).
