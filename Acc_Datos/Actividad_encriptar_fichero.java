import java.io.File;
import java.util.Scanner;

public class Actividad_encriptar_fichero {
    public static void main(String[] args) {
        int o;
        String clave [] = {"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",""};
        String contraseña;
        Scanner sc = new Scanner(System.in);

       try {
        System.out.println("1- Encriptar un fichero\n2-Desencriptar un fichero\n3-Salir");
        o = sc.nextInt();

        if(o != 1 && o != 2 && o != 3 ){
            System.out.println("Debe ingresar una de las opciones del menú");
        }else{
            if(o==1){
                //

            }else if(o==2){
                //
                System.out.println("Ingrese la ruta de su fichero");
                String ruta = sc.nextLine();

                File f = new File(ruta);

                if(f.exists()){
                    if(f.isFile()){
                        //TODO:

                    }else{
                        System.out.println("Es un directorio");
                    }
                }else{
                    System.out.println("El fichero no existe");
                }
            }

        }
       } catch (Exception e) {
            System.out.println("Debe ingresar un número");
       }
        
        sc.close();
    }
}
