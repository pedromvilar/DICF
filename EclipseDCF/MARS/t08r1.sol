cnf(h1, top_clause, [
 -concentration(l2aminoadipate, up, 8),
 -concentration(transaconitate, up, 8),
 -concentration(taurine, up, 8),
 -concentration(hippurate, down, 8),
 -concentration(formate, down, 8),
 -concentration(acetate, up, 8),
 -concentration(nmna, down, 8),
 -concentration(nmnd, up, 8),
 -concentration(glucose, up, 8),
 -concentration(creatinine, down, 8),
 -concentration(methylamine, up, 8),
 -concentration(lactate, up, 8),
 -concentration(tmao, down, 8),
 -concentration(betaalanine, up, 8),
 -concentration(las, up, 8),
 -concentration(e2oxoglutarate, down, 8),
 -concentration(creatine, up, 8),
 -concentration(succinate, down, 8),
 -concentration(fumarate, down, 8),
 -concentration(citrate, down, 8)]
 ).

cnf(an, axiom, [concentration(X, down, Y), -reactionnode(X, Z, W), -inhibited(Z, t, W, X, Y)]).
cnf(an, axiom, [concentration(X, down, Y), -reactionnode(X, Z, W), -inhibited(Z, f, W, X, Y), -concentration(W, down, Y)]).
cnf(an, axiom, [concentration(X, up, Y), -reactionnode(Z, W, X), -inhibited(W, t, X, Z, Y)]).
cnf(an, axiom, [concentration(X, up, Y), -reactionnode(X, Z, W), -inhibited(Z, f, W, X, Y), -concentration(W, up, Y)]).
cnf(an, axiom, [-concentration(X, up, Y), -concentration(X, down, Y)]).
cnf(an, axiom, [-inhibited(X, t, Y, Z, W), -inhibited(X, f, Y, Z, W)]).
cnf(an, axiom, [-inhibited(X, f, Y, Z, W), -inhibited(X, f, Z, Y, W)]).

cnf(an, axiom, [reactionnode(l2aminoadipate, d2d6d1d39, e2oxoglutarate)]).
cnf(an, axiom, [reactionnode(e2oxoglutarate, d2d6d1d39, l2aminoadipate)]).
cnf(an, axiom, [reactionnode(e2oxoglutarate, d1d1d1d42, isocitrate)]).
cnf(an, axiom, [reactionnode(isocitrate, d1d1d1d42, e2oxoglutarate)]).
cnf(an, axiom, [reactionnode(e2oxoglutarate, d2d3d1d61, succinate)]).
cnf(an, axiom, [reactionnode(succinate, d2d3d1d61, e2oxoglutarate)]).
cnf(an, axiom, [reactionnode(isocitrate, d4d2d1d3, citrate)]).
cnf(an, axiom, [reactionnode(citrate, d4d2d1d3, isocitrate)]).
cnf(an, axiom, [reactionnode(isocitrate, d4d2d1d3, transaconitate)]).
cnf(an, axiom, [reactionnode(transaconitate, d4d2d1d3, isocitrate)]).
cnf(an, axiom, [reactionnode(citrate, d4d2d1d2, fumarate)]).
cnf(an, axiom, [reactionnode(fumarate, d4d2d1d2, citrate)]).
cnf(an, axiom, [reactionnode(succinate, d1d3d99d1, fumarate)]).
cnf(an, axiom, [reactionnode(fumarate, d1d3d99d1, succinate)]).
cnf(an, axiom, [reactionnode(succinate, d1d13d11d16, hippurate)]).
cnf(an, axiom, [reactionnode(hippurate, d1d13d11d16, succinate)]).
cnf(an, axiom, [reactionnode(citrate, d2d6d1dx, taurine)]).
cnf(an, axiom, [reactionnode(taurine, d2d6d1dx, citrate)]).
cnf(an, axiom, [reactionnode(arginine, d4d3d2d1, las)]).
cnf(an, axiom, [reactionnode(las, d4d3d2d1, arginine)]).
cnf(an, axiom, [reactionnode(fumarate, d4d3d2d1, las)]).
cnf(an, axiom, [reactionnode(las, d4d3d2d1, fumarate)]).
cnf(an, axiom, [reactionnode(nmnd, d2d1d1d1, fumarate)]).
cnf(an, axiom, [reactionnode(fumarate, d2d1d1d1, nmnd)]).
cnf(an, axiom, [reactionnode(nmna, d2d1d1d7, fumarate)]).
cnf(an, axiom, [reactionnode(fumarate, d2d1d1d7, nmna)]).
cnf(an, axiom, [reactionnode(las, d6d3d4d5, citruline)]).
cnf(an, axiom, [reactionnode(citruline, d6d3d4d5, las)]).
cnf(an, axiom, [reactionnode(citruline, d2d1d3d3, ornithine)]).
cnf(an, axiom, [reactionnode(ornithine, d2d1d3d3, citruline)]).
cnf(an, axiom, [reactionnode(arginine, d3d5d3d1, urea)]).
cnf(an, axiom, [reactionnode(arginine, d3d5d3d1, ornithine)]).
cnf(an, axiom, [reactionnode(urea, d3d5d3d1, arginine)]).
cnf(an, axiom, [reactionnode(ornithine, d3d5d3d1, arginine)]).
cnf(an, axiom, [reactionnode(ornithine, d2d1d1d2, creatine)]).
cnf(an, axiom, [reactionnode(creatine, d2d1d1d2, ornithine)]).
cnf(an, axiom, [reactionnode(creatine, d3d5d3d3, urea)]).
cnf(an, axiom, [reactionnode(creatine, d3d5d3d3, sarcosine)]).
cnf(an, axiom, [reactionnode(urea, d3d5d3d3, creatine)]).
cnf(an, axiom, [reactionnode(sarcosine, d3d5d3d3, creatine)]).
cnf(an, axiom, [reactionnode(creatinine, d3d5d2d10, creatine)]).
cnf(an, axiom, [reactionnode(creatine, d3d5d2d10, creatinine)]).
cnf(an, axiom, [reactionnode(creatinine, d3d5d1d59, sarcosine)]).
cnf(an, axiom, [reactionnode(sarcosine, d3d5d1d59, creatinine)]).
cnf(an, axiom, [reactionnode(formaldehyde, d1d5d99d1, sarcosine)]).
cnf(an, axiom, [reactionnode(sarcosine, d1d5d99d1, formaldehyde)]).
cnf(an, axiom, [reactionnode(formaldehyde, d1d1d99d8, formate)]).
cnf(an, axiom, [reactionnode(formate, d1d1d99d8, formaldehyde)]).
cnf(an, axiom, [reactionnode(formaldehyde, d1d4d99d3, methylamine)]).
cnf(an, axiom, [reactionnode(methylamine, d1d4d99d3, formaldehyde)]).
cnf(an, axiom, [reactionnode(tmao, d4d1d2d32, formaldehyde)]).
cnf(an, axiom, [reactionnode(tmao, d4d1d2d32, methylamine)]).
cnf(an, axiom, [reactionnode(formaldehyde, d4d1d2d32, tmao)]).
cnf(an, axiom, [reactionnode(methylamine, d4d1d2d32, tmao)]).
cnf(an, axiom, [reactionnode(lactate, d4d2d1d54, acryloylcoa)]).
cnf(an, axiom, [reactionnode(acryloylcoa, d4d2d1d54, lactate)]).
cnf(an, axiom, [reactionnode(betaalanine, d4d3d1d6, acryloylcoa)]).
cnf(an, axiom, [reactionnode(acryloylcoa, d4d3d1d6, betaalanine)]).
cnf(an, axiom, [reactionnode(acryloylcoa, d2d1d3d1, succinate)]).
cnf(an, axiom, [reactionnode(succinate, d2d1d3d1, acryloylcoa)]).
cnf(an, axiom, [reactionnode(betaalanine, d4d1d1d20, llysine)]).
cnf(an, axiom, [reactionnode(llysine, d4d1d1d20, betaalanine)]).
cnf(an, axiom, [reactionnode(betaalanine, d2d6d1d14, citrate)]).
cnf(an, axiom, [reactionnode(citrate, d2d6d1d14, betaalanine)]).
cnf(an, axiom, [reactionnode(l2aminoadipate, d1d2d1d31, llysine)]).
cnf(an, axiom, [reactionnode(llysine, d1d2d1d31, l2aminoadipate)]).
cnf(an, axiom, [reactionnode(glucose, d2d7d1d69, pyruvate)]).
cnf(an, axiom, [reactionnode(pyruvate, d2d7d1d69, glucose)]).
cnf(an, axiom, [reactionnode(pyruvate, d1d1d1d27, lactate)]).
cnf(an, axiom, [reactionnode(lactate, d1d1d1d27, pyruvate)]).
cnf(an, axiom, [reactionnode(pyruvate, d1d2d4d1, acetylcoa)]).
cnf(an, axiom, [reactionnode(acetylcoa, d1d2d4d1, pyruvate)]).
cnf(an, axiom, [reactionnode(acetylcoa, d2d3d3d1, citrate)]).
cnf(an, axiom, [reactionnode(citrate, d2d3d3d1, acetylcoa)]).
cnf(an, axiom, [reactionnode(acetylcoa, d6d2d1d1, acetate)]).
cnf(an, axiom, [reactionnode(acetate, d6d2d1d1, acetylcoa)]).

production_field([-inhibited(_,_,_,_,_)] <= 15).
