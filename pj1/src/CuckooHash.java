/**
 * Created by txwyy123 on 19/2/15.
 */
public class CuckooHash extends BaseHash implements Hash {

    Entry[] a1;
    Entry[] a2;
    int[] count1;
    int[] count2;

    CuckooHash(int size){
        super();
        this.a1 = new Entry[size];
        this.a2 = new Entry[size];
        this.count1 = new int[size];
        this.count2 = new int[size];
        this.size = size;
    }

    @Override
    public void insert(int key, int value) throws Exception {
        int hashCode = hash(key);
        int index = 0;
        Entry e = new Entry(key, value);
        Entry oringnal = a1[hashCode];
        while(oringnal != null && ((index == 0 && count1[hashCode] < 3) || (index == 1 && count2[hashCode] < 3))){
            if(index == 0){
                oringnal = a1[hashCode];
                a1[hashCode] = e;
                count1[hashCode]++;
            }else{
                oringnal = a2[hashCode];
                a2[hashCode] = e;
                count2[hashCode]++;
            }
            e = oringnal;
            index = 1-index;
            hashCode = e != null ? (index == 0 ? hash(e.key) : hash1(e.key)) : 0;
        }

        if((index == 0 && count1[hashCode] >= 3) || (index == 1 && count2[hashCode] >= 3))
            throw new Exception("infinite loop found");

        if(index == 0)
            a1[hashCode] = e;
        else
            a2[hashCode] = e;
        eleCount++;
    }

    @Override
    public int search(int key){
        int hashCode = hash(key);
        if(a1[hashCode] != null && a1[hashCode].key == key)
            return a1[hashCode].value;
        hashCode = hash1(key);
        if(a2[hashCode] != null && a2[hashCode].key == key)
            return a2[hashCode].value;
//        throw new Exception("key not found");
        return -1;
    }

    @Override
    public void delete(int key) throws Exception {
        int hashCode = hash(key);
        if(a1[hashCode] != null && a1[hashCode].key == key) {
            a1[hashCode] = null;
            eleCount--;
        }else {
            hashCode = hash1(key);
            if (a2[hashCode] != null && a2[hashCode].key == key) {
                a2[hashCode] = null;
                eleCount--;
            }
//            else
//                throw new Exception("key not found");
        }
    }

    @Override
    public void clear() {
        this.a1 = new Entry[size];
        this.a2 = new Entry[size];
        this.count1 = new int[size];
        this.count2 = new int[size];
        this.eleCount = 0;
    }
}
