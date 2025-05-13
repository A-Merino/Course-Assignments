

s --> gate.
s --> gate, s.

gate --> ins, op.

ins --> letter, in2.
ins --> letter.
in2 --> letter.
in2 --> [].

letter --> A.

op --> ["+"].
op --> ["*"].
op --> ["-"].
op --> [].


and(X, Y, Z) :- Z =:= X*Y.
or(X, Y , Z) :- z =:= X+Y.
not(X, Y) :- ~x.

assign(bools, )