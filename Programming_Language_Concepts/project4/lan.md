For the circuit problem, the first main problem that occurred was figuring out how to accept inputs for Prolog. Since it wasn't something that was covered in class, we had to do some sifting through the documentation on how to handle, accept, and reject input into the program. This was all handled by the *catch* predicate, which is a standard function. The *catch* predicate allows for us to call our user defined predicate, *process_input*, where we can collect input and turn them into lists of atoms using the *atomic_list_concat* predicate.

Before getting to that predicate, we looked at and used two predicates to collect the input. The first being *current_input/1* which collects the current input stream, as a stream. Since we cannot directly use the stream we need a preprocessing step, which is where *read_line_to_codes/2* comes in. This predicate uses the first parameter as the input stream and returns a string of character codes (in ascii) as the second parameter. Since we use another way to deal with new line characters, we specifically used the **/2** predicate, instead of the **/3** version that removes the trailing newline character from the stream. Learning about this also lead us to learn that Prolog supports overloading predicates.

The *atomic_list_concat/3* predicate is another default Prolog predicate that can turn a list into a string. This is very similar to the Python *.split()* function where given a string and the character to split at, we can return a list of strings. Normally this predicate is used to turn a list of strings into one string, separated by some character(s) (similar to *.join()* in Python). However, this predicate can be used both ways by making the first input the variable, instead of the third. This is a very interesting thing about Prolog in how by changing which input is the variable, we can use one function a multitude of ways. 

Another predicate we utilized this idea is with the *atom_codes/2* predicate from default Prolog. The predicate's intended function is to turn a string into a list of characters codes. Since each character only has one code, we can clearly do the reverse and give the predicate a list of character codes and expect to recieve only one output, which we do get. 

After creating the lists we called our three predicates:

### values_to_bool

The goal of this predicate is to assign the values *true* and *false* to the input string of "T F T ...". This is done by going through the list of strings we created and assigning *true* to "T" and *false* to "F". 

### eval_with_stack

Once the above function finishes, we call the *eval_with_stack/4* predicate, the list of parameters are as follows:

1. The circuit we are given as the third line of input
2. The boolean values that were assigned, which we parsed
3. The stack, which is initialized as empty
4. The result

We have five *eval_with_stack/4* predicates:

1. Empty List, default case
2. And gate, " * " case
3. Or gate, "+" case
4. Not gate, "-" case
5. Inputs to circuit, "A, B, C, ..." case 

Case 2,3, and 4 are where we take the first one(not)/two(and/or) boolean values off the stack and compute the gate given those values. Case 5 is where those boolean values are assigned and appended to the stack. The way that we solved this problem is very unique in that Prolog is one of the few if not only languages where there are no errors when having the same predicate/function name as well as the same amount of parameters. This is one of the advantages that Prolog does have and it allows for a much cleaner way to do flow control (mainly less lines of code and no if statements). 

### output

All output does is print "T" or "F" based on the output (hence the name) of the circuit. The printing is done to console by the predicate *write/1*, which is the last default Prolog predicate which was looked at.


The last thing that was looked at were some packages that Prolog supports, one of which is predefined logic gates such as and, or, xor, nand, etc. This package would have been very interesting to use however with the inputs which Kattis gives us, it would have required a decent amount more of parsing.  