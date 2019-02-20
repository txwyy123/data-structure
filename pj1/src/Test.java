import java.util.Random;

/**
 * Created by txwyy123 on 19/2/15.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Test test = new Test();

        for(int i = 1000; i <= 3000; i+=200) {
            test.testInsert(new ChainedHash(i), i);
            test.testInsert(new LinearHash(i), i);
            test.testInsert(new CuckooHash(i), i);
            test.testInsert(new QuadraticHash(i), i);
            System.out.println();
        }

        for(int i = 1000; i <= 3000; i+=200) {
            test.testSearch(new ChainedHash(i), i);
            test.testSearch(new LinearHash(i), i);
            test.testSearch(new CuckooHash(i), i);
            test.testSearch(new QuadraticHash(i), i);
            System.out.println();
        }

        for(int i = 1000; i <= 3000; i+=200) {
            test.testDelete(new ChainedHash(i), i);
            test.testDelete(new LinearHash(i), i);
            test.testDelete(new CuckooHash(i), i);
            test.testDelete(new QuadraticHash(i), i);
            System.out.println();
        }

    }

    void testInsert(Hash hash, int size) {
        long total = 0;
        int count = 1000;
        for(int i = 0; i < 100; i++) {
            Random random = new Random();
            int[] array = new int[count];
            for(int j = 0; j < count; j++)
                array[j] = random.nextInt(10000);
            hash.clear();
            long time = System.currentTimeMillis();
            for (int num : array)
                try {
                    hash.insert(num, num);
                } catch (Exception e) {
                }
            total += System.currentTimeMillis() - time;
        }
        System.out.println(hash.getClass().getName()+" for "+count+" insertion in "+size+" size map: " + total);
    }

    void testSearch(Hash hash, int size) throws Exception {
        long total = 0;
        int count = 1000;
        for(int i = 0; i < 100; i++) {
            Random random = new Random();
            int[] array = new int[count];
            for(int j = 0; j < count; j++)
                array[j] = random.nextInt(10000);
            hash.clear();
            for (int num : array)
                try {
                    hash.insert(num, num);
                } catch (Exception e) {
                }

            long time = System.currentTimeMillis();
            for(int num : array)
                hash.search(num);
            total += System.currentTimeMillis() - time;
        }
        System.out.println(hash.getClass().getName()+" for "+count+" search in "+size+" size map: " + total);
    }

    void testDelete(Hash hash, int size) throws Exception {
        long total = 0;
        int count = 1000;
        for(int i = 0; i < 100; i++) {
            Random random = new Random();
            int[] array = new int[count];
            for(int j = 0; j < count; j++)
                array[j] = random.nextInt(10000);
            hash.clear();
            for (int num : array)
                try {
                    hash.insert(num, num);
                } catch (Exception e) {
                }

            long time = System.currentTimeMillis();
            for(int num : array)
                hash.delete(num);
            total += System.currentTimeMillis() - time;
        }
        System.out.println(hash.getClass().getName()+" for "+count+" delete in "+size+" size map: " + total);
    }

    void testChain() throws Exception {
        ChainedHash hash = new ChainedHash(13);
        hash.insert(18, 18);
        hash.insert(41, 41);
        hash.insert(22, 22);
        hash.insert(44, 44);
        hash.insert(59, 59);
        hash.insert(32, 32);
        hash.insert(31, 31);
        hash.insert(73, 73);
        hash.insert(73, 72);
        System.out.println(hash.search(18));
        hash.delete(18);
        hash.delete(44);
        hash.delete(73);
//        System.out.println(chain.search(73));
//        chain.delete(73);
        System.out.println(hash.search(73));
        System.out.println();
    }

    void testLinear() throws Exception {
        LinearHash hash = new LinearHash(13);
        hash.insert(18, 18);
        hash.insert(41, 41);
        hash.insert(22, 22);
        hash.insert(44, 44);
        hash.insert(59, 59);
        hash.insert(32, 32);
        hash.insert(31, 31);
        hash.insert(73, 73);
        hash.insert(73, 72);
        System.out.println(hash.search(18));
        hash.delete(18);
        hash.delete(41);
        hash.delete(22);
        hash.delete(44);
        hash.delete(59);
        hash.delete(32);
        hash.delete(31);
        hash.delete(73);
//        System.out.println(chain.search(73));
//        chain.delete(73);
        System.out.println(hash.search(73));
        System.out.println();
    }

    void testCuckoo() throws Exception {
        CuckooHash hash = new CuckooHash(11);
        hash.insert(12, 12);
        hash.insert(26, 26);
        hash.insert(92, 92);
        hash.insert(23, 23);
        hash.insert(28, 28);
        hash.insert(94, 94);
        hash.insert(15, 15);
        System.out.println(hash.search(12));
        hash.delete(26);
        hash.delete(23);
        hash.delete(94);
        System.out.println(hash.search(15));
        System.out.println();
    }

    void testQuadratic() throws Exception {
        QuadraticHash hash = new QuadraticHash(13);
        hash.insert(18, 18);
        hash.insert(41, 41);
        hash.insert(22, 22);
        hash.insert(44, 44);
        hash.insert(59, 59);
        hash.insert(32, 32);
        hash.insert(31, 31);
        hash.insert(73, 73);
        hash.insert(73, 72);
        System.out.println(hash.search(18));
        hash.delete(41);
        hash.delete(22);
        hash.delete(31);
        System.out.println(hash.search(41));
        System.out.println();
    }
}