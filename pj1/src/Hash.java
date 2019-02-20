/**
 * Created by txwyy123 on 19/2/15.
 */
public interface Hash {

    void insert(int key, int value) throws Exception;
    int search(int key) throws Exception;
    void delete(int key) throws Exception;
    void clear();
}
