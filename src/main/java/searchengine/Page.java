package searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

/**
 * The Page class represents a web page and includes the relevant information about the web page.
 *
 */
public class Page implements Comparable<Page>{
    private String url, title;
    
    private HashMap<String, Integer> wordFrequency;
    private int pagerank;

    /**
     * Constructor for the Page class.
     * Initializes a new instance of a Page with its corresponding URL, title, and word frequency.
     *
     * @param webPage A list of strings representing the content of the Page.
     */
    public Page(List<String> webPage) {
        
        validateData(webPage);
        this.url = findUrl(webPage.get(0));
        this.title = webPage.get(1);
        this.wordFrequency = getWord(webPage);
        this.pagerank = 0;
    }

    /**
     * Validates the input parameter of the constructor
     *
     * @param webPage A list of strings representing the content of the Page.
     */
    private void validateData(List<String> webPage) {
        if (webPage == null || webPage.size() < 2) {
            throw new IllegalArgumentException("missing page data");
        }
    }

    /**
     * Counts the frequency of words on the Page.
     * 
     * @param webPage A list of strings representing the content of the Page.
     * @return A HashMap using words as keys and their corresponding frequency as values.
     */
    private HashMap<String, Integer> getWord(List<String> webPage) {
        HashMap<String, Integer> words =  new HashMap<String, Integer>();
        Iterator<String> itr = webPage.iterator();
        itr.next();
        itr.next();
        String word = "";

        while(itr.hasNext()){
            word = itr.next();
            if(words.containsKey(word)){
                words.put(word, words.get(word) + 1);
            }
            else{
                words.put(word, 1);
            }
        }
        return words;
    }

    /**
     * Retrieves the URL of the Page.
     *
     * @return The String value of the URL of the Page.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Extracts the URL from the first line of the Page.
     *
     * @return The String value of the URL of the Page.
     */
    private String findUrl(String firstLine) {
        int prefixLength = 6;
        if (firstLine.length() <= prefixLength) {
            throw new IllegalArgumentException("wrong data format");
        }
        return firstLine.substring(prefixLength);
    }

    /**
     * Retrieves the title of the Page.
     *
     * @return The String value of the title of the Page.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the words present on the Page.
     *
     * @return A Set of strings containing all words found on the Page.
     */
    public Set<String> getWebSiteWords(){
        return wordFrequency.keySet();
    }
    
    /**
     * Retrieves the frequency of a given word on the Page.
     *
     * @param word The word which frequency is to be retrieved.
     * @return The integer value of the frequency of the given word. Returns -1 if the word was not found.
     */
    public Integer getWordFrequency(String word){
        if (!wordFrequency.containsKey(word)){
            return -1;
        }
        return wordFrequency.get(word);
    }

    
    /** 
     * Sets the pageRank-field to the integer value given as an argument in the page-instance.
     * 
     * @param rank
     */
    public void setRank(int rank){
        this.pagerank = rank;
    }

    
    /** 
     * Returns the pageRank-field of an instance of a Page.
     * @return int
     */
    public int getRank()
    {
        return this.pagerank;
    }

    //simple implementation for the usecase of ranking webpages
    //it is still too simple to actually be good
    @Override
    public int compareTo(Page otherPage) {
        return Integer.compare(this.getRank(), otherPage.getRank());
     }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((wordFrequency == null) ? 0 : wordFrequency.hashCode());
        result = prime * result + pagerank;
        return result;
    }

}
