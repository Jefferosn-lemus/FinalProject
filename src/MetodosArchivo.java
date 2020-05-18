import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveAction;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import gt.edu.umg.programa1.ejemploFinal.Atributo;
import gt.edu.umg.programa1.ejemploFinal.Entidad;
import gt.edu.umg.programa1.ejemploFinal.TipoDato;


public class MetodosArchivo {

	
	Scanner sc = new Scanner(System.in);
	RandomAccessFile archivoP;
	RandomAccessFile Entidades;
	RandomAccessFile Atributos;
	private List<Entidades> listaEntidades = new ArrayList<>();
	private final int totalBytes = 83, bytesEntidades = 47, bytesAtributos = 43;
	private final static String formatoFecha = "dd/MM/yyyy";
	static DateFormat format = new SimpleDateFormat(formatoFecha);

	private boolean abrirArchivo() {
		boolean respuesta = false;
		try {
		Entidades = new RandomAccessFile("entidades.txt", "rw");
		Atributos = new RandomAccessFile("Atributos.txt", "rw");
		
		long longitud = Entidades.length();// capturamos la longitud del archivo Atributos
		if (longitud <= 0) {
			System.out.println("El archivo esta vacio");
			respuesta = false; // finalizar el procedimiento
		}
		if (longitud >= bytesEntidades) {
			// posicionarse al principio del archivo
			Entidades.seek(0);
			Entidades e;
			while (longitud >= bytesEntidades) {
				e = new Entidades();
				e.setIndice(Entidades.readInt());
				byte[] bNombre = new byte[30]; // leer 30 bytes para el nombre
				Entidades.read(bNombre);
				e.setBytesNombre(bNombre);
				e.setCantidad(Entidades.readInt());
				e.setBytes(Entidades.readInt());
				e.setPosicion(Entidades.readLong());
				Entidades.readByte();// leer el cambio de linea
				longitud -= bytesEntidades;
				// leer atributos
				long longitudAtributos = Atributos.length();
				if (longitudAtributos <= 0) {
					System.out.println("No hay registros");
					respuesta = false; // finalizar el procedimiento
					break;
				}
				Atributos.seek(e.getPosicion());
				Atributos a;
				longitudAtributos = e.getCantidad() * bytesAtributos;
				while (longitudAtributos >= bytesAtributos) {
					a = new Atributos();
					a.setIndice(Atributos.readInt());
					byte[] bNombreAtributo = new byte[30]; // leer 30 bytes para el nombre
					Atributos.read(bNombreAtributo);
					a.setBytesNombre(bNombreAtributo);
					a.setValorTipoDato(Atributos.readInt());
					a.setLongitud(Atributos.readInt());
					a.setNombreTipoDato();
					Atributos.readByte();// leer el cambio de linea
					e.setAtributos(a);
					longitudAtributos -= bytesAtributos;
				}
				listaEntidades.add(e);
			}
			if (listaEntidades.size() > 0) {
				respuesta = true;
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return respuesta;
	}

	private boolean IngresarEntidad() {
		boolean resultado = false;
		String auxNombre;
		int longitud = 0;
		sc.nextLine();
		try {
			Entidades entidad = new Entidades();
			entidad.setIndice(listaEntidades.size() + 1);
			do {
				System.out.println("Ingrese el nombre de la entidad");
				auxNombre = sc.nextLine();
				longitud = auxNombre.length();
				if (longitud < 2 || longitud > 30) {
					JOptionPane.showMessageDialog(null,"La Cantidad de los caracteres no es correcta (3 - 30)");
				} else {
					if (auxNombre.contains(" ")) {
						JOptionPane.showMessageDialog(null,"El nombre no puede contener espacios, sustituya por guion bajo (underscore)");
						longitud = 0;
					}
				}
			} while (longitud < 2 || longitud > 30);
			entidad.setNombre(auxNombre);
			System.out.println("Atributos de la entidad");
			int bndDetener = 0;
			do {
				Atributos atributo = new Atributos();
				atributo.setIndice(entidad.getIndice());
				longitud = 0;
				System.out.println("Escriba el nombre del atributo no. " + (entidad.getCantidad() + 1));
				do {
					auxNombre = sc.nextLine();
					longitud = auxNombre.length();
					if (longitud < 2 || longitud > 30) {
						JOptionPane.showMessageDialog(null,"La Cantidad de los caracteres no es correcta (3 - 30)");
					} else {
						if (auxNombre.contains(" ")) {
							JOptionPane.showInternalMessageDialog(null,"El nombre no puede contener espacios, sustituya por guion bajo (underscore)");
							longitud = 0;
						}
					}
				} while (longitud < 2 || longitud > 30);
				atributo.setNombre(auxNombre);
				int valor = JOptionPane.showConfirmDialog(null,"Seleccione el tipo de dato: "
				+"\n"+TipoDato.INT.getValue() + " .......... " + TipoDato.INT.name()
				+"\n"+TipoDato.LONG.getValue() + " .......... " + TipoDato.LONG.name()
				+"\n"+TipoDato.STRING.getValue() + " .......... " + TipoDato.STRING.name()
				+"\n"+TipoDato.DOUBLE.getValue() + " .......... " + TipoDato.DOUBLE.name()
				+"\n"+TipoDato.FLOAT.getValue() + " .......... " + TipoDato.FLOAT.name()
				+"\n"+TipoDato.DATE.getValue() + " .......... " + TipoDato.DATE.name()
				+"\n"+TipoDato.CHAR.getValue() + " .......... " + TipoDato.CHAR.name());
				atributo.setValorTipoDato(valor);
				if (atributo.isRequiereLongitud()) {
					System.out.println("Ingrese la longitud");
					atributo.setLongitud(sc.nextInt());
				} else {
					atributo.setLongitud(0);
				}
				atributo.setNombreTipoDato();
				entidad.setAtributos(atributo);
				System.out.println("Desea agregar otro atributo presione cualquier numero, de lo contrario 0");
				bndDetener = sc.nextInt();
			} while (bndDetener != 0);
			System.out.println("Los datos a registrar son: ");
			mostrarEntidad(entidad);
			System.out.println("Presione 1 para guardar 0 para cancelar");
			longitud = sc.nextInt();
			if (longitud == 1) {
				// primero guardar atributos
				// establecer la posicion inicial donde se va a guardar
				entidad.setPosicion(Atributos.length());
				Atributos.seek(Atributos.length());// calcular la longitud el archivo
				for (Atributos atributo : entidad.getAtributos()) {
					Atributos.writeInt(atributo.getIndice());
					Atributos.write(atributo.getBytesNombre());
					Atributos.writeInt(atributo.getValorTipoDato());
					Atributos.writeInt(atributo.getLongitud());
					Atributos.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
				}
				// guardar la entidad
				Entidades.writeInt(entidad.getIndice());
				Entidades.write(entidad.getBytesNombre());
				Entidades.writeInt(entidad.getCantidad());
				Entidades.writeInt(entidad.getBytes());
				Entidades.writeLong(entidad.getPosicion());
				Entidades.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
				listaEntidades.add(entidad);
				resultado = true;
			} else {
				JOptionPane.showMessageDialog(null, "Entidad no guardada");
				resultado = false;
			}
			System.out.println("Presione una tecla para continuar...");
			System.in.read();
		} catch (Exception e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;
	}

	private void mostrarEntidad(Entidades entidad) {
		System.out.println("Indice: " + entidad.getIndice());
		System.out.println("Nombre: " + entidad.getNombre());
		System.out.println("Cantidad de atributos: " + entidad.getCantidad());
		System.out.println("Atributos:");
		int i = 1;
		for (Atributos atributo : entidad.getAtributos()) {
			System.out.println("\tNo. " + i);
			System.out.println("\tNombre: " + atributo.getNombre());
			System.out.println("\tTipo de dato: " + atributo.getNombreTipoDato());
			if (atributo.isRequiereLongitud()) {
				System.out.println("\tLongitud: " + atributo.getLongitud());
			}
			i++;
		}
	}

	private boolean borrarArchivos() {
		boolean res = false;
		try {
			File file;
			for (Entidades entidad : listaEntidades) {
				file = new File(entidad.getNombre().trim() + ".dat");
				if (file.exists()) {
					file.delete();
				}
				file = null;
			}
			file = new File("Atributos.txt");
			if (file.exists()) {
				file.delete();
			}
			file = null;
			file = new File("entidades.txt");
			if (file.exists()) {
				file.delete();
			}
			file = null;
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private void iniciar(int indice) {
		int opcion = 0;
		String nombreFichero = "";
		try {
			Entidades entidad = null;
			for (Entidades e : listaEntidades) {
				if (indice == e.getIndice()) {
					nombreFichero = (e.getNombre()+ ".data");
					entidad = e;
					break;
				}
			}
			archivoP= new RandomAccessFile( nombreFichero, "rw");
			System.out.println("Bienvenido (a)");
			Atributos a = entidad.getAtributos().get(0);
			do {
				try {
					System.out.println("Seleccione su opcion");
					System.out.println("1.\t\tAgregar");
					System.out.println("2.\t\tListar");
					System.out.println("3.\t\tBuscar");
					System.out.println("4.\t\tModificar");
					System.out.println("0.\t\tRegresar al menu anterior");
					opcion = sc.nextInt();
					switch (opcion) {
					case 0:
						System.out.println("");
						break;
					case 1:
						grabarRegistro(entidad);
						break;
					case 2:
						listarRegistros(entidad);
						break;
					case 3:
						System.out.println("Se hara la busqueda en la primera columna ");
						System.out.println("Ingrese " + a.getNombre().trim() + " a buscar");
						// sc.nextLine();
						// encontrarRegistro(carne);
						break;
					case 4:
						System.out.println("Ingrese el carne a modificar: ");
						// carne = sc.nextInt();
						// sc.nextLine();
						// modificarRegistro(carne);
						break;
					default:
						System.out.println("Opcion no valida");
						break;
					}
				} catch (Exception e) { // capturar cualquier excepcion que ocurra
					System.out.println("Error: " + e.getMessage());
				}
			} while (opcion != 0);
			archivoP.close();
		} catch (Exception e) { // capturar cualquier excepcion que ocurra
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void cerrarArchivo() throws Exception {// cerramos nuestro archivo crados 
		if (archivoP != null && Entidades != null && Atributos != null) {
			archivoP.close();
			Entidades.close();
			Atributos.close();
		}
	}
//}
//JFileChooser direccion = new JFileChooser();
//direccion.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//// indica cual fue la accion de usuario sobre el jfilechoose
//archivoP = new RandomAccessFile(direccion.getSelectedFile(), "rw"); //creamos nuestro archivo Random que sera del tipo lectura y escritura (rw)
