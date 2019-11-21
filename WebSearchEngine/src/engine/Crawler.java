package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    static Queue<String> queue = new LinkedList<>();
    
    static Set<String> marked = new HashSet<>();
    
    static String regex = "(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
   
//   static String root = "http://www.uwindsor.ca/";
    
    public Crawler(String root) throws IOException {
    	System.out.println("Crawling started...");
    	boolean flag = false;
        URL url;
        BufferedReader reader = null;
        
        queue.add(root);
        while(!queue.isEmpty()){ 
            String s = queue.poll();
            if(marked.size()>10000)
            	return;

            while(!flag){ 
                try{
	                url = new URL(s);
	                reader = new BufferedReader(new InputStreamReader(url.openStream()));
	                flag = true;
                }catch(MalformedURLException e){
                    System.out.println("\nMalformedURL : "+s+"\n");
                    s = queue.poll();
                    flag = false;
                }catch(IOException e){
                    System.out.println("\nIOException for URL : "+s+"\n");
                    s = queue.poll();
                    flag = false;
                }
            }         
            
            StringBuilder sb = new StringBuilder();
            
            while((s = reader.readLine())!=null){
                sb.append(s);
            }
            s = sb.toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            
            while(matcher.find()){
                String w = matcher.group(); 
                
                if(!marked.contains(w)){
                    marked.add(w);
                    queue.add(w);
                }
            } 
            
        }
        System.out.println("Crawling complete.");
    }
    
    public Set<String> getMarked(){
    	return marked;
    }
    
   
    
    public static void main(String[] args){

    }
}
