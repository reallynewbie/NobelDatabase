
package nobeldatabase;

/**
 * Data class for country/country code relation for use in the ui
 * @author Shawn Gagnon (gagnons23@mymacewan.ca)
 */
public class Countries {
    private String name;
    private String countryCode;
    
    /**
     * constructor, needs name and code
     * @param n name of country
     * @param code  country code
     */
    Countries(String n, String code){
        this.name = n;
        this.countryCode = code;
    }
    
   
    @Override
    public String toString(){
        return this.name;
    }
    
    
    /**
     * Incase of wanting to clean the name up for use
     * @param newName cleaned name
     */
    public void setName(String newName){
        this.name = newName;
    }
    
    /**
     * gets the current name of the country
     * @return  name of country
     */
    public String getName(){
        return "" + this.name;
    }
    
    
    /**
     * gets the country code
     * @return country code
     */
    public String getCountryCode(){
        return "" + this.countryCode;
    }
}

