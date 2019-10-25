
import java.io.*;
import java.util.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

public class NewSyntax {
	public static void main(String[] args) throws IOException {
		
		
		System.out.println("Sentence segmentation: ");
		Document doc = new Document("The text paragraph. Another sentence. Yet another sentence.");
		List<Sentence> sentences = doc.sentences();
		sentences.stream().forEach(System.out::println);
		
		System.out.println("Word Tokenization: ");
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new FileReader("C:\\Users\\Prateik\\Desktop\\My LP1\\syntaxinput.txt"), new CoreLabelTokenFactory(), "");
	     while (ptbt.hasNext()) {
	       //CoreLabel label = ptbt.next();
	       
	    	 System.out.println(ptbt.next());
	     }
	     
	     System.out.println("Parts Of speech: ");
	    Properties props = new Properties();
	     props.setProperty("annotators","tokenize, ssplit, pos,lemma, ner, parse");
	     
	     StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	     Annotation annotation;  
	     String readString = null;
	 	  
	 	 BufferedReader br = new BufferedReader ( new FileReader ( "C:\\Users\\Prateik\\Desktop\\My LP1\\syntaxinput.txt" )  ) ;
	 	 PrintWriter out = new PrintWriter("C:\\Users\\Prateik\\Desktop\\My LP1\\syntaxoutput.txt");
	 	 
	 	 while  (( readString = br.readLine ())  != null)   {

		    	annotation = new Annotation(readString);
		    	pipeline.annotate(annotation); 
		    	pipeline.prettyPrint(annotation, out);
		}
		br.close();
	    System.out.println("Done...");
	     
	     
	    
//	    System.out.println("parsing ");
//	    LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
//	    String sent2 = "This is another sentence.";
//	    TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
//	    List<CoreLabel> rawWords2 = tokenizerFactory.getTokenizer(new StringReader(sent2)).tokenize();
//	    Tree parse = lp.apply(rawWords2);
//	    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//	    tp.printTree(parse);
	}
}
