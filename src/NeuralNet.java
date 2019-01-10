import java.util.ArrayList;



class NeuralNet {


    ArrayList<Neuron> network;


    NeuralNet(ArrayList<ArrayList<String>> Database_rowOriented, ArrayList<ArrayList<String>> Database_colOriented){

        network = new ArrayList<>();

        for (ArrayList<String> row : Database_rowOriented) {

            for (String value : row) {

                boolean already_added = false;

                for (Neuron n : network) if (n.value.equals(value)) already_added = true;

                if (!already_added) network.add(new Neuron(value));

            }
        }

        int hm_columns = Database_colOriented.size();
        int hm_rows    = Database_rowOriented.size();

        for (int col_id = 0; col_id < hm_columns; col_id++) {

            ArrayList<String> col = Database_colOriented.get(col_id);

            for (int row_id = 0; row_id < hm_rows; row_id++) {
                
                ArrayList<String> row = Database_rowOriented.get(row_id);
                
                String current_val = col.get(row_id);
                Neuron current_neuron = null ; for (Neuron n : network) if (n.value.equals(current_val)) current_neuron = n;

                for (String row_value : row) {
                                         
                    Neuron other_neuron = null ; for (Neuron n : network) if (n.value.equals(row_value)) other_neuron = n;
                        
                    current_neuron.connections.add(new Object[]{other_neuron, 1});
                    
                }
                
                for (String col_value : col) {
                                         
                    Neuron other_neuron = null ; for (Neuron n : network) if (n.value.equals(col_value)) other_neuron = n;
                        
                    if (current_val.equals(col_value)) current_neuron.connections.add(new Object[]{other_neuron, 1});
                        
                    else                               current_neuron.connections.add(new Object[]{other_neuron, -1});
                    
                }
            }
        }
        
        for (Neuron n : network) {

            ArrayList<Object[]> to_remove = new ArrayList<>();
            
            for (Object[] connection : n.connections) {
                
                if (((Neuron) connection[0]).value.equals(n.value)) to_remove.add(connection);
                
            }

            for (Object[] connection : to_remove) {

                n.connections.remove(connection);

            }
        }
    }


    ArrayList<Object[]> stimulate(ArrayList<String> stimulants){

        ArrayList<Object[]> stimulated_neurons = new ArrayList<>();

        for (String stimulant : stimulants) {

            for (Neuron neuron : network) {
                
                if (neuron.value.equals(stimulant)) {

                    for (Object[] connection : neuron.connections) {
                        
                        Neuron connected_neuron = (Neuron) connection[0];
                        Integer connection_size = (Integer) connection[1];


                        boolean is_already_added = false;

                        for (Object[] added : stimulated_neurons) if (((Neuron) added[0]).value.equals(connected_neuron.value)) {

                            is_already_added = true;
                            added[1]         = ((Integer) added[1]) + connection_size;

                        }

                        if (!is_already_added) stimulated_neurons.add(new Object[]{connected_neuron, connection_size});

                    }
                }
            }
        }

        return stimulated_neurons;
    }





    static NeuralNet create_from_txt(String txt_path){

        ArrayList<ArrayList<String>> Database_rowOriented = new ArrayList<>();
        ArrayList<ArrayList<String>> Database_colOriented = new ArrayList<>();


        // todo : read the txt and append to row and col oriented arraylists here.


        return new NeuralNet(Database_rowOriented, Database_colOriented);
    }

}


class Neuron{

    String value;
    ArrayList<Object[]> connections;
    Integer stimulation;

    Neuron(String value){

        this.value = value;
        this.connections = new ArrayList<>();
        this.stimulation = 0;

    }

}