package searchengine;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

public class Page {
    private String url, title;
    private HashMap<String, Integer> wordFrequency;

    public Page(List<String> webPage)
    {
        this.url = webPage.get(0).substring(6);
        this.title = webPage.get(1);
        this.wordFrequency = getWord(webPage);
    }

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

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getWebSiteWords(){
        return wordFrequency.keySet();
    }
    
    public Integer getWordFrequency(String word){
        if (!wordFrequency.containsKey(word)){
            return -1;
        }
        return wordFrequency.get(word);
    }
}
