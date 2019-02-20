import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by txwyy123 on 19/2/15.
 */
public class ChainedHash extends BaseHash implements Hash{

    List<Entry>[] map;

    ChainedHash(int size){
        super();
        this.map = new ArrayList[size];
        this.size = size;
    }

    @Override
    public void insert(int key, int value) {
        int hashCode = hash(key);
        List<Entry> list = map[hashCode];
        if(list == null)
            list = new ArrayList<>();

        for(Entry k : list){
            if(k.key == key) {
                k.value = value;
                return;
            }
        }

        list.add(new Entry(key, value));
        this.eleCount++;
        map[hashCode] = list;
    }

    @Override
    public int search(int key) throws Exception {
        int hashCode = hash(key);
        List<Entry> list = map[hashCode];
        for(Entry e : list)
            if(e.key == key)
                return e.value;
        throw new Exception("key not found");
    }

    @Override
    public void delete(int key) throws Exception {
        int hashCode = hash(key);
        List<Entry> list = map[hashCode];
        Iterator<Entry> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().key == key) {
                it.remove();
                this.eleCount--;
                return;
            }
        }
//        throw new Exception("key not found");
    }

    @Override
    public void clear() {
        this.map = new ArrayList[size];
        this.eleCount = 0;
    }
}
