package serializer;

import java.io.*;

/**
 * <h1>Serializer </h1>
 *
 * This class uses serialization properties within the project to save and achieve data persistence
 * @author Armando Aquino and Kevin Ariel Merino Peña
 * @version 1
 */
public class Serializer implements Serializable {

    /**
     * Write into a path the object provided
     * @param objetToSerialize object to save
     * @param path where to write, must include extension
     */
    public void write(Object objetToSerialize, String path){
        ObjectOutputStream writer = null;
        try{
            writer = new ObjectOutputStream(new FileOutputStream(path));
            writer.writeObject(objetToSerialize);
            writer.close();
        }catch(NotSerializableException exc){
            System.out.println(exc);
        }catch(FileNotFoundException e){
            e.getMessage();
        }catch(IOException e){
            System.out.println(e);
        }finally{
            if(writer == null){
                System.out.println("El archivo no esta abierto");
            }
        }
    }

    /**
     * this explicit write the string object, diffrent from the first method int the clas that serialize in binary some object
     * @param object object to be saved into a readable txt
     * @param path where to save txt with extension please
     */
    public void writeTXT(Object object, String path){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try{
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            pw.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    /**
     * creates a directory in case this does not exists already
     * @param nameDir name of the directory
     */
    public void makeDir(String nameDir) {
        File directory = new File(nameDir);
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }

    /**
     * allow us to read a binary object that had been serialized before
     * @param path where to read
     * @return the object read
     */
    public Object read(String path){
        ObjectInputStream in = null;
        Object obj = null;
        try{
            in = new ObjectInputStream(new FileInputStream(path));
            obj = in.readObject();
        }catch(EOFException e){
            System.out.println("Fin del archivo");
        }catch(FileNotFoundException e){
            e.getMessage();
        }catch(IOException e){
            System.out.println(e);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            if(in == null){
                System.out.println("Nuevo archivo creado");
                write(obj, path);
            }else{
                try{
                    in.close();
                }catch(IOException e){
                    System.out.println(e);
                }
            }
        }
        return obj;
    }
}
