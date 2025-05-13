main :-
    catch(process_input, _, fail),
    halt.

process_input :-
    read_line(NLine),
    NLine \== end_of_file,
    atom_codes(NAtom, NLine),
    atom_number(NAtom, _N),
    read_line(VLine),
    VLine \== end_of_file,
    read_line(CLine),
    CLine \== end_of_file,
    atom_codes(VS, VLine),
    atom_codes(CS, CLine),
    atomic_list_concat(VTokens, ' ', VS),
    atomic_list_concat(CTokens, ' ', CS),
    values_to_bool(VTokens, Values),
    evaluate(CTokens, Values, Result),
    output(Result).

read_line(Line) :-
    current_input(Stream),
    read_line_to_codes(Stream, Line).

values_to_bool([], []).
values_to_bool(['T'|Xs], [true|Ys]) :- values_to_bool(Xs, Ys).
values_to_bool(['F'|Xs], [false|Ys]) :- values_to_bool(Xs, Ys).
values_to_bool([''|Xs], Ys) :- values_to_bool(Xs, Ys).

evaluate(Circuit, Values, Result) :-
    eval_with_stack(Circuit, Values, [], [Result|_]).

eval_with_stack([], _, Stack, Stack).
eval_with_stack(['*'|Rest], Values, [B,A|Stack], Result) :-
    and_gate(A, B, R),
    eval_with_stack(Rest, Values, [R|Stack], Result).
eval_with_stack(['+'|Rest], Values, [B,A|Stack], Result) :-
    or_gate(A, B, R),
    eval_with_stack(Rest, Values, [R|Stack], Result).
eval_with_stack(['-'|Rest], Values, [A|Stack], Result) :-
    not_gate(A, R),
    eval_with_stack(Rest, Values, [R|Stack], Result).
eval_with_stack([Var|Rest], Values, Stack, Result) :-
    atom(Var),
    atom_codes(Var, [Code]),
    Index is Code - 65,
    nth0(Index, Values, Value),
    eval_with_stack(Rest, Values, [Value|Stack], Result).

nth0(0, [X|_], X) :- !.
nth0(N, [_|Xs], X) :- N > 0, N1 is N - 1, nth0(N1, Xs, X).

output(true) :- write('T'), nl.
output(false) :- write('F'), nl.

% Logic gates
and_gate(true, true, true).
and_gate(_, _, false).

or_gate(false, false, false).
or_gate(_, _, true).

not_gate(true, false).
not_gate(false, true).

:- initialization(main).