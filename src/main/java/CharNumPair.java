public class CharNumPair implements Comparable<CharNumPair>{
    char charValue;
    int numValue;

    public CharNumPair(char charValue, int numValue) {
        this.charValue = charValue;
        this.numValue = numValue;
    }

    public void addToInteger(){
        this.numValue += 1;
        return;
    }

    public int compareTo(CharNumPair o) {
        return Character.compare(this.charValue, o.charValue);
    }

    @Override
    public String toString() {
        return "{\"" + charValue + "\", " + numValue + '}';
    }
}
