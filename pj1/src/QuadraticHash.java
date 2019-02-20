/**
 * Created by txwyy123 on 19/2/15.
 */
public class QuadraticHash extends BaseHash implements Hash {

    Entry[] map;

    QuadraticHash(int size){
        super();
        this.map = new Entry[size];
        this.size = size;
    }

    @Override
    public void insert(int key, int value) {
        int hashCode = hash(key);
        int i = 0;
        for(i = 0; i < size; i++)
            if(map[(hashCode+i*i)%size] == null || map[(hashCode+i*i)%size].key == key || map[(hashCode+i*i)%size].status == 1)
                break;
        map[(hashCode+i*i)%size] = new Entry(key, value);
        eleCount++;
    }

    @Override
    public int search(int key) throws Exception {
        int hashCode = hash(key);
        int i = 0;
        for(i = 0; i < size; i++)
            if(map[(hashCode+i*i)%size] == null || map[(hashCode+i*i)%size].key == key)
                break;

        if(map[(hashCode+i*i)%size] == null || map[(hashCode+i*i)%size].status != 1)
//            throw new Exception("key not found");
            return -1;
        return map[(hashCode+i*i)%size].value;
    }

    @Override
    public void delete(int key) throws Exception {
        int hashCode = hash(key);
        int i = 0;
        for(i = 0; i < size; i++)
            if(map[(hashCode+i*i)%size] == null || map[(hashCode+i*i)%size].key == key)
                break;

        if(map[(hashCode+i*i)%size] == null || map[(hashCode+i*i)%size].status != 1)
//            throw new Exception("key not found");
            return;
        map[(hashCode+i*i)%size].status = 2;
    }

    @Override
    public void clear() {
        this.map = new Entry[size];
        this.eleCount = 0;
    }
}
