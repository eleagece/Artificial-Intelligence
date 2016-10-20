%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% GRUPO 9                             %
% Luis Alfonso González de la Calzada %
% Alfredo Hernández Burgos            %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%
% CONSULTA %
%%%%%%%%%%%%
	consulta :- read(F),procesa_pregunta(F).						
	procesa_pregunta(Xs) :- preguntaAoB(Nombre,Atributo,Xs,[]),
							empleado(Nombre,Atributo,Valor),
							write('"'),write(Nombre),write('" tiene en "'),write(Atributo),write('" el valor "'),write(Valor),write('"'),nl,								
							consulta.
	procesa_pregunta(Xs) :- preguntaC(Atributo,Valor,Xs,[]),
							setof(Nombre,empleado(Nombre,Atributo,Valor),Out),
							write('Nombres de empleados con "'),write(Atributo),write('/'),write(Valor),write('": '),write(Out),nl,
							consulta.
%%%%%%%%%%%%%
% GRAMÁTICA %
%%%%%%%%%%%%%
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	% Pregunta tipo A: ¿Cuál es el/la "atributo" de "empleado"? %
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	preguntaAoB(Nombre,Atributo) --> preguntaA(Nombre,Atributo). 
	preguntaA(Nombre,Atributo) --> comienzoA,articulo(Genero,singular),atributo(Atributo,Genero),[de],nombre(Nombre).
	comienzoA --> [cual,es] ; [dime] ; [quiero,saber].
	articulo(Genero,Numero) --> [Articulo], {es_articulo(Articulo,Genero,Numero)}.
	atributo(Atributo,Genero) --> [Atributo], {es_atributoDeEmpleado(Atributo,Genero)}.
	nombre(Nombre) --> [Nombre], {es_nombreDeEmpleado(Nombre)}.

	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	% Pregunta tipo B: ¿Qué "atributo" tiene "empleado"? %
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	preguntaAoB(Nombre,Atributo) --> preguntaB(Nombre,Atributo). 
	preguntaB(Nombre,Atributo) --> [que],atributo(Atributo,_),[tiene],nombre(Nombre).

	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	% Pregunta tipo C: ¿Cuáles son los nombres de los empleados del/de_la "atributo" de "valor"? %
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	preguntaC(Atributo,Valor) --> comienzoC,[los,nombres,de,los,empleados],conector(Genero),atributo(Atributo,Valor,Genero),[de],valor(Atributo,Valor).
	comienzoC --> [dame] ; [quiero,saber] ; [cuales,son].
	conector(Genero) --> [Conector], {es_conector(Conector,Genero)}.
	atributo(Atributo,Valor,Genero) --> [Atributo], {es_atributoYValor(Atributo,Valor,Genero)}.
	valor(Atributo,Valor) --> [Valor], {es_atributoYValor(Atributo,Valor,Genero)}.
	
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% BÚSQUEDA EN LA BASE DE DATOS %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	es_articulo(Articulo,Genero,Numero) :- articulo(Articulo,Genero,Numero). % Recupero el nombre del articulo en "Articulo", su genero en "Genero" y su numero en "Numero"
	es_conector(Conector,Genero) :- conector(Conector,Genero). % Recupero el nombre del conector en "Conector" y su genero en "Genero"
	es_atributoDeEmpleado(Atributo,Genero) :- empleado(_,Atributo,_), nombre(Atributo,Genero). % Recupero el nombre del atributo en "Atributo" y su género en "Genero"
	es_nombreDeEmpleado(Nombre) :- empleado(Nombre,_,_). % Recupero el nombre del empleado en "Nombre"
	es_atributoYValor(Atributo,Valor,Genero) :- empleado(_,Atributo,Valor), nombre(Atributo,Genero). % Recupero el atributo del empleado en "Atributo" y el valor del atributo en "Valor" y el género del atributo en "Genero"
	es_empleado(Nombre,Atributo,Valor) :- empleado(Nombre,Atributo,Valor). % Recupero todas las entradas para empleado "Nombre", con atributo "atributo" y valor "valor"
	
%%%%%%%%%%%%%%%%%
% BASE DE DATOS %
%%%%%%%%%%%%%%%%%
	%% Artículos
	articulo(el,masculino,singular).
	articulo(la,femenino,singular).
	articulo(los,masculino,plural).
	articulo(las,femenino,plural).
	%% Conectores
	conector(del,masculino).
	conector(de_la,femenino).
	%% Nombres
	nombre(salario,masculino).
	nombre(departamento,masculino).
	nombre(edad,femenino).
	nombre(factoria,femenino).
	%% Empleados
	empleado(juan,salario,1000).
	empleado(juan,departamento,ventas).
	empleado(juan,edad,18).
	empleado(juan,factoria,madrid).
	empleado(narciso,salario,1100).
	empleado(narciso,departamento,ventas).
	empleado(narciso,edad,18).
	empleado(narciso,factoria,madrid).
	
%%%%%%%%%%%%%%%%%%%%
% PREGUNTAS TIPO A %
%%%%%%%%%%%%%%%%%%%%
% |: [cual,es,el,salario,de,narciso].
% "narciso" tiene en "salario" el valor "1100"
% |: [quiero,saber,la,factoria,de,juan].
% "juan" tiene en "factoria" el valor "madrid"
% |: [dime,el,departamento,de,narciso].
% "narciso" tiene en "departamento" el valor "ventas"
%%%%%%%%%%%%%%%%%%%%
% PREGUNTAS TIPO B %
%%%%%%%%%%%%%%%%%%%%
% |: [que,salario,tiene,juan].
% "juan" tiene en "salario" el valor "1000"
% |: [que,departamento,tiene,narciso].
% "narciso" tiene en "departamento" el valor "ventas"
% |: [que,factoria,tiene,narciso].
% "narciso" tiene en "factoria" el valor "madrid"
%%%%%%%%%%%%%%%%%%%%
% PREGUNTAS TIPO C %
%%%%%%%%%%%%%%%%%%%%
% ?- consulta.
% |: [quiero,saber,los,nombres,de,los,empleados,de_la,edad,de,18].
% Nombres de empleados con "edad/18": [juan,narciso]
% |: [dame,los,nombres,de,los,empleados,del,salario,de,1000].
% Nombres de empleados con "salario/1000": [juan]
% |: [cuales,son,los,nombres,de,los,empleados,de_la,factoria,de,madrid].
% Nombres de empleados con "factoria/madrid": [juan,narciso]
% |: [quiero,saber,los,nombres,de,los,empleados,del,salario,de,1100].
% Nombres de empleados con "salario/1100": [narciso]