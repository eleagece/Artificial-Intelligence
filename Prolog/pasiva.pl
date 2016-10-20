%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% GRUPO 9                             %
% Luis Alfonso González de la Calzada %
% Alfredo Hernández Burgos            %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%
% CONSULTA %
%%%%%%%%%%%%
	consulta :- read(F),procesa_frase(F).
	procesa_frase(Xs) :- frase(Salida,Xs,[]),
						 write(Salida),nl,
						 consulta.

%%%%%%%%%%%%%
% GRAMÁTICA %
%%%%%%%%%%%%%
	frase(Salida)
		--> gn(Sujeto, GeneroS, PersonaSyV, NumeroSyV), % Sujeto (artículo y nombre o pronombre), su género, persona y número
			vb(VerboRaiz_List, TiempoV, PersonaSyV, NumeroSyV, PreposicionVyCC), % Raíz del verbo, tiempo verbal, número verbal y preposición que acompaña el verbo
			gn(ComplDir, GeneroCD, PersonaCD, NumeroCD), % Complemento directo (artículo y nombre), su género, persona y número
			cc(ComplCirc, PreposicionVyCC), % Complemento circunstancial no obligatorio y preposición que acompaña al complemento circunstancial (la tiene que permitir el verbo)
			{ 
			enPasiva(Salida, Sujeto, VerboRaiz_List, TiempoV, ComplDir, GeneroCD, NumeroCD, ComplCirc) % guardamos en 'enPasiva' la forma pasiva de la entrada
			}.
	% Grupo nominal para 'artículo nombre'. No hay forma de construir articulo y nombre si no es con tercera persona: una flor, los niños...
	gn([Articulo, Nombre], GeneroAyN, 3, NumeroAyN) 
		--> [Articulo, Nombre], % -Entrada ejemplo sujeto: 'el,nino,...' -Entrada ejemplo cd: '...la,flor,...'
			{ 
			es_articulo(Articulo, NumeroAyN, GeneroAyN),
			es_nombre(Nombre, NumeroAyN, GeneroAyN) 
			}.
	% Grupo nominal para 'pronombre'. 
	gn([PronombrePas], _, PersonaPnb, NumeroPnb) 
		--> [PronombreAct], % -Entrada ejemplo sujeto: 'el,...'
			{ 
			es_pronombre(PronombreAct, PersonaPnb, NumeroPnb, PronombrePas) 
			}.
	vb(VerboRaiz_List, TiempoV, PersonaV, NumeroV, PreposicionV) 
		--> [Verbo], % -Entrada ejemplo: '...dibuja,...'
			{
			name(Verbo, Verbo_List), % de átomo (Verbo) a lista de caracteres (Verbo_List)
			append(VerboRaiz_List, TerminacionV_List, Verbo_List), % sacamos VerboRaiz_List (dibuj-) y TerminacionV_List (-a) sabiendo Verbo_List (dibuja)
			name(VerboRaiz, VerboRaiz_List), % de lista de caracteres (VerboRaiz_List) a átomo (VerboRaiz)
			es_raiz(VerboRaiz, PreposicionV), % recuperación de su preposición asociada (PreposicionV) a la raíz para ver luego que CC se puede usar
			name(TerminacionV, TerminacionV_List), % de lista de caracteres (TerminacionV_List) a átomo (TerminacionV)
			es_terminacion(TerminacionV, TiempoV, PersonaV, NumeroV) % recuperación de los atributos verbales (TiempoV, PersonaV, NumeroV) a partir de la terminación verbal
			}.
	cc([PreposicionCC|GrupoNominal], [PreposicionCC]) % [Cabeza|Resto]: se usa | en lugar de , para que los elementos de GrupoNominal se traten como elementos separados de la lista y no como uno solo
		--> [PreposicionCC], % -Entrada ejemplo: '...en,...'
			{ 
			es_preposicion(PreposicionCC) 
			},
			gn(GrupoNominal, _, _, _). % -Entrada ejemplo: '...el,cuaderno'
	cc([], _) 
		--> [].% Si no hay CC


%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% REGLAS PROLOG AUXILIARES %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	enPasiva(Salida, Sujeto, VerboRaiz_List, TiempoV, ComplDir, GeneroCD, NumeroCD, ComplCirc) 
		:- 	% Construir el verbo a forma pasiva: 'dibujada'
			verbo_ser(TiempoV, NumeroCD, Tiempo_ser), % obtiene verbo ser en pasiva (Tiempo_ser)
			participio(Terminacion, GeneroCD, NumeroCD), % obtiene la terminación del participio (Terminacion)
			name(Terminacion, Terminacion_List), % de átomo (Terminacion) a lista de caracteres (Terminacion_List)
			append(VerboRaiz_List, Terminacion_List, Participio_List), % concatena la raíz del participio en formato lista (VerboRaiz_List) y su terminación en formato lista (Terminacion_List) y lo guarda en formato lista (Participio_List)
			name(Participio, Participio_List), % de lista de caracteres(Participio_List) a átomo (Participio)
			% Construir la frase en pasiva
			append(ComplDir, [Tiempo_ser, Participio, por], Aux1), % 'la,flor,fue,dibujada,por,...' se guarda en Aux1
			append(Sujeto, ComplCirc, Aux2), % '...el,niño,en,el,cuaderno' se guarda en Aux2
			append(Aux1, Aux2, Salida). % 'la,flor,fue,dibujada,por,el,niño,en,el,cuaderno' se guarda en Salida
			
%%%%%%%%%%%%%%%%%
% BASE DE DATOS %
%%%%%%%%%%%%%%%%%
	%% Artículos 
	es_articulo(el, singular, masc).
	es_articulo(la, singular, fem).
	es_articulo(los, plural, masc).
	es_articulo(las, plural, fem).
	es_articulo(un, singular, masc).
	es_articulo(una, singular, fem).
	es_articulo(unos, plural, masc).
	es_articulo(unas, plural, fem).
	%% Nombres
	es_nombre(ninos, plural, masc).
	es_nombre(cuaderno, singular, masc).
	es_nombre(decision, singular, fem).
	es_nombre(nino, singular, masc).
	es_nombre(flor, singular, fem).
	%% Preposiciones
	es_preposicion(en).
	es_preposicion(con).
	%% Terminaciones en presente
	es_terminacion(o, presente, 1, singular).
	es_terminacion(as, presente, 2, singular).
	es_terminacion(a, presente, 3, singular).
	es_terminacion(amos, presente, 1, plural).
	es_terminacion(ais, presente, 2, plural).
	es_terminacion(an, presente, 3, plural).
	%% Terminaciones en pasado
	es_terminacion(e, pasado, 1, singular).
	es_terminacion(aste, pasado, 2, singular).
	es_terminacion(o, pasado, 3, singular).
	es_terminacion(amos, pasado, 1, plural).
	es_terminacion(asteis, pasado, 2, plural).
	es_terminacion(aron, pasado, 3, plural).
	%% Pronombres
	es_pronombre(yo, 1, singular, mi).
	es_pronombre(tu, 2, singular, ti).
	es_pronombre(el, 3, singular, el).
	es_pronombre(ella, 3, singular, ella).
	es_pronombre(nosotros, 1, plural, nosotros).
	es_pronombre(nosotras, 1, plural, nosotras).
	es_pronombre(vosotros, 2, plural, vosotros).
	es_pronombre(vosotras, 2, plural, vosotras).
	es_pronombre(ellos, 3, plural, ellos).
	es_pronombre(ellas, 3, plural, ellas).
	%% Participios
	participio(ada, fem, singular).
	participio(ado, masc, singular).
	participio(adas, fem, plural).
	participio(ados, masc, plural).
	%% Verbo ser
	verbo_ser(pasado, singular, fue).
	verbo_ser(presente, singular, es).
	verbo_ser(pasado, plural, fueron).
	verbo_ser(presente, plural, son).
	%% Raíces
	es_raiz(pint, [en]).
	es_raiz(habl, [con]).
	es_raiz(am, []). % Ninguna prep. permitida
	es_raiz(dibuj, [_P]). % Cualquier prep. permitida
	
%%%%%%%%%%%%%%%%%%%%%%%%
% FRASES PARA PROCESAR %
%%%%%%%%%%%%%%%%%%%%%%%%
% ?- frase(Salida, [el, nino, dibujo, una, flor], []).
% Salida = [una, flor, fue, dibujada, por, el, nino] 
% ?- frase(Salida, [el, nino, dibujo, una, flor, en, el, cuaderno], []).
% Salida = [una, flor, fue, dibujada, por, el, nino, en, el, cuaderno] 
% ?- frase(Salida, [yo, tome, la, decision], []).
% Salida = [la, decision, fue, tomada, por, mi]