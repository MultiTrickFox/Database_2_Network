import java.lang.Math;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;



static class Neuron implements Serializable {


    String value;
    double activation;
    ArrayList<Object[]> connections;
    

    Neuron(String value){

        this.value = value;
        this.connections = new ArrayList();
        this.activation = 0;

    }


}



static class Network implements Serializable {


    int    propogation_depth = 2;
    double propogation_decay = 0.1;

    ArrayList<Neuron>    network;
    ArrayList<Neuron>    uniques;
    ArrayList<Neuron> attributes;


    Network() {

        network    = new ArrayList();
        uniques    = new ArrayList();
        attributes = new ArrayList();

    }


    void update_network(ArrayList<ArrayList<String>> Database_rowOriented, ArrayList<ArrayList<String>> Database_colOriented){

        // fill in total network
      
        for (ArrayList<String> row : Database_rowOriented) {

            for (String value : row) {

                if (!value.equals("")) {

                    boolean already_added = false;

                    for (Neuron neuron : network) if (neuron.value.equals(value)) already_added = true;

                    if (!already_added) network.add(new Neuron(value));

                }
            }
        }
        
        int hm_cols = Database_colOriented.size();
        int hm_rows    = Database_rowOriented.size();
        
        // fill in unique neurons
        
        ArrayList<String> col0 = Database_colOriented.get(0);
        
        for (String val : col0) {
          
             Neuron neuron = null; for (Neuron n : network) if (n.value.equals(val)) neuron = n;
          
             boolean already_added = false;
             
             for (Neuron n : uniques) if (n.value.equals(val)) already_added = true;
             
             if (!already_added) uniques.add(neuron);
          
        }
        
        // fill in attribute neurons
        
        for (int i = 1; i < hm_cols; i++) {
          
             ArrayList<String> attr_col = Database_colOriented.get(i);
             
             for (String val : attr_col) {
               
                  if (!val.equals("")) {
                    
                      Neuron neuron = null; for (Neuron n : network) if (n.value.equals(val)) neuron = n;
          
                      boolean already_added = false;
             
                      for (Neuron n : attributes) if (n.value.equals(val)) already_added = true;
             
                      if (!already_added) attributes.add(neuron);
                    
                  }
             }
        }
        
        // main preprocessing
        
        for (ArrayList<String> row : Database_rowOriented) {
                       
             String unique_val = row.get(0);
             
             ArrayList<String> attrs = new ArrayList();
          
             for (int i = 1; i < hm_cols; i++) {
               
                  String attr_val = row.get(i);
                  
                  if (!attr_val.equals("")) {
                    
                      attrs.add(attr_val);
                    
                      update_connection(unique_val, attr_val, 1);
                      update_connection(attr_val, unique_val, 1);
                      
                  }
               
             }
             
             //for (String attr_val : attrs)
               
             //     for (String attr_val2 : attrs)
             
             //          update_connection(attr_val, attr_val2);
             
             
             for (ArrayList<String> other_row : Database_rowOriented) {
               
                  if (!other_row.equals(row)) {
                    
                      String other_unique_val = other_row.get(0);
                      
                      ArrayList<String> other_attrs = new ArrayList();
                      
                      for (int i = 1; i < hm_cols; i++) {
                       
                           String other_attr = other_row.get(i);
                           if (!other_attr.equals("")) other_attrs.add(other_attr);
                        
                      }
                      
                      double similarity = 0;
                     
                      for (String attr : attrs) {
                           
                           boolean found = false;
                        
                           for (String other_attr : other_attrs) {
                             
                                if (other_attr.equals(attr)) {
                                    
                                    similarity++;
                                    found = true;

                                }
                            
                           }
                           
                           if (!found) similarity--;
                                                   
                      }

                      similarity /= (hm_cols-1); // println(similarity);
                                                                                                            
                      update_connection(unique_val, other_unique_val, similarity);         
                      for (String attr : attrs) for (String other_attr : other_attrs) 
                           update_connection(attr, other_attr, similarity);
                      //for (String attr : attrs) update_connection(attr, other_unique_val, similarity);
                    
                  }
                  
             }
             
        }        
        
        for (Neuron neuron : attributes) {
          
             double[] weights = new double[neuron.connections.size()];
                    
             int i =- 1;
             for (Object[] conn : neuron.connections) {
                  i++;
                  
                  weights[i] = (double) conn[1]; 
                  
             }
                   
             double[] max_vals = array_maxvals(weights);
             
             for (Object[] conn : neuron.connections) {
               
                  if ((double) conn[1] > 0 ) conn[1] = (double) conn[1] / max_vals[0];
                  else                       conn[1] = (double) conn[1] / max_vals[1];
                  
                  // conn[1] = Math.tanh((double) conn[1]);
               
             }

        }     
        
        for (Neuron neuron : uniques) {
          
             double[] weights = new double[neuron.connections.size()];
                    
             int i =- 1;
             for (Object[] conn : neuron.connections) {
                  i++;
                  
                  weights[i] = (double) conn[1]; 
                  
             }
                   
             double[] max_vals = array_maxvals(weights);
             
             for (Object[] conn : neuron.connections) {
               
                  if ((double) conn[1] > 0 ) conn[1] = (double) conn[1] / max_vals[0];
                  else                       conn[1] = (double) conn[1] / max_vals[1];
                  
                  // conn[1] = Math.tanh((double) conn[1]);
               
             }
          
        }
            
              
        // a place for debugging
        
        
         //for (Neuron n : attributes) // or attributes
         //  for (Object[] conn : n.connections) // if ((double) conn[1] > 1)
         //    println(n.value, "connected to", ((Neuron) conn[0]).value, "weight", conn[1]);
 
 
        int hm_neg, hm_pos, hm_zero;
        hm_neg = 0; hm_pos = 0; hm_zero = 0;
        for (Neuron neuron : network) for (Object[] conn : neuron.connections) {
          if ((double) conn[1] > 0)      hm_pos++;
          else if ((double) conn[1] < 0) hm_neg++;
          else                           hm_zero++;
        } println("Weights =>","Hm zero:", hm_zero, "Hm neg:", hm_neg, "Hm pos:", hm_pos);
     
    }
    
    
    void update_connection(String from, String to, double conn_value) {
      
        if (!from.equals(to)) {
      
          Neuron neuron_from = null; for (Neuron n : network) if (n.value.equals(from)) neuron_from = n; 
          Neuron neuron_to = null; for (Neuron n : network) if (n.value.equals(to)) neuron_to = n; 
        
          boolean is_connected = false;
          int conn_index = 0; int i = 0;
  
          for (Object[] connection : neuron_from.connections)
               if (connection[0] == neuron_to) { is_connected = true; conn_index = i; } else i++;
  
           if (is_connected) {
  
               Object[] link = neuron_from.connections.get(conn_index);
               link[1] = ((double) link[1] * (int) link[2] + conn_value) / ((int) link[2] + 1);
               link[2] = (int) link[2]+1;
               // println(from, to, (double) link[1]);
  
            }
  
            else neuron_from.connections.add(new Object[]{neuron_to, conn_value, 1});
            
        }
      
    }
    
    
    void normalize_activations(ArrayList<Neuron> netw){

        double[] activations = new double[netw.size()];

        for (int i = 0; i < netw.size(); i++) activations[i] = netw.get(i).activation;

        double[] norms = array_maxvals(activations);

        for (Neuron neuron : netw)
          if      (neuron.activation < 0) neuron.activation = (double) (neuron.activation / norms[1]);
          else if (neuron.activation > 0) neuron.activation = (double) (neuron.activation / norms[0]);

    }
    

    void stimulate(String stimulant, double stimval){
      
        ArrayList<Object[]> stim_counters;

        Neuron starting_neuron = null; for (Neuron neuron : network) if (neuron.value.equals(stimulant)) starting_neuron = neuron;

        starting_neuron.activation = stimval;
        
        Neuron other_neuron;
        
        //stim_counters = new ArrayList();
        
        //for (Object[] connection : starting_neuron.connections) {
          
        //     stim_counters.add(new Object[]{(Neuron) connection[0], 1});
          
        //}
        
        for (Object[] connection : starting_neuron.connections) {
            
            other_neuron = (Neuron) connection[0];
            other_neuron.activation = starting_neuron.activation * (double) connection[1];
            
        }
         
        // normalize_activations(uniques);     
            
        for (Object[] connection : starting_neuron.connections) {
                      
            other_neuron = (Neuron) connection[0];
            other_neuron.activation = starting_neuron.activation * (double) connection[1];    
            
            for (Object[] conn : other_neuron.connections) {
              
                 //Object[] stim = null; for (Object[] stim_ctr : stim_counters) if (stim_ctr[0].equals(other_neuron)) stim = stim_ctr;
                 double act_val = ((Neuron) conn[0]).activation;
                 ((Neuron) conn[0]).activation += propogation_decay * (double) conn[1] * other_neuron.activation;
                 
                 // ((Neuron) conn[0]).activation = Math.tanh(((Neuron) conn[0]).activation);
              
                 //((Neuron) conn[0]).activation = (((Neuron) conn[0]).activation * (int) stim[1]) + propogation_decay * (double) conn[1] * other_neuron.activation;
                 //stim[1] = (int) stim[1] +1;
                 //((Neuron) conn[0]).activation /= (int) stim[1];
                 
            }
   
        }    

        // for (Neuron n : network) println(n.activation);

    }


    void depolarize(){

        for (Neuron neuron : network) neuron.activation = 0;

    }


    double[] array_maxvals(double[] values){
            
        ArrayList<Double> neg_values = new ArrayList();
        ArrayList<Double> pos_values = new ArrayList();

        double pos_max;
        double neg_max;

        for (double val : values) 
             if      (val > 0) pos_values.add((double) val);
             else if (val < 0) neg_values.add((double) Math.abs(val));

        try { pos_max = Collections.max(pos_values); } catch (Exception e) { pos_max = 1; }
        try { neg_max = Collections.max(neg_values); } catch (Exception e) { neg_max = 1; }

        return new double[]{pos_max, neg_max};

    }


}
