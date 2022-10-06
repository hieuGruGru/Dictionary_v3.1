package sample;

public class Trie {

    public static class TrieNode{
        TrieNode[] children;
        private boolean endOfWord;
        private String meaning;
        private String target;
        String target_Normalize;
        public TrieNode() {
            this.children = new TrieNode[26];
            this.endOfWord = false;
            this.meaning = "";
            this.target = "";
            this.target_Normalize = "";
            for(int i= 0; i < 26; i++) {
                this.children[i] = null;
            }
        }
        public String getMeaning() {
            return meaning;
        }
        public String getTarget() {
            return target;
        }
        public boolean getEndOfWord() {
            return endOfWord;
        }
        public String getTarget_Normalize() {
            return target_Normalize;
        }

        public void setTarget(String target) {
            this.target = target;
        }
        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }
        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }
        public void setTarget_Normalize(String target_Normalize) {
            this.target_Normalize = target_Normalize;
        }
    }

    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }


}