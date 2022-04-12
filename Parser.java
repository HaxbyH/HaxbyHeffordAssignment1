import java.io.*;

public class Parser {

	public static void main(String[] args) {
		BufferedReader reader;
		BufferedWriter buffer;
		String line;
		try {
			reader = new BufferedReader(new FileReader("wsj.xml"));
			int docIndex = 0;
			try {
				buffer = new BufferedWriter(new FileWriter("parse.txt"));
				// Run through everyline
				int docTag = 1;
				while ((line = reader.readLine()) != null) {
					String[] splitted = line.split("[^a-zA-Z0-9><'-]");
					if(splitted.length >= 1) {
						// If <DOCNO> turns up print a line
						if (splitted[0].equals("<DOCNO>")) {
							if(!(docIndex==0)){
								docTag++;
								buffer.write("\n");
							}
							docIndex++;
						}

						// add word to stream if conditions met
						for(String x : splitted) {
							// Pick up word that is attached to </HL>
							if(x.length() > 2) {
								if ((x.charAt(x.length()-1)) == '<') {
									buffer.write(x.substring(0, x.length()-1).trim().toLowerCase() + "\n");
								}
							}

							// Exclude </TEXT> etc
							if (!(x.contains(">")) && !(x.contains("<"))) {
								if(!x.isEmpty()) {
									// If statement so DOCNO is not lowered
									if (docTag == 1) {
										buffer.write(x.trim() + "\n");
										docTag = 0;
									} else if (x.length() > 2){
										buffer.write(x.trim().toLowerCase() + "\n");
									}
								}
							}
						}
					}
				}
				buffer.write("\n");
				reader.close();
				buffer.close();
			} catch( IOException e) {
					System.out.println("Writer Error!");
			}
		} catch (IOException e) {
				System.out.println("File Not Found!");
		}
	}
}
