import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.nio.file.FileSystems;



static class Runner {
    //Runner(){ System.out.println("Default path set as: " + 
    //  FileSystems.getDefault().getPath("").toAbsolutePath().toString());
    //}
    static void print_path(){ System.out.println("Default path set as: " + 
      FileSystems.getDefault().getPath("").toAbsolutePath().toString());
    }
  
    
    static boolean try_load_network = false;
    
    static String database_path = "pkmn.csv";
  

    static void train_from_csv(Network network){

        Object[] db = parse_csv(database_path);
        
        if (db[0] != null) {
          
            System.out.println("Network training..");
          
            network.update_network((ArrayList) db[0], (ArrayList) db[1]);
            
            System.out.println("Training completed.");
            
            save_network(network);
            
        }      

    }


    static Network get_network(){

        if (try_load_network) {

            ObjectInputStream in;
            try {
                
                FileInputStream fis = new FileInputStream("network");
                BufferedInputStream bis = new BufferedInputStream(fis);
                in = new ObjectInputStream(fis);
                Network obj;
                try {
                  obj = (Network) in.readObject();
                } catch (ClassNotFoundException e) { return new Network(); }
                finally {  
                  fis.close();
                  bis.close();
                  in.close();
                }
                
                System.out.println("Network loaded.");
                return obj;

            } catch (IOException e) { return new Network(); }

        }

        return new Network();

    }


    static void save_network(Network network){

        ObjectOutputStream out;
        try {
          
            FileOutputStream fos = new FileOutputStream(FileSystems.getDefault().getPath("network").toAbsolutePath().toString());
            out = new ObjectOutputStream(fos);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            out.writeObject(network);
            out.flush();

            System.out.println("Network saved.");
            bos.close();
            fos.close();
            out.close();

        } catch (IOException e) { System.out.println("Network save failed."); }// + " : " + e.toString()); }

    }


    static Object[] parse_csv(String csv_path){

        ArrayList<ArrayList<String>> Database_rowOriented, Database_colOriented;
        Database_rowOriented = new ArrayList() ; Database_colOriented = new ArrayList();

        String line; 

        try {
          
            String path = FileSystems.getDefault().getPath(csv_path).toAbsolutePath().toString();

            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {

                String[] row = line.split(",");

                Database_rowOriented.add(new ArrayList(Arrays.asList(row)));

            }

            int hm_rows = Database_rowOriented.get(0).size();

            for (int i = 0; i < hm_rows; i++) Database_colOriented.add(new ArrayList());

            int this_col = -1;
            for (ArrayList<String> col : Database_colOriented) {
                this_col++;

                for (ArrayList<String> row : Database_rowOriented) col.add(row.get(this_col));

            }

            System.out.println(".csv Database loaded.");

            fr.close();
            br.close();

            return new Object[]{

                    Database_rowOriented,
                    Database_colOriented

            } ;


        } catch (IOException e) {

            System.out.println("Database load failed.");
            return new Object[]{null,null} ;
            
        }
        
    }
    
}
