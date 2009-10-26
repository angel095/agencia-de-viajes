package interfaz;

import dominio.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;

public class HandlerAlojamientos extends JPanel implements Observer, ActionListener, ListSelectionListener{
                    
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
          private JButton paquetesTuristicos;
          private JButton destinos;
          private JLabel textoAlojamientos;
          private JList listaAlojamientos;
          private DefaultListModel modeloListaAlojamientos;
          private Sistema sistema;
          
          public HandlerAlojamientos(Sistema sistemaP) {
               
               super();
               this.setSize(900,750); 
               this.setLayout(null);
               this.sistema=sistemaP;
               modeloListaAlojamientos = new DefaultListModel();
               cargarModelo(modeloListaAlojamientos, sistema.getEmpresa().getListaAlojamientos());
               listaAlojamientos = new JList(modeloListaAlojamientos);
               listaAlojamientos.setSize(300,500);
               listaAlojamientos.setLocation(500,100);
               listaAlojamientos.addListSelectionListener(this);
               listaAlojamientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
               this.add(listaAlojamientos);
               
               
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
               guardar.addActionListener(this);
               this.add(guardar);
               
               eliminar=new JButton("Eliminar");
               eliminar.setSize(90,25);
               eliminar.setLocation(500,630);
               eliminar.addActionListener(this);
               this.add(eliminar);
               
               modificar=new JButton("Modificar");
               modificar.setSize(90,25);
               modificar.setLocation(600,630);
               modificar.addActionListener(this);
               this.add(modificar);
               
               textoAlojamientos=new JLabel("Alojamientos");
               textoAlojamientos.setSize(150,25);
               textoAlojamientos.setLocation(500,50);
               this.add(textoAlojamientos);
               
               
               
               sistema.getEmpresa().addObserver(this);
          }
          
          public void actionPerformed(ActionEvent evento){
               
               if((evento.getSource() == guardar) || (evento.getSource() == modificar) ){
                   
                    String nombreA = nombre.getText(); 
                    Alojamiento.Tipo tipoA= (Alojamiento.Tipo)tipoAlojamiento.getSelectedItem();
                    int estrellas= cantidadEstrellas.getValue();
                    Alojamiento.Pension pensionP= (Alojamiento.Pension)tipoPension.getSelectedItem();
                    if(nombreA.length() > 0){
                         
                         if(evento.getSource() == guardar){
                              Alojamiento aloja = new Alojamiento(nombreA, tipoA, estrellas, pensionP);
                              if(!sistema.getEmpresa().agregarAlojamiento(aloja)){
                                   JOptionPane.showMessageDialog(null, "ERROR: Ese Alojamiento ya existe" , "Alojamiento existente", JOptionPane.ERROR_MESSAGE);
                              }            
                         }
                         else if(evento.getSource() == modificar){
                              if (!listaAlojamientos.isSelectionEmpty()){
                                   Alojamiento aloja = (Alojamiento)listaAlojamientos.getSelectedValue();
                                   aloja.setNombre(nombreA);
                                   aloja.setTipo(tipoA);
                                   aloja.setEstrellas(estrellas);
                                   aloja.setPension(pensionP);
                              }
                             else{
                                  JOptionPane.showMessageDialog(null, "No hay Alojamiento seleccionado" , "Atención", JOptionPane.INFORMATION_MESSAGE);
                             }
                         }
                    }
               else{
                    JOptionPane.showMessageDialog(null, "ERROR: Falta el Nombre del Alojamiento" , "ERROR", JOptionPane.ERROR_MESSAGE);
               }
               }
               
               if(evento.getSource() == eliminar){
                    if (!listaAlojamientos.isSelectionEmpty()){
                         int respuesta = JOptionPane.showConfirmDialog(null, " �Eliminar este Alojamiento?", "Confirmacion", JOptionPane.WARNING_MESSAGE);
                         if (respuesta == JOptionPane.YES_OPTION){
                              Alojamiento aloja = (Alojamiento)listaAlojamientos.getSelectedValue();
                              sistema.getEmpresa().eliminarAlojamiento(aloja);
                         }
                    }else{
                         JOptionPane.showMessageDialog(null, "No hay Alojamiento seleccionado" , "Atencion", JOptionPane.INFORMATION_MESSAGE);
                    }
               }
               
               
          }
          
          
          
          public void valueChanged(ListSelectionEvent evento) {
               if (!listaAlojamientos.isSelectionEmpty()){
                    Alojamiento aloja = (Alojamiento)listaAlojamientos.getSelectedValue();
                    nombre.setText(aloja.getNombre());
                    tipoAlojamiento.setSelectedItem(aloja.getTipo());
                    cantidadEstrellas.setValue(aloja.getEstrellas());
                    tipoPension.setSelectedItem(aloja.getPension());
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
               cargarModelo(modeloListaAlojamientos, sistema.getEmpresa().getListaAlojamientos());
               listaAlojamientos.setSelectedIndex(-1);
               listaAlojamientos.setModel(modeloListaAlojamientos);
          }
     }