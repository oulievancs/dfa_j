import java.util.*;
import java.lang.*;
import java.io.*;

public class DFA {
	public static void main(String [] args) {
		String filename = "dfa.txt";
		try{
			int num_states;
			String [] alphabet, final_states, cur;
			String start_state, current, userInput, state;
			HashMap <String, ArrayList<Transition>> states = new HashMap<String, ArrayList<Transition>>();
			Scanner sc = new Scanner(System.in);
			
			BufferedReader reader = new BufferedReader (new FileReader (filename));	//open file
			
			num_states = Integer.parseInt(reader.readLine());	//Read from file number of states.
			alphabet = reader.readLine().split(" ");			//Read from file the alphabet.
			start_state = reader.readLine();					//Read from file the start state
																//	that DFA starts.
			final_states = reader.readLine().split(" ");		//Read from file to Array all final states.
			
			while ((current = reader.readLine()) != null) {
				cur = current.split(" ");
				
				boolean isOnAlphabet = false;
				for (String s : alphabet) {						//Check if symbol is in Alphabet.
					if (s.equals(cur[1])) {
						isOnAlphabet = true;
						break;
					}
				}
				
				if (!isOnAlphabet) {
					System.out.println("False symbol on file input.");
					System.exit(12);
				}
				if (states.get(cur[0]) == null) {
					states.put(cur[0], new ArrayList<Transition> ());		//Generate transition.
				}
				
				states.get(cur[0]).add(new Transition(cur[1], cur[2]));
			}
			
			System.out.print("### GIVE a sequence:\t");
			userInput = sc.nextLine();
			
			while (userInput != "\n" && userInput != null) {
				state = start_state;
				for(char c : userInput.toCharArray()) {						//Analyze user input to chars.
					ArrayList<Transition> h = states.get(state);
					for (Transition tr : h) {
						if (tr.input.equals(String.valueOf(c))) {			//Chaech if user input is equal to
																			//  transition symbol.
							System.out.print("Transition [State from " + state);
							state = tr.trans;								//Make the transition.
							
							System.out.print(" => to State " + state + "]\n");
							break;
						}
					}
				}
				
				boolean isOk = false;
				for(String fin : final_states) {							//Check if state is in final states.
					if (state.equals(fin)) {
							isOk = true;
							break;
					}
				}
				
				if (isOk) {
					System.out.println("\n\n#####SEQUENCE OK#####\n");
				} else {
					System.out.println("\n\n#####SEQUENCE NOT OK#####\n");
				}
				
				System.out.print("### GIVE a sequence:\t");
				userInput = sc.nextLine();
			}
			
			reader.close();	//close file stream
		} catch (Exception e) {
				System.err.format("Exception occurred trying to read '%s'.", filename);
				e.printStackTrace();
		}
	}
}