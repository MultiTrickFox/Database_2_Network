import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;



class Main {



    static boolean train_on_dataset = true;
    static String database_path     = "pkmn.csv";


    static String[] stimuli = new String[]{"Normal", "Flying"};
    static double[] ratios  = new double[]{0.9, 0.4};



    public static void main(String[] args) {


        Network network = get_network();


        if (train_on_dataset) {

            Object[] db = parse_csv(database_path);

            network.update_network((ArrayList) db[0], (ArrayList) db[1]);

            save_network(network);

        }


        ArrayList<Object[]> stim_results = network

                .stimulate(
                        new ArrayList<>(Arrays.asList(
                                stimuli)), ratios);

        display_results(stim_results);

        
    }


    static Network get_network(){

        ObjectInputStream in;
        try {

            in = new ObjectInputStream(new FileInputStream("network"));
            Network obj = (Network) in.readObject();
            in.close();
            System.out.println("Network loaded.");
            return obj;

        } catch (IOException | ClassNotFoundException e) { System.out.println("Network created."); return new Network(); }

    }


    static void save_network(Network network){

        ObjectOutputStream out;
        try {

            out = new ObjectOutputStream(new FileOutputStream("network",false));
            out.writeObject(network);
            out.close();
            System.out.println("Network saved.");

        } catch (IOException e) { e.printStackTrace(); System.out.println("Network save failed."); }

    }


    static void display_results(ArrayList<Object[]> stim_results){

        for (Object[] result : stim_results)
        {
            String value = (String) result[0];
            double strength = (double) result[1];

            System.out.println(value + " : " + strength);
        }


        // todo : graph here

    }


    static Object[] parse_csv(String csv_path){


        ArrayList<ArrayList<String>> Database_rowOriented, Database_colOriented;
        Database_rowOriented = new ArrayList<>() ; Database_colOriented = new ArrayList<>();

        String line; String path = FileSystems.getDefault().getPath(csv_path).toAbsolutePath().toString();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {

                String[] row = line.split(",");

                Database_rowOriented.add(new ArrayList<>(Arrays.asList(row)));

            }

            int hm_rows = Database_rowOriented.get(0).size();

            for (int i = 0; i < hm_rows; i++) Database_colOriented.add(new ArrayList<>());

            int this_col = -1;
            for (ArrayList<String> col : Database_colOriented) {
                this_col++;

                for (ArrayList<String> row : Database_rowOriented) col.add(row.get(this_col));

            }

            System.out.println(".csv Database loaded.");

            return new Object[]{

                    Database_rowOriented,
                    Database_colOriented

            } ;


        } catch (IOException e) {

            e.printStackTrace();

            System.out.println("Database load failed.");

            return new Object[]{null, null};

        }
    }


}
