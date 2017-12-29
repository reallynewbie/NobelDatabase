package nobeldatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Builds and imports a database from the Nobel
 * Prize API
 * @author Shawn Gagnon (gagnons23@mymacewan.ca)
 */
public class NobelDatabase {
    ArrayList<Nobelwinner> winners = new ArrayList();
    public  int size = 0;
    
    /**
     * Builds and initializes the database
     * @throws IOException - if something break catch it
     */
    NobelDatabase() throws IOException{
        // Try to download the Data from the website
        try{
        downloadDB();
        }catch(UnknownHostException e){
            System.out.println("No Internet Connection, Trying Local Database");
        }
        // Try to find the local file, works if the file has been downloaded and there is not internet
        try{
        importDB();
        }catch(NoSuchFileException e){
            System.out.println("File not Found, Connect to Internet to Download File");
            System.exit(1);
        }
        
        System.out.println("Import complete");
    }
    
    /**
     * Processes a single entry from the csv file
     * @param currLine line/entry to be processed
     * @param file passed incase of a multi line entry
     */
    public  void processLine(String currLine,Scanner file){
        Scanner line = new Scanner(currLine);
        String[] data = new String[20];
        Nobelwinner winner = new Nobelwinner();
        int count = 0;
        String linea = null,lineb;
        line.useDelimiter(",");
        
        //while entires left to find
        while(line.hasNext()&& count<20){
            
            //data[count]
            linea = line.next();
            //if the value has a comma in it this is needed to capture all data
            if(linea.startsWith("\"")&& !linea.endsWith("\"")){
                boolean x = true;
                //try and find the end of the ""'s
                while(x == true){
                    if(line.hasNext()){
                        lineb = line.next();
                        linea += lineb;
                    }
                    else{
                        x=false;
                }
                    //found the end of the "'s
                    if(linea.endsWith("\"")){
                        x = false;
                    }
                }
                
            }
            
            //System.out.println(linea);
            data[count] = linea;
            count++;
            
        }
        
        //emergency case, data spans 2 lines rest operates same as above
        if(count < 19){
                file.nextLine();
                while(line.hasNext()&& count<20){
            
            //data[count]
                    
                    linea = line.next();
                    if(linea.startsWith("\"")&& !linea.endsWith("\"")){
                        boolean x = true;
                        while(x == true){
                        if(line.hasNext()){
                            lineb = line.next();
                            linea += lineb;
                        }
                        else{
                            x=false;
                        }
                        if(linea.endsWith("\"")){
                            x = false;
                        }
                    }
                
            }
            
            //System.out.println(linea);
            data[count] = linea;
            count++;
            
        }
        }
        //id,firstname,surname,born,died,bornCountry,bornCountryCode,bornCity,diedCountry,diedCountryCode,diedCity,gender,year,category,overallMotivation,share,motivation,name,city,country
        winner.setId(Integer.parseInt(data[0]));
        winner.setFirstName(data[1]);
        //System.out.println(data[2]);
        winner.setLastName(data[2]);
        winner.setBorn(data[3]);
        winner.setDied(data[4]);
        winner.setBornCountry(data[5]);
        winner.setBornCountryCode(data[6]);
        winner.setBornCity(data[7]);
        winner.setDiedCountry(data[8]);
        winner.setDiedCountryCode(data[9]);
        winner.setDiedCity(data[10]);
        winner.setGender(data[11]);
        try{
            winner.setYear(Integer.parseInt(data[12]));
        }
        catch(NumberFormatException ex){
            winner.setYear(0);
            
    }
        winner.setCategory(data[13]);
        winner.setOverallMotivation(data[14]);
        try{
            winner.setShare(Integer.parseInt(data[15]));
        }
        catch(NumberFormatException ex){
             winner.setShare(0);
        }
        winner.setMotivation(data[16]);
        winner.setName(data[17]);
        winner.setCity(data[18]);
        winner.setCountry(data[19]);
        //System.out.println(Arrays.toString(data));
        winners.add(winner);
        size++;
        
    }
    
    
    public  void importDB() throws IOException{
        Scanner inputFile = new Scanner(Paths.get("Database.csv"));
        String currSpot,currLine;
        
        inputFile.nextLine();
        while(inputFile.hasNextLine()){
            currLine = inputFile.nextLine();
            processLine(currLine,inputFile);
        }
    }
    
    
    /**
     * Download the database using url to a csv
     * downloads to working directory, (works best in netbeans ide)
     * @throws IOException possible to throw a io exception, capture it.
     */
    private  void downloadDB() throws IOException{
        URL url = null;
        File currentDirectory = new File(new File(".").getAbsolutePath());
        
        try {
            url = new URL("http://api.nobelprize.org/v1/laureate.csv?");
        } catch (MalformedURLException ex) {
            Logger.getLogger(NobelDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        final String dir = System.getProperty("user.dir");
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(currentDirectory.getCanonicalPath() +  "\\Database.csv");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
}
