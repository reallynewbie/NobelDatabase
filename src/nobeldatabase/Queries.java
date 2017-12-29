
package nobeldatabase;

import java.util.ArrayList;

/**
 * Query handler for Nobel database
 * @author Shawn Gagnon (gagnons23@mymacewan.ca)
 */
public class Queries {
    int id, lowYear, highYear;
    String category, countryCode, gender, name;
    
    /**
     * Constructor used for running queries
     * @param runId ID to search, -1 if not using
     * @param cat category to search for, null if not using
     * @param yearLow low end of a year search -1 if not
     * @param yearHigh high end of a year search -1 if not
     * @param codeCountry search by birth country using x code - null if not
     * @param Gender search by specific gender null if not using
     */
    Queries(int runId,String cat,int yearLow, int yearHigh,String codeCountry, String Gender, String Name){
        id = runId;
        category = cat;
        lowYear = yearLow;
        highYear = yearHigh;
        countryCode = codeCountry;
        gender = Gender;
        name = Name;
    }
    
    /**
     * Run the query or queries
     * @param winners the Nobel database
     * @return the arraylist of winners that meet your search
     */
    public ArrayList<Nobelwinner>  runQueries(ArrayList<Nobelwinner> winners){
        ArrayList<Nobelwinner> output = new ArrayList(), input = winners;
        //if search by id
        if(this.id != -1){
            output  = queryById(input);
            input = output;
        }
        //if search by category
        if(this.category != null){
            output = queryByCategory(input);
            input = output;
        }
        //low year given
        if(this.lowYear != -1){
            //but no high year given
            if(this.highYear == -1){
                highYear = 5000;
            }
            output = queryByYear(input);
            input = output;
        }
        //high year given with no low year
        else if(this.highYear != -1){
            output = queryByYear(input);
            input = output;
        }
        //country code given
        if(this.countryCode!=null){
            output = queryByCountry(input);
            input = output;
        }
        //gender given
        if(this.gender !=null){
            output = queryByGender(input);
            input = output;
        }
        //name given
        if(this.name !=null){
            output = queryByName(input);
            input = output;
        }
        
        return output;
    }
    
    
    /**
     * Queries for a particular id in database
     * @param winners current list of winners to search against
     * @return list of results of the search
     */
    private ArrayList<Nobelwinner> queryById(ArrayList<Nobelwinner> winners){
       ArrayList<Nobelwinner> output = new ArrayList();
        //run the current winner slist
        for(Nobelwinner winner:winners){
            //found a match
            if(winner.getId() == id){
                output.add(winner);
            }
        }

        return output;
    }
    
    
   /**
     * Queries for a particular category in database
     * @param winners current list of winners to search against
     * @return list of results of the search
     */
    private ArrayList<Nobelwinner> queryByCategory(ArrayList<Nobelwinner> winners){
        ArrayList<Nobelwinner> output = new ArrayList();
        //run the current list
        for(Nobelwinner winner:winners){
           //found a match
            if(winner.getCategory().equals(this.category)){
                output.add(winner);
            }
        }
        
        return output;
    }
    
     /**
     * Used to populate a list of all countries in the DB for a drop down menu
     * @param winners - array list of the database
     * @return output, a arraylist of all countries in DB
     */
    public ArrayList<Countries> getCountries(ArrayList<Nobelwinner> winners){
        ArrayList<Countries> output = new ArrayList();
        ArrayList<String> out = new ArrayList();
        for(Nobelwinner winner:winners){
           //Sfound a match
            if(!out.contains(winner.getBornCountry())){
                out.add(winner.getBornCountry());
                output.add(new Countries(winner.getBornCountry(),winner.getBornCountryCode()));
            }
        }
        
        return output;
    }
    
    /**
     * Searches based on the given last name
     * @param winners the arraylist to search through
     * @return the arraylist containing entries with the name to be searched
     * Matthew Craner
     */
    private ArrayList<Nobelwinner> queryByName(ArrayList<Nobelwinner> winners){
        ArrayList<Nobelwinner> output = new ArrayList();
        //run the current list
        for(Nobelwinner winner:winners){
           //Sfound a match
            if(winner.getLastName().toLowerCase().contains(name.toLowerCase())){
                output.add(winner);
            }
        }
        
        return output;
    }
    
    /**
     * Queries for a particular year or range of years
     * @param winners current list of winners to search against
     * @return list of results of the search
     */
    private ArrayList<Nobelwinner> queryByYear(ArrayList<Nobelwinner> winners){
        ArrayList<Nobelwinner> output = new ArrayList();
        //run the current list
        for(Nobelwinner winner:winners){
           //Sfound a match
            if(winner.getYear() >= lowYear && winner.getYear() <= highYear){
                output.add(winner);
            }
        }
        
        return output;
    }
    
    /**
     * Queries for a particular birth country
     * @param winners current list of winners to search against
     * @return list of results of the search
     */
    private ArrayList<Nobelwinner> queryByCountry(ArrayList<Nobelwinner> winners){
        ArrayList<Nobelwinner> output = new ArrayList();
        //run the current list
        for(Nobelwinner winner:winners){
           //Sfound a match
            if(winner.getBornCountryCode().equals(this.countryCode)){
                output.add(winner);
            }
        }
        
        return output;
    }

    /**
     * Queries for a particular gender
     * @param winners current list of winners to search against
     * @return list of results of the search
     */
    private ArrayList<Nobelwinner> queryByGender(ArrayList<Nobelwinner> winners) {
        ArrayList<Nobelwinner> output = new ArrayList();
        //run the current list
        for(Nobelwinner winner:winners){
           //Sfound a match
            if(winner.getGender().equals(this.gender)){
                output.add(winner);
            }
        }
        
        return output;
    }
}

    