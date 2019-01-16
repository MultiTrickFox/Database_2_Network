import java.util.Arrays;
Network net;

PFont font;
ArrayList<String> names;

int hm_neurons, hm_rows, hm_cols;
final int neuron_radius = 40;



void settings() {
  
  Runner.print_path();
  Runner.try_load_network = false;
  Runner.database_path    = "pkmn.csv";
  
  net = Runner.get_network();
  
  Runner.train_from_csv(net);  
  
  hm_neurons = net.network.size();
  hm_cols = (int) sqrt(hm_neurons);
  hm_rows = (int) hm_neurons/hm_cols +1;
         
  size(neuron_radius * (hm_rows-1), neuron_radius * (hm_rows-1));
  
}



void setup() {
  
  names = new ArrayList();
  font = createFont("Arial",neuron_radius/4,true);
  textFont(font,neuron_radius/4);
    
  int id = -1; Neuron neuron;
  for (int i = 0; i < hm_rows; i++) 
    for (int j = 0; j < hm_cols; j++)
      { id++; if (id < hm_neurons) names.add(((Neuron) net.network.get(id)).value); }
      
  stroke(50);
 
}



void draw() {
    
  background(0);
  
  display_network();
  
}



void keyPressed() {
    
  net.depolarize();
  
}



void mousePressed() {
  
  int col_id = mouseX * hm_cols / width;
  int row_id = mouseY * (hm_rows-1) / height;
      
  println("Selected:", names.get(row_id * hm_cols + col_id));
      
  net.stimulate(names.get(row_id * hm_cols + col_id), 1);
  net.normalize_activations(net.attributes);
  net.normalize_activations(net.uniques);
  
  for (Neuron n : net.network) // or attributes
  //if (n.value.equals("Bulbasaur") || n.value.equals("Ivysaur") || n.value.equals("Venusaur"))
       println(n.value, "activation:", n.activation);
}



void display_network() {
  
  int netsize = net.network.size();
  
  double[] activations = new double[netsize];
  for (int i = 0 ; i < netsize; i++) activations[i] = net.network.get(i).activation;
  
  float x_coord, y_coord;
  
  int id = -1; Neuron neuron; 
  for (int j = 0; j < hm_cols; j++) {
    for (int i = 0; i < hm_cols; i++) {
      id++; if (id < hm_neurons) {
        
        neuron = net.network.get(id);
                
        if      (neuron.activation < 0) fill(255,0,0,map((float)neuron.activation, 0, -1, 0, 255));
        else if (neuron.activation > 0) fill(0,255,0,map((float)neuron.activation, 0, 1, 0, 255));
        else                            fill(0,0,0,0);
        
        x_coord = i*neuron_radius + neuron_radius/2;
        y_coord = j*neuron_radius + neuron_radius/2;
        
        ellipse(x_coord, y_coord, neuron_radius, neuron_radius);
        
        fill(255); 
        textAlign(CENTER);
        text(names.get(id),x_coord,y_coord);
        
      }
    }
  }
  
}
