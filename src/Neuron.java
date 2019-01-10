import java.io.Serializable;
import java.util.ArrayList;



class Neuron implements Serializable {


    String value;
    ArrayList<Object[]> connections;
    int stimulation;


    Neuron(String value){

        this.value = value;
        this.connections = new ArrayList<>();
        this.stimulation = 0;

    }


}