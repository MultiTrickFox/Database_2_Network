import java.util.ArrayList;
import java.util.Arrays;


class Runner {


    static String database_path = "sample_database.txt";

    static String[] queries = new String[]{"Marketing", "Car"};

                                        // todo : add here importance coefficient


    public static void main(String[] args) {

        //Network network = Network.create_from_txt(database_path);

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
        sample_row1.add("Comp Sci");
        sample_row2.add("Clever");

        sample_row3.add("Person3");
        sample_row3.add("House");
        sample_row1.add("Comp Sci");
        sample_row3.add("Clever");

        sample_row4.add("Person4");
        sample_row4.add("House");
        sample_row1.add("Marketing");
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

        Network network = new Network(Database_rowOriented, Database_colOriented);

        ///



        // todo: save neural net here.



        ArrayList<Object[]> query_results = network.stimulate(new ArrayList<>(Arrays.asList(queries)));

        for (Object[] result : query_results) {

            Neuron result_neuron = (Neuron) result[0];
            Integer strength = (Integer) result[1];

            System.out.println("Neuron: " + result_neuron.value + " Strength: " + strength);

        }
    }
}
