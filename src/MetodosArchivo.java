import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveAction;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import gt.edu.umg.programa1.ejemploFinal.ArchivoDirecto;
import gt.edu.umg.programa1.ejemploFinal.Atributo;
import gt.edu.umg.programa1.ejemploFinal.Entidad;

public class MetodosArchivo {

	
	Scanner sc = new Scanner(System.in);
	RandomAccessFile archivoP;
	RandomAccessFile Entidades;
	RandomAccessFile Atributos;
	private List<Entidades> listaEntidades = new ArrayList<>();
	private final int totalBytes = 83, bytesEntidades = 47, bytesAtributos = 43;
	private final static String formatoFecha = "dd/MM/yyyy";
	static DateFormat format = new SimpleDateFormat(formatoFecha);

//	private boolean abrirArchivo() {
//		boolean respuesta = false;
//		try {
//		Entidades = new RandomAccessFile("entidades.txt", "rw");
//		Atributos = new RandomAccessFile("Atributos.txt", "rw");
//		
//		long longitud = Entidades.length();// capturamos la longitud del archivo Atributos
//		if (longitud <= 0) {
//			System.out.println("El archivo esta vacio");
//			respuesta = false; // finalizar el procedimiento
//		}
//		if (longitud >= bytesEntidades) {
//			// posicionarse al principio del archivo
//			Entidades.seek(0);
//			Entidades e;
//			while (longitud >= bytesEntidades) {
//				e = new Entidades();
//				e.setIndice(Entidades.readInt());
//				byte[] bNombre = new byte[30]; // leer 30 bytes para el nombre
//				Entidades.read(bNombre);
//				e.setBytesNombre(bNombre);
//				e.setCantidad(Entidades.readInt());
//				e.setBytes(Entidades.readInt());
//				e.setPosicion(Entidades.readLong());
//				Entidades.readByte();// leer el cambio de linea
//				longitud -= bytesEntidades;
//				// leer atributos
//				long longitudAtributos = Atributos.length();
//				if (longitudAtributos <= 0) {
//					System.out.println("No hay registros");
//					respuesta = false; // finalizar el procedimiento
//					break;
//				}
//				Atributos.seek(e.getPosicion());
//				Atributos a;
//				longitudAtributos = e.getCantidad() * bytesAtributos;
//				while (longitudAtributos >= bytesAtributos) {
//					a = new Atributos();
//					a.setIndice(Atributos.readInt());
//					byte[] bNombreAtributo = new byte[30]; // leer 30 bytes para el nombre
//					Atributos.read(bNombreAtributo);
//					a.setBytesNombre(bNombreAtributo);
//					a.setValorTipoDato(Atributos.readInt());
//					a.setLongitud(Atributos.readInt());
//					a.setNombreTipoDato();
//					Atributos.readByte();// leer el cambio de linea
//					e.setAtributos(a);
//					longitudAtributos -= bytesAtributos;
//				}
//				listaEntidades.add(e);
//			}
//			if (listaEntidades.size() > 0) {
//				respuesta = true;
//			}
//		}
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//		return respuesta;
//	}
//
//	private boolean IngresarEntidad() {
//		boolean resultado = false;
//		String auxNombre;
//		int longitud = 0;
//		sc.nextLine();
//		try {
//			Entidades entidad = new Entidades();
//			entidad.setIndice(listaEntidades.size() + 1);
//			do {
//				auxNombre = JOptionPane.showInputDialog(null,"Ingrese el nombre de la entidad");
//				longitud = auxNombre.length();
//				if (longitud < 2 || longitud > 30) {
//					JOptionPane.showMessageDialog(null,"La Cantidad de los caracteres no es correcta (3 - 30)");
//				} else {
//					if (auxNombre.contains(" ")) {
//						JOptionPane.showMessageDialog(null,"El nombre no puede contener espacios, sustituya por guion bajo (underscore)");
//						longitud = 0;
//					}
//				}
//			} while (longitud < 2 || longitud > 30);
//			entidad.setNombre(auxNombre);//
//			System.out.println("Atributos de la entidad");
//			int bndDetener = 0;
//			do {
//				Atributos atributo = new Atributos();
//				atributo.setIndice(entidad.getIndice());
//				longitud = 0;
//				auxNombre = JOptionPane.showInputDialog(null,"Escriba el nombre del atributo no. " + (entidad.getCantidad() + 1));
//				do {
//					
//					longitud = auxNombre.length();
//					if (longitud < 2 || longitud > 30) {
//						JOptionPane.showMessageDialog(null,"La Cantidad de los caracteres no es correcta (3 - 30)");
//					} else {
//						if (auxNombre.contains(" ")) {
//							JOptionPane.showMessageDialog(null,"El nombre no puede contener espacios, sustituya por guion bajo (underscore)");
//							longitud = 0;
//						}
//					}
//				} while (longitud < 2 || longitud > 30);
//				atributo.setNombre(auxNombre);
//				int valor = Integer.parseInt(JOptionPane.showInputDialog(null, "Seleccione el tipo de dato: "
//				+"\n"+TipoDato.INT.getValue() + " .......... " + TipoDato.INT.name()
//				+"\n"+TipoDato.LONG.getValue() + " .......... " + TipoDato.LONG.name()
//				+"\n"+TipoDato.STRING.getValue() + " .......... " + TipoDato.STRING.name()
//				+"\n"+TipoDato.DOUBLE.getValue() + " .......... " + TipoDato.DOUBLE.name()
//				+"\n"+TipoDato.FLOAT.getValue() + " .......... " + TipoDato.FLOAT.name()
//				+"\n"+TipoDato.DATE.getValue() + " .......... " + TipoDato.DATE.name()
//				+"\n"+TipoDato.CHAR.getValue() + " .......... " + TipoDato.CHAR.name()));
//				atributo.setValorTipoDato(valor);
//				if (atributo.isRequiereLongitud()) {
//					int lg = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la longitud"));
//					atributo.setLongitud(lg);
//				} else {
//					atributo.setLongitud(0);
//				}
//				atributo.setNombreTipoDato();
//				entidad.setAtributos(atributo);
//				bndDetener = Integer.parseInt(JOptionPane.showInputDialog(null," Si Desea agregar otro atributo presione cualquier numero,"
//						+ " de lo contrario 0"));
//			} while (bndDetener != 0);
//			System.out.println("Los datos a registrar son: ");
//			//mostrarEntidad(entidad);
//			longitud = Integer.parseInt(JOptionPane.showInputDialog(null,"Presione 1 para guardar 0 para cancelar"));
//			if (longitud == 1) {
//				// primero guardar atributos
//				// establecer la posicion inicial donde se va a guardar
//				entidad.setPosicion(Atributos.length());
//				Atributos.seek(Atributos.length());// calcular la longitud el archivo
//				for (Atributos atributo : entidad.getAtributos()) {
//					Atributos.writeInt(atributo.getIndice());
//					Atributos.write(atributo.getBytesNombre());
//					Atributos.writeInt(atributo.getValorTipoDato());
//					Atributos.writeInt(atributo.getLongitud());
//					Atributos.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
//				}
//				// guardar la entidad
//				Entidades.writeInt(entidad.getIndice());
//				Entidades.write(entidad.getBytesNombre());
//				Entidades.writeInt(entidad.getCantidad());
//				Entidades.writeInt(entidad.getBytes());
//				Entidades.writeLong(entidad.getPosicion());
//				Entidades.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
//				listaEntidades.add(entidad);
//				resultado = true;
//			} else {
//				JOptionPane.showMessageDialog(null, "Entidad no guardada");
//				resultado = false;
//			}
//			System.out.println("Presione una tecla para continuar...");
//			System.in.read();
//		} catch (Exception e) {
//			resultado = false;
//			e.printStackTrace();
//		}
//		return resultado;
//	}
//
//	private void mostrarEntidad(Entidades entidad) {
//		System.out.println("Indice: " + entidad.getIndice());
//		System.out.println("Nombre: " + entidad.getNombre());
//		System.out.println("Cantidad de atributos: " + entidad.getCantidad());
//		System.out.println("Atributos:");
//		int i = 1;
//		for (Atributos atributo : entidad.getAtributos()) {
//			System.out.println("\tNo. " + i);
//			System.out.println("\tNombre: " + atributo.getNombre());
//			System.out.println("\tTipo de dato: " + atributo.getNombreTipoDato());
//			if (atributo.isRequiereLongitud()) {
//				System.out.println("\tLongitud: " + atributo.getLongitud());
//			}
//			i++;
//		}
//	}
//
//	private void modificarEntidad() {
//		try {
//			int indice = 0;
//			while (indice < 1 || indice > listaEntidades.size()) {
//				for (Entidades entidad : listaEntidades) {
//					System.out.println(entidad.getIndice() + " ...... " + entidad.getNombre());
//				}
//				System.out.println("Seleccione la entidad que desea modificar");
//				indice = sc.nextInt();
//				sc.nextLine();
//			}
//			Entidades entidad = new Entidades();
//			for (Entidades e : listaEntidades) {
//				if (indice == e.getIndice()) {
//					entidad = e;
//					break;
//				}
//			}
//			String nombreFichero = formarNombreFichero(entidad.getNombre());
//			archivoP = new RandomAccessFile(nombreFichero, "rw");
//			long longitudDatos = archivoP.length();
//			
//			if (longitudDatos >0) {
//				System.out.println("No es posible modificar la entidad debido a que ya tiene datos asociados");
//			} else {
//				// bandera para verificar que el registro fue encontrado
//				boolean bndEncontrado = false, bndModificado = false;
//				// posicionarse al principio del archivo
//				Entidades.seek(0);
//				long longitud = Entidades.length();
//				int registros = 0, salir = 0, i;
//				Entidades e;
//				byte[] tmpBytes;
//				archivoP.close();
//				while (longitud > totalBytes) {
//					e = new Entidades();
//					e.setIndice(Entidades.readInt());
//					tmpBytes = new byte[30];
//					Entidades.read(tmpBytes);
//					e.setBytesNombre(tmpBytes);
//					e.setCantidad(Entidades.readInt());
//					e.setBytes(Entidades.readInt());
//					e.setPosicion(Entidades.readLong());
//					if (entidad.getIndice() == e.getIndice()) {
//						System.out.println("Si no desea modificar el campo presione enter");
//						System.out.println("Ingrese el nombre");
//						String tmpStr;
//						sc.nextLine();
//						int len = 0;
//						long posicion;
//						do {
//							tmpStr = sc.nextLine();
//							len = tmpStr.length();
//							if (len == 1 || len > 30) {
//								System.out.println("La longitud del nombre no es valida [2 - 30]");
//							}
//						} while (len == 1 || len > 30);
//						if (len > 0) {
//							e.setNombre(tmpStr);
//							posicion = registros * totalBytes;
//							archivoP.seek(posicion);
//							 // moverse despues del indice (int = 4 bytes)
//							// grabar el cambio
//							archivoP.write(e.getBytesNombre());
//							bndModificado = true;
//						}
//						for (Entidades e1 : listaEntidades) {
//							System.out.println("Modificando entidad" + e);
//							System.out.println(e1.getNombre().trim());
//						}
//						
//						break;
//					}
//					registros++;
//					// restar los bytes del registro leido
//					longitud -= totalBytes;
//				}
//			}
//		} catch (Exception e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//	}
//	
//	private boolean borrarArchivos() {
//		boolean res = false;
//		try {
//			File file;
//			for (Entidades entidad : listaEntidades) {
//				file = new File(entidad.getNombre().trim() + ".dat");
//				if (file.exists()) {
//					file.delete();
//				}
//				file = null;
//			}
//			file = new File("Atributos.txt");
//			if (file.exists()) {
//				file.delete();
//			}
//			file = null;
//			file = new File("entidades.txt");
//			if (file.exists()) {
//				file.delete();
//			}
//			file = null;
//			res = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return res;
//	}
//
//	private void iniciar(int indice) {
//		int opcion = 0;
//		String nombreFichero = "";
//		try {
//			Entidades entidad = null;
//			for (Entidades e : listaEntidades) {
//				if (indice == e.getIndice()) {
//					nombreFichero = (e.getNombre()+ ".data");
//					entidad = e;
//					break;
//				}
//			}
//			archivoP= new RandomAccessFile( nombreFichero, "rw");
//			System.out.println("Bienvenido (a)");
//			Atributos a = entidad.getAtributos().get(0);
//			do {
//				try {
//					System.out.println("Seleccione su opcion");
//					System.out.println("1.\t\tAgregar");
//					System.out.println("2.\t\tListar");
//					System.out.println("3.\t\tBuscar");
//					System.out.println("4.\t\tModificar");
//					System.out.println("0.\t\tRegresar al menu anterior");
//					opcion = sc.nextInt();
//					switch (opcion) {
//					case 0:
//						System.out.println("");
//						break;
//					case 1:
//						grabarRegistro(entidad);
//						break;
//					case 2:
//						listarRegistros(entidad);
//						break;
//					case 3:
//						System.out.println("Se hara la busqueda en la primera columna ");
//						System.out.println("Ingrese " + a.getNombre().trim() + " a buscar");
//						// sc.nextLine();
//						// encontrarRegistro(carne);
//						break;
//					case 4:
//						System.out.println("Ingrese el carne a modificar: ");
//						// carne = sc.nextInt();
//						// sc.nextLine();
//						// modificarRegistro(carne);
//						break;
//					default:
//						System.out.println("Opcion no valida");
//						break;
//					}
//				} catch (Exception e) { // capturar cualquier excepcion que ocurra
//					System.out.println("Error: " + e.getMessage());
//				}
//			} while (opcion != 0);
//			archivoP.close();
//		} catch (Exception e) { // capturar cualquier excepcion que ocurra
//			System.out.println("Error: " + e.getMessage());
//		}
//	}
//	
//	private void menuDefinicion(boolean mostrarAgregarRegistro) throws Exception {
//		int opcion = 1;
//		while (opcion != 0) {
//			opcion = Integer.parseInt(JOptionPane.showInputDialog(null,"Elija su opcion"
//			+"\n1 ........ Agregar entidad"
//			+"\n2 ........ Modificar entidad"
//			+"\n3 ........ Listar entidades"
//			+"\n4 ........ Agregar registros"
//			+"\n5 ........ Borrar bases de datos"
//			+"\n0 ........ Salir"));
//			switch (opcion) {
//			case 0:
//				JOptionPane.showMessageDialog(null,"Gracias por usar nuestra aplicacion");
//				break;
//			case 1:
//				if (IngresarEntidad()) {
//					JOptionPane.showMessageDialog(null,"Entidad agregada con exito");
//					mostrarAgregarRegistro = true;
//				}
//				break;
//			case 2:
//				modificarEntidad();
//				break;
//			case 3:
//				FrmGUI fr = new FrmGUI();
//				
//				if (listaEntidades.size() > 0) {
//					int tmpInt = 0;
//					tmpInt = Integer.parseInt(JOptionPane.showInputDialog(null,"Desea imprimir los detalles?"
//							+ " Si, presione 1. No, presione 0?"));
//					if (tmpInt == 1) {
//						
//						for (Entidades entidad : listaEntidades) {
//							mostrarEntidad(entidad);
//						}
//						
//					} else {
//						for (Entidades entidad : listaEntidades) {
//							System.out.println("Indice: " + entidad.getIndice());
//							System.out.println("Nombre: " + entidad.getNombre());
//							System.out.println("Cantidad de atributos: " + entidad.getCantidad());
//						}
//					}
//				} else {
//					System.out.println("No hay entidades registradas");
//				}
//				fr.inicio();
//				System.out.println("Presione una tecla para continuar...");
//				System.in.read();
//				break;
//			case 4:
//				int indice = 0;
//				while (indice < 1 || indice > listaEntidades.size()) {
//					for (Entidades entidad : listaEntidades) {
//						System.out.println(entidad.getIndice() + " ...... " + entidad.getNombre());
//					}
//					System.out.println("Seleccione la entidad que desea trabajar");
//					indice = sc.nextInt();
//				}
//				iniciar(indice);
//				break;
//			case 5:
//				int confirmar = 0;
//				System.out.println(
//						"Esta seguro de borrar los archivos de base de datos, presione 1 de lo contrario cualquier numero para cancelar? Esta accion no se podra reversar");
//				confirmar = sc.nextInt();
//				if (confirmar == 1) {
//					cerrarArchivo();
//					if (borrarArchivos()) {
//						listaEntidades = null;
//						listaEntidades = new ArrayList<>();
//						mostrarAgregarRegistro = false;
//						System.out.println("Archivos borrados");
//					}
//				}
//				break;
//			default:
//				System.out.println("Opcion no valida");
//				break;
//			}
//		}
//	}
//	
//	
//	public void cerrarArchivo() throws Exception {// cerramos nuestro archivo crados 
//		if (archivoP != null && Entidades != null && Atributos != null) {
//			archivoP.close();
//			Entidades.close();
//			Atributos.close();
//		}
//	}
//
//	private boolean grabarRegistro(Entidades entidad) {
//		boolean resultado = false;
//		try {
//			// posicionarse al final para grabar
//			archivoP.seek(archivoP.length());
//			boolean valido;
//			byte[] bytesString;
//			String tmpString = "";
//			for (Atributos atributo : entidad.getAtributos()) {
//				valido = false;
//				System.out.println("Ingrese " + atributo.getNombre().trim());
//				while (!valido) {
//					try {
//						switch (atributo.getTipoDato()) {
//						case INT:
//							int tmpInt = sc.nextInt();
//							archivoP.writeInt(tmpInt);
//							sc.nextLine();
//							break;
//						case LONG:
//							long tmpLong = sc.nextLong();
//							archivoP.writeLong(tmpLong);
//							break;
//						case STRING:
//							int longitud = 0;
//							do {
//								tmpString = sc.nextLine();
//								longitud = tmpString.length();
//								if (longitud <= 1 || longitud > atributo.getLongitud()) {
//									System.out.println("La longitud de " + atributo.getNombre().trim()
//											+ " no es valida [1 - " + atributo.getLongitud() + "]");
//								}
//							} while (longitud <= 0 || longitud > atributo.getLongitud());
//							// arreglo de bytes de longitud segun definida
//							bytesString = new byte[atributo.getLongitud()];
//							// convertir caracter por caracter a byte y agregarlo al arreglo
//							for (int i = 0; i < tmpString.length(); i++) {
//								bytesString[i] = (byte) tmpString.charAt(i);
//							}
//							archivoP.write(bytesString);
//							break;
//						case DOUBLE:
//							double tmpDouble = sc.nextDouble();
//							archivoP.writeDouble(tmpDouble);
//							break;
//						case FLOAT:
//							float tmpFloat = sc.nextFloat();
//							archivoP.writeFloat(tmpFloat);
//							break;
//						case DATE:
//							Date date = null;
//							tmpString = "";
//							while (date == null) {
//								System.out.println("Formato de fecha: " + formatoFecha);
//								tmpString = sc.nextLine();
//								date = strintToDate(tmpString);
//							}
//							bytesString = new byte[atributo.getBytes()];
//							for (int i = 0; i < tmpString.length(); i++) {
//								bytesString[i] = (byte) tmpString.charAt(i);
//							}
//							archivoP.write(bytesString);
//							break;
//						case CHAR:
//							do {
//								tmpString = sc.nextLine();
//								longitud = tmpString.length();
//								if (longitud < 1 || longitud > 1) {
//									System.out.println("Solo se permite un caracter");
//								}
//							} while (longitud < 1 || longitud > 1);
//							byte caracter = (byte) tmpString.charAt(0);
//							archivoP.writeByte(caracter);
//							break;
//						}
//						valido = true;
//					} catch (Exception e) {
//						System.out.println(
//								"Error " + e.getMessage() + " al capturar tipo de dato, vuelva a ingresar el valor: ");
//						sc.nextLine();
//					}
//				} // end while
//			} // end for
//			archivoP.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
//			resultado = true;
//		} catch (Exception e) {
//			resultado = false;
//			System.out.println("Error al agregar el registro " + e.getMessage());
//		}
//		return resultado;
//	}
//
//	public void listarRegistros(Entidades entidad) {
//		try {
//			long longitud = archivoP.length();
//			if (longitud <= 0) {
//				System.out.println("No hay registros");
//				return; // finalizar el procedimiento
//			}
//			// posicionarse al principio del archivo
//			archivoP.seek(0);
//			byte[] tmpArrayByte;
//			String linea = "";
//			for (Atributos atributo : entidad.getAtributos()) {
//				linea += atributo.getNombre().toString().trim() + "\t\t";
//			}
//			System.out.println(linea);
//			while (longitud >= entidad.getBytes()) {
//				linea = "";
//				for (Atributos atributo : entidad.getAtributos()) {
//					switch (atributo.getTipoDato()) {
//					case INT:
//						int tmpInt = archivoP.readInt();
//						linea += String.valueOf(tmpInt) + "\t\t";
//						break;
//					case LONG:
//						long tmpLong = archivoP.readLong();
//						linea += String.valueOf(tmpLong) + "\t\t";
//						break;
//					case STRING:
//						tmpArrayByte = new byte[atributo.getLongitud()];
//						archivoP.read(tmpArrayByte);
//						String tmpString = new String(tmpArrayByte);
//						linea += tmpString.trim() + "\t\t";
//						break;
//					case DOUBLE:
//						double tmpDouble = archivoP.readDouble();
//						linea += String.valueOf(tmpDouble) + "\t\t";
//						break;
//					case FLOAT:
//						float tmpFloat = archivoP.readFloat();
//						linea += String.valueOf(tmpFloat) + "\t\t";
//						break;
//					case DATE:
//						tmpArrayByte = new byte[atributo.getBytes()];
//						archivoP.read(tmpArrayByte);
//						tmpString = new String(tmpArrayByte);
//						linea += tmpString.trim() + "\t\t";
//						break;
//					case CHAR:
//						char tmpChar = (char) archivoP.readByte();
//						linea += tmpChar + "\t\t";
//						break;
//					}
//				}
//				archivoP.readByte();// leer el cambio de linea
//				// restar los bytes del registro leido
//				longitud -= entidad.getBytes();
//				System.out.println(linea + " "+longitud);
//			}
//		} catch (Exception e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//	}
//
//	public Date strintToDate(String strFecha) {
//		Date date = null;
//		try {
//			date = format.parse(strFecha);
//		} catch (Exception e) {
//			date = null;
//			System.out.println("Error en fecha: " + e.getMessage());
//		}
//		return date;
//	}
//
//	public String dateToString(Date date) {
//		String strFecha;
//		strFecha = format.format(date);
//		return strFecha;
//	}
//
//	private String formarNombreFichero(String nombre) {
//		return nombre.trim() + ".dat";
//	}
//
//	public static void main(String[] args) throws Exception {
//		MetodosArchivo ad = new MetodosArchivo();
//		if (ad.abrirArchivo()) {
//			ad.menuDefinicion(true);
//		} else {
//			ad.menuDefinicion(false);
//		}
//		System.exit(0); // finalize application
//	}
//
//	
//
//
//}

//}
//JFileChooser direccion = new JFileChooser();
//direccion.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//// indica cual fue la accion de usuario sobre el jfilechoose
//archivoP = new RandomAccessFile(direccion.getSelectedFile(), "rw"); //creamos nuestro archivo Random que sera del tipo lectura y escritura (rw)
