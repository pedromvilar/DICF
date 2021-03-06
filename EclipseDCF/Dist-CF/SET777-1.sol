%SET777-1
%Problem:  Set theory membership and subsets axioms
%Status:  Satisfiable
%Type: CF

cnf(membership_in_subsets, axiom, [-member(Element,Subset), -subset(Subset,Superset), member(Element,Superset)]).
cnf(subsets_axiom1, axiom, [subset(Subset,Superset), member(member_of_1_not_of_2(Subset,Superset),Subset)]).
cnf(subsets_axiom2, axiom, [-member(member_of_1_not_of_2(Subset,Superset),Superset), subset(Subset,Superset)]).
cnf(set_equal_sets_are_subsets1, axiom, [-equal_sets(Subset,Superset), subset(Subset,Superset)]).
cnf(set_equal_sets_are_subsets2, axiom, [-equal_sets(Superset,Subset), subset(Subset,Superset)]).
cnf(subsets_are_set_equal_sets, axiom, [-subset(Set1,Set2), -subset(Set2,Set1), equal_sets(Set2,Set1)]).

pf([-member(_,_), member(_,_), -subset(_,_), subset(_,_), -equal_sets(_,_), equal_sets(_,_)]).
