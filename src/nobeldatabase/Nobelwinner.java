package nobeldatabase;

/**
 * This is a basic data structure for integration with the Nobel Peace Prize API
 * @author Shawn Gagnon (Gagnons23@mymacewan.ca)
 */
public class Nobelwinner {
    private int year;
    private String category;
    private String overallMotivation;
    private int id;
    private String firstName;
    private String lastName;
    private String motivation;
    private int share;
    private String born;
    private String died;
    private String bornCountry;
    private String bornCountryCode;
    private String bornCity;
    private String diedCountryCode;
    private String diedCountry;
    private String diedCity;
    private String gender;
    private String name;
    private String city;
    private String country;
    
    //investigate a date 
    
    /**
     * Constructor of a singular Winner entry
     
     */
    Nobelwinner(){
       
                
                
    }
    
    @Override
    public String toString(){
       StringBuilder stringmeup = new StringBuilder();
       stringmeup.append(id);
       stringmeup.append(",");
       stringmeup.append(firstName);
       stringmeup.append(",");
       stringmeup.append(lastName);
       stringmeup.append(",");
       stringmeup.append(country);
       stringmeup.append(",");
       stringmeup.append(category);
       
       return stringmeup.toString();
    }
    
    
    /**
     * Gets and returns the year of award
     * @return integer of year
     */
    public int getYear(){
        return this.year;
    }
    
    /**
     * Gets and returns the category of award
     * @return category
     */
    public String getCategory(){
        return "" + this.category;
    }
    
    /**
     * Gets and returns the Overall motivation of the award
     * @return overallMotivation
     */
    public String getOverallMotivation(){
        return "" + this.overallMotivation;
    }
    
    /**
     * Gets and returns the id of the award
     * @return id
     */
    public int getId(){
        return this.id;
    }
    
    
    /**
     * Gets and returns the first name of the award winner
     * @return firstname
     */
    public String getFirstName(){
        return "" + this.firstName;
    }
    
    
    /**
     * Gets and returns the last name of the award winner
     * @return lastName
     */
    public String getLastName(){
        return "" + this.lastName;
    }
    
    /**
     * Gets and returns the motivation
     * @return motivation
     */
    public String getMotivation(){
        return "" + this.motivation;
    }
    
    /**
     * Gets and returns the share of the project
     * @return share
     */
    public int getShare(){
        return this.share;
    }
    
    /**
     * Gets and returns the birthdate of the award winner
     * @return birthday
     */
    public String getBorn(){
        return "" + this.born;
    }
    
    
    /**
     * Gets and returns the death date of the award winner
     * @return deathdate
     */
    public String getDied(){
        return "" + this.died;
    }
    
    /**
     * Gets and returns the birth country of winner
     * @return borncountry
     */
    public String getBornCountry(){
        return "" + this.bornCountry;
    }
    
    /**
     * Gets and returns the birth city of winner
     * @return birthcity
     */
    public String getBornCity(){
        return "" + this.bornCity;
    }
    
    /**
     * Gets and returns the birth country code XX
     * @return countrycode
     */
    public String getBornCountryCode(){
        return "" + this.bornCountryCode;
    }
    
    
    /**
     * Gets and returns the death country code
     * @return countrycode
     */
    public String getDiedCountryCode(){
        return "" + this.diedCountryCode;
    }
    
    /**
     * Gets and returns the death country of award winner
     * @return deathcountry
     */
    public String getDiedCountry(){
        return "" + this.diedCountry;
    }
    
    /**
     * Gets and returns the death city of the winner
     * @return deathcity
     */
    public String getDiedCity(){
        return "" + this.diedCity;
    }
    
    /**
     * Gets and returns the gender of the award winner
     * @return  gender
     */
    public String getGender(){
        return "" + this.gender;
    }
    
    /**
     * Gets and returns the name of the institution of the winner
     * @return institutionname
     */
    public String getName(){
        return "" + this.name;
    }
    
    /**
     * Gets and returns the city of the insitition of the winner
     * @return city
     */
    public String getCity(){
        return "" + this.city;
    }
    
    
    /**
     * Gets and returns the country of the institution of the winner
     * @return country
     */
    public String getCountry(){
        return "" + this.country;
    }
    
    
    
    /**
     * Sets the year for class
     * @param year year
     */
    public void setYear(int year){
        this.year = year;
    }
    
    /**
     * sets the category for class
     * @param category category 
     */
    public void setCategory(String category){
        this.category = category;
    }
    
    /**
     * sets the overall motivation for the award
     * @param om overallMotivation
     */
    public void setOverallMotivation(String om){
        this.overallMotivation = om;
    }
    
    /**
     * sets the id for the winner
     * @param ID id num
     */
    public void setId(int ID){
        this.id = ID;
    }
    
    /**
     * sets the first name of the winner
     * @param name first name
     */
    public void setFirstName(String name){
        this.firstName = name;
    }
    
    /**
     * sets the last name of the winner
     * @param name last name
     */
    public void setLastName(String name){
        this.lastName = name;
    }
    
    /**
     * sets the motivation for the work
     * @param moti motiviation
     */
    public void setMotivation(String moti){
        this.motivation = moti;
    }
    
    /**
     * sets the share for the winner
     * @param share the share
     */
    public void setShare(int share){
        this.share = share;
    }
    
    /**
     * sets the birthday of the winner
     * @param born birthday
     */
    public void setBorn(String born){
        this.born = born;
    }
    
    /**
     * sets the death day of the winner
     * @param died death day
     */
    public void setDied(String died){
        this.died = died;
    }
    
    /**
     * sets the birthcountry of the winner
     * @param bc birth country
     */
    public void setBornCountry(String bc){
        this.bornCountry = bc;
    }
    
    /**
     * birth countries code
     * @param bcc 
     */
    public void setBornCountryCode(String bcc){
        this.bornCountryCode = bcc;
    }
    
    /**
     * birth city of the winner
     * @param bc birth city
     */
    public void setBornCity(String bc){
        this.bornCity = bc;
    }
    
    /**
     * country code of death
     * @param dcc death country code
     */
    public void setDiedCountryCode(String dcc){
        this.diedCountryCode = dcc;
    }
    
    /**
     * death country of the winner
     * @param dc death country
     */
    public void setDiedCountry(String dc){
        this.diedCountry = dc;
    }
    
    /**
     * death city
     * @param dc death city
     */
    public void setDiedCity(String dc){
        this.diedCity = dc;
    }
    
    /**
     * set the gender of winner
     * @param gender 
     */
    public void setGender(String gender){
        this.gender = gender;
    }
    
    /**
     * Name of winners employer
     * @param name 
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * name of employers city
     * @param city 
     */
    public void setCity(String city){
        this.city = city;
    }
    
    /**
     * name of employers country
     * @param country 
     */
    public void setCountry(String country){
        this.country = country;
    }
}
