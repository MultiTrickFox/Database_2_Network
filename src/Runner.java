import java.util.ArrayList;
import java.util.Arrays;


class Runner {


    static String database_path = "sample_database.txt";

    static String[] stimuli = new String[]{"Marketing", "Car"};
    static double[] ratios = new double[]{0.9, 0.4};


    public static void main(String[] args) {


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

        ArrayList<ArrayList<String>> Database_rowOriented = new ArrayList<>();
        ArrayList<ArrayList<String>> Database_colOriented = new ArrayList<>();

        Database_rowOriented.add(sample_row1);
        Database_rowOriented.add(sample_row2);
        Database_rowOriented.add(sample_row3);
        Database_rowOriented.add(sample_row4);

        Database_colOriented.add(sample_col1);
        Database_colOriented.add(sample_col2);
        Database_colOriented.add(sample_col3);
        Database_colOriented.add(sample_col4);

        ///

        Network network = new Network();
        network.update_network(Database_rowOriented, Database_colOriented);

        ///



        // todo: save neural net here.


        for (Object[] result : network.stimulate(
                   new ArrayList<>(Arrays.asList(
                               stimuli)), ratios))
        {
            String value = (String) result[0];
            double strength = (double) result[1];

            System.out.println("Value: " + value + " Strength: " + strength);
        }


    }


    static Network import_txt(String txt_path){

        // todo 2: check to see if previously created neural net exists, if so, just update it.

        Network network = new Network();


        ArrayList<ArrayList<String>> Database_rowOriented = new ArrayList<>();
        ArrayList<ArrayList<String>> Database_colOriented = new ArrayList<>();



        network.update_network(Database_rowOriented, Database_colOriented);


        // todo : read the txt and append to row and col oriented arraylists here.


        return network;
    }

}
