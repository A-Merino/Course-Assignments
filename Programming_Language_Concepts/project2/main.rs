// imports standard input/output
use std::collections::HashMap;
use std::io;

// main function
fn main() {
    // create a String Hashmap 
    // the reason we use a HashMap is because the input can be out of order
    let mut code: HashMap<String, Vec<String>> = HashMap::new();


    // save the standard in to imut var
    let stdin = io::stdin(); 
    
    // go through each line, unwrap it and add to vector
    for line in stdin.lines() {
        let line_itr: Vec<String> = line
                .as_ref() // borrows the reference from line
                .unwrap() // unwraps the text from input form to &str
                .split(" ") // splits the &str by space and returns Split() iter
                .map(|v| v.to_string()) // Maps each &str in iter to a String literal
                .collect(); // Transforms the Split() iter into a Vector (array)

        /*
        insert into our Hash Map
            key: label of Basic code, e.g. 10, 20, 50, 120
            value: the actual basic code, e.g. PRINTLN "HELLO"

            We close the line_itr[0] because it will not remember it outside of the scope of the
            for loop

            For the rest of the line we take a subset of the vector [1..line_itr.len()]
                So from 1, inclusive, to len of vector, exclusive, we get the Vector as a not vector type
                To solve this we cast it to a vector by calling .to_vec()
        */
        code.insert(line_itr[0].clone(), line_itr[1..line_itr.len()].to_vec());
        
    }


    // parse through code in order

    // create list of variables
    let mut vars: HashMap<String, i32> = HashMap::new();

    // Set our curent label to the first one
    let mut cur_label = 10;

    // boolean for while loop
    let mut not_last_line = true;

    let mut mat_op = 4;

    while not_last_line {
        // get our line of basic code
        // we grab the reference of the Vector<String> by using the reference of cur_label
        let line = &code[&cur_label.to_string()];

        // switch case
        match line[0].as_str() {
            "LET" => {
                //  create variable from Basic and value from variable
                let var_name = line[1].clone();
                let mut new_val: i32 = 0;

                // if variable has no value
                if !vars.contains_key(&var_name) {
                    // create it and set to 0
                    vars.insert(var_name.clone(), 0);   
                } else {
                    // if it does then collect value
                    new_val = vars[&var_name];
                }

                // we know that the first three lits here are "LET <VAR> =" so we can skip them 
                for lit in &line[3..line.len()] {
                    // check if we can parse the string in to an integer
                    if lit.parse::<i32>().is_ok() {

                        let new_int = lit.parse::<i32>().unwrap();

                        // based on math operation from before the int, do it
                        match mat_op {
                            0 => new_val += new_int, 
                            1 => new_val -= new_int,
                            2 => new_val *= new_int,
                            3 => new_val /= new_int,
                            4 => new_val = new_int,  //default is add e.g. A = 100
                            _ => break
                        }
                    } 
                    // if next literal is a variable
                    else if vars.contains_key(lit){
                        // get the value of the variable
                        let cur_var = vars[lit];

                        // based on math operation from before this var, do it
                        match mat_op {
                            0 => new_val += cur_var, 
                            1 => new_val -= cur_var,
                            2 => new_val *= cur_var,
                            3 => new_val /= cur_var,
                            4 => new_val = cur_var,  //default is add e.g. A = B
                            _ => break
                        }
                    }
                    // if not variable or int it must be a mathematical operation
                    else {
                        match lit.as_str() {
                            "+" => mat_op = 0,
                            "-" => mat_op = 1,
                            "*" => mat_op = 2,
                            "/" => mat_op = 3,
                            _ => mat_op = -1
                        }

                    }
                }
                // reset default math operation (just assignment)
                mat_op = 4;

                // update variable in HashMap (memory)
                *vars.get_mut(&var_name).unwrap() = new_val;
                cur_label += 10;
            }
            "PRINT" => {
                // if only one string or var
                if line.len() == 2 {
                    // if string then get rid of both quotes
                    if line[1].starts_with("\"") {
                        print!("{}", &line[1].replace("\"", ""));
                    }
                    // if var then print var amount
                    else {
                        print!("{}", vars[&line[1]]);
                    }
                } 
                // multi word string
                else {
                    // for each word in string
                    for lit in &line[1..line.len()] {
                        // if the String was just a " then put a space because thats where the split occurred
                        if lit == "\"" {
                            print!(" ");
                        }
                        // if starts with quote, begone
                        else if lit.starts_with("\"") {
                            print!("{}", &lit.replace("\"", ""));

                        }
                        // if middle word, print
                        else {
                            print!(" {}", lit.replace("\"", ""));
                        }

                    }
                }
                // go to next line of Basic code
                cur_label += 10;

            }
            "PRINTLN" => {
                // if only only 1 string or variable being printed
                if line.len() == 2 {
                    // if not a variable it will start with "
                    if line[1].starts_with("\"") {
                        // if string is DONE then the program is done
                        if line[1].contains("DONE") {
                            not_last_line = false; 

                        }
                        // print string
                        print!("{}", &line[1].replace("\"", ""));

                    } else {
                        // grab variable value from variable HashMap and print it
                        println!("{}", vars[&line[1]]);

                    }
                }
                // if multi-word string
                else {
                    // go through each word
                    for lit in &line[1..line.len()] {

                        // if it is done then end program
                        // we can probably delete this but for testing keep
                        if lit.contains("DONE") {
                            not_last_line = false; 
                        }

                        // if only a quote then add a space since we got
                        // rid of it earlier and we don't want to print quotes
                        if lit == "\"" {

                        }
                        // if starts with quote get rid of it
                        else if lit.starts_with("\"") {
                            print!("{}", &lit.replace("\"", ""));

                        }
                        // if ends with quote get rid of it
                        else if lit.ends_with("\"") {

                            print!(" {}", &lit.replace("\"", ""));

                        }
                        // if word in middle of string then just print word
                         else {
                            print!(" {}", lit.replace("\"", ""));
                        }
                        // new line at end

                    }
                    println!();
                }
                // go to next line of code
                cur_label += 10;
            }
            "IF" => {
                // get the goto label from end of line
                let goto: i32 = line[line.len()-1].parse::<i32>().unwrap();
                // int for comparison and var1/var2 values
                let mut comparison: i32 = -1;
                let mut var1: i32 = 0;
                let mut var2: i32 = 0;

                // go through the next three literals
                for lit in &line[1..4] {
                    // if it is a variable
                    if vars.contains_key(lit) {
                        // if before comparison sign set it to var 1
                        if comparison == -1 {
                            var1 = vars[lit];
                        } else {
                            // if after comparison sign set it to var 2

                            var2 = vars[lit];
                        }
                    }
                    // check if literal is a constant integer
                    else if lit.parse::<i32>().is_ok() {
                        // if beforecomparison sign set it to var 1
                        if comparison == -1 {
                            var1 = lit.parse::<i32>().unwrap();
                        } else {
                            // if after comparison sign set it to var 2
                            var2 = lit.parse::<i32>().unwrap();
                        }
                    } 
                    else {
                        // if not a constant or variable it must be a comparison symbol
                        match lit.as_str() {
                            "=" => comparison   = 0,
                            ">" => comparison   = 1,
                            "<" => comparison   = 2,
                            "<>" => comparison  = 3,
                            ">=" => comparison  = 4,
                            "<=" => comparison  = 5,
                            _ => comparison = -1
                        }
                    }
                } // end finding vars and comparison

                // look at comparison
                match comparison {
                    0 => { // do if equals
                        if var1 == var2 {
                            cur_label = goto;
                        } else {
                            cur_label += 10;
                        }
                    }
                    1 => { // do if greater than
                        
                        if var1 > var2 {
                            cur_label = goto;
                        } else {
                            cur_label += 10;
                        }
                    }
                    2 => { // do if less than
                        
                        if var1 < var2 {
                            cur_label = goto;
                        } else {
                            cur_label += 10;
                        }
                    }
                    3 => { // do if not equal
                        
                        if var1 != var2 {
                            cur_label = goto;
                        } else {
                            cur_label += 10;
                        }
                    }
                    4 => { // do if greater than or equal to
                        
                        if var1 >= var2 {
                            cur_label = goto;
                        } else {
                            cur_label += 10;
                        }
                    }
                    5 => { // do if less than or equal to

                        if var1 <= var2 {
                            cur_label = goto;
                        } else {
                            cur_label += 10;
                        }
                    }
                    _ => break
                }

            }
            _ => { // default case
                println!("NOTHING");
            } // end "IF" case
        } //end switch case


    } // end while loop 

    
}

