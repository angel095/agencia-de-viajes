package interfaz;

import dominio.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class VentanaGestion extends JFrame implements ActionListener{

    private JPanel panelInicio = null;
    private HandlerClientes panelClientes = null;
    private HandlerViajes panelViajes = null;
    private JPanel panelTrabajadores = null;
    private JMenu menuArchivo = null;
    private JMenu menuOpciones = null;
    private JMenu menuAyuda = null;
    private JMenuBar barra = null;
    private JMenu subMenuGestion = null;
    private Sistema sistema = null;


    public VentanaGestion(Sistema sistemaP){

 super();
 this.setTitle("Gestion");
 this.setSize(900,750);
 this.setContentPane(getPanelInicio());
 this.setResizable(false);
 this.setJMenuBar(getMenuBarra());
 this.sistema = sistemaP;
 this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
 //uso Adapter para no implementar todos los métodos del Listener
 this.addWindowListener(new java.awt.event.WindowAdapter(){
     public void windowClosing(WindowEvent evento){
  int respuesta = JOptionPane.showConfirmDialog(null, " ¿Desea salir?", "Confirmación", JOptionPane.WARNING_MESSAGE);
  if (respuesta == JOptionPane.YES_OPTION){
      System.exit(0);
  }
     }

 });
    }

    private JPanel getPanelInicio(){
 if(panelInicio == null){
     panelInicio = new JPanel();
     panelInicio.setSize(900,750); 
     panelInicio.setLayout(null); 
 }
 return panelInicio;
    }

    private HandlerClientes getPanelClientes(){
 if(panelClientes == null){
     panelClientes = new HandlerClientes();
 }
 return panelClientes;
    }

    private JPanel getPanelTrabajadores(){
 if(panelTrabajadores == null){
     panelTrabajadores = new JPanel();
     panelTrabajadores.setSize(900,750); 
     panelTrabajadores.setLayout(null);     
 }
 return panelTrabajadores;
    }

    private HandlerViajes getPanelViajes(){
 if(panelViajes == null){
      panelViajes = new HandlerViajes();
 }
 return panelViajes;
    }

    private JMenu getMenuArchivo(){
 if(this.menuArchivo == null){
     menuArchivo = new JMenu("Archivo");
     menuArchivo.add(getSubMenuGestion());
     JMenuItem salir = new JMenuItem("Salir");
     salir.addActionListener(new java.awt.event.ActionListener(){
  public void actionPerformed (ActionEvent evento){
      int respuesta = JOptionPane.showConfirmDialog(null, " ¿Desea salir?", "Confirmación", JOptionPane.WARNING_MESSAGE);
      if (respuesta == JOptionPane.YES_OPTION){
   System.exit(0);
      }
  }
     });
     menuArchivo.add(salir);     

 }
 return this.menuArchivo;
    }

    private JMenu getMenuOpciones(){
 if(this.menuOpciones == null){
     menuOpciones = new JMenu("Opciones");
     menuOpciones.add(new JMenuItem("Preferencias"));

 }
 return this.menuOpciones;
    }

    private JMenu getMenuAyuda(){
 if(this.menuAyuda == null){
     menuAyuda = new JMenu("Ayuda");
     menuAyuda.add(new JMenuItem("Manual"));
     menuAyuda.add(new JMenuItem("Acerca de..."));

 }
 return this.menuAyuda;
    }

    private JMenu getSubMenuGestion(){
 if(this.subMenuGestion == null){
     subMenuGestion = new JMenu("Gestion");
     poblarSubMenuGestion(subMenuGestion);
 }
 return this.subMenuGestion;
    }

    private void poblarSubMenuGestion(JMenu subMenu){
 JMenuItem clientes = new JMenuItem("Clientes");
 JMenuItem trabajadores = new JMenuItem("Trabajadores");
 JMenuItem viajes = new JMenuItem("Viajes");

 subMenu.add(clientes);
 subMenu.add(trabajadores);
 subMenu.add(viajes);

 clientes.addActionListener(this);
 trabajadores.addActionListener(this);
 viajes.addActionListener(this);

    }

    private JMenuBar getMenuBarra(){
 if(this.barra == null){
     barra = new JMenuBar();
     barra.add(getMenuArchivo());
     barra.add(getMenuOpciones());
     barra.add(getMenuAyuda());
 }
 return this.barra;
    }
 
    public void actionPerformed(ActionEvent e){

 JMenuItem j = (JMenuItem)e.getSource();
 if(j.getText().equals("Clientes")){
     this.setContentPane(getPanelClientes());
 }
 if(j.getText().equals("Trabajadores")){
     this.setContentPane(getPanelTrabajadores());
 }
 if(j.getText().equals("Viajes")){
     this.setContentPane(getPanelViajes()); 
 } 
    }

    @SuppressWarnings("serial")
    private class HandlerClientes extends JPanel implements Observer, ActionListener, ListSelectionListener{


 private JList listaClientes;
 private JList listaBuscados;
 private JButton agregaListaBuscados;
 private JButton quitaListaBuscados;
 private JList listaRealizados;
 private JTextField nombre;
 private JTextField apellido;
 private JTextField ci;
 private JButton agregar;
 private JButton eliminar;
 private JButton modificar;
 private JButton activos;
 private JButton espera;
 private JLabel listaClientesL;
 private JLabel nombreL;
 private JLabel apellidoL;
 private JLabel ciL;
 private JLabel buscadosL;
 private JLabel realizadosL;
 private DefaultListModel modeloListaClientes;
 private DefaultListModel modeloListaEnEspera;
 private DefaultListModel modeloBuscados;
 private DefaultListModel modeloRealizados;

 public HandlerClientes() {

     super();
     this.setSize(900,750); 
     this.setLayout(null);

     modeloListaClientes = new DefaultListModel();
     modeloBuscados = new DefaultListModel();
     modeloRealizados = new DefaultListModel();
     modeloListaEnEspera = new DefaultListModel();
     cargarModelo(modeloListaClientes, sistema.getEmpresa().getListaClientes());
     cargarModelo(modeloListaEnEspera, sistema.getEmpresa().getListaDeEspera());
     listaClientes = new JList(modeloListaClientes);
     listaClientes.setSize(200,400);
     listaClientes.setLocation(75,85);
     listaClientes.addListSelectionListener(this);
     listaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     this.add(listaClientes);

     listaRealizados = new JList();
     listaRealizados.setSize(200,100);
     listaRealizados.setLocation(500,280);
     this.add(listaRealizados);
     
     listaBuscados = new JList();
     listaBuscados.setSize(200,100);
     listaBuscados.setLocation(500,430);
     this.add(listaBuscados);

     nombre = new JTextField();
     this.add(nombre);
     nombre.setSize(200,25);
     nombre.setLocation(500,100);

     apellido = new JTextField();
     this.add(apellido);
     apellido.setSize(200,25);
     apellido.setLocation(500,150);

     ci = new JTextField();
     this.add(ci);
     ci.setSize(200,25);
     ci.setLocation(500,200);

     agregar = new JButton("Agregar");
     this.add(agregar);
     agregar.setSize(120,25);
     agregar.setLocation(300,225);
     agregar.addActionListener(this);

     eliminar = new JButton("Eliminar");
     this.add(eliminar);
     eliminar.setSize(120,25);
     eliminar.setLocation(300,275);
     eliminar.addActionListener(this);

     modificar = new JButton("Modificar");
     this.add(modificar);
     modificar.setSize(120,25);
     modificar.setLocation(500,550);
     modificar.addActionListener(this);

     activos = new JButton("Ver Activos");
     this.add(activos);
     activos.setSize(110,25);
     activos.setLocation(75,500);
     activos.addActionListener(this);
     activos.setEnabled(false);

     espera = new JButton("Ver Espera");
     this.add(espera);
     espera.setSize(110,25);
     espera.setLocation(175,500);
     espera.addActionListener(this);
     
     agregaListaBuscados = new JButton("+");
     this.add(agregaListaBuscados);
     agregaListaBuscados.setSize(45,25);
     agregaListaBuscados.setLocation(720,430);
     agregaListaBuscados.addActionListener(this);
     
     quitaListaBuscados = new JButton("-");
     this.add(quitaListaBuscados);
     quitaListaBuscados.setSize(45,25);
     quitaListaBuscados.setLocation(720,460);
     quitaListaBuscados.addActionListener(this);

     listaClientesL = new JLabel("Lista de Clientes");
     this.add(listaClientesL);
     listaClientesL.setSize(150,25);
     listaClientesL.setLocation(75,50);

     nombreL = new JLabel("Nombre");
     this.add(nombreL);
     nombreL.setSize(75,25);
     nombreL.setLocation(500,75);

     apellidoL = new JLabel("Apellido");
     this.add(apellidoL);  
     apellidoL.setSize(75,25);
     apellidoL.setLocation(500,125);

     ciL = new JLabel("Cedula");
     this.add(ciL);
     ciL.setSize(75,25);
     ciL.setLocation(500,175);

     buscadosL = new JLabel("Destinos Buscados");
     this.add(buscadosL);
     buscadosL.setSize(200,25);
     buscadosL.setLocation(500,400);

     realizadosL = new JLabel("Viajes Realizados");
     this.add(realizadosL);
     realizadosL.setSize(100,25);
     realizadosL.setLocation(500,250);

     sistema.getEmpresa().addObserver(this);
 }

 public void actionPerformed(ActionEvent evento) {

     if(evento.getSource().getClass().getName().equals("javax.swing.JButton")){

  if((evento.getSource() == agregar) || (evento.getSource() == modificar) ){

      String nombreP = nombre.getText(); String apellidoP = apellido.getText();

      if(nombreP.length() > 0 && apellidoP.length() > 0){

   try{
       int cedulaP = Integer.parseInt(this.ci.getText());

       if(evento.getSource() == agregar){
    Cliente cli = new Cliente(nombreP, apellidoP, cedulaP, 0, new ArrayList <Destino>(), new ArrayList <Destino>());
    if(!sistema.getEmpresa().agregarCliente(cli)){
        JOptionPane.showMessageDialog(null, "ERROR: Ese Cliente ya existe" , "Cliente existente", JOptionPane.ERROR_MESSAGE);
    }            
       }
       else if(evento.getSource() == modificar){
    if (!listaClientes.isSelectionEmpty()){
        Cliente cli = (Cliente)listaClientes.getSelectedValue();
        cli.setNombre(nombreP);
        cli.setApellido(apellidoP);
        cli.setCedula(cedulaP);
    }
    else{
        JOptionPane.showMessageDialog(null, "No hay cliente seleccionado" , "Atención", JOptionPane.INFORMATION_MESSAGE);
    }
       }
   }
   catch(NumberFormatException e){
       JOptionPane.showMessageDialog(null, "ERROR: Ingrese un número valido en la cédula" , "ERROR", JOptionPane.ERROR_MESSAGE);
       this.ci.setText("");
   }
      }else{
   JOptionPane.showMessageDialog(null, "ERROR: Faltan los datos de nombre o apellido" , "ERROR", JOptionPane.ERROR_MESSAGE);
      }
  }
  else if(evento.getSource() == eliminar){
      if (!listaClientes.isSelectionEmpty()){
   int respuesta = JOptionPane.showConfirmDialog(null, " ¿Eliminar este cliente?", "Confirmación", JOptionPane.WARNING_MESSAGE);
   if (respuesta == JOptionPane.YES_OPTION){
       Cliente cli = (Cliente)listaClientes.getSelectedValue();
       sistema.getEmpresa().eliminarCliente(cli);
   }
      }else{
   JOptionPane.showMessageDialog(null, "No hay cliente seleccionado" , "Atención", JOptionPane.INFORMATION_MESSAGE);
      }
  }
  else if(evento.getSource() == activos ){
      cargarModelo(modeloListaClientes, sistema.getEmpresa().getListaClientes());
      listaClientes.setModel(modeloListaClientes);
      cambiarEstadoBotones(true);
  }
  else if(evento.getSource() == espera ){
      cargarModelo(modeloListaEnEspera, sistema.getEmpresa().getListaDeEspera());
      listaClientes.setModel(modeloListaEnEspera);
      cambiarEstadoBotones(false);
  }
  else if(evento.getSource()== agregaListaBuscados){
       
       final JDialog seleccion=new JDialog();
       seleccion.setTitle("Agregar");
       seleccion.setSize(450,500);
       seleccion.setVisible(true);
              
       JPanel destinos=new JPanel();
       destinos.setSize(450,500);
       destinos.setLayout(null);
       seleccion.setContentPane(destinos);
       
       
       DefaultListModel modeloDestinos=new DefaultListModel();
       cargarModelo(modeloDestinos, sistema.getEmpresa().getListaDestinos());
       
       final JList listaDestinos = new JList(modeloDestinos);
       listaDestinos.setSize(200,300);
       listaDestinos.setLocation(100,75);
       destinos.add(listaDestinos);
       listaDestinos.addListSelectionListener(this);
       listaDestinos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
              
       JLabel textoDestinos = new JLabel("Lista de Destinos");
       destinos.add(textoDestinos);
       textoDestinos.setSize(100,25);
       textoDestinos.setLocation(100,25);
       
       JButton aceptar=new JButton("Aceptar");
       aceptar.setSize(100,25);
       aceptar.setLocation(100,405);
       destinos.add(aceptar);
       aceptar.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed (ActionEvent evento){
                 if (!listaDestinos.isSelectionEmpty()){
                      Cliente cli= (Cliente) listaClientes.getSelectedValue();
                      Destino des = (Destino)listaDestinos.getSelectedValue();
                      
                 }
                 else{
                      JOptionPane.showMessageDialog(null, "No hay destino seleccionado" , "Atencion", JOptionPane.INFORMATION_MESSAGE);
                 }
                 
                 
            }
       });
       
       JButton cancelar=new JButton("Cancelar");
       cancelar.setSize(100,25);
       cancelar.setLocation(230,405);
       destinos.add(cancelar);
       cancelar.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed (ActionEvent evento){
                 seleccion.dispose();
                 
            }
       });
       
       
    }
    else if (evento.getSource()== quitaListaBuscados){
         if (!listaBuscados.isSelectionEmpty()){
   int respuesta = JOptionPane.showConfirmDialog(null, "�Eliminar este destino?", "Confirmacion", JOptionPane.WARNING_MESSAGE);
   if (respuesta == JOptionPane.YES_OPTION){
       Destino des = (Destino)listaBuscados.getSelectedValue();
       sistema.getEmpresa().eliminarDestino(des);
   }
      }else{
   JOptionPane.showMessageDialog(null, "No hay destino seleccionado" , "Atencion", JOptionPane.INFORMATION_MESSAGE);
      }
  }
    
       
       
       
  }
     }


 

 private void cambiarEstadoBotones(boolean estaHabilitado){
     agregar.setEnabled(estaHabilitado);
     eliminar.setEnabled(estaHabilitado);
     modificar.setEnabled(estaHabilitado);
     activos.setEnabled(!estaHabilitado);
     espera.setEnabled(estaHabilitado);//si está en espera, los demás botones se desactivan
 }



 public void valueChanged(ListSelectionEvent evento) {
     if (!listaClientes.isSelectionEmpty()){
  Cliente cli = (Cliente)listaClientes.getSelectedValue();
  nombre.setText(cli.getNombre());
  apellido.setText(cli.getApellido());
  ci.setText(""+cli.getCedula());
  cargarModelo(modeloRealizados, cli.getViajesRealizados());
  cargarModelo(modeloBuscados, cli.getDestinosBuscados());
     }

 }

 private <E> void cargarModelo (DefaultListModel modelo, ArrayList<E> datos){

     modelo.clear();
     for(E objeto:datos){
  if (objeto != null){
      modelo.addElement(objeto);
  }
     }     
 }

 public void update(Observable o, Object ar){
     cargarModelo(modeloListaClientes, sistema.getEmpresa().getListaClientes());
     listaClientes.setSelectedIndex(-1);
     listaClientes.setModel(modeloListaClientes);
 }
    }
//---------------------------------------------------------------------------------------------------------    
    @SuppressWarnings("serial")
    private class HandlerViajes extends JPanel implements Observer, ActionListener, ListSelectionListener{


 private JList listaClientes;
 private JList listaBuscados;
 private JList listaRealizados;
 private JTextField nombrec;
 private JTextField apellido;
 private JTextField ci;
 private JButton agregar;
 private JButton eliminarc;
 private JButton modificarc;
 private JButton activos;
 private JButton espera;
 private JLabel listaClientesL;
 private JLabel nombreL;
 private JLabel apellidoL;
 private JLabel ciL;
 private JLabel buscadosL;
 private JLabel realizadosL;
 private DefaultListModel modeloListaClientes;
 private DefaultListModel modeloListaEnEspera;
 private DefaultListModel modeloBuscados;
 private DefaultListModel modeloRealizados;
 //--------------------------------------------
 private JLabel textoNombreAlojamiento;
 private JTextField nombre;
 private JLabel textoTipoAlojamiento;
 private ArrayList <Alojamiento.Tipo> tipo;
 private JComboBox tipoAlojamiento;
 private JLabel textoPension;
 private ArrayList <Alojamiento.Pension> tipop;
 private JComboBox tipoPension;
 private JLabel textoCantidadEstrellas;
 private JSlider cantidadEstrellas;
 private JButton guardar;
 private JButton eliminar;
 private JButton modificar;
 private JLabel textoAlojamientos;
 private JList listaAlojamientos;
      
    public HandlerViajes() {

     super();
     this.setSize(900,750); 
     this.setLayout(null);

          textoNombreAlojamiento=new JLabel("Nombre de alojamiento:");
          textoNombreAlojamiento.setSize(150,25);
          textoNombreAlojamiento.setLocation(75,50);
          this.add(textoNombreAlojamiento);
       
          nombre= new JTextField();
          nombre.setSize(200,25);
          nombre.setLocation(230,50);
          this.add(nombre);
          
          textoTipoAlojamiento=new JLabel("Tipo de alojamiento:");
          textoTipoAlojamiento.setSize(150,25);
          textoTipoAlojamiento.setLocation(75,100);
          this.add(textoTipoAlojamiento);
          
          ArrayList <Alojamiento.Tipo> tipo= new ArrayList <Alojamiento.Tipo>();
          for(Alojamiento.Tipo dato:Alojamiento.Tipo.values()){
               tipo.add(dato);
          }
          tipoAlojamiento= new JComboBox(tipo.toArray());
          tipoAlojamiento.setSize(200,25);
          tipoAlojamiento.setLocation(230,100);
          this.add(tipoAlojamiento);
          
          textoPension=new JLabel("Pension:");
          textoPension.setSize(150,25);
          textoPension.setLocation(75,150);
          this.add(textoPension);
          
          tipop= new ArrayList <Alojamiento.Pension>();
          for(Alojamiento.Pension datop:Alojamiento.Pension.values()){
               tipop.add(datop);
          }
          tipoPension= new JComboBox(tipop.toArray());
          tipoPension.setSize(200,25);
          tipoPension.setLocation(230,150);
          this.add(tipoPension);
          
          textoCantidadEstrellas=new JLabel("Cantidad de Estrellas:");
          textoCantidadEstrellas.setSize(150,25);
          textoCantidadEstrellas.setLocation(75,200);
          this.add(textoCantidadEstrellas);
         
          cantidadEstrellas= new JSlider(JSlider.HORIZONTAL, 1, 7, 1);
          cantidadEstrellas.setMajorTickSpacing(1);
          cantidadEstrellas.setMinorTickSpacing(1);
          cantidadEstrellas.setPaintTicks(true);
          cantidadEstrellas.setPaintLabels(true);
          cantidadEstrellas.setSize(200,50);
          cantidadEstrellas.setLocation(230,200);
          this.add(cantidadEstrellas);
          
          guardar=new JButton("Guardar");
          guardar.setSize(90,25);
          guardar.setLocation(80,300);
          this.add(guardar);
          
          eliminar=new JButton("Eliminar");
          eliminar.setSize(90,25);
          eliminar.setLocation(500,630);
          this.add(eliminar);
          
          modificar=new JButton("Modificar");
          modificar.setSize(90,25);
          modificar.setLocation(600,630);
          this.add(modificar);
          
          textoAlojamientos=new JLabel("Alojamientos");
          textoAlojamientos.setSize(150,25);
          textoAlojamientos.setLocation(500,50);
          this.add(textoAlojamientos);
          
          listaAlojamientos=new JList();
          listaAlojamientos.setSize(300,500);
          listaAlojamientos.setLocation(500,100);
          this.add(listaAlojamientos);
          
          sistema.getEmpresa().addObserver(this);
 }

 public void actionPerformed(ActionEvent evento) {

     if(evento.getSource().getClass().getName().equals("javax.swing.JButton")){

  if((evento.getSource() == agregar) || (evento.getSource() == modificar) ){

      String nombreP = nombre.getText(); String apellidoP = apellido.getText();

      if(nombreP.length() > 0 && apellidoP.length() > 0){

   try{
       int cedulaP = Integer.parseInt(this.ci.getText());

       if(evento.getSource() == agregar){
    Cliente cli = new Cliente(nombreP, apellidoP, cedulaP, 0, new ArrayList <Destino>(), new ArrayList <Destino>());
    if(!sistema.getEmpresa().agregarCliente(cli)){
        JOptionPane.showMessageDialog(null, "ERROR: Ese Cliente ya existe" , "Cliente existente", JOptionPane.ERROR_MESSAGE);
    }            
       }
       else if(evento.getSource() == modificar){
    if (!listaClientes.isSelectionEmpty()){
        Cliente cli = (Cliente)listaClientes.getSelectedValue();
        cli.setNombre(nombreP);
        cli.setApellido(apellidoP);
        cli.setCedula(cedulaP);
    }
    else{
        JOptionPane.showMessageDialog(null, "No hay cliente seleccionado" , "Atención", JOptionPane.INFORMATION_MESSAGE);
    }
       }
   }
   catch(NumberFormatException e){
       JOptionPane.showMessageDialog(null, "ERROR: Ingrese un número valido en la cédula" , "ERROR", JOptionPane.ERROR_MESSAGE);
       this.ci.setText("");
   }
      }else{
   JOptionPane.showMessageDialog(null, "ERROR: Faltan los datos de nombre o apellido" , "ERROR", JOptionPane.ERROR_MESSAGE);
      }
  }
  else if(evento.getSource() == eliminar){
      if (!listaClientes.isSelectionEmpty()){
   int respuesta = JOptionPane.showConfirmDialog(null, " ¿Eliminar este cliente?", "Confirmación", JOptionPane.WARNING_MESSAGE);
   if (respuesta == JOptionPane.YES_OPTION){
       Cliente cli = (Cliente)listaClientes.getSelectedValue();
       sistema.getEmpresa().eliminarCliente(cli);
   }
      }else{
   JOptionPane.showMessageDialog(null, "No hay cliente seleccionado" , "Atención", JOptionPane.INFORMATION_MESSAGE);
      }
  }
  else if(evento.getSource() == activos ){
      cargarModelo(modeloListaClientes, sistema.getEmpresa().getListaClientes());
      listaClientes.setModel(modeloListaClientes);
      cambiarEstadoBotones(true);
  }
  else if(evento.getSource() == espera ){
      cargarModelo(modeloListaEnEspera, sistema.getEmpresa().getListaDeEspera());
      listaClientes.setModel(modeloListaEnEspera);
      cambiarEstadoBotones(false);
  }
  
     }
 }
 

 private void cambiarEstadoBotones(boolean estaHabilitado){
     agregar.setEnabled(estaHabilitado);
     eliminar.setEnabled(estaHabilitado);
     modificar.setEnabled(estaHabilitado);
     activos.setEnabled(!estaHabilitado);
     espera.setEnabled(estaHabilitado);//si está en espera, los demás botones se desactivan
 }



 public void valueChanged(ListSelectionEvent evento) {
     if (!listaClientes.isSelectionEmpty()){
  Cliente cli = (Cliente)listaClientes.getSelectedValue();
  nombre.setText(cli.getNombre());
  apellido.setText(cli.getApellido());
  ci.setText(""+cli.getCedula());
  cargarModelo(modeloRealizados, cli.getViajesRealizados());
  cargarModelo(modeloBuscados, cli.getDestinosBuscados());
     }

 }
 private <E> void cargarLista(DefaultListModel modelo, ArrayList <E> datos){
      
      for (int i=0;i<modelo.size();i++){
           datos.add((E)modelo.get(i));
           
      }
 }
 private <E> void cargarModelo (DefaultListModel modelo, ArrayList<E> datos){

     modelo.clear();
     for(E objeto:datos){
          if (objeto != null){
               modelo.addElement(objeto);
          }
     }     
 }


 public void update(Observable o, Object ar){
     cargarModelo(modeloListaClientes, sistema.getEmpresa().getListaClientes());
     listaClientes.setSelectedIndex(-1);
     listaClientes.setModel(modeloListaClientes);
 }
    }
    }

