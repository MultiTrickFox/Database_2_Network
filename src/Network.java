import java.io.Serializable;
import java.util.ArrayList;



class Network implements Serializable {

    static int propogation_depth = 2;
    static double decay_ratio = 0.8;


    ArrayList<Neuron> network;


    Network() {

        network = new ArrayList<>();

    }


    void update_network(ArrayList<ArrayList<String>> Database_rowOriented, ArrayList<ArrayList<String>> Database_colOriented){

        for (ArrayList<String> row : Database_rowOriented) {

            for (String value : row) {

                boolean already_added = false;

                for (Neuron neuron : network) if (neuron.value.equals(value)) already_added = true;

                if (!already_added) network.add(new Neuron(value));

            }
        }

        int hm_columns = Database_colOriented.size();
        int hm_rows    = Database_rowOriented.size();

        for (int row_id = 0; row_id < hm_rows; row_id++) {

            System.out.println("training: data " + row_id);

            ArrayList<String> row_data = Database_rowOriented.get(row_id);

            for (int col_id = 0; col_id < hm_columns; col_id++) {
                
                ArrayList<String> col_data = Database_colOriented.get(col_id);


                String current_val = row_data.get(col_id);
                Neuron current_neuron = null ; for (Neuron neuron : network) if (neuron.value.equals(current_val)) current_neuron = neuron;


                for (String row_val : row_data) {

                    if (!row_val.equals(current_val)) {

                        Neuron other_neuron = null; for (Neuron neuron : network) if (neuron.value.equals(row_val)) other_neuron = neuron;
                        boolean is_connected = false;
                        int conn_index = 0; int i = 0;

                        for (Object[] connection : current_neuron.connections) if (connection[0] == other_neuron)

                            { is_connected = true; conn_index = i; } else i++;

                        if (is_connected) {

                            Object[] link = current_neuron.connections.get(conn_index);
                            link[1] = (double) link[1] + 1;

                        }

                        else current_neuron.connections.add(new Object[]{other_neuron, 1.0});

                    }
                }


                int this_row_id = -1;
                for (String col_val : col_data) {
                    this_row_id ++;

                    double similarity_value;

                    if (current_val.equals(col_val)) similarity_value =  1.0;
                    else                             similarity_value = -1.0;

                    ArrayList<String> other_row_data = Database_rowOriented.get(this_row_id);

                    for (String other_row_val : other_row_data) {

                        if (!other_row_val.equals(col_val)) {

                            Neuron other_neuron = null ; for (Neuron neuron : network) if (neuron.value.equals(other_row_val)) other_neuron = neuron;
                            boolean is_connected = false;
                            int conn_index = 0; int i = 0;

                            for (Object[] connection : current_neuron.connections) if (connection[0] == other_neuron)

                            { is_connected = true; conn_index = i; } else i++;

                            if (is_connected) {

                                Object[] link = current_neuron.connections.get(conn_index);
                                link[1] = (double) link[1] + similarity_value;

                            }

                            else current_neuron.connections.add(new Object[]{other_neuron, similarity_value});

                        }
                    }
                }
            }
        }
    }


    ArrayList<Object[]> stimulate(ArrayList<String> stimulants, double[] propogations){

        ArrayList<Object[]> stimulation_results = new ArrayList<>();

        int s_id = -1;
        for (String stimulant : stimulants) {
            s_id++;

            for (Neuron neuron : network) {
                
                if (neuron.value.equals(stimulant)) {

                    ArrayList<Object[]> to_connect = new ArrayList<>();

                    to_connect.addAll(neuron.connections);

                    for (int r = 0; r < propogation_depth-1; r++) {

                        ArrayList<Object[]> recursive_connect = new ArrayList<>();


                        for (Object[] connection : to_connect) {

                            double decayed, ratio; ratio = (r+1) * decay_ratio;
                            if ((double) connection[1] > 0) decayed = Math.pow((double) connection[1], ratio);
                            else                            decayed = -Math.pow(-((double) connection[1]), ratio);

                            recursive_connect.add(new Object[]{connection[0], decayed});

                        }

                        to_connect.addAll(recursive_connect);

                    }


                    for (Object[] connection : to_connect) {
                        
                        Neuron connected_neuron = (Neuron) connection[0];
                        double connection_size = (double) connection[1] * propogations[s_id];

                        boolean is_already_added = false;

                        for (Object[] added : stimulation_results) if (added[0].equals(connected_neuron.value)) {

                            is_already_added = true;
                            added[1]         = ((double) added[1]) + connection_size;

                        }

                        if (!is_already_added) stimulation_results.add(new Object[]{connected_neuron.value, connection_size});

                    }
                }
            }
        }


        for (Neuron neuron : network) neuron.stimulation = 0;


        for (int k = 0; k < 20_000; k++) {

            for (int i = 0; i < stimulation_results.size()-1; i++) {

                if ((double) stimulation_results.get(i)[1] < (double) stimulation_results.get(i+1)[1]) {

                    Object[] temp = stimulation_results.get(i);
                    stimulation_results.set(i, stimulation_results.get(i+1));
                    stimulation_results.set(i+1, temp);

                }
            }
        }

        ArrayList<Object[]> to_remove = new ArrayList<>();

        for (String stim : stimulants) for (Object[] res : stimulation_results) if (stim.equals(res[0])) to_remove.add(res);

        for (Object[] rm : to_remove) stimulation_results.remove(stimulation_results.indexOf(rm));

        return stimulation_results;
    }


}