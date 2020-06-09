package serializer;

import java.io.*;

/**
 * <h1>Serializer </h1>
 *
 * @author Armando Aquino and Kevin Ariel Merino Peña
 * @version 1
 */
public class Serializer {

    /**
     *
     * @param objetToSerialize
     * @param path
     */
    public void write(Object objetToSerialize, String path){
        ObjectOutputStream writer = null;
        try{
            writer = new ObjectOutputStream(new FileOutputStream(path));
            writer.writeObject(objetToSerialize);
        }catch(NotSerializableException exc){
            System.out.println(exc);
        }catch(FileNotFoundException e){
            e.getMessage();
        }catch(IOException e){
            System.out.println(e);
        }finally{
            if(writer == null){
                System.out.println("El archivo no esta abierto");
            }else{
                try{
                    writer.close();
                }catch(IOException e){
                    System.out.println(e);
                    System.out.println("NO se puedo cerrar el documento");
                }
            }
        }
    }

    /**
     *
     * @param object
     * @param path
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
     *
     * @param nameDir
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
     *
     * @param path
     * @return
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
