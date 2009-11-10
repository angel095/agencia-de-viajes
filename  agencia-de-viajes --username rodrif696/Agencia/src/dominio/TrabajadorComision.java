package dominio;

public class TrabajadorComision extends Trabajador{
     
     public TrabajadorComision (){
          
          this.setNombre("sin definir");
          this.setApellido("sin definir");
          this.setCi(0);
          this.setNumTrabajador(0);
          this.setGanancias(0);
          this.setPassword(new char[0]);
          this.setPermisosAdministrativos(false);
     }
     
     public TrabajadorComision (String nombre, String apellido, int ci, int numeroTrabajador, double ganancias, char[] passwordP){
          
          this.setNombre(nombre);
          this.setApellido(apellido);
          this.setCi(ci);
          this.setNumTrabajador(numeroTrabajador);
          this.setGanancias(ganancias);
          this.setPassword(passwordP);
          this.setPermisosAdministrativos(false);
     }
     
     public TrabajadorComision (Trabajador traba){
          
          this.setNombre(traba.getNombre());
          this.setApellido(traba.getApellido());
          this.setCi(traba.getCi());
          this.setNumTrabajador(traba.getNumTrabajador());
          this.setGanancias(traba.getGanancias());
          this.setPassword(traba.getPassword());
          this.setPermisosAdministrativos(traba.getPermisosAdministrativos());
     }
         
     public double calcularGanancias (double montoBase){
          return (super.calcularGanancias(montoBase)-montoBase);
     }
     
     public String toString(){
          return "(Comision)"+super.toString();
     }
}