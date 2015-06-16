%PUZ001-3
%Problem:  Dreadbury Mansion
%Status:  Satisfiable
%Type: CF

cnf(agatha, axiom, [lives(agatha)]).
cnf(butler, axiom, [lives(butler)]).
cnf(charles, axiom, [lives(charles)]).
cnf(poorer_killer, axiom, [-killed(X,Y), -richer(X,Y)]).
cnf(different_hates, axiom, [-hates(agatha,X), -hates(charles,X)]).
cnf(no_one_hates_everyone, axiom, [-hates(X,agatha), -hates(X,butler), -hates(X,charles)]).
cnf(agatha_hates_agatha, axiom, [hates(agatha,agatha)]).
cnf(agatha_hates_charles, axiom, [hates(agatha,charles)]).
cnf(killer_hates_victim, axiom, [-killed(X,Y), hates(X,Y)]).
cnf(same_hates, axiom, [-hates(agatha,X), hates(butler,X)]).
cnf(butler_hates_poor, axiom, [-lives(X), richer(X,agatha), hates(butler,X)]).
cnf(somebody_did_it, axiom, [killed(agatha,agatha), killed(butler,agatha), killed(charles,agatha)]).

pf([lives(_), -lives(_), -killed(_,_), killed(_,_), -richer(_,_), richer(_,_), -hates(_,_), hates(_,_)]).
