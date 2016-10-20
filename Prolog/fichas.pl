% Grupo 2. Alfredo Hernández y Luis Alfonso González
% ESTADOS INICIAL Y OBJETIVO:
inicial(estado(1,1,1,0,2,2,2)).
objetivo(estado(2,2,2,0,1,1,1)).

%Definimos las operaciones
%Movimiento de una ficha a la posición vacía de la izquierda
movimiento(estado(0,P1,P2,P3,P4,P5,P6),estado(P1,0,P2,P3,P4,P5,P6),i0).
movimiento(estado(P1,0,P2,P3,P4,P5,P6),estado(P1,P2,0,P3,P4,P5,P6),i0).
movimiento(estado(P1,P2,0,P3,P4,P5,P6),estado(P1,P2,P3,0,P4,P5,P6),i0).
movimiento(estado(P1,P2,P3,0,P4,P5,P6),estado(P1,P2,P3,P4,0,P5,P6),i0).
movimiento(estado(P1,P2,P3,P4,0,P5,P6),estado(P1,P2,P3,P4,P5,0,P6),i0).
movimiento(estado(P1,P2,P3,P4,P5,0,P6),estado(P1,P2,P3,P4,P5,P6,0),i0).
%Movimiento de una ficha a la posición vacía de la derecha
movimiento(estado(P1,P2,P3,P4,P5,P6,0),estado(P1,P2,P3,P4,P5,0,P6),d0).
movimiento(estado(P1,P2,P3,P4,P5,0,P6),estado(P1,P2,P3,P4,0,P5,P6),d0).
movimiento(estado(P1,P2,P3,P4,0,P5,P6),estado(P1,P2,P3,0,P4,P5,P6),d0).
movimiento(estado(P1,P2,P3,0,P4,P5,P6),estado(P1,P2,0,P3,P4,P5,P6),d0).
movimiento(estado(P1,P2,0,P3,P4,P5,P6),estado(P1,0,P2,P3,P4,P5,P6),d0).
movimiento(estado(P1,0,P2,P3,P4,P5,P6),estado(0,P1,P2,P3,P4,P5,P6),d0).
%Movimiento de una ficha saltando otra ficha hasta la posición vacía de la izquierda
movimiento(estado(0,P1,P2,P3,P4,P5,P6),estado(P2,P1,0,P3,P4,P5,P6),i1).
movimiento(estado(P1,0,P2,P3,P4,P5,P6),estado(P1,P3,P2,0,P4,P5,P6),i1).
movimiento(estado(P1,P2,0,P3,P4,P5,P6),estado(P1,P2,P4,P3,0,P5,P6),i1).
movimiento(estado(P1,P2,P3,0,P4,P5,P6),estado(P1,P2,P3,P5,P4,0,P6),i1).
movimiento(estado(P1,P2,P3,P4,0,P5,P6),estado(P1,P2,P3,P4,P6,P5,0),i1).
%Movimiento de una ficha saltando otra ficha hasta la posición vacía de la derecha
movimiento(estado(P1,P2,P3,P4,P5,P6,0),estado(P1,P2,P3,P4,0,P6,P5),d1).
movimiento(estado(P1,P2,P3,P4,P5,0,P6),estado(P1,P2,P3,0,P5,P4,P6),d1).
movimiento(estado(P1,P2,P3,P4,0,P5,P6),estado(P1,P2,0,P4,P3,P5,P6),d1).
movimiento(estado(P1,P2,P3,0,P4,P5,P6),estado(P1,0,P3,P2,P5,P4,P6),d1).
movimiento(estado(P1,P2,0,P3,P4,P5,P6),estado(0,P2,P1,P3,P4,P5,P6),d1).
%Movimiento de una ficha saltando dos fichas hasta la posición vacía de la izquierda
movimiento(estado(0,P1,P2,P3,P4,P5,P6),estado(P3,P1,P2,0,P4,P5,P6),i2).
movimiento(estado(P1,0,P2,P3,P4,P5,P6),estado(P1,P4,P2,P3,0,P5,P6),i2).
movimiento(estado(P1,P2,0,P3,P4,P5,P6),estado(P1,P2,P5,P3,P4,0,P6),i2).
movimiento(estado(P1,P2,P3,0,P4,P5,P6),estado(P1,P2,P3,P6,P4,P5,0),i2).
%Movimiento de una ficha saltando dos fichas hasta la posición vacía de la derecha
movimiento(estado(P1,P2,P3,P4,P5,P6,0),estado(P1,P2,P3,0,P5,P6,P4),d2).
movimiento(estado(P1,P2,P3,P4,P5,0,P6),estado(P1,P2,0,P4,P5,P3,P6),d2).
movimiento(estado(P1,P2,P3,P4,0,P5,P6),estado(P1,0,P3,P4,P2,P5,P6),d2).
movimiento(estado(P1,P2,P3,0,P4,P5,P6),estado(0,P2,P3,P1,P4,P5,P6),d2).

% Determinar si hay solución, con control de repeticiones y devolviendo el camino
puede(Estado,_,[]) :- objetivo(Estado).	% comprueba si estamos en estado objetivo
puede(Estado,Visitados,[Operador|Operadores]) :- 
			movimiento(Estado,Estadoi,Operador),
			\+member(Estadoi,Visitados),puede(Estadoi,[Estadoi|Visitados],Operadores).

% CONSULTA:
consulta :- inicial(Estado),
			puede(Estado,[Estado],Camino),
			write('SOLUCION ENCONTRADA: '),nl, 
			write(Camino).
