import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static Cruise[] getCruises(Scanner sc) {
        int numCruises = sc.nextInt();
        Cruise[] cruises = new Cruise[numCruises];

        for (int i = 0; i < numCruises; i++) {
            String id = sc.next();
            int arrival = sc.nextInt();

            if (sc.hasNextInt()) {
                int loaders = sc.nextInt();
                int loadTime = sc.nextInt();
                cruises[i] = new BigCruise(id, arrival, loaders, loadTime);
            } else {
                cruises[i] = new Cruise(id, arrival);
            }
        }

        return cruises;
    }

    public static void allocateLoaders(Loader[] loaders, Cruise[] cruises) {
        for (int i = 0; i < cruises.length; i++) {
            int numLoaders = 0;
            int requiredLoaders = cruises[i].getNumLoadersRequired();

            // Reallocate current loaders
            for (Loader loader : loaders) {
                if (loader.serve(cruises[i]) != null) {
                    numLoaders++;
                    System.out.println(loader);
                }

                if (numLoaders == cruises[i].getNumLoadersRequired()) {
                    break;
                }
            }

            // Insufficient loaders
            if (numLoaders < cruises[i].getNumLoadersRequired()) {
                // Allocate new loaders
                int loadersNeeded = cruises[i].getNumLoadersRequired() - numLoaders;
                
                for (int j = 0; j < loadersNeeded; j++) {
                    // Increment size of array
                    loaders = Arrays.copyOf(loaders, loaders.length + 1);

                    int lastIndex = loaders.length - 1;
                    if (loaders.length % 3 == 0) {
                        loaders[lastIndex] = new RecycledLoader(loaders.length);
                    } else {
                        loaders[lastIndex] = new Loader(loaders.length);
                    }
                    loaders[lastIndex].serve(cruises[i]);
                    System.out.println(loaders[lastIndex]);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Cruise[] cruises = getCruises(sc);
        Loader[] loaders = new Loader[0];
        
        allocateLoaders(loaders, cruises);
        
        sc.close();
    }
}
