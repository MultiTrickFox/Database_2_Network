import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;



class Main {





    static String database_path = "sample_database.txt";


    static String[] stimuli = new String[]{"Marketing", "Car"};
    static double[] ratios = new double[]{0.9, 0.4};





    public static void main(String[] args) {

        Network network = get_network();

        Object[] db = import_txt(database_path);

        network.update_network((ArrayList) db[0], (ArrayList) db[1]);

        save_network(network);

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


    static Object[] import_txt(String txt_path){


        ArrayList<ArrayList<String>> Database_rowOriented, Database_colOriented;
        Database_rowOriented = new ArrayList<>() ; Database_colOriented = new ArrayList<>();



        //Network network = import_txt(database_path);

        ///

        ArrayList<String> sample_row1 = new ArrayList<>();
        ArrayList<String> sample_row2 = new ArrayList<>();
        ArrayList<String> sample_row3 = new ArrayList<>();
        ArrayList<String> sample_row4 = new ArrayList<>();

        ArrayList<String> sample_col1 = new ArrayList<>();
        ArrayList<String> sample_col2 = new ArrayList<>();
        ArrayList<String> sample_col3 = new ArrayList<>();
        ArrayList<String> sample_col4 = new ArrayList<>();

        ///

        sample_row1.add("Person1");
        sample_row1.add("Car");
        sample_row1.add("Marketing");
        sample_row1.add("Dumb");

        sample_row2.add("Person2");
        sample_row2.add("Nature");
        sample_row2.add("Comp Sci");
        sample_row2.add("Clever");

        sample_row3.add("Person3");
        sample_row3.add("House");
        sample_row3.add("Comp Sci");
        sample_row3.add("Clever");

        sample_row4.add("Person4");
        sample_row4.add("House");
        sample_row4.add("Marketing");
        sample_row4.add("Dumb");

        ///

        sample_col1.add("Person1");
        sample_col1.add("Person2");
        sample_col1.add("Person3");
        sample_col1.add("Person4");

        sample_col2.add("Car");
        sample_col2.add("Nature");
        sample_col2.add("House");
        sample_col2.add("House");

        sample_col3.add("Marketing");
        sample_col3.add("Comp Sci");
        sample_col3.add("Comp Sci");
        sample_col3.add("Marketing");

        sample_col4.add("Dumb");
        sample_col4.add("Clever");
        sample_col4.add("Clever");
        sample_col4.add("Dumb");

        ///



        Database_rowOriented.add(sample_row1);
        Database_rowOriented.add(sample_row2);
        Database_rowOriented.add(sample_row3);
        Database_rowOriented.add(sample_row4);

        Database_colOriented.add(sample_col1);
        Database_colOriented.add(sample_col2);
        Database_colOriented.add(sample_col3);
        Database_colOriented.add(sample_col4);



        // todo : read the txt and append to row and col oriented arraylists here.



        return new Object[]{

                Database_rowOriented,
                Database_colOriented

        } ;
    }

}
