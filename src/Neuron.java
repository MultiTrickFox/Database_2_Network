import java.util.ArrayList;



class Neuron{


    String value;
    ArrayList<Object[]> connections;
    int stimulation;


    Neuron(String value){

        this.value = value;
        this.connections = new ArrayList<>();
        this.stimulation = 0;

    }


}