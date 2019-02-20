/**
 * Created by txwyy123 on 19/2/15.
 */
public class LinearHash extends BaseHash implements Hash {

    Entry[] map;

    LinearHash(int size) {
        super();
        this.size = size;
        this.map = new Entry[size];
    }

    @Override
    public void insert(int key, int value) {
        int hashCode = hash(key);
        while(map[hashCode] != null && map[hashCode].key != key)
            hashCode = (hashCode+1)%size;
        map[hashCode] = new Entry(key, value);
        eleCount++;
    }

    @Override
    public int search(int key) throws Exception {
        int hashCode = hash(key);
        while (map[hashCode] != null && map[hashCode].key != key)
            hashCode = (hashCode+1)%size;
        if(map[hashCode] == null)
            throw new Exception("key not found");
        return map[hashCode].value;
    }

    @Override
    public void delete(int key) throws Exception {
        int hashCode = hash(key);
        while(map[hashCode] != null && map[hashCode].key != key)
            hashCode = (hashCode+1)%size;
        if(map[hashCode] == null)
//            throw new Exception("key not found");
            return;
        int next = (hashCode+1)%size;
        while(map[next] != null){
            int h = hash(map[next].key);
            if((next >= (hashCode+1)%size && (h < (hashCode+1)%size || h > next))
                    || (next < (hashCode+1)%size && (h > next && h < (hashCode+1)%size))){
                map[hashCode] = map[next];
                hashCode = next;
            }
            next = (next+1)%size;
        }
        map[hashCode] = null;
        eleCount--;
    }

    @Override
    public void clear() {
        this.map = new Entry[size];
        this.eleCount = 0;
    }
}
