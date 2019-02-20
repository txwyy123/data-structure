/**
 * Created by txwyy123 on 19/2/15.
 */
public class BaseHash {

    int size;
    int eleCount;

    BaseHash(){
        this.eleCount = 0;
        this.size = 0;
    }

    int hash(int key){
        return key%size;
    }

    int hash1(int key){
        return (key/size)%size;
    }

    protected class Entry {
        int key;
        int value;
        int status;//0-free 1-used 2-cleared

        Entry(int key, int value){
            this.key = key;
            this.value = value;
            this.status = 1;
        }
    }
}


